package com.secondhandbook.info;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import com.secondhandbook.net.Netconnection;
import com.secondhandbook.net.httpMethod.HttpMethod;
import com.secondhandbook.util.Config;
import com.secondhandbook.util.JsonTool;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class BookInfo implements Parcelable{

	//intent携带的固定参数
	public static final String CATEGORY = "category";
	public static final String CATEGORY_JZFS = "jingzhengfashe";
	public static final String CATEGORY_WSZK = "wenshizheke"; 
	public static final String CATEGORY_ZRKX = "zirankexue";
	public static final String CATEGORY_XDKJ = "xiandaikeji";
	public static final String CATEGORY_YSGL = "yishuguanli";
	public static final String CATEGORY_QKZZ = "qikanzazhi";
	
	public static final String HOTBOOK = "hotbook";
	public static final String HOTBOOK_MORE = "morehotbook";
	
	public static String REGION = "region";
	public static String BOOKCATEGORY = "category";
	public static String BOOKISBN = "isbn";
	public static String BOOKNEWEST = "isnewest";
	public static String BOOKLOWEST = "islowest";
	
	public static String BOOKNAME = "bookname";
	public static String BOOKAUTHOR = "author";
	public static String BOOKPRICE = "oldprice";
	public static String BOOKCOST = "newprice";
	public static String BOOKNEWOROLD = "neworold";
	
	public static String BOOKCOVERURL = "coverurl";
	
	public String book_name;
	public String book_author;
	public String book_oldprice;
	public String book_newprice;
	public String book_neworold;
	public Bitmap book_cover;
	
	public BookInfo()
	{
		
	}
	
	public String getBook_name() {
		return book_name;
	}
	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}
	public String getBook_author() {
		return book_author;
	}
	public void setBook_author(String book_author) {
		this.book_author = book_author;
	}
	public String getBook_oldprice() {
		return book_oldprice;
	}
	public void setBook_oldprice(String book_oldprice) {
		this.book_oldprice = book_oldprice;
	}
	public String getBook_newprice() {
		return book_newprice;
	}
	public void setBook_newprice(String book_newprice) {
		this.book_newprice = book_newprice;
	}
	public String getBook_neworold() {
		return book_neworold;
	}
	public void setBook_neworold(String book_neworold) {
		this.book_neworold = book_neworold;
	}
	public Bitmap getBook_cover() {
		return book_cover;
	}
	public void setBook_cover(Bitmap book_cover) {
		this.book_cover = book_cover;
	}


    public static final Parcelable.Creator<BookInfo> CREATOR = new Creator<BookInfo>() {
        public BookInfo createFromParcel(Parcel source) {
            BookInfo bookInfo = new BookInfo();
            bookInfo.book_name = source.readString();
            bookInfo.book_cover = source.readParcelable(Bitmap.class.getClassLoader());
            bookInfo.book_author = source.readString();
            bookInfo.book_oldprice = source.readString();
            bookInfo.book_newprice = source.readString();
            bookInfo.book_neworold = source.readString();

            return bookInfo;
        }
        public BookInfo[] newArray(int size) {
            return new BookInfo[size];
        }
    };
	
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(book_name);
		dest.writeParcelable(book_cover, flags);
		dest.writeString(book_author);
		dest.writeString(book_oldprice);
		dest.writeString(book_newprice);
		dest.writeString(book_neworold);
	}
	
	private Context context = null;
	public BookInfo(Context context) {
		this.context = context;
	}
	/**
	 * 根据用户提交的种类获取相关书籍的方法
	 * @param category
	 * 	根据种类信息获取详细书籍
	 * @param action
	 * 	携带的action为根据类别回去信息
	 * @param successCallback
	 * 	成功回调
	 * @param failCallback
	 * 	失败回调
	 * @throws JSONException
	 */
	public void showbook_by_category(String category,int action, 
			final SuccessCallback successCallback,
			final FailCallback failCallback)throws JSONException
	{
		// 将请求参数转化成json字符串
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(BookInfo.BOOKCATEGORY, category);
		String jsonRequestParams = JsonTool.createJsonString(
				JsonTool.JSON_REQUEST_PARAMS, map);
		map = null;
		// 带着请求参数链接服务器
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
	 * 获取七本热门书籍的方法
	 * @param action
	 * 	首页上显示的七本热门书籍
	 * @param successCallback
	 * 	成功回调
	 * @param failCallback
	 * 	失败回调
	 * @throws JSONException
	 */
	public void showhotbook(int action, 
			final SuccessCallback successCallback,
			final FailCallback failCallback)throws JSONException
	{

		// 带着请求参数链接服务器
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
				}, action,null);
	}
	
	/**
	 * 获取更多热门书籍的方法
	 * @param action
	 * @param successCallback
	 * @param failCallback
	 * @throws JSONException
	 */
	public void showhoebook_more(int action, 
			final SuccessCallback successCallback,
			final FailCallback failCallback)throws JSONException
	{

		// 带着请求参数链接服务器
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
				}, action,null);
	}
	/**
	 * 我要买书点击搜索后的方法，通过ISBN或者名字获取书籍的方法
	 * @param isbn
	 * @param bookname
	 * @param isnewest
	 * @param islowest
	 * @param action
	 * @param successCallback
	 * @param failCallback
	 * @throws JSONException
	 */
	public void showbook_by_searchIsbnOrName(String isbn,String bookname,String region,
			boolean isnewest,boolean islowest, int action, 
			final SuccessCallback successCallback,
			final FailCallback failCallback)throws JSONException
	{
		// 将请求参数转化成json字符串
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(BookInfo.REGION, region);
		map.put(BookInfo.BOOKISBN, isbn);
		map.put(BookInfo.BOOKNAME, bookname);
		map.put(BookInfo.BOOKNEWEST, isnewest);
		map.put(BookInfo.BOOKLOWEST, islowest);
		String jsonRequestParams = JsonTool.createJsonString(
				JsonTool.JSON_REQUEST_PARAMS, map);
		map = null;
		// 带着请求参数链接服务器
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
	 * 发布书籍的方法
	 * @param isbn
	 * @param bookname
	 * @param oldprice
	 * @param newprice
	 * @param category
	 * @param neworold
	 * @param region
	 * @param action
	 * @param successCallback
	 * @param failCallback
	 * @throws JSONException
	 */
	public void publicbook(String isbn,String bookname,String oldprice,String newprice,
			String category, String neworold,String region,int action, 
			final SuccessCallback successCallback,
			final FailCallback failCallback)throws JSONException
	{
		// 将请求参数转化成json字符串
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(BookInfo.BOOKISBN, isbn);
		map.put(BookInfo.BOOKNAME, bookname);
		map.put(BookInfo.BOOKPRICE, oldprice);
		map.put(BookInfo.BOOKCOST, newprice);
		map.put(BookInfo.BOOKCATEGORY, category);
		map.put(BookInfo.BOOKNEWOROLD, neworold);
		map.put(BookInfo.REGION, region);
		String jsonRequestParams = JsonTool.createJsonString(
				JsonTool.JSON_REQUEST_PARAMS, map);
		map = null;
		// 带着请求参数链接服务器
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
