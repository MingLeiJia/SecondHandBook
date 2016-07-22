/**
 * @author MingLei Jia
 */
package com.secondhandbook.aty;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import com.secondhandbook.util.ScanBookInfo;
import com.secondhandbook.util.textcheck.NewWatcher;
import com.secondhandbook.aty.adapter.Sell_CategoryAdapter;
import com.secondhandbook.aty.adapter.Sell_NeworOldAdapter;
import com.secondhandbook.info.BookInfo;
import com.secondhandbook.info.SPKey;
import com.secondhandbook.info.UserAction;
import com.secondhandbook.util.Config;
import com.secondhandbook.util.DoubanUtil;
import com.secondhandbook.util.Information;
import com.secondhandbook.util.JsonTool;
import com.secondhandbook.util.Region;
import com.secondhandbook.util.SPUtils;

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

	private Spinner provinceSpinner = null;  //ʡ����ʡ��ֱϽ�У�
	private Spinner citySpinner = null;     //�ؼ���
	private Spinner countySpinner = null;    //�ؼ��������ء��ؼ��У�
	private ArrayAdapter<String> provinceAdapter = null;  //ʡ��������
	private ArrayAdapter<String> cityAdapter = null;    //�ؼ�������
	private ArrayAdapter<String> countyAdapter = null;    //�ؼ�������
	private static int provincePosition = 3;

	private EditText bookname,bookprice,bookisbn,bookcost;
	private TextView category,neworold;
	private ProgressDialog mpd;;
	private Handler hd;
	private Button sell;
	private List<String> categoryList,categoryDetailList,neworoldList;
	private ProgressDialog pdialog;
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
				//��������ʧ
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
		categoryList.add("��ʷ�ܿ�");categoryList.add("��������");categoryList.add("��Ȼ��ѧ");
		categoryList.add("�ִ��Ƽ�");categoryList.add("��־�ڿ�");categoryList.add("��������");
		categoryDetailList = new ArrayList<String>();
		categoryDetailList.add("��ʷ�ܿư�����ѧ����ʷ�����ԡ���ѧ���ƻõ������鼮");
		categoryDetailList.add("��������������á����Ρ����ɡ����������鼮");
		categoryDetailList.add("��Ȼ��ѧ������ѧ����ѧ������ѧ������ѧ������ѧ��ũҵ����ѧ�������鼮");
		categoryDetailList.add("�ִ��Ƽ��������������ͨ�����պ��졢������ҽҩ�������ִ���ҵ�������鼮");
		categoryDetailList.add("���������������������������ԣ����С�����Ա���������鼮");
		categoryDetailList.add("��־�ڿ������ڿ�����־����ֽ�������鼮");
		neworoldList = new ArrayList<String>();
		neworoldList.add("ȫ��");neworoldList.add("�ų���");neworoldList.add("�˳���");
		neworoldList.add("�߳���");neworoldList.add("������");neworoldList.add("�����");


		ImageView scan = (ImageView) findViewById(R.id.iv_sell_scanisbn_search);
		scan.setOnClickListener(new MyOnClickListener());
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
	}
	/*
	 * ����������
	 */
	private void setSpinner()
	{        
		provinceSpinner = (Spinner)findViewById(R.id.spinner_province_sell);
		citySpinner = (Spinner)findViewById(R.id.spinner_city_sell);
		countySpinner = (Spinner)findViewById(R.id.spinner_county_sell);

		//����������ֵ
		provinceAdapter = new ArrayAdapter<String>(Aty_Sell.this,
				android.R.layout.simple_spinner_item, Region.province);
		provinceSpinner.setAdapter(provinceAdapter);
		provinceSpinner.setSelection(1,true);  //����Ĭ��ѡ����˴�ΪĬ��ѡ�е�4��ֵ

		cityAdapter = new ArrayAdapter<String>(Aty_Sell.this, 
				android.R.layout.simple_spinner_item, Region.city[1]);
		citySpinner.setAdapter(cityAdapter);
		citySpinner.setSelection(0,true);  //Ĭ��ѡ�е�0��

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

			case R.id.spinner_province_sell:
				//positionΪ��ǰʡ��ѡ�е�ֵ�����

				//���ؼ���������ֵ�ı�Ϊcity[position]�е�ֵ
				cityAdapter = new ArrayAdapter<String>(
						Aty_Sell.this, android.R.layout.simple_spinner_item, Region.city[position]);
				// ���ö��������б��ѡ������������
				citySpinner.setAdapter(cityAdapter);
				provincePosition = position;    //��¼��ǰʡ����ţ����������޸��ؼ�������ʱ��

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
				.setTitle("ѡ�����")
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
				.setTitle("ѡ���¾ɳ̶�")
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
				
			case R.id.bn_sell_book:
									
				String token = (String) SPUtils.getParam(Aty_Sell.this, SPKey.TOKEN, "hello");
				if(token.equals("hello") || token.equals(null)){
					new AlertDialog.Builder(Aty_Sell.this)
					.setTitle("������ʾ")
					.setMessage("��ǰû�е�¼���޷������鼮��")
					.setCancelable(false)
					.setNegativeButton("������", null)
					.setPositiveButton("ȥ��¼", new android.content.DialogInterface.OnClickListener(){

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Toast.makeText(Aty_Sell.this, "���л����ҵĿ�ʼ��¼��", Toast.LENGTH_SHORT).show();
						}
						
					}).create()
					.show();
				}else{
				new AlertDialog.Builder(Aty_Sell.this)
				.setTitle("������ʾ")
				.setMessage("���õ�����������Ŷ~")
				.setCancelable(false)
				.setNegativeButton("ȥ����", null)
				.setPositiveButton("���ú���", new android.content.DialogInterface.OnClickListener(){

					public void onClick(DialogInterface dialog,int which) {
						// TODO Auto-generated method stub
						
						pdialog=new ProgressDialog(Aty_Sell.this);
						pdialog.setMessage("���ڷ����أ��԰�����Ӵ~");
						pdialog.show();
						
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

						int userid = (Integer) SPUtils.getParam(Aty_Sell.this, SPKey.USERID, -1);
						UserAction ua = new UserAction(Aty_Sell.this);
						try {
							ua.publicbook(userid, isbn, name, oldprice, newprice, 
									categoryname, newold, region, UserAction.ACTION_PUBLICBOOK, 
									new UserAction.SuccessCallback() {
										
										@Override
										public void onSuccess(String jsonResult) {
											// TODO Auto-generated method stub
											pdialog.dismiss();
											System.out.println(jsonResult);
											try {
												JSONObject jsonobject = new JSONObject(jsonResult);
												JSONObject jsonresult = jsonobject.getJSONObject(JsonTool.JSON_RESULT_CODE);
												String status = jsonresult.getString(Information.STATUS);
												if(status.equals(Information.SUCCESS))
												{

													new AlertDialog.Builder(Aty_Sell.this)
													.setTitle("������ʾ")
													.setMessage("��ϲ���������ɹ�����")
													.setCancelable(false)
													.setPositiveButton("֪����", new android.content.DialogInterface.OnClickListener(){

														public void onClick(DialogInterface dialog,int which) {
															// TODO Auto-generated method stub
															
														}
													}).create().show();
												}else if(status.equals(Information.FAIL))
												{
													new AlertDialog.Builder(Aty_Sell.this)
													.setTitle("������ʾ")
													.setMessage("�Բ������������鼮��Ϣ����ȷ��")
													.setCancelable(false)
													.setPositiveButton("֪����", new android.content.DialogInterface.OnClickListener(){

														public void onClick(DialogInterface dialog,int which) {
															// TODO Auto-generated method stub
															
														}
													}).create().show();
												}
												bookname.setText("");
												bookprice.setText("");
												bookisbn.setText("");
												bookcost.setText("");
												category.setText("");
												neworold.setText("");
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
											Toast.makeText(Aty_Sell.this, "�Բ����޷����ӵ���������", Toast.LENGTH_SHORT).show();
										}
									});
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}).create().show();
				}
				
				
				break;
			default:
				break;
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
