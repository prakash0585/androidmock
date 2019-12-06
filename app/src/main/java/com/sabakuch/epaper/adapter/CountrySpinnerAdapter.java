package com.sabakuch.epaper.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sabakuch.epaper.R;
import com.sabakuch.epaper.data.CountryResponse;

public class CountrySpinnerAdapter extends BaseAdapter{

	
	Context cxt;
//	ArrayList<Country>counList;
	ArrayList<CountryResponse.Country>counList;
	
	public CountrySpinnerAdapter(Context cxt,ArrayList<CountryResponse.Country> subcategory) {
		// TODO Auto-generated constructor stub
		this.cxt=cxt;
		this.counList=subcategory;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return counList.size();
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
		{/*simple_spinner_dropdown_item*/
			v=View.inflate(cxt, R.layout.spinner_item, null);
		}
		TextView tvCountyName=(TextView)v.findViewById(R.id.text1);
	//	tvCountyName.setText(counList.get(position).strName);
		CountryResponse.Country mSubcategory = counList.get(position);
		tvCountyName.setText(mSubcategory.getName());
		return v;
	}

}
