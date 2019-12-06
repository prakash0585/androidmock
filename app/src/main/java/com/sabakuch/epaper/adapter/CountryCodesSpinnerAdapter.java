package com.sabakuch.epaper.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sabakuch.epaper.R;
import com.sabakuch.epaper.data.CountryCodeData;

import java.util.ArrayList;

/**
 * Created by dell on 17-Apr-17.
 */
public class CountryCodesSpinnerAdapter extends BaseAdapter {


    Context cxt;
    ArrayList<CountryCodeData.Country> arrList;

    public CountryCodesSpinnerAdapter(Context cxt, ArrayList<CountryCodeData.Country> countryList) {
        this.cxt = cxt;
        this.arrList = countryList;
    }

    @Override
    public int getCount() {
        return arrList.size();
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
        View v = convertView;
        if (v == null) {
            v = View.inflate(cxt, R.layout.list_item, null);
        }
        TextView tvCountyName = (TextView) v.findViewById(R.id.tvListItem);
        tvCountyName.setText("+ " + arrList.get(position).getPhonecode());
        return v;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = View.inflate(cxt, R.layout.list_item, null);
        }
        TextView tvCountyName = (TextView) v.findViewById(R.id.tvListItem);
        tvCountyName.setText(" +" + arrList.get(position).getPhonecode() + " " + arrList.get(position).getCountryname());
        return v;
    }
}


