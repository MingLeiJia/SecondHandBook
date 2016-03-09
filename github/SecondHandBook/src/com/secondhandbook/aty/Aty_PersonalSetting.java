/**
 * @author MingLei Jia
 */
package com.secondhandbook.aty;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class Aty_PersonalSetting extends Activity{



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_personalsetting);
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
