package com.secondhandbook.aty;

import com.secondhandbook.util.TimeCountUtil;
import com.secondhandbook.util.textcheck.NewWatcher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Aty_Register extends Activity{

	private EditText phone,email,nickname,pass,passagain,msgcode;
	private TextView protocol;
	private Button register,send_msgcode;
	private TimeCountUtil timeCountUtil;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_register);
		
		initView();

	}

	public void initView()
	{
		register = (Button) findViewById(R.id.bn_register);
		send_msgcode = (Button) findViewById(R.id.bn_register_send_msg_code);
		protocol = (TextView) findViewById(R.id.tv_register_statements);
		
		phone = (EditText) findViewById(R.id.et_register_phone);
		email = (EditText) findViewById(R.id.et_register_email);
		nickname = (EditText) findViewById(R.id.et_register_name);
		pass = (EditText) findViewById(R.id.et_register_password);
		passagain = (EditText) findViewById(R.id.et_register_pass_again);
		msgcode = (EditText) findViewById(R.id.et_register_msgcode);
		phone.addTextChangedListener(new NewWatcher(6,register));
		email.addTextChangedListener(new NewWatcher(6,register));
		nickname.addTextChangedListener(new NewWatcher(6,register));
		pass.addTextChangedListener(new NewWatcher(6,register));
		passagain.addTextChangedListener(new NewWatcher(6,register));
		msgcode.addTextChangedListener(new NewWatcher(6,register));
		
		timeCountUtil = new TimeCountUtil(this, 60000, 1000, send_msgcode);
		//timeCountUtil.start();
		send_msgcode.setText("获取验证码");
		send_msgcode.setOnClickListener(new MyOnClickListener());
		protocol.setOnClickListener(new MyOnClickListener());
		
		
	}
	private class MyOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.tv_register_statements:
				Intent intent1 = new Intent(Aty_Register.this, Aty_Protocol.class);
				startActivity(intent1);
				intent1 = null;
				break;
			case R.id.bn_register_send_msg_code:
				timeCountUtil.start();
				break;
				
			case R.id.bn_register:
				
				break;
			default:
				break;
			}
		}
		
	}
}
