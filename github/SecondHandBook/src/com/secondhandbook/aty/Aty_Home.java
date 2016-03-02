package com.secondhandbook.aty;

import java.util.Timer;
import java.util.TimerTask;

import com.secondhandbook.aty.R.id;
import com.secondhandbook.info.BookInfo;
import com.secondhandbook.info.UserAction;
import com.secondhandbook.util.Config;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Aty_Home extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_home);
		
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
		
		ImageView hotbook_big = (ImageView) findViewById(R.id.iv_hotbook_big);
		ImageView hotbook_small1 = (ImageView) findViewById(R.id.iv_hotbook_small_1);
		ImageView hotbook_small2 = (ImageView) findViewById(R.id.iv_hotbook_small_2);
		ImageView hotbook_small3 = (ImageView) findViewById(R.id.iv_hotbook_small_3);
		ImageView hotbook_small4 = (ImageView) findViewById(R.id.iv_hotbook_small_4);
		ImageView hotbook_small5 = (ImageView) findViewById(R.id.iv_hotbook_small_5);
		ImageView hotbook_small6 = (ImageView) findViewById(R.id.iv_hotbook_small_6);
		
		hotbook_big.setOnClickListener(new myOnClickListener());
		hotbook_small1.setOnClickListener(new myOnClickListener());
		hotbook_small2.setOnClickListener(new myOnClickListener());
		hotbook_small3.setOnClickListener(new myOnClickListener());
		hotbook_small4.setOnClickListener(new myOnClickListener());
		hotbook_small5.setOnClickListener(new myOnClickListener());
		hotbook_small6.setOnClickListener(new myOnClickListener());
		
		
		
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
