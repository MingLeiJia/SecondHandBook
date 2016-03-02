package com.secondhandbook.aty;

import org.json.JSONException;
import org.json.JSONObject;

import com.secondhandbook.info.UserAction;
import com.secondhandbook.util.JsonTool;
import com.secondhandbook.util.MD5Tool;
import com.secondhandbook.util.TimeCountUtil;
import com.secondhandbook.util.textcheck.NewWatcher;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Aty_Register extends Activity{

	private EditText phone,email,nickname,pass,passagain,msgcode;
	private TextView protocol;
	private Button register,send_msgcode;
	private TimeCountUtil timeCountUtil;
	private ProgressDialog pdialog;
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
		public void onClick(View v) 
		{
			// TODO Auto-generated method stub
			switch (v.getId()) 
			{
			case R.id.tv_register_statements:
				Intent intent1 = new Intent(Aty_Register.this, Aty_Protocol.class);
				startActivity(intent1);
				intent1 = null;
				break;
			case R.id.bn_register_send_msg_code:
				timeCountUtil.start();
				break;

			case R.id.bn_register:

				pdialog=new ProgressDialog(Aty_Register.this);
				pdialog.setMessage("正在注册，请稍后...");
				pdialog.show();

				String phonenum = phone.getText().toString();
				String emailadd = email.getText().toString();
				String nickname1 = nickname.getText().toString();
				String pw = pass.getText().toString();
				String pwagin = passagain.getText().toString();
				String msgcode1 = msgcode.getText().toString();
				if(!phonenum.matches("\\d{11}"))
				{
					Toast.makeText(Aty_Register.this, "输入手机号有误！", Toast.LENGTH_SHORT).show();
					return;
				}
				if(!pw.matches("[0-9a-zA-Z@#$_]{6,15}"))
				{
					Toast.makeText(Aty_Register.this, "密码输入有误！", Toast.LENGTH_SHORT).show();
					return;
				}
				if(pw.equals(pwagin))
				{
					Toast.makeText(Aty_Register.this, "两次输入密码不匹配！", Toast.LENGTH_SHORT).show();
					return;
				}

				UserAction ua = new UserAction(Aty_Register.this);
				try {
					ua.register(msgcode1, nickname1, phonenum, MD5Tool.md5(pw), 
							emailadd, UserAction.ACTION_REGISTER, new UserAction.SuccessCallback() {

						@Override
						public void onSuccess(String jsonResult) {
							// TODO Auto-generated method stub
							pdialog.dismiss();

							JSONObject jsonobject;
							try {
								jsonobject = new JSONObject(jsonResult);
								JSONObject jsonresult = jsonobject.getJSONObject(JsonTool.JSON_RESULT_CODE);
								String status = jsonresult.getString("status");
								if(status.equals("1"))
								{

									Toast.makeText(Aty_Register.this, "恭喜你，注册成功！", Toast.LENGTH_SHORT).show();
									finish();
								}else if(status.equals("0"))
								{
									String reason = jsonresult.getString("reason");
									if(reason.equals("1"))
									{											
										Toast.makeText(Aty_Register.this, "对不起，密码错误！", Toast.LENGTH_SHORT).show();
									}else if(reason.equals("0"))
									{
										Toast.makeText(Aty_Register.this, "对不起，用户不存在！", Toast.LENGTH_SHORT).show();
									}
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}, new UserAction.FailCallback() {

						@Override
						public void onFail(int status, int reason) {
							// TODO Auto-generated method stub

						}
					});
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
		}
	}
}
