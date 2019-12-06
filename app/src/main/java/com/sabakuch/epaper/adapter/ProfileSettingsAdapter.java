package com.sabakuch.epaper.adapter;

import java.util.ArrayList;
import android.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ProfileSettingsAdapter extends BaseAdapter{

	
	Context cxt;
	ArrayList<String>counList;
	private LayoutInflater inflater;
	public ProfileSettingsAdapter(Context cxt,ArrayList<String> countryList) {
		this.cxt=cxt;
		inflater = LayoutInflater.from(cxt);
		this.counList=countryList;
	}

	@Override
	public int getCount() {
		return counList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View v=convertView;
		if(v==null)
		{
			v = inflater.inflate(R.layout.simple_spinner_dropdown_item, parent, false);
		}
		TextView tvCountyName=(TextView)v.findViewById(R.id.text1);
		tvCountyName.setText(counList.get(position));
		return v;
	}

}

















