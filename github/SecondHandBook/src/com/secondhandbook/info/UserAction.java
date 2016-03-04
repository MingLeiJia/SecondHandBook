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
	
	public static final int ACTION_PUBLICBOOK = 11;
	public static final int ACTION_BUYBOOK = 10;


	private Context context = null;

	public UserAction(Context context) {
		this.context = context;
	}

	/**
	 * 登陆
	 * @param phoneMd5
	 *            登陆的手机号
	 * @param passwordMd5
	 *            登陆密码
	 * 
	 * @param action
	 *            请求类别
	 * 
	 * @param successCallback
	 *            成功时候回调的方法
	 * 
	 * @param failCallback
	 *            失败时候回调的方法
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
	 * 注册
	 * @param phoneMd5
	 *            登陆的手机号
	 * @param passwordMd5
	 *            登陆密码
	 * 
	 * @param action
	 *            请求类别
	 * 
	 * @param successCallback
	 *            成功时候回调的方法
	 * 
	 * @param failCallback
	 *            失败时候回调的方法
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
				// TODO Auto-generated method stub
				if (failCallback != null) {
					failCallback.onFail(status, reason);
				}
			}
		});
		accountInfo = null;
	}
	
	
	public void publicbook(String isbn,String bookname,String oldprice,String newprice,
			String category, String neworold,String region,int action,
			final SuccessCallback successCallback,
			final FailCallback failCallback) throws JSONException {

		BookInfo bookInfo = new BookInfo(context);
		bookInfo.publicbook(isbn, bookname, oldprice, newprice, 
				category, neworold, region, action,
				new BookInfo.SuccessCallback() {
			
			@Override
			public void onSuccess(String jsonResult) {
				// TODO Auto-generated method stub
				if (successCallback != null) {
					successCallback.onSuccess(jsonResult);
				} else {
					failCallback.onFail(Config.STATUS_FAIL,
							Config.STATUS_FAIL_REASON_OTHER);
				}
			}
		}, new BookInfo.FailCallback() {
			
			@Override
			public void onFail(int status, int reason) {
				// TODO Auto-generated method stub
				if (failCallback != null) {
					failCallback.onFail(status, reason);
				}
			}
		});

		bookInfo = null;
	}
	

	/**
	 * 成功回调方法
	 *
	 */
	public interface SuccessCallback {
		void onSuccess(String jsonResult);
	}

	/**
	 * 失败回调方法
	 */
	public interface FailCallback {
		void onFail(int status, int reason);
	}
}
