/**
 * @author MingLei Jia
 */
package com.secondhandbook.aty;

import com.secondhandbook.util.TimeCountUtil;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Aty_Forget_Pass_One extends Activity{

	private TimeCountUtil timeCountUtil;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_forget_password_one);
		
		Button sendagain = (Button) findViewById(R.id.bn_send_msg_code);
		timeCountUtil = new TimeCountUtil(this, 60000, 1000, sendagain);
		sendagain.setText("获取验证码");
		
		sendagain.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				timeCountUtil.start();
			}
		});
	}

}
