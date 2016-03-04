package com.secondhandbook.aty;

import java.util.Timer;
import java.util.TimerTask;

import com.secondhandbook.util.Region;
import com.secondhandbook.util.ScanBookInfo;
import com.secondhandbook.info.BookInfo;
import com.secondhandbook.info.UserAction;
import com.secondhandbook.util.Config;
import com.secondhandbook.util.DoubanUtil;

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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class Aty_Buy extends Activity {

	private Spinner provinceSpinner = null;  //省级（省、直辖市）
    private Spinner citySpinner = null;     //地级市
    private Spinner countySpinner = null;    //县级（区、县、县级市）
    private ArrayAdapter<String> provinceAdapter = null;  //省级适配器
    private ArrayAdapter<String> cityAdapter = null;    //地级适配器
    private ArrayAdapter<String> countyAdapter = null;    //县级适配器
    private static int provincePosition = 3;
    private EditText bookISBN;
    private ProgressDialog mpd;;
    private Handler hd;
    private boolean isNewest,isLowest;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_buy);
		
		initView();
		
        hd=	new Handler(){
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);
                ScanBookInfo book= (ScanBookInfo)msg.obj;

                //进度条消失
                mpd.dismiss();
                           
                bookISBN.setText(book.getISBN());

            }
        };
	}
	public void initView()
	{
		bookISBN = (EditText) findViewById(R.id.et_input_book_name);
		ImageView scanbarcode = (ImageView) findViewById(R.id.iv_scanisbn_search);		
		scanbarcode.setOnClickListener(new MyOnClickListener());
		ImageButton search = (ImageButton) findViewById(R.id.ibn_search_booklist);
		search.setOnClickListener(new MyOnClickListener());
		isLowest = false; isNewest = false;
		CheckBox newest = (CheckBox) findViewById(R.id.checkBox_new_or_old);
		CheckBox lowest = (CheckBox) findViewById(R.id.checkBox_price);
		newest.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
		lowest.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
	}
	private class MyOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.iv_scanisbn_search:
				Intent openCameraIntent = new Intent(Aty_Buy.this,Aty_ScanBarCode.class);
				startActivityForResult(openCameraIntent, 0);
				break;
			case R.id.ibn_search_booklist:
				
				new AlertDialog.Builder(Aty_Buy.this)
				.setTitle("友情提示")
				.setMessage("输入地址得到更准确的结果哦~")
				.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener(){

					public void onClick(DialogInterface dialog,int which) {
						// TODO Auto-generated method stub
						String input = bookISBN.getText().toString();
						String isbn,bookname;
						
						String Provinceselected = provinceSpinner.getSelectedItem().toString();
						String CitySelected = citySpinner.getSelectedItem().toString();
						String CountySelected = countySpinner.getSelectedItem().toString();
				        String region = Provinceselected+CitySelected+CountySelected;
				        				
						if(input.matches("9\\d{12}"))
						{
							isbn = input;
							bookname = null;
						}else
						{
							isbn = null;
							bookname = input;
						}
						if(input.length() > 0 )
						{
							Intent intent1 = new Intent(Aty_Buy.this, Aty_BookList.class);
							Bundle bundle = new Bundle();
							bundle.putInt(Config.KEY_ACTION,UserAction.ACTION_SEARCHBOOK);
							bundle.putString(BookInfo.BOOKISBN,isbn);
							bundle.putString(BookInfo.BOOKNAME, bookname);
							bundle.putString(BookInfo.REGION, region);
							bundle.putBoolean(BookInfo.BOOKNEWEST, isNewest);
							bundle.putBoolean(BookInfo.BOOKLOWEST, isLowest);
							intent1.putExtras(bundle);
							startActivity(intent1);
							intent1 = null;					
						}else
						{
							Toast.makeText(Aty_Buy.this, "请输入书名或者ISBN号查询", Toast.LENGTH_SHORT).show();
						}
					}
				}).create().show();
				

				break;
			default:
				break;
			}
		}
		
	}
	private class MyOnCheckedChangeListener implements OnCheckedChangeListener
	{

		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			// TODO Auto-generated method stub
			if(isChecked){				
				switch (buttonView.getId()) {
				case R.id.checkBox_new_or_old:
					isNewest = true;
					break;
				case R.id.checkBox_price:
					isLowest = true;
					break;
				default:
					break;
			}
			}else{
				switch (buttonView.getId()) {
				case R.id.checkBox_new_or_old:
					isNewest = false;
					break;
				case R.id.checkBox_price:
					isLowest = false;
					break;
				default:
					break;
			}
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
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setSpinner();
        //省级下拉框监听
        provinceSpinner.setOnItemSelectedListener(new MyOnItemSelectedListener());      
        //地级下拉监听
        citySpinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
        /*获取选定的值
        Provinceselected = provinceSpinner.getSelectedItem().toString();
        CitySelected = citySpinner.getSelectedItem().toString();
        CountySelected = countySpinner.getSelectedItem().toString();
        */
	}
	
    /*
     * 设置下拉框
     */
    private void setSpinner()
    {        
        provinceSpinner = (Spinner)findViewById(R.id.spinner_province);
        citySpinner = (Spinner)findViewById(R.id.spinner_city);
        countySpinner = (Spinner)findViewById(R.id.spinner_county);
        
        //绑定适配器和值
        provinceAdapter = new ArrayAdapter<String>(Aty_Buy.this,
                android.R.layout.simple_spinner_item, Region.province);
        provinceSpinner.setAdapter(provinceAdapter);
        provinceSpinner.setSelection(1,true);  //设置默认选中项，此处为默认选中第4个值
        
        cityAdapter = new ArrayAdapter<String>(Aty_Buy.this, 
                android.R.layout.simple_spinner_item, Region.city[1]);
        citySpinner.setAdapter(cityAdapter);
        citySpinner.setSelection(0,true);  //默认选中第0个
        
        countyAdapter = new ArrayAdapter<String>(Aty_Buy.this,
                android.R.layout.simple_spinner_item, Region.county[1][0]);
        countySpinner.setAdapter(countyAdapter);
        countySpinner.setSelection(0, true);
               

    }
    
    private class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener{

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			switch (parent.getId()) {
			case R.id.spinner_province:
                //position为当前省级选中的值的序号
                
                //将地级适配器的值改变为city[position]中的值
                cityAdapter = new ArrayAdapter<String>(
                        Aty_Buy.this, android.R.layout.simple_spinner_item, Region.city[position]);
                // 设置二级下拉列表的选项内容适配器
                citySpinner.setAdapter(cityAdapter);
                provincePosition = position;    //记录当前省级序号，留给下面修改县级适配器时用
          
                break;
			case R.id.spinner_city:
                countyAdapter = new ArrayAdapter<String>(Aty_Buy.this,
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
