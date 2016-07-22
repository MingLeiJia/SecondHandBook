/**
 * 
 */
package com.secondhandbook.util.textcheck;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;

/**
 * @author MingLei Jia
 */
public abstract class MyTextWatcher implements TextWatcher{
	
	public static MyTextWatcher instance;
	public static Context context;
	
	public MyTextWatcher(){
		
	}
	
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		
	}
	
	@Override
	public void afterTextChanged(Editable s) {
		
	}
	
}
