package com.secondhandbook.info;

import org.json.JSONException;

import com.secondhandbook.util.Config;

import android.content.Context;

public class UserAction {

	public static final int ACTION_VIEWBOOK_BY_CATEGORY = 1;
	public static final int ACTION_DOWN_HOTBOOK_COVER = 2;
	public static final int ACTION_VIEW_HOTBOOK = 3;
	public static final int ACTION_SHOW_HOTBOOK_MORE = 4;
	public static final int ACTION_SEARCHBOOK = 5;

	public static final int ACTION_LOGIN = 6;
	public static final int ACTION_REGISTER = 7;
	public static final int ACTION_PASSFORGET = 8;
	public static final int ACTION_PASSRESET = 9;


	private Context context = null;

	public UserAction(Context context) {
		this.context = context;
	}

	/**
	 * ��½
	 * @param phoneMd5
	 *            ��½���ֻ���
	 * @param passwordMd5
	 *            ��½����
	 * 
	 * @param action
	 *            �������
	 * 
	 * @param successCallback
	 *            �ɹ�ʱ��ص��ķ���
	 * 
	 * @param failCallback
	 *            ʧ��ʱ��ص��ķ���
	 * @throws JSONException
	 */
	public void login(String phoneMd5, String passwordMd5,int action,
			final SuccessCallback successCallback,
			final FailCallback failCallback) throws JSONException {

		AccountInfo accountInfo = new AccountInfo(context);
		accountInfo.login(phoneMd5, passwordMd5,action, new AccountInfo.SuccessCallback() {

			@Override
			public void onSuccess(String jsonResult) {
				if (successCallback != null) {
					successCallback.onSuccess(jsonResult);
				} else {
					failCallback.onFail(Config.STATUS_FAIL,
							Config.STATUS_FAIL_REASON_OTHER);
				}
			}
		}, new AccountInfo.FailCallback() {

			@Override
			public void onFail(int status, int reason) {
				if (failCallback != null) {
					failCallback.onFail(status, reason);
				}
			}
		});

		accountInfo = null;

	}

	/**
	 * ע��
	 * @param phoneMd5
	 *            ��½���ֻ���
	 * @param passwordMd5
	 *            ��½����
	 * 
	 * @param action
	 *            �������
	 * 
	 * @param successCallback
	 *            �ɹ�ʱ��ص��ķ���
	 * 
	 * @param failCallback
	 *            ʧ��ʱ��ص��ķ���
	 * @throws JSONException
	 */
	public void register(String msgcode, String nickname, String phone,
			String passwordMd5, String email, int action,
			final SuccessCallback successCallback,
			final FailCallback failCallback) throws JSONException {

		AccountInfo accountInfo = new AccountInfo(context);
		accountInfo.register(msgcode, nickname, phone, passwordMd5, email, action, 
				new AccountInfo.SuccessCallback() {

			@Override
			public void onSuccess(String jsonResult) {
				// TODO Auto-generated method stub

			}
		}, new AccountInfo.FailCallback() {

			@Override
			public void onFail(int status, int reason) {
				// TODO Auto-generated method stub

			}
		});
		accountInfo = null;
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
