package com.secondhandbook.aty;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TabHost.TabSpec;

public class Aty_Already_Buy extends TabActivity {
	
	private TabHost mTabHost;
	private Intent mAIntent;
	private Intent mBIntent;
	private Intent mCIntent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_already_buy);
		
		 this.mAIntent = new Intent(this,Aty_Already_Buy_WaitShipment.class);
	        this.mBIntent = new Intent(this,Aty_Already_Buy_WaitReceive.class);
	        this.mCIntent = new Intent(this,Aty_Already_Buy_Finish.class);
	        
	        
	        setupIntent();
	}
	private void setupIntent() {
		this.mTabHost = getTabHost();
		TabHost localTabHost = this.mTabHost;

		localTabHost.addTab(buildTabSpec("A_TAB", R.string.already_buy_waitshipment, this.mAIntent));

		localTabHost.addTab(buildTabSpec("B_TAB", R.string.already_buy_waitreceive, this.mBIntent));

		localTabHost.addTab(buildTabSpec("C_TAB",R.string.already_buy_finish,this.mCIntent));

	}
	private TabHost.TabSpec buildTabSpec(String tag, int resLabel,final Intent content) {
		return this.mTabHost.newTabSpec(tag).setIndicator(getString(resLabel)).setContent(content);
	}


}
