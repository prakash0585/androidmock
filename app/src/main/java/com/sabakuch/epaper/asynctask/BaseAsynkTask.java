package com.sabakuch.epaper.asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.sabakuch.epaper.apputils.CommonUtils;
import com.sabakuch.epaper.serviceclasses.ServiceInterface;


public class BaseAsynkTask extends AsyncTask<String,String,String>{

	private ServiceInterface mBase;
	private Context mContext;
	private Exception mExcep;
	private ProgressDialog pb=null;
	private String res="";
	private boolean pagination=false;
	
	public BaseAsynkTask(ServiceInterface base, Context con) {
		this.mBase = base;
		this.mContext = con;
	}
	
	public BaseAsynkTask(ServiceInterface base, Context con,boolean pagination)
	{
		this.mBase=base;
		this.mContext=con;
		this.pagination=pagination;
		
	}
	
	public BaseAsynkTask(ServiceInterface base) {
		this.mBase = base;
	}
	
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		try {
			if (mContext != null) {
				if(pagination)
				{
				}else
				{
				pb = ProgressDialog.show(mContext,"","Processing...");

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected String doInBackground(String... params) {
		res =  mBase.httpPost();
		return res;
	}
	
	@Override
	protected void onPostExecute(String result) {
		mBase.httpAfterPost(result);
		if (mContext != null) {
			try {
				((Activity) mContext).runOnUiThread(new Runnable() {
					public void run() {

						try {
							if (pagination) {

							} else {
								pb.dismiss();
								pb = null;
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				});
			}catch (Exception e){

			}
		}
		super.onPostExecute(result);

		CommonUtils.LogMsg(BaseAsynkTask.class.getSimpleName(),"Data : ==============" + result);

	}
	
	
	

}
