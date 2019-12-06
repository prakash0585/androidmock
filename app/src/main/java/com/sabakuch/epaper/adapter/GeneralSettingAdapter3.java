package com.sabakuch.epaper.adapter;

import java.util.ArrayList;


import android.R;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GeneralSettingAdapter3 extends BaseAdapter{
	
	Context cxt;
	ArrayList<String>timecounList;
	
	

	public GeneralSettingAdapter3(Context cxt,ArrayList<String> timeList) {
		// TODO Auto-generated constructor stub
		
		this.cxt = cxt;
		this.timecounList = timeList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return timecounList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v=convertView;
		if(v==null)
		{
			v=View.inflate(cxt, R.layout.simple_spinner_dropdown_item, null);
		}
		TextView spTimeZone=(TextView)v.findViewById(R.id.text1);
	//	spTimeZone.setText(timecounList.get(position).strTimezonename);
		spTimeZone.setText(timecounList.get(position));
		return v;
	}

}
