/**
 * @author MingLei Jia
 */
package com.secondhandbook.aty;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TabHost.TabSpec;

public class Aty_Already_Sell extends TabActivity{

	private TabHost mTabHost;
	private Intent mAIntent;
	private Intent mBIntent;
	private Intent mCIntent;
	private Intent mDIntent;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_already_sell);
		
        this.mAIntent = new Intent(this,Aty_Already_Sell_Selling.class);
        this.mBIntent = new Intent(this,Aty_Already_Sell_WaitShipment.class);
        this.mCIntent = new Intent(this,Aty_Already_Sell_WaitAffirm.class);
        this.mDIntent = new Intent(this,Aty_Already_Sell_Finish.class);
        
        setupIntent();
		
	}

	private void setupIntent() {
		this.mTabHost = getTabHost();
		TabHost localTabHost = this.mTabHost;

		localTabHost.addTab(buildTabSpec("A_TAB", R.string.already_sell_selling, this.mAIntent));

		localTabHost.addTab(buildTabSpec("B_TAB", R.string.already_sell_waitshipment, this.mBIntent));

		localTabHost.addTab(buildTabSpec("C_TAB",R.string.already_sell_waitaffirm,this.mCIntent));

		localTabHost.addTab(buildTabSpec("D_TAB", R.string.already_sell_finsh,this.mDIntent));


	}
	private TabHost.TabSpec buildTabSpec(String tag, int resLabel,final Intent content) {
		return this.mTabHost.newTabSpec(tag).setIndicator(getString(resLabel)).setContent(content);
	}

}
