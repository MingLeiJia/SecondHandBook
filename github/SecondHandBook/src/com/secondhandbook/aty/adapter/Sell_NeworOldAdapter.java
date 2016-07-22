package com.secondhandbook.aty.adapter;
/**
 * @author MingLei Jia
 */
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

public class Sell_NeworOldAdapter extends BaseAdapter {

	private List<String> neworold = null;
	private Context context = null;

	public Sell_NeworOldAdapter(Context context, List<String> neworold) {
		this.context = context;
		this.neworold = neworold;
	}

	@Override
	public int getCount() {
		return neworold.size();
	}

	@Override
	public String getItem(int arg0) {
		return neworold.get(arg0);
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
					R.layout.aty_sell_neworold_cell, null);
		}

		TextView show = (TextView) convertView
				.findViewById(R.id.tv_sell_neworold_cell);
		String neworold1 = neworold.get(position);
		show.setText(neworold1);


		return convertView;
	}

}
