package com.secondhandbook.aty;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class Aty_My extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_my);
		
		TextView my_buy = (TextView) findViewById(R.id.tv_my_buy_book);
		my_buy.setOnClickListener(new MyOnClickListener());
		
		TextView my_sell = (TextView) findViewById(R.id.tv_my_sell_book);
		my_sell.setOnClickListener(new MyOnClickListener());
		
		TextView my_setting = (TextView) findViewById(R.id.tv_my_buy_setting);
		my_setting.setOnClickListener(new MyOnClickListener());
		
	}

	private class MyOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.tv_my_buy_book:
				Intent intent1 = new Intent(Aty_My.this, Aty_Already_Buy.class);
				startActivity(intent1);
				intent1 = null;
				break;
			case R.id.tv_my_sell_book:
				Intent intent2 = new Intent(Aty_My.this, Aty_Already_Sell.class);
				startActivity(intent2);
				intent2 = null;
				break;
			case R.id.tv_my_buy_setting:
				Intent intent3 = new Intent(Aty_My.this, Aty_PersonalSetting.class);
				startActivity(intent3);
				intent3 = null;
				break;
			default:
				break;
			}
		}
		
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
