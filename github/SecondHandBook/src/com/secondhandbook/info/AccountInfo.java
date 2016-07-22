package com.secondhandbook.info;
/**
 * @author MingLei Jia
 */
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import com.secondhandbook.net.Netconnection;
import com.secondhandbook.net.httpMethod.HttpMethod;
import com.secondhandbook.util.Config;
import com.secondhandbook.util.JsonTool;

import android.content.Context;

public class AccountInfo {

	public static final String MSGCODE = "msgCode";
	public static final String NICKNAME = "nickName";
	public static final String PHONE = "phone";
	public static final String PHONE_MD5 = "phoneMd5";
	public static final String PASSWORD_MD5 = "passwordMd5";
	//public static final String MSG_CODE = "msgCode";
	public static final String USER_ID = "userId";
	public static final String TOKEN = "token";
	public static final String EMAIL = "email";

	// ��¼ʱ��һЩ״̬��
	public static final int STATUS_LOGIN_SUCCESS = 1;
	public static final int STATUS_LOGIN_FAIL = 0;
	public static final int FAIL_REASON_ACCOUNT_FAIL = 1;
	public static final int FAIL_REASON_OTHER = 0;

	// ��¼ʱ��һЩ״̬��
	public static final int STATUS_REGISTER_SUCCESS = 1;
	public static final int STATUS_REGISTER_FAIL = 0;
	public static final int FAIL_REASON_MSG_CODE_NOT_CORRECT = -1;
	public static final int FAIL_REASON_PHONE_REGISTERED = 1;

	// ��������ʱ��һЩ״̬��
	public static final int STATUS_RESET_PASSWORD_SUCCESS = 1;
	public static final int STATUS_RESET_PASSWORD_FAIL = 0;
	public static final int FAIL_REASON_PHONE_NOT_REGISTERED = 1;

	private Context context = null;
	public AccountInfo(Context context) {
		this.context = context;
	}

	/**
	 * ע���˻�
	 *
	 * @param nickName
	 *            �˻��ǳ�
	 * @param phone
	 *            ע���ֻ���
	 * @param passwordMd5
	 *            ע������
	 *            
	 * @param action	�������
	 * 
	 * @param successCallback	�ɹ�ʱ��ص��ķ���
	 * 
	 * @param failCallback	ʧ��ʱ��ص��ķ���
	 * @throws JSONException
	 */
	public void register(String msgcode, String nickName, String phone,
			String passwordMd5, String email, int action,
			final SuccessCallback successCallback,
			final FailCallback failCallback) throws JSONException {

		// ���������ת����json�ַ���
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(AccountInfo.MSGCODE, msgcode);
		map.put(AccountInfo.NICKNAME, nickName);
		map.put(AccountInfo.PHONE, phone);
		map.put(AccountInfo.PASSWORD_MD5, passwordMd5);
		map.put(AccountInfo.EMAIL, email);

		String jsonRequestParams = JsonTool.createJsonString(
				JsonTool.JSON_REQUEST_PARAMS, map);
		map = null;

		// ��������������ӷ�����
		new Netconnection(context,Config.GATE_URL, HttpMethod.POST,
				new Netconnection.SuccessCallback() {

					@Override
					public void onSuccess(String result) {

						if (successCallback != null) {
							successCallback.onSuccess(result);
						}
					}
				}, new Netconnection.FailCallback() {

					@Override
					public void onFail(int status, int reason) {

						if (failCallback != null) {
							failCallback.onFail(status, reason);
						}
					}
				}, action, jsonRequestParams);

	}

	/**
	 * ��½
	 * 
	 * @param phoneMd5
	 *            ��½���ֻ���
	 * @param passwordMd5
	 *            ��½����
	 * @param deviceId_imei
	 *            �豸macֵ
	 * @param deviceName
	 *            �豸����
	 *            
	 * @param action	�������
	 * 
	 * @param successCallback	�ɹ�ʱ��ص��ķ���
	 * 
	 * @param failCallback	ʧ��ʱ��ص��ķ���
	 * @throws JSONException
	 */
	public void login(String phone, String passwordMd5,int action,
			final SuccessCallback successCallback,
			final FailCallback failCallback) throws JSONException {

		// ���������ת����json�ַ���
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(AccountInfo.PHONE, phone);
		map.put(AccountInfo.PASSWORD_MD5, passwordMd5);

		String jsonRequestParams = JsonTool.createJsonString(
				JsonTool.JSON_REQUEST_PARAMS, map);
		map = null;

		// ��������������ӷ�����
		new Netconnection(context,Config.GATE_URL, HttpMethod.POST,
				new Netconnection.SuccessCallback() {

					@Override
					public void onSuccess(String result) {

						if (successCallback != null) {
							successCallback.onSuccess(result);
						}
					}
				}, new Netconnection.FailCallback() {

					@Override
					public void onFail(int status, int reason) {

						if (failCallback != null) {
							failCallback.onFail(status, reason);
						}
					}
				}, action, jsonRequestParams);

	}

	/**
	 * ��������
	 * 
	 * @param phone
	 *            �˺��ֻ���
	 * @param newPasswordMd5
	 *            ������
	 * @param magCode
	 *            ����У����
	 *            
	 * @param action	�������
	 * 
	 * @param successCallback	�ɹ�ʱ��ص��ķ���
	 * 
	 * @param failCallback	ʧ��ʱ��ص��ķ���
	 * @throws JSONException
	 */
	public void resetLoginPassword(String phone,
			String newPasswordMD5, int action,
			final SuccessCallback successCallback,
			final FailCallback failCallback) throws JSONException {

		// ���������ת����json�ַ���
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(AccountInfo.PHONE, phone);
		map.put(AccountInfo.PASSWORD_MD5, newPasswordMD5);

		String jsonRequestParams = JsonTool.createJsonString(
				JsonTool.JSON_REQUEST_PARAMS, map);
		map = null;

		// ��������������ӷ�����
		new Netconnection(context,Config.GATE_URL, HttpMethod.POST,
				new Netconnection.SuccessCallback() {

					@Override
					public void onSuccess(String result) {

						if (successCallback != null) {
							successCallback.onSuccess(result);
						}
					}
				}, new Netconnection.FailCallback() {

					@Override
					public void onFail(int status, int reason) {

						if (failCallback != null) {
							failCallback.onFail(status, reason);
						}
					}
				}, action,jsonRequestParams);

	}

	
	/**
	 * �ɹ��ص�����
	 *
	 */
	public interface SuccessCallback {
		void onSuccess(String jsonResult);
	}

	/**
	 * ʧ�ܻص�����
	 */
	public interface FailCallback {
		void onFail(int status, int reason);
	}

}
