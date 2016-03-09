/**
 * @author MingLei Jia
 */
package com.secondhandbook.aty;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.secondhandbook.info.BookInfo;
import com.secondhandbook.info.UserAction;
import com.secondhandbook.util.Config;
import com.secondhandbook.util.ImageHelper;
import com.secondhandbook.util.JsonTool;
import com.secondhandbook.viewpager.MyViewPager;

public class Aty_Home extends Activity {

	private ImageView hotbook_big, hotbook_small1, hotbook_small2, hotbook_small3, 
	hotbook_small4, hotbook_small5, hotbook_small6;
	private String url1, url2, url3, url4, url5, url6, url7;
	
	private ViewPager mViewpager ;
	private LinearLayout layoutDots;
	
	private String[] cpurl;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_home);
		
		initView();

		getCarouselBook();

		getHotBookCover();
   
	}

	public void initView()
	{
		
		mViewpager = (ViewPager) findViewById(R.id.viewPager);
		layoutDots = (LinearLayout) findViewById(R.id.viewGroup);
		
		
		Button bn_wszk = (Button) findViewById(R.id.bn_wszk);
		Button bn_jzfs = (Button) findViewById(R.id.bn_jzfs);
		Button bn_zrkx = (Button) findViewById(R.id.bn_zrkx);
		Button bn_xdkj = (Button) findViewById(R.id.bn_xdkj);
		Button bn_ysgl = (Button) findViewById(R.id.bn_ysgl);
		Button bn_qkzz = (Button) findViewById(R.id.bn_qkzz);
		TextView morebook = (TextView) findViewById(R.id.tv_bookmore);

		bn_wszk.setOnClickListener(new myOnClickListener());
		bn_jzfs.setOnClickListener(new myOnClickListener());
		bn_zrkx.setOnClickListener(new myOnClickListener());
		bn_xdkj.setOnClickListener(new myOnClickListener());
		bn_ysgl.setOnClickListener(new myOnClickListener());
		bn_qkzz.setOnClickListener(new myOnClickListener());
		morebook.setOnClickListener(new myOnClickListener());

		hotbook_big = (ImageView) findViewById(R.id.iv_hotbook_big);
		hotbook_small1 = (ImageView) findViewById(R.id.iv_hotbook_small_1);
		hotbook_small2 = (ImageView) findViewById(R.id.iv_hotbook_small_2);
		hotbook_small3 = (ImageView) findViewById(R.id.iv_hotbook_small_3);
		hotbook_small4 = (ImageView) findViewById(R.id.iv_hotbook_small_4);
		hotbook_small5 = (ImageView) findViewById(R.id.iv_hotbook_small_5);
		hotbook_small6 = (ImageView) findViewById(R.id.iv_hotbook_small_6);

		hotbook_big.setOnClickListener(new myOnClickListener());
		hotbook_small1.setOnClickListener(new myOnClickListener());
		hotbook_small2.setOnClickListener(new myOnClickListener());
		hotbook_small3.setOnClickListener(new myOnClickListener());
		hotbook_small4.setOnClickListener(new myOnClickListener());
		hotbook_small5.setOnClickListener(new myOnClickListener());
		hotbook_small6.setOnClickListener(new myOnClickListener());

	}
	
	public void getCarouselBook()
	{
		UserAction ua = new UserAction(this);
		try {
			ua.downCarouseBook(UserAction.ACTION_DOWNCAROUSEBOOK,
					new UserAction.SuccessCallback() {
						
						@Override
						public void onSuccess(String jsonResult) {
							// TODO Auto-generated method stub
							JSONObject jsonObject;
							try {
								jsonObject = new JSONObject(jsonResult);
								JSONObject jsonresult = jsonObject.getJSONObject(JsonTool.JSON_RESULT_CODE);
								String cpurl1 = jsonresult.getString(BookInfo.CAROUSEBOOK1);
								String cpurl2 = jsonresult.getString(BookInfo.CAROUSEBOOK2);
								String cpurl3 = jsonresult.getString(BookInfo.CAROUSEBOOK3);
								String cpurl4 = jsonresult.getString(BookInfo.CAROUSEBOOK4);
								cpurl = new String[4];
								cpurl[0] = cpurl1;
								cpurl[1] = cpurl2;
								cpurl[2] = cpurl3;
								cpurl[3] = cpurl4;
								System.out.println("%%%%%%%%%"+jsonresult.toString());
	
								new MyViewPager(Aty_Home.this, cpurl, layoutDots,mViewpager);
		
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

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
	}
	
	
	public void getHotBookCover()
	{
		UserAction ua = new UserAction(this);
		try {
			ua.showhotbook(UserAction.ACTION_DOWN_HOTBOOK_COVER, 
					new UserAction.SuccessCallback() {

				@Override
				public void onSuccess(String jsonResult) {
					// TODO Auto-generated method stub
					try {
						//System.out.println(jsonResult);
						JSONObject jsonObject = new JSONObject(jsonResult);
						JSONObject jsonresult = jsonObject.getJSONObject(JsonTool.JSON_RESULT_CODE);
						url1 = jsonresult.getString(BookInfo.HOTBOOT1);
						url2 = jsonresult.getString(BookInfo.HOTBOOT2);
						url3 = jsonresult.getString(BookInfo.HOTBOOT3);
						url4 = jsonresult.getString(BookInfo.HOTBOOT4);
						url5 = jsonresult.getString(BookInfo.HOTBOOT5);
						url6 = jsonresult.getString(BookInfo.HOTBOOT6);
						url7 = jsonresult.getString(BookInfo.HOTBOOT7);
						//System.out.println(url1);
						ImageHelper.getInstance().displayImage(url1, hotbook_big);
						ImageHelper.getInstance().displayImage(url2, hotbook_small1);
						ImageHelper.getInstance().displayImage(url3, hotbook_small2);
						ImageHelper.getInstance().displayImage(url4, hotbook_small3);
						ImageHelper.getInstance().displayImage(url5, hotbook_small4);
						ImageHelper.getInstance().displayImage(url6, hotbook_small5);
						ImageHelper.getInstance().displayImage(url7, hotbook_small6);
						

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

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
		
		ua = null;
	}



	private class myOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.bn_wszk:
				Intent intent1 = new Intent(Aty_Home.this, Aty_BookList.class);
				Bundle bundle1 = new Bundle();
				bundle1.putInt(Config.KEY_ACTION,UserAction.ACTION_VIEWBOOK_BY_CATEGORY);
				bundle1.putString(BookInfo.CATEGORY, BookInfo.CATEGORY_WSZK);
				intent1.putExtras(bundle1);
				startActivity(intent1);
				intent1 = null;
				break;
			case R.id.bn_jzfs:
				Intent intent2 = new Intent(Aty_Home.this, Aty_BookList.class);
				Bundle bundle2 = new Bundle();
				bundle2.putInt(Config.KEY_ACTION,UserAction.ACTION_VIEWBOOK_BY_CATEGORY);
				bundle2.putString(BookInfo.CATEGORY, BookInfo.CATEGORY_JZFS);
				intent2.putExtras(bundle2);
				startActivity(intent2);
				intent2 = null;
				break;
			case R.id.bn_zrkx:
				Intent intent3 = new Intent(Aty_Home.this, Aty_BookList.class);
				Bundle bundle3 = new Bundle();
				bundle3.putInt(Config.KEY_ACTION,UserAction.ACTION_VIEWBOOK_BY_CATEGORY);
				bundle3.putString(BookInfo.CATEGORY, BookInfo.CATEGORY_ZRKX);
				intent3.putExtras(bundle3);
				startActivity(intent3);
				intent3 = null;
				break;
			case R.id.bn_xdkj:
				Intent intent4 = new Intent(Aty_Home.this, Aty_BookList.class);
				Bundle bundle4 = new Bundle();
				bundle4.putInt(Config.KEY_ACTION,UserAction.ACTION_VIEWBOOK_BY_CATEGORY);
				bundle4.putString(BookInfo.CATEGORY, BookInfo.CATEGORY_XDKJ);
				intent4.putExtras(bundle4);
				startActivity(intent4);
				intent4 = null;
				break;
			case R.id.bn_ysgl:
				Intent intent5 = new Intent(Aty_Home.this, Aty_BookList.class);
				Bundle bundle5 = new Bundle();
				bundle5.putInt(Config.KEY_ACTION,UserAction.ACTION_VIEWBOOK_BY_CATEGORY);
				bundle5.putString(BookInfo.CATEGORY, BookInfo.CATEGORY_YSGL);
				intent5.putExtras(bundle5);
				startActivity(intent5);
				intent5 = null;
				break;
			case R.id.bn_qkzz:
				Intent intent6 = new Intent(Aty_Home.this, Aty_BookList.class);
				Bundle bundle6 = new Bundle();
				bundle6.putInt(Config.KEY_ACTION,UserAction.ACTION_VIEWBOOK_BY_CATEGORY);
				bundle6.putString(BookInfo.CATEGORY, BookInfo.CATEGORY_QKZZ);
				intent6.putExtras(bundle6);
				startActivity(intent6);
				intent6 = null;
				break;
			case R.id.tv_bookmore:
				Intent intent7 = new Intent(Aty_Home.this, Aty_BookList.class);
				Bundle bundle7 = new Bundle();
				bundle7.putInt(Config.KEY_ACTION,UserAction.ACTION_VIEW_HOTBOOK);
				//bundle7.putString(BookInfo.HOTBOOK, BookInfo.HOTBOOK_MORE);
				intent7.putExtras(bundle7);
				startActivity(intent7);
				intent7 = null;
				break;
			case R.id.iv_hotbook_big:
				break;
			case R.id.iv_hotbook_small_1:
				break;
			case R.id.iv_hotbook_small_2:
				break;
			case R.id.iv_hotbook_small_3:
				break;
			case R.id.iv_hotbook_small_4:
				break;
			case R.id.iv_hotbook_small_5:
				break;
			case R.id.iv_hotbook_small_6:
				break;
			default:
				break;
			}
		}

	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitBy2Click();
		}
		return false;
	}

	private static Boolean isExit = false;

	private void exitBy2Click() {
		Timer tExit = null;
		if (isExit == false) {
			isExit = true;
			Toast.makeText(this, "再按一次返回退出", Toast.LENGTH_SHORT).show();
			tExit = new Timer();
			tExit.schedule(new TimerTask() {

				@Override
				public void run() {
					isExit = false;
				}

			}, 2000);
		} else {
			finish();
			System.exit(0);
		}
	}
}
