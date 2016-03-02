package com.secondhandbook.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.secondhandbook.net.httpMethod.HttpMethod;
import com.secondhandbook.util.Config;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;

public class BaseNetConnection {

	private Context context = null;

	public BaseNetConnection(Context context) {
		this.context = context;
	}

	/**
	 * �������ӻ���
	 * 
	 * @param url
	 *            ���������ص�ַ
	 * @param httpMethod
	 *            ���󷽷�
	 * @param successCallback
	 *            �ɹ�ʱ��ص��ķ���
	 * @param failCallback
	 *            ʧ��ʱ��Ļص�����
	 * @param action
	 *            �������
	 * @param identify
	 *            �û���ݱ�ʶ
	 * @param jsonParams
	 *            ���������json�ַ���
	 */

	public BaseNetConnection(Context contex, final String url,
			final HttpMethod httpMethod, final SuccessCallback successCallback,
			final FailCallback failCallback, final int action, final String jsonParams) {

		this.context = contex;

		new AsyncTask<String, Void, String>() {

			@Override
			protected String doInBackground(String... params) {

				StringBuffer jsonResult = new StringBuffer();
				URLConnection conn = null;

				String sessionId = BaseNetConnection.this
						.get_cashed_sessionId();

				try {
					switch (httpMethod) {
					case POST:

						StringBuffer postParams = new StringBuffer();
						postParams.append(Config.KEY_ACTION).append("=")
								.append(action).append("&")
								.append(Config.KEY_JSON_PARAMS).append("=")
								.append(jsonParams);

						conn = new URL(url).openConnection();
						conn.setRequestProperty("connection", "Keep-Alive");
						if (!sessionId
								.equals(BaseNetConnection.this.DEFAULT_SESSION_ID) && sessionId != null) {

							conn.setRequestProperty("Cookie", "JSESSIONID="
									+ sessionId);
						}
						conn.setDoInput(true);
						conn.setDoOutput(true);
						conn.connect();

						// ���������д��ȥ
						BufferedWriter bw = new BufferedWriter(
								new OutputStreamWriter(conn.getOutputStream(),
										Config.CHARSET));
						bw.write(postParams.toString());
						bw.flush();
						bw.close();

						postParams = null;

						break;

					case GET:
						if (sessionId
								.equals(BaseNetConnection.this.DEFAULT_SESSION_ID)) {
							conn = new URL(url + "?" + Config.KEY_ACTION + "="
									+ action + "&" + Config.KEY_JSON_PARAMS
									+ "=" + jsonParams).openConnection();
						} else {

							conn = new URL(url + "?" + Config.KEY_ACTION + "="
									+ action + "&" +Config.KEY_JSON_PARAMS
									+ "=" + jsonParams + ";" + "JSESSIONID="
									+ sessionId).openConnection();
						}
						conn.setRequestProperty("connection", "Keep-Alive");
						conn.connect();
						break;

					}

					// ��ȡ���������صĽ��
					BufferedReader br = new BufferedReader(
							new InputStreamReader(conn.getInputStream(),
									Config.CHARSET));
					String line = "";
					while ((line = br.readLine()) != null) {
						jsonResult.append(line);
					}

					br.close();

					return jsonResult.toString();

				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				return null;
			}

			@Override
			protected void onPostExecute(String result) {

				if (result != null) {
					if (successCallback != null) {

						// ���������ص�json���β������sessionId
						String[] results = result.split("#");
						if (results.length > 1) {
							result = results[0];
							BaseNetConnection.this.cash_sessionId(results[1]);
						}
						successCallback.onSuccess(result);
					}

				} else {
					if (failCallback != null) {
						failCallback.onFail(Config.STATUS_FAIL,
								Config.STATUS_FAIL_REASON_OTHER);
					}
				}
			}
		}.execute();

	}

	// �ɹ��ص�����
	public interface SuccessCallback {

		void onSuccess(String result);
	}

	// ʧ�ܻص�����
	public interface FailCallback {
		void onFail(int status, int reason);
	}

	private String SHAREDPREFERENCE_SESSION = "SharedPreferences_sessionId";
	private String SESSION_ID = "sessionId";
	private String DEFAULT_SESSION_ID = "-1";

	/**
	 * �����������ص�sessionId���ڱ���
	 * 
	 * @param sessionId
	 *            sessionId
	 */
	public void cash_sessionId(String sessionId) {

		SharedPreferences sd = context.getSharedPreferences(
				this.SHAREDPREFERENCE_SESSION, Context.MODE_PRIVATE);
		Editor e = sd.edit();
		e.putString(this.SESSION_ID, sessionId);
		e.commit();
		
	}

	/**
	 * ��ȡ���ڱ��ص�sessionId
	 * 
	 * @return
	 */
	private String get_cashed_sessionId() {
		SharedPreferences sd = context.getSharedPreferences(
				this.SHAREDPREFERENCE_SESSION, Context.MODE_PRIVATE);
		return sd.getString(this.SESSION_ID, this.DEFAULT_SESSION_ID);
	}

}
