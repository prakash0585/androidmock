package com.sabakuch.epaper.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.sabakuch.epaper.R;
import com.sabakuch.epaper.constants.Constants;
import com.sabakuch.epaper.data.DashboardData;
import com.sabakuch.epaper.data.DashboardHeaderData;
import com.sabakuch.epaper.fragment.TestReviewFragment;


public class DashboardExpListAdapter extends BaseExpandableListAdapter {

	private Context mcontext;
	private List<DashboardHeaderData> mlistDataHeader; // header titles
	// child data in format of header title, child title
	private HashMap<String, String> mlistDataChild;

	public DashboardExpListAdapter(Context context,
								   List<DashboardHeaderData> listDataHeader, HashMap<String, String> listChildData) {
		this.mcontext = context;
		this.mlistDataHeader = listDataHeader;
		this.mlistDataChild = listChildData;
	}



	@Override
	public Object getChild(int groupPosition, int childPosititon) {
		return this.mlistDataChild.get(this.mlistDataHeader.get(groupPosition).getLastPaperId());
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition,
							 boolean isLastChild, View convertView, ViewGroup parent) {

		final String childText = (String) getChild(groupPosition, childPosition);

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this.mcontext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(
					R.layout.row_dashboard, null);
		}

		TextView txtListChild = (TextView) convertView
				.findViewById(R.id.tv_list_data);
		TextView tvTestReview = (TextView) convertView
				.findViewById(R.id.tv_test_review);

		tvTestReview.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Fragment fragment = new TestReviewFragment();
				Bundle bundle = new Bundle();
				bundle.putString(Constants.LAST_PAPER_ID, mlistDataHeader.get(groupPosition).getLastPaperId());
				fragment.setArguments(bundle);
				if(mcontext!=null) {
					setFragment(fragment);
				}
			}
		});
		txtListChild.setText(childText);
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return 1;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return this.mlistDataHeader.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return this.mlistDataHeader.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		DashboardHeaderData header = (DashboardHeaderData) getGroup(groupPosition);
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this.mcontext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(
					R.layout.row_dashboard_header, null);
		}

		TextView lblListHeader = (TextView) convertView
				.findViewById(R.id.tv_hotel_overview_title);
		lblListHeader.setTypeface(null, Typeface.NORMAL);
		int grpos=groupPosition+1;
		lblListHeader.setText("Set:"+grpos+" "+header.getExamName());

		if (isExpanded) {
			lblListHeader.setTextColor(ContextCompat.getColor(mcontext,R.color.colorPrimary));
			lblListHeader.setBackgroundResource(R.drawable.gray_button_corner_bg);
		} else {
			lblListHeader.setTextColor(ContextCompat.getColor(mcontext,R.color.text_color));
			lblListHeader.setBackgroundResource(R.drawable.white_button_corner_bg);

		}

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	protected void setFragment(Fragment fragment) {
		FragmentTransaction ftTransaction = ((FragmentActivity)mcontext).getSupportFragmentManager().beginTransaction();
		ftTransaction.replace(R.id.container_body, fragment)
				.addToBackStack(null);
		ftTransaction.commit();
	}
}
