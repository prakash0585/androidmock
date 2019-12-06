package com.sabakuch.epaper.apiclass;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.sabakuch.epaper.R;
import com.sabakuch.epaper.apputils.CheckInternetConection;
import com.sabakuch.epaper.apputils.CommonUtils;
import com.sabakuch.epaper.asynctask.BaseAsynkTask;
import com.sabakuch.epaper.entitymime.MultipartEntity;
import com.sabakuch.epaper.serviceclasses.ServiceInterface;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class APIAccess {
	
	public  static String openConnection(String url, MultipartEntity reqEntity) {
		String result = "";

		HttpClient httpclient = new DefaultHttpClient();
		try {
			HttpPost httppost = new HttpPost(url);
			//httppost.setHeader("Accept", "application/json");
			CommonUtils.LogMsg(APIAccess.class.getSimpleName(),"URL===" + url);

			httppost.setEntity(reqEntity);

			HttpResponse response = httpclient.execute(httppost);
			HttpEntity resEntity = response.getEntity();

		//	httppost.setHeader("Content-type", "application/json");


			
			result = EntityUtils.toString(resEntity/*, HTTP.UTF_8*/);
			CommonUtils.LogMsg(APIAccess.class.getSimpleName(),url+ "\nResponse  " + result);
			
			// }
		} catch (Exception ex) {
			// System.out.println("Respons nothing: ");
		} finally {
			try {
				// deleteFileRecords();
				httpclient.getConnectionManager().shutdown();
			} catch (Exception ignore) {
			}
		}
		return result;
	}

	public static void fetchData(ServiceInterface base, Context cxt, Activity act) {
		// TODO Auto-generated method stub
		
		try{
	if (CheckInternetConection.isInternetConnection(cxt)) {

			BaseAsynkTask asynkTask = new BaseAsynkTask(base, act);
			asynkTask.execute("");
		} else {

			Toast.makeText(cxt, cxt.getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();

		/*	DialogClasses.showDialogFinishActivity(
					"Internet Connection Failed!", "connection error", act);*/
		}
		}catch(Exception e){
			
		//	Toast.makeText(cxt, "Internet Connection Failed!", Toast.LENGTH_SHORT).show();
		}
	}

	public static void fetchData(ServiceInterface base, Context cxt) {
		// TODO Auto-generated method stub

		try{
			if (CheckInternetConection.isInternetConnection(cxt)) {

				BaseAsynkTask asynkTask = new BaseAsynkTask(base, cxt);
				asynkTask.execute("");
			} else {

				Toast.makeText(cxt, cxt.getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();

		/*	DialogClasses.showDialogFinishActivity(
					"Internet Connection Failed!", "connection error", act);*/
			}
		}catch(Exception e){

			//	Toast.makeText(cxt, "Internet Connection Failed!", Toast.LENGTH_SHORT).show();
		}
	}


	public static void fetchPagingData(ServiceInterface base,Context cxt,Activity act,boolean paging) {
		// TODO Auto-generated method stub
		if (CheckInternetConection.isInternetConnection(cxt)) {

			BaseAsynkTask asynkTask = new BaseAsynkTask(base, act,paging);
			asynkTask.execute("");

		} else {

			Toast.makeText(cxt, cxt.getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();

			/*DialogClasses.showDialogFinishActivity(
					"Internet Connection Failed!", "connection error", act);*/

		}
	}

	public static void fetchPagingDataIfOffline(ServiceInterface base,Context cxt,Activity act,boolean paging) {
		// TODO Auto-generated method stub
		if (CheckInternetConection.isInternetConnection(cxt)) {

			BaseAsynkTask asynkTask = new BaseAsynkTask(base, act,paging);
			asynkTask.execute("");

		} else {

			//Toast.makeText(cxt, "Internet Connection Failed!", Toast.LENGTH_SHORT).show();

			/*DialogClasses.showDialogFinishActivity(
					"Internet Connection Failed!", "connection error", act);*/

		}
	}
	
}
