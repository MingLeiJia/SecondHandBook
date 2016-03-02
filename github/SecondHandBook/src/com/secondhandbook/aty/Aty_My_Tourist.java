package com.secondhandbook.aty;

import org.json.JSONException;
import org.json.JSONObject;

import com.secondhandbook.info.UserAction;
import com.secondhandbook.util.JsonTool;
import com.secondhandbook.util.MD5Tool;
import com.secondhandbook.util.textcheck.NewWatcher;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityGroup;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class Aty_My_Tourist extends Activity {

	private Button login;
	private EditText account,pass;
	private UserAction ua;
	private ProgressDialog pdialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_my_include);

		TextView register = (TextView) findViewById(R.id.tv_register);
		TextView forgetpass = (TextView) findViewById(R.id.tv_forget_password);

		login = (Button) findViewById(R.id.bn_login);
		login.setOnClickListener(new MyOnClickListener());

		account = (EditText) findViewById(R.id.et_login_account);
		pass = (EditText) findViewById(R.id.et_login_password);

		register.setOnClickListener(new MyOnClickListener());
		forgetpass.setOnClickListener(new MyOnClickListener());

		account.addTextChangedListener(new NewWatcher(2,login));
		pass.addTextChangedListener(new NewWatcher(2,login));


		ua = new UserAction(this);
	}

	private class MyOnClickListener implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stubo
			switch (v.getId()) {
			case R.id.tv_register:
				Intent intent1 = new Intent(Aty_My_Tourist.this, Aty_Register.class);
				startActivity(intent1);
				intent1 = null;
				break;
			case R.id.tv_forget_password:
				Intent intent2 = new Intent(Aty_My_Tourist.this, Aty_Forget_Pass_One.class);
				startActivity(intent2);
				intent2 = null;
				break;
			case R.id.bn_login:
				
				pdialog=new ProgressDialog(Aty_My_Tourist.this);
				pdialog.setMessage("正在登录，请稍后...");
				pdialog.show();
				
				String accountinfo = account.getText().toString().trim();
				String password = pass.getText().toString().trim();
				if (accountinfo.equals("") || TextUtils.isEmpty(accountinfo)) {
					Toast.makeText(Aty_My_Tourist.this, "账号不能为空", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				if (password.equals("") || TextUtils.isEmpty(password)) {
					Toast.makeText(Aty_My_Tourist.this, "密码不能为空", Toast.LENGTH_SHORT)
							.show();
					return;
				}/*
				if(!accountinfo.contains("@") && !accountinfo.matches("\\d{11}"))
				{
					Toast.makeText(Aty_My_Tourist.this, "账号输入有误！", Toast.LENGTH_SHORT).show();
					return;
				}else if(!password.matches("[0-9a-zA-Z@#$_]{6,15}")){
					Toast.makeText(Aty_My_Tourist.this, "密码输入有误！", Toast.LENGTH_SHORT).show();
					return;
				}*/
				
				try {
					ua.login(accountinfo, MD5Tool.md5(password), 
							UserAction.ACTION_LOGIN, new UserAction.SuccessCallback() {
								
								@Override
								public void onSuccess(String jsonResult) {
									// TODO Auto-generated method stub
									pdialog.dismiss();
									System.out.println("#########"+jsonResult);
									
									JSONObject jsonobject;
									try {
										jsonobject = new JSONObject(jsonResult);
										JSONObject jsonresult = jsonobject.getJSONObject(JsonTool.JSON_RESULT_CODE);
										String status = jsonresult.getString("status");
										if(status.equals("1"))
										{
											
											Toast.makeText(Aty_My_Tourist.this, "恭喜你，登录成功！", Toast.LENGTH_SHORT).show();
											findViewById(R.id.ll).setVisibility(View.GONE);
										}else if(status.equals("0"))
										{
											String reason = jsonresult.getString("reason");
											if(reason.equals("1"))
											{											
												Toast.makeText(Aty_My_Tourist.this, "对不起，密码错误！", Toast.LENGTH_SHORT).show();
											}else if(reason.equals("0"))
											{
												Toast.makeText(Aty_My_Tourist.this, "对不起，用户不存在！", Toast.LENGTH_SHORT).show();
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
									pdialog.dismiss();
									Toast.makeText(Aty_My_Tourist.this, "对不起，无法连接到网络！", Toast.LENGTH_SHORT).show();
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
