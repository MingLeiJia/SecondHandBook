package com.secondhandbook.net;


import com.secondhandbook.net.httpMethod.HttpMethod;
import com.secondhandbook.util.Config;

import android.content.Context;

public class Netconnection {
	
	/**
	 * 网络连接的实现类
	 * @param url				服务器的网关
	 * @param httpMethod		请求方法
	 * @param successCallback	成功时候回调的方法
	 * @param failCallback		失败时候的回调方法
	 * @param action			请求类别
	 * @param identify			用户身份标识
	 * @param jsonParams		请求参数的json字符串
	 */
	public Netconnection(Context context,final String url, final HttpMethod httpMethod,
			final SuccessCallback successCallback,
			final FailCallback failCallback, final int action,
			final String jsonParams) {
		
		//链接服务器
		new BaseNetConnection(context,url, httpMethod, new BaseNetConnection.SuccessCallback() {
			
			@Override
			public void onSuccess(String result) {

				if (result != null) {
					if (successCallback != null) {
						successCallback.onSuccess(result);
					}
				} else {
					if (failCallback != null) {
						failCallback.onFail(Config.STATUS_FAIL, Config.STATUS_FAIL_REASON_OTHER);
					}
				}
			}
		},new BaseNetConnection.FailCallback() {
			
			@Override
			public void onFail(int status, int reason) {

				if (failCallback != null) {
					failCallback.onFail(status, reason);
				}
			}
		}, action,jsonParams);

	
	
	
	}
	
	
	//成功回调方法
		public interface SuccessCallback {

			void onSuccess(String result);
		}

		
		//失败回调方法
		public interface FailCallback {
			void onFail(int status, int reason);
		}

}
