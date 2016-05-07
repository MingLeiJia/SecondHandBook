/**
 * @author MingLei Jia
 */
package com.secondhandbook.aty;

import com.secondhandbook.info.SPKey;
import com.secondhandbook.util.SPUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Aty_PersonalSetting extends Activity{


	private Button exit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_personalsetting);
		
		initView();
		exit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SPUtils.setParam(Aty_PersonalSetting.this, SPKey.TOKEN,  "hello");
				findViewById(R.id.ll_my).setVisibility(View.GONE);
				finish();
			}
			
		});
	}

	private void initView(){
		exit = (Button) findViewById(R.id.bn_setting_exit);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.personal_setting_edit, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		if (id == R.id.setting_edit)
		{
			Intent intent1 = new Intent(Aty_PersonalSetting.this, Aty_PersonalSetting_Edit.class);
			startActivity(intent1);
			finish();
			intent1 = null;
		}
		return super.onOptionsItemSelected(item);
	}
}
