/**
 * @author MingLei Jia
 */
package com.secondhandbook.aty;

import com.secondhandbook.info.AccountInfo;
import com.secondhandbook.info.BookInfo;
import com.secondhandbook.info.SPKey;
import com.secondhandbook.util.DoubanUtil;
import com.secondhandbook.util.ImageHelper;
import com.secondhandbook.util.Information;
import com.secondhandbook.util.SPUtils;
import com.secondhandbook.util.ScanBookInfo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class Aty_Book_Detail extends Activity {

	private Handler hd;
	private ImageView image;
	private TextView neworold, bookname, author, publisher, sellername, newprice;
	private ProgressDialog pdialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_book_detail);

		initView();

		Intent intent = getIntent();
		final String bookisbn = intent.getStringExtra(Information.BOOKISBN);
		final String newold = intent.getStringExtra(BookInfo.BOOKNEWOROLD);
		final String price = intent.getStringExtra(BookInfo.BOOKCOST);
		int userid = (Integer) SPUtils.getParam(Aty_Book_Detail.this, SPKey.USERID, -1);
		final String bookid = intent.getStringExtra(BookInfo.ID);
		final String imageurl = intent.getStringExtra(BookInfo.BOOKCOVERURL);
		final String booknameString = intent.getStringExtra(BookInfo.BOOKNAME);
		final String authorString = intent.getStringExtra(BookInfo.BOOKAUTHOR);
		final String publisherString = intent.getStringExtra(BookInfo.PUBLISHER);

  
		bookname.setText(booknameString);
		author.setText(authorString);
		publisher.setText(publisherString);
		neworold.setText(newold);
		newprice.setText(price);
		sellername.setText(userid+"号卖书郎");
		ImageHelper.getInstance().displayImage(imageurl, image);


	}
	private void initView(){
		/*
		pdialog=new ProgressDialog(Aty_Book_Detail.this);
		pdialog.setMessage("请稍候，正在读取书籍信息...");
		pdialog.show();
		 */
		image = (ImageView) findViewById(R.id.iv_bookdetail_cover);
		neworold = (TextView) findViewById(R.id.tv_bookdetail_new_or_old);
		bookname = (TextView) findViewById(R.id.bookdetail_bookname);
		author = (TextView) findViewById(R.id.bookdetail_author);
		publisher = (TextView) findViewById(R.id.bookdetail_public_company);
		sellername = (TextView) findViewById(R.id.tv_bookdetail_sellername);
		newprice = (TextView) findViewById(R.id.tv_newprice);				

	}

}
