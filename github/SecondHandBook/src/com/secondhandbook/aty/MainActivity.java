package com.secondhandbook.aty;

import java.util.Timer;
import java.util.TimerTask;

import com.secondhandbook.info.AccountInfo;
import com.secondhandbook.info.SPKey;
import com.secondhandbook.textbutton.TextViewWithIcon;
import com.secondhandbook.textbutton.TextWithIconGroup;
import com.secondhandbook.textbutton.TextWithIconGroup.OnItemClickListener;
import com.secondhandbook.util.Config;
import com.secondhandbook.util.SPUtils;

import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity implements OnItemClickListener{

	private TabHost mTabHost;
	private Intent mAIntent;
	private Intent mBIntent;
	private Intent mCIntent;
	private Intent mDIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
        this.mAIntent = new Intent(this,Aty_Home.class);
        this.mBIntent = new Intent(this,Aty_Buy.class);
        this.mCIntent = new Intent(this,Aty_Sell.class);
        String token = (String)SPUtils.getParam(this, SPKey.TOKEN, "hello");
		if(token.equals("hello") || token.equals(""))
		{
			this.mDIntent = new Intent(this,Aty_My_Tourist.class);
		}else{
			
			this.mDIntent = new Intent(this,Aty_My.class);
		}
        
		((TextWithIconGroup)findViewById(R.id.main_radio)).setItemClickListener(this);
        
        setupIntent();
	}
	private void setupIntent() {
		this.mTabHost = getTabHost();
		TabHost localTabHost = this.mTabHost;

		localTabHost.addTab(buildTabSpec("A_TAB", R.string.main_home,
				R.drawable.pic_tab_home, this.mAIntent));
		localTabHost.addTab(buildTabSpec("B_TAB", R.string.main_buy,
				R.drawable.pic_tab_buy, this.mBIntent));

		localTabHost.addTab(buildTabSpec("C_TAB",R.string.main_sell,
				R.drawable.pic_tab_sell,this.mCIntent));

		localTabHost.addTab(buildTabSpec("D_TAB", R.string.main_my,
				R.drawable.pic_tab_my, this.mDIntent));


	}
	private TabHost.TabSpec buildTabSpec(String tag, int resLabel, int resIcon,
			final Intent content) {
		return this.mTabHost.newTabSpec(tag).setIndicator(getString(resLabel),
				getResources().getDrawable(resIcon)).setContent(content);
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
	@Override
	public void onItemClick(TextViewWithIcon view) {
		switch (view.getId()) {
		case R.id.radio_button0:
			this.mTabHost.setCurrentTabByTag("A_TAB");
			break;
		case R.id.radio_button1:
			this.mTabHost.setCurrentTabByTag("B_TAB");
			break;
		case R.id.radio_button2:
			this.mTabHost.setCurrentTabByTag("C_TAB");
			break;
		case R.id.radio_button3:
			this.mTabHost.setCurrentTabByTag("D_TAB");
			break;
		}
		
	}
}
