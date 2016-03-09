/**
 * @author MingLei Jia
 */
package com.secondhandbook.aty;

import org.json.JSONException;

import com.secondhandbook.aty.adapter.BookListAdapter;
import com.secondhandbook.info.BookInfo;
import com.secondhandbook.info.SPKey;
import com.secondhandbook.info.UserAction;
import com.secondhandbook.util.Config;
import com.secondhandbook.util.SPUtils;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

public class Aty_BookList extends Activity {

	private int action;
	private ListView booklist;
	private UserAction ua;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_booklist);
		
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
			Toast.makeText(this, region+String.valueOf(islowest), Toast.LENGTH_SHORT).show();
			
			try {
				ua.searchbook(userid, bookisbn, region, 
						islowest, isnewest, UserAction.ACTION_SEARCHBOOK, 
						new UserAction.SuccessCallback() {
							
							@Override
							public void onSuccess(String jsonResult) {
								// TODO Auto-generated method stub
								
							}
						}, new UserAction.FailCallback() {
							
							@Override
							public void onFail(int status, int reason) {
								// TODO Auto-generated method stub
								
							}
						});
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case UserAction.ACTION_VIEWBOOK_BY_CATEGORY:
			String category = bundle.getString(BookInfo.CATEGORY);
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
