package com.secondhandbook.aty.adapter;

import java.util.List;

import com.secondhandbook.aty.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Sell_CategoryAdapter extends BaseAdapter {

	private List<String> category = null;
	private List<String> categorydetail = null;
	private Context context = null;

	public Sell_CategoryAdapter(Context context, List<String> category , List<String> categorydetail) {
		this.context = context;
		this.category = category;
		this.categorydetail = categorydetail;
	}

	@Override
	public int getCount() {
		return category.size();
	}

	@Override
	public String getItem(int arg0) {
		return category.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.aty_sell_category_cell, null);
		}

		TextView show = (TextView) convertView
				.findViewById(R.id.tv_sell_category_cell);
		String categoryes = category.get(position);
		show.setText(categoryes);
		TextView showdetail = (TextView) convertView
				.findViewById(R.id.tv_sell_category_cell_detail);
		String categorydetails = categorydetail.get(position);
		showdetail.setText(categorydetails);


		return convertView;
	}

}
