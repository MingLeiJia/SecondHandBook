package com.secondhandbook.util.textcheck;

import com.secondhandbook.aty.R;

import android.text.Editable;
import android.widget.Button;

public class NewWatcher extends MyTextWatcher{

	public int sum;
	public Button btn;
	public NewWatcher(int sum , Button btn)
	{
		this.sum = sum;
		this.btn = btn;
	}
	private boolean check = false, check_ = true;

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {

		if (s.length() > 0) {
			check = true;
		} else if(s.length() == 0){
			check_ = true;							
			check = false;
		}
		if(check){
			if(check_){
				EdtCheckEntity.checkNum++;
				check_ = false;						
				if(EdtCheckEntity.checkNum == sum){
					btn.setEnabled(true);
					btn.setBackgroundResource(R.drawable.btn_default);
				}
			}
		}else{
			EdtCheckEntity.checkNum--;
			if(EdtCheckEntity.checkNum < sum){
				btn.setEnabled(false);
				btn.setBackgroundResource(R.drawable.btn_press);
			}
		}
	}
	
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

	@Override
	public void afterTextChanged(Editable s) {}
	
}
