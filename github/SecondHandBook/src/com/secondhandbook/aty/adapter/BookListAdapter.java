package com.secondhandbook.aty.adapter;
/**
 * @author MingLei Jia
 */
import java.io.BufferedInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;

import org.json.JSONArray;
import org.json.JSONException;

import com.secondhandbook.aty.R;
import com.secondhandbook.info.BookInfo;
import com.secondhandbook.util.ImageHelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BookListAdapter extends BaseAdapter {
	private JSONArray ja = null;
	private Context context = null;

	public BookListAdapter(Context context, JSONArray ja) {
		this.context = context;
		this.ja = ja;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return ja.length();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		try {
			return ja.getJSONObject(position);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(com.secondhandbook.aty.R.layout.aty_booklist_cell, null);
		}
		ImageView booklist_book = (ImageView) convertView.findViewById(R.id.iv_booklist);
		TextView bookname = (TextView) convertView.findViewById(R.id.tv_booklist_cell_bookname);
		TextView author = (TextView) convertView.findViewById(R.id.tv_booklist_cell_author);
		TextView oldprice = (TextView) convertView.findViewById(R.id.tv_booklist_cell_oldcost);
		TextView newprice = (TextView) convertView.findViewById(R.id.tv_booklist_cell_newcost);
		TextView neworold = (TextView) convertView.findViewById(R.id.tv_booklist_cell_neworold);
		Typeface face = Typeface.createFromAsset(context.getAssets(),"fonts/fangzhenghuali.ttf");
		neworold.setTypeface(face);
		oldprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		try {
			String bookName = ja.getJSONObject(position).getString(BookInfo.BOOKNAME);
			String bookAuthor = ja.getJSONObject(position).getString(BookInfo.BOOKAUTHOR);
			String bookOldPrice = ja.getJSONObject(position).getString(BookInfo.BOOKPRICE);
			String bookNewCost = ja.getJSONObject(position).getString(BookInfo.BOOKCOST);
			String bookNewOrOld = ja.getJSONObject(position).getString(BookInfo.BOOKNEWOROLD);

			bookname.setText(bookName);
			author.setText(bookAuthor);
			oldprice.setText(bookOldPrice);
			newprice.setText(bookNewCost);
			neworold.setText(bookNewOrOld);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//-----------------------------------------------------
		try {
			String bookcoverurl = ja.getJSONObject(position).getString(BookInfo.BOOKCOVERURL);
			ImageHelper.getInstance().displayImage(bookcoverurl, booklist_book);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//  Í¼Æ¬µÄ´¦Àí
		return convertView;
	}

}
