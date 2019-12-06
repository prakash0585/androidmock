package com.sabakuch.epaper.activity;


import android.content.Context;

import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;

public class HttpClientWritten {
	private String url;
    private HttpURLConnection con;
    private OutputStream os;
    Context context = null;
    public static final int TIME_OUT_CONNECTION = 50000;
	public static final int TIME_OUT_SOCKET_LIMIT = 120000;	
    
	private String delimiter = "--";
    private String boundary =  "SwA"+ Long.toString(System.currentTimeMillis())+"SwA";

	public HttpClientWritten(String url, Context context) {
		this.url = url;
		this.context = context;
	}
	
	public byte[] downloadImage(String imgName) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			System.out.println("URL ["+url+"] - Name ["+imgName+"]");
			
			HttpURLConnection con = (HttpURLConnection) ( new URL(url)).openConnection();
			con.setRequestMethod("POST");
			con.setDoInput(true);
			con.setDoOutput(true);
			con.connect();
			con.getOutputStream().write( ("name=" + imgName).getBytes());
			
	 		InputStream is = con.getInputStream();
			byte[] b = new byte[1024];
			while ( is.read(b) != -1)
				baos.write(b);
			con.disconnect();
		}
		catch(Throwable t) {
			t.printStackTrace();
		}
		return baos.toByteArray();
	}
	public void connectForMultipart() throws Exception {
		con = (HttpURLConnection) ( new URL(url)).openConnection();
		con.setRequestMethod("POST");
		con.setDoInput(true);
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, TIME_OUT_CONNECTION);
		// Set the default socket timeout (SO_TIMEOUT) 
		// in milliseconds which is the timeout for waiting for data.		
		HttpConnectionParams.setSoTimeout(httpParameters, TIME_OUT_SOCKET_LIMIT);
		con.setDoOutput(true);
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Accept-Charset", "UTF-8");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
		con.connect();
		os = con.getOutputStream();
	}
	
	public void addFormPart(String paramName, String value) throws Exception {
		writeParamData(paramName, value);
	}
	
	public void addFilePart(String paramName, String fileName, byte[] data) throws Exception {
		os.write( (delimiter + boundary + "\r\n").getBytes());
		os.write( ("Content-Disposition: form-data; name=\"" + paramName +  "\"; filename=\"" + fileName + "\"\r\n"  ).getBytes());
		os.write( ("Content-Type: application/octet-stream\r\n"  ).getBytes());
		os.write( ("Content-Transfer-Encoding: binary\r\n"  ).getBytes());
		os.write("\r\n".getBytes());
   
		os.write(data);
		
		os.write("\r\n".getBytes());
	}
	
	public void finishMultipart() throws Exception {
		os.write( (delimiter + boundary + delimiter + "\r\n").getBytes());
	}
	
	
	public String getResponse() throws Exception {
		
		String responseData = null;
		try {
			StringBuilder stringBuilder = new StringBuilder();

			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			String line = null;
			while ((line = reader.readLine())!= null) {       
				stringBuilder.append(line);
			}                 			
			responseData = stringBuilder.toString();
			con.disconnect();
		}catch (ConnectTimeoutException e) {
			e.printStackTrace();
			//Toast.makeText(context, context.getString(R.string.Alert_Timeout_Msg), Toast.LENGTH_SHORT).show();
		
	    }catch (SocketTimeoutException e) {
			e.printStackTrace();
			//Toast.makeText(context, context.getString(R.string.Alert_Timeout_Msg), Toast.LENGTH_SHORT).show();

					
		}catch (UnknownHostException e) {
			e.printStackTrace();
			//Toast.makeText(context, context.getString(R.string.Network_Connection_Error_Msg), Toast.LENGTH_SHORT).show();

		
		}catch (SocketException e) {
			e.printStackTrace();
			//Toast.makeText(context, context.getString(R.string.Network_Connection_Error_Msg), Toast.LENGTH_SHORT).show();


		}catch(Exception e){
			e.printStackTrace();
			//Toast.makeText(context, context.getString(R.string.Network_Connection_Error_Msg), Toast.LENGTH_SHORT).show();

				
		}
		return responseData;
	}
	

	
	private void writeParamData(String paramName, String value) throws Exception {
		
		
		os.write( (delimiter + boundary + "\r\n").getBytes());
		os.write( "Content-Type: text/plain\r\n".getBytes());
		os.write( ("Content-Disposition: form-data; name=\"" + paramName + "\"\r\n").getBytes());;
		os.write( ("\r\n" + value + "\r\n").getBytes());
			
		
	}
}
