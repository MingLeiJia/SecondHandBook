package com.secondhandbook.aty;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;

import com.secondhandbook.util.ScanBookInfo;
import com.secondhandbook.util.textcheck.NewWatcher;
import com.secondhandbook.aty.adapter.Sell_CategoryAdapter;
import com.secondhandbook.aty.adapter.Sell_NeworOldAdapter;
import com.secondhandbook.info.UserAction;
import com.secondhandbook.util.DoubanUtil;
import com.secondhandbook.util.Region;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class Aty_Sell extends Activity {

	private Spinner provinceSpinner = null;  //省级（省、直辖市）
	private Spinner citySpinner = null;     //地级市
	private Spinner countySpinner = null;    //县级（区、县、县级市）
	private ArrayAdapter<String> provinceAdapter = null;  //省级适配器
	private ArrayAdapter<String> cityAdapter = null;    //地级适配器
	private ArrayAdapter<String> countyAdapter = null;    //县级适配器
	private static int provincePosition = 3;

	private EditText bookname,bookprice,bookisbn,bookcost;
	private TextView category,neworold;
	private ProgressDialog mpd;;
	private Handler hd;
	private Button sell;
	private List<String> categoryList,categoryDetailList,neworoldList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_sell);

		initView();

		hd=	new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				ScanBookInfo book= (ScanBookInfo)msg.obj;
				//进度条消失
				mpd.dismiss();

				bookname.setText(book.getTitle());
				bookprice.setText(book.getPrice());
				bookisbn.setText(book.getISBN());

			}
		};
	}
	public void initView()
	{
		sell = (Button) findViewById(R.id.bn_sell_book);

		bookname = (EditText) findViewById(R.id.et_sell_bookname);
		bookprice = (EditText) findViewById(R.id.et_sell_price);
		bookisbn = (EditText) findViewById(R.id.et_sell_isbn);
		bookcost = (EditText) findViewById(R.id.et_sell_cost);
		category = (TextView) findViewById(R.id.tv_sell_category);
		neworold = (TextView) findViewById(R.id.tv_sell_neworold);
		bookname.addTextChangedListener(new NewWatcher(6,sell));
		bookprice.addTextChangedListener(new NewWatcher(6,sell));
		bookisbn.addTextChangedListener(new NewWatcher(6,sell));
		bookcost.addTextChangedListener(new NewWatcher(6,sell));
		category.addTextChangedListener(new NewWatcher(6,sell));
		neworold.addTextChangedListener(new NewWatcher(6,sell));

		category.setOnClickListener(new MyOnClickListener());
		neworold.setOnClickListener(new MyOnClickListener());
		sell.setOnClickListener(new MyOnClickListener());


		categoryList = new ArrayList<String>();
		categoryList.add("文史哲科");categoryList.add("经政法社");categoryList.add("自然科学");
		categoryList.add("现代科技");categoryList.add("杂志期刊");categoryList.add("艺术管理");
		categoryDetailList = new ArrayList<String>();
		categoryDetailList.add("文史哲科包括文学、历史、语言、哲学、科幻等类型书籍");
		categoryDetailList.add("经政法社包括经济、政治、法律、社会等类型书籍");
		categoryDetailList.add("自然科学包括力学、数学、物理学、天文学、生物学、农业、化学等类型书籍");
		categoryDetailList.add("现代科技包括计算机、交通、航空航天、环境、医药卫生、现代工业等类型书籍");
		categoryDetailList.add("艺术管理包括艺术、管理、生活、考试（考研、公务员）等类型书籍");
		categoryDetailList.add("杂志期刊包括期刊、杂志、报纸等类型书籍");
		neworoldList = new ArrayList<String>();
		neworoldList.add("全新");neworoldList.add("九成新");neworoldList.add("八成新");
		neworoldList.add("七成新");neworoldList.add("六成新");neworoldList.add("五成新");


		ImageView scan = (ImageView) findViewById(R.id.iv_sell_scanisbn_search);
		scan.setOnClickListener(new MyOnClickListener());
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setSpinner();
		//省级下拉框监听
		provinceSpinner.setOnItemSelectedListener(new MyOnItemSelectedListener());      
		//地级下拉监听
		citySpinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
	}
	/*
	 * 设置下拉框
	 */
	private void setSpinner()
	{        
		provinceSpinner = (Spinner)findViewById(R.id.spinner_province_sell);
		citySpinner = (Spinner)findViewById(R.id.spinner_city_sell);
		countySpinner = (Spinner)findViewById(R.id.spinner_county_sell);

		//绑定适配器和值
		provinceAdapter = new ArrayAdapter<String>(Aty_Sell.this,
				android.R.layout.simple_spinner_item, Region.province);
		provinceSpinner.setAdapter(provinceAdapter);
		provinceSpinner.setSelection(1,true);  //设置默认选中项，此处为默认选中第4个值

		cityAdapter = new ArrayAdapter<String>(Aty_Sell.this, 
				android.R.layout.simple_spinner_item, Region.city[1]);
		citySpinner.setAdapter(cityAdapter);
		citySpinner.setSelection(0,true);  //默认选中第0个

		countyAdapter = new ArrayAdapter<String>(Aty_Sell.this,
				android.R.layout.simple_spinner_item, Region.county[1][0]);
		countySpinner.setAdapter(countyAdapter);
		countySpinner.setSelection(0, true);


	}
	private class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener{

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			switch (parent.getId()) {
			case R.id.bn_sell_book:
				
				String Provinceselected = provinceSpinner.getSelectedItem().toString();
				String CitySelected = citySpinner.getSelectedItem().toString();
				String CountySelected = countySpinner.getSelectedItem().toString();
		        String region = Provinceselected+CitySelected+CountySelected;
				
				String isbn = bookisbn.getText().toString();
				String name = bookname.getText().toString();
				String oldprice = bookprice.getText().toString();
				String newprice = bookcost.getText().toString();
				String categoryname = category.getText().toString();
				String newold = neworold.getText().toString();

				UserAction ua = new UserAction(Aty_Sell.this);
				try {
					ua.publicbook(isbn, name, oldprice, newprice, 
							categoryname, newold, region, UserAction.ACTION_PUBLICBOOK, 
							new UserAction.SuccessCallback() {
								
								@Override
								public void onSuccess(String jsonResult) {
									// TODO Auto-generated method stub
									
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
			case R.id.spinner_province_sell:
				//position为当前省级选中的值的序号

				//将地级适配器的值改变为city[position]中的值
				cityAdapter = new ArrayAdapter<String>(
						Aty_Sell.this, android.R.layout.simple_spinner_item, Region.city[position]);
				// 设置二级下拉列表的选项内容适配器
				citySpinner.setAdapter(cityAdapter);
				provincePosition = position;    //记录当前省级序号，留给下面修改县级适配器时用

				break;
			case R.id.spinner_city_sell:
				countyAdapter = new ArrayAdapter<String>(Aty_Sell.this,
						android.R.layout.simple_spinner_item, Region.county[provincePosition][position]);
				countySpinner.setAdapter(countyAdapter);

				break;

			default:
				break;
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub

		}

	}
	private class MyOnClickListener implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.tv_sell_category:
				Sell_CategoryAdapter adapterCategory = new Sell_CategoryAdapter
				(Aty_Sell.this, categoryList,categoryDetailList);
				new AlertDialog.Builder(Aty_Sell.this)
				.setTitle("选择类别")
				.setAdapter(adapterCategory,
						new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						category.setText(categoryList.get(which));
					}
				}).create().show();
				break;
			case R.id.tv_sell_neworold:
				Sell_NeworOldAdapter adapterNewOrOld = new Sell_NeworOldAdapter(Aty_Sell.this, neworoldList);
				new AlertDialog.Builder(Aty_Sell.this)
				.setTitle("选择新旧程度")
				.setAdapter(adapterNewOrOld,
						new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						neworold.setText(neworoldList.get(which));
					}
				}).create().show();
				break;

			case R.id.iv_sell_scanisbn_search:				
				Intent openCameraIntent1 = new Intent(Aty_Sell.this,Aty_ScanBarCode.class);
				startActivityForResult(openCameraIntent1, 0);
				break;
			default:
				break;
			}
		}

	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//处理扫描结果（在界面上显示）
		if (resultCode == RESULT_OK) {
			Bundle bundle = data.getExtras();
			String scanResult = bundle.getString("result");
			mpd=new ProgressDialog(this);
			mpd.setMessage("请稍候，正在读取信息...");
			mpd.show();

			String urlstr="https://api.douban.com/v2/book/isbn/"+scanResult;
			Log.i("OUTPUT",urlstr);
			//扫到ISBN后，启动下载线程下载图书信息
			new DownloadThread(urlstr).start();
		}
	}
	private class DownloadThread extends Thread
	{
		String url=null;
		public DownloadThread(String urlstr)
		{
			url=urlstr;
		}
		public void run()
		{
			String result=DoubanUtil.Download(url);
			Log.i("OUTPUT", "download over");
			ScanBookInfo book=new DoubanUtil().parseBookInfo(result);
			Log.i("OUTPUT", "parse over");
			Log.i("OUTPUT",book.getSummary()+book.getAuthor());
			//给主线程UI界面发消息，提醒下载信息，解析信息完毕

			Message msg=Message.obtain();
			msg.obj=book;
			hd.sendMessage(msg);
			Log.i("OUTPUT","send over");
		}
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitBy2Click();
		}
		return false;
	}

	private static Boolean isExit = false;

	private void exitBy2Click() {
		Timer tExit = null;
		if (isExit == false) {
			isExit = true;
			Toast.makeText(this, "再按一次返回退出", Toast.LENGTH_SHORT).show();
			tExit = new Timer();
			tExit.schedule(new TimerTask() {

				@Override
				public void run() {
					isExit = false;
				}

			}, 2000);
		} else {
			finish();
			System.exit(0);
		}
	}
}
