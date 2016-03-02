package com.secondhandbook.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class MoneyTextView extends TextView{
	
	private static volatile Typeface moneyFont;
	
	public MoneyTextView(Context context) {
		this(context, null);
	}
	
	public MoneyTextView(Context context, AttributeSet attrs){
		super(context, attrs);
		setCustomFont(context);
	}

	private void setCustomFont(Context context) {
		if(moneyFont == null){
			synchronized(MoneyTextView.class){
				if(moneyFont == null){
					AssetManager assertMgr = context.getAssets();
					moneyFont = Typeface.createFromAsset(assertMgr, "fonts/money.otf");
				}
			}
		}
		setTypeface(moneyFont);
	}
}
