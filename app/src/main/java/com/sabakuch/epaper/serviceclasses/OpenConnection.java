package com.sabakuch.epaper.serviceclasses;

import android.util.Log;

import com.sabakuch.epaper.apputils.CommonUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class OpenConnection {

	public static String callUrl(String url) {

		String response = "";
		 
		try {
			HttpClient httpclient = new DefaultHttpClient();
			CommonUtils.LogMsg(OpenConnection.class.getSimpleName(),"URL======================" + url);
			HttpGet httpget = new HttpGet(url);
			httpget.setHeader("Accept", "application/json");
			HttpResponse httpresponse = httpclient.execute(httpget);
			HttpEntity entity = httpresponse.getEntity();

			response = EntityUtils.toString(entity).trim();
			CommonUtils.LogMsg(OpenConnection.class.getSimpleName(),url+"\nResponse ======" + response.trim());

			
			  InputStream webs = entity.getContent(); try{ BufferedReader
			  reader = new BufferedReader(new InputStreamReader(webs,"iso-8859-1"), 8 );
//				Log.e(OpenConnection.class.getSimpleName(),"Res : " +reader.readLine()); //test.setText(reader.readLine());
			  webs.close(); 
			  }catch(Exception e) {
			  
				  Log.e("Error in conversion: ", e.toString()); }
			 
			return response;
		} catch (Exception e) {
			Log.e("Error in connection: ", e.toString());
		}
		return response;
	}

	public static String callDeleteUrl(String url) {
		String response = "";
		try {
			HttpClient httpclient = new DefaultHttpClient();
			Log.e(OpenConnection.class.getSimpleName(),"URL======================" + url);
			HttpDelete httpDelete = new HttpDelete(url);
			HttpResponse httpresponse = httpclient.execute(httpDelete);
			HttpEntity entity = httpresponse.getEntity();

			response = EntityUtils.toString(entity).trim();
			System.out.println("Response ======" + response.trim());


			InputStream webs = entity.getContent(); try{ BufferedReader
					reader = new BufferedReader(new InputStreamReader(webs,"iso-8859-1"), 8 );
				System.out.println("Res : " +reader.readLine()); //test.setText(reader.readLine());
				webs.close();
			}catch(Exception e) {

				Log.e("Error in conversion: ", e.toString()); }

			return response;
		} catch (Exception e) {
			Log.e("Error in connection: ", e.toString());
		}
		return response;
	}

}
