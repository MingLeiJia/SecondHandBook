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

	private Spinner provinceSpinner = null;  //ʡ����ʡ��ֱϽ�У�
    private Spinner citySpinner = null;     //�ؼ���
    private Spinner countySpinner = null;    //�ؼ��������ء��ؼ��У�
    private ArrayAdapter<String> provinceAdapter = null;  //ʡ��������
    private ArrayAdapter<String> cityAdapter = null;    //�ؼ�������
    private ArrayAdapter<String> countyAdapter = null;    //�ؼ�������
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

                //��������ʧ
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
				.setTitle("������ʾ")
				.setMessage("�����ַ�õ���׼ȷ�Ľ��Ŷ~")
				.setNegativeButton("ȥ����", null)
				.setPositiveButton("���ú���", new android.content.DialogInterface.OnClickListener(){

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
							Toast.makeText(Aty_Buy.this, "��������������ISBN�Ų�ѯ", Toast.LENGTH_SHORT).show();
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
		//����ɨ�������ڽ�������ʾ��
		if (resultCode == RESULT_OK) {
			Bundle bundle = data.getExtras();
			String scanResult = bundle.getString("result");
			
	        mpd=new ProgressDialog(this);
	        mpd.setMessage("���Ժ����ڶ�ȡ��Ϣ...");
	        mpd.show();
	        
	        String urlstr="https://api.douban.com/v2/book/isbn/"+scanResult;
	        Log.i("OUTPUT",urlstr);
	        //ɨ��ISBN�����������߳�����ͼ����Ϣ
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
            //�����߳�UI���淢��Ϣ������������Ϣ��������Ϣ���

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
        //ʡ�����������
        provinceSpinner.setOnItemSelectedListener(new MyOnItemSelectedListener());      
        //�ؼ���������
        citySpinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
        /*��ȡѡ����ֵ
        Provinceselected = provinceSpinner.getSelectedItem().toString();
        CitySelected = citySpinner.getSelectedItem().toString();
        CountySelected = countySpinner.getSelectedItem().toString();
        */
	}
	
    /*
     * ����������
     */
    private void setSpinner()
    {        
        provinceSpinner = (Spinner)findViewById(R.id.spinner_province);
        citySpinner = (Spinner)findViewById(R.id.spinner_city);
        countySpinner = (Spinner)findViewById(R.id.spinner_county);
        
        //����������ֵ
        provinceAdapter = new ArrayAdapter<String>(Aty_Buy.this,
                android.R.layout.simple_spinner_item, Region.province);
        provinceSpinner.setAdapter(provinceAdapter);
        provinceSpinner.setSelection(1,true);  //����Ĭ��ѡ����˴�ΪĬ��ѡ�е�4��ֵ
        
        cityAdapter = new ArrayAdapter<String>(Aty_Buy.this, 
                android.R.layout.simple_spinner_item, Region.city[1]);
        citySpinner.setAdapter(cityAdapter);
        citySpinner.setSelection(0,true);  //Ĭ��ѡ�е�0��
        
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
                //positionΪ��ǰʡ��ѡ�е�ֵ�����
                
                //���ؼ���������ֵ�ı�Ϊcity[position]�е�ֵ
                cityAdapter = new ArrayAdapter<String>(
                        Aty_Buy.this, android.R.layout.simple_spinner_item, Region.city[position]);
                // ���ö��������б��ѡ������������
                citySpinner.setAdapter(cityAdapter);
                provincePosition = position;    //��¼��ǰʡ����ţ����������޸��ؼ�������ʱ��
          
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
			Toast.makeText(this, "�ٰ�һ�η����˳�", Toast.LENGTH_SHORT).show();
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
