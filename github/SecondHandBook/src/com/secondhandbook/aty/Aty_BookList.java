/**
 * @author MingLei Jia
 */
package com.secondhandbook.aty;

import java.net.URLDecoder;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.secondhandbook.aty.adapter.BookListAdapter;
import com.secondhandbook.info.AccountInfo;
import com.secondhandbook.info.BookInfo;
import com.secondhandbook.info.SPKey;
import com.secondhandbook.info.UserAction;
import com.secondhandbook.util.Config;
import com.secondhandbook.util.Information;
import com.secondhandbook.util.JsonTool;
import com.secondhandbook.util.SPUtils;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class Aty_BookList extends Activity {

	private int action;
	private ListView booklist;
	private UserAction ua;
	private ProgressDialog pdialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_booklist);

		pdialog=new ProgressDialog(Aty_BookList.this);
		pdialog.setMessage("请稍候，正在读取书籍信息...");
		pdialog.show();
		
		ua = new UserAction(this);

		ActionBar actionbar = getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		action = bundle.getInt(Config.KEY_ACTION);

		booklist = (ListView) findViewById(R.id.listView_booklist);
		//booklist.setAdapter(new BookListAdapter(this, ja));


		switch (action) {
		case UserAction.ACTION_SEARCHBOOK:
			int userid = (Integer) SPUtils.getParam(Aty_BookList.this, SPKey.USERID, -1);
			boolean isnewest = bundle.getBoolean(BookInfo.BOOKNEWEST);
			boolean islowest = bundle.getBoolean(BookInfo.BOOKLOWEST);
			String bookisbn = bundle.getString(BookInfo.BOOKISBN);
			String bookname = bundle.getString(BookInfo.BOOKNAME);
			String region = bundle.getString(BookInfo.REGION);
			//Toast.makeText(this, region+String.valueOf(islowest), Toast.LENGTH_SHORT).show();

			try {
				ua.searchbook(userid, bookisbn, region, 
						islowest, isnewest, UserAction.ACTION_SEARCHBOOK, 
						new UserAction.SuccessCallback() {

					@Override
					public void onSuccess(String jsonResult) {
						// TODO Auto-generated method stub
						
						try {
							pdialog.dismiss();
							String jsonresult = URLDecoder.decode(jsonResult,"utf-8");
							//String jsonresult = new String(jsonResult.getBytes("utf-8"),"GBK");
							System.out.println("#############"+jsonresult);
							JSONObject jsonObject = new JSONObject(jsonresult);
							JSONObject json_result=jsonObject.getJSONObject(JsonTool.JSON_RESULT_CODE);
							
							String status=json_result.getString(Information.STATUS);
							if(status.equals(Information.SUCCESS)){
								final JSONArray arr=json_result.getJSONArray(JsonTool.JSON_BOOK_ARRAY);
								//System.out.println("!!!!!!!!!!!!!!!"+arr.toString());
								BookListAdapter adapter = new BookListAdapter(Aty_BookList.this, arr);
								booklist.setAdapter(adapter);
								booklist.setOnItemClickListener(new OnItemClickListener() {

									@Override
									public void onItemClick(
											AdapterView<?> parent, View view,
											int position, long id) {
										// TODO Auto-generated method stub
										try {
											String bookisbn = arr.getJSONObject(position).getString(BookInfo.BOOKISBN);
											String neworold = arr.getJSONObject(position).getString(BookInfo.BOOKNEWOROLD);
											String price = arr.getJSONObject(position).getString(BookInfo.BOOKCOST);
											String bookid = arr.getJSONObject(position).getString(BookInfo.ID);
											String imageurl = arr.getJSONObject(position).getString(BookInfo.BOOKCOVERURL);
											String bookname = arr.getJSONObject(position).getString(BookInfo.BOOKNAME);
											String publisher = arr.getJSONObject(position).getString(BookInfo.PUBLISHER);
											String author = arr.getJSONObject(position).getString(BookInfo.BOOKAUTHOR);
						
											Intent intent = new Intent(Aty_BookList.this, Aty_Book_Detail.class);
											intent.putExtra(BookInfo.BOOKNEWOROLD, neworold);
											intent.putExtra(Information.BOOKISBN, bookisbn);
											intent.putExtra(BookInfo.BOOKCOST, price);
											intent.putExtra(BookInfo.ID, bookid);
											intent.putExtra(BookInfo.BOOKCOVERURL, imageurl);
											intent.putExtra(BookInfo.PUBLISHER, publisher);
											intent.putExtra(BookInfo.BOOKNAME, bookname);
											intent.putExtra(BookInfo.BOOKAUTHOR, author);
											startActivity(intent);
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								});
							}else if(status.equals(Information.FAIL))
							{
								new AlertDialog.Builder(Aty_BookList.this)
								.setTitle("友情提示")
								.setMessage("对不起，获取书籍失败")
								.setCancelable(false)
								.setPositiveButton("知道啦", new android.content.DialogInterface.OnClickListener(){

									public void onClick(DialogInterface dialog,int which) {
										// TODO Auto-generated method stub

									}
								}).create().show();
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
					}
				}, new UserAction.FailCallback() {

					@Override
					public void onFail(int status, int reason) {
						// TODO Auto-generated method stub
						Toast.makeText(Aty_BookList.this, "对不起，服务器故障！", Toast.LENGTH_SHORT).show();
					}
				});
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		case UserAction.ACTION_VIEWBOOK_BY_CATEGORY:
			String category = bundle.getString(BookInfo.CATEGORY);
			try {
				ua.searchBookbyCategory(category, UserAction.ACTION_VIEWBOOK_BY_CATEGORY, new UserAction.SuccessCallback() {
					
					@Override
					public void onSuccess(String jsonResult) {
						// TODO Auto-generated method stub
						try {
							pdialog.dismiss();
							String jsonresult = URLDecoder.decode(jsonResult,"utf-8");
							System.out.println("#############"+jsonresult);
							JSONObject jsonObject = new JSONObject(jsonresult);
							JSONObject json_result=jsonObject.getJSONObject(JsonTool.JSON_RESULT_CODE);
							
							String status=json_result.getString(Information.STATUS);
							if(status.equals(Information.SUCCESS)){
								final JSONArray arr=json_result.getJSONArray(JsonTool.JSON_BOOK_ARRAY);
								BookListAdapter adapter = new BookListAdapter(Aty_BookList.this, arr);
								booklist.setAdapter(adapter);
								booklist.setOnItemClickListener(new OnItemClickListener() {

									@Override
									public void onItemClick(
											AdapterView<?> parent, View view,
											int position, long id) {
										// TODO Auto-generated method stub
										try {
											String bookisbn = arr.getJSONObject(position).getString(BookInfo.BOOKISBN);
											String neworold = arr.getJSONObject(position).getString(BookInfo.BOOKNEWOROLD);
											String price = arr.getJSONObject(position).getString(BookInfo.BOOKCOST);
											String bookid = arr.getJSONObject(position).getString(BookInfo.ID);
											String imageurl = arr.getJSONObject(position).getString(BookInfo.BOOKCOVERURL);
											String bookname = arr.getJSONObject(position).getString(BookInfo.BOOKNAME);
											String publisher = arr.getJSONObject(position).getString(BookInfo.PUBLISHER);
											String author = arr.getJSONObject(position).getString(BookInfo.BOOKAUTHOR);						
											Intent intent = new Intent(Aty_BookList.this, Aty_Book_Detail.class);
											intent.putExtra(BookInfo.BOOKNEWOROLD, neworold);
											intent.putExtra(Information.BOOKISBN, bookisbn);
											intent.putExtra(BookInfo.BOOKCOST, price);
											intent.putExtra(BookInfo.ID, bookid);
											intent.putExtra(BookInfo.BOOKCOVERURL, imageurl);
											intent.putExtra(BookInfo.PUBLISHER, publisher);
											intent.putExtra(BookInfo.BOOKNAME, bookname);
											intent.putExtra(BookInfo.BOOKAUTHOR, author);
											startActivity(intent);
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								});
							}else if(status.equals(Information.FAIL))
							{
								new AlertDialog.Builder(Aty_BookList.this)
								.setTitle("友情提示")
								.setMessage("对不起，获取书籍失败")
								.setCancelable(false)
								.setPositiveButton("知道啦", new android.content.DialogInterface.OnClickListener(){

									public void onClick(DialogInterface dialog,int which) {
										// TODO Auto-generated method stub

									}
								}).create().show();
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
					}
				}, new UserAction.FailCallback() {
					
					@Override
					public void onFail(int status, int reason) {
						// TODO Auto-generated method stub
						Toast.makeText(Aty_BookList.this, "对不起，服务器故障！", Toast.LENGTH_SHORT).show();
					}
				});
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case UserAction.ACTION_SHOW_HOTBOOK_MORE:

			break;
		default:
			break;
		}

	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
