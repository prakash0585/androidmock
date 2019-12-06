package com.sabakuch.epaper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.sabakuch.epaper.R;
import com.sabakuch.epaper.apiclass.APIAccess;
import com.sabakuch.epaper.apputils.CommonUtils;
import com.sabakuch.epaper.apputils.SabaKuchParse;
import com.sabakuch.epaper.apputils.ServiceUrl;
import com.sabakuch.epaper.entitymime.MultipartEntity;
import com.sabakuch.epaper.entitymimecontent.StringBody;
import com.sabakuch.epaper.serviceclasses.ServiceInterface;

import java.util.regex.Pattern;


public class ContactUsActivity extends Activity implements OnClickListener, ServiceInterface
{
	private Button btnSubmit;
	private TextView tvTerms;
	private EditText etUserName,etEmail,etComments;
	private String strUserName,strEmail,strComments;
	private MultipartEntity reqEntity;
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_contact_us);
		CommonUtils.setTracking(ContactUsActivity.class.getSimpleName());
		setId();							

	}

	private void setId() {
		btnSubmit=(Button)findViewById(R.id.btn_submit);
		etUserName=(EditText)findViewById(R.id.et_user_name);
		etEmail=(EditText)findViewById(R.id.et_email);
		etComments=(EditText)findViewById(R.id.et_comment);
		tvTerms = (TextView) findViewById(R.id.tv_queries_link);
		btnSubmit.setOnClickListener(this);
		tvTerms.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setType("text/html");
				intent.putExtra(Intent.EXTRA_EMAIL, "info@sabakuch.com");
				startActivity(Intent.createChooser(intent, "Send Email"));
			}
		});
	}


	public void postLogin()
	{
		try
		{
			StringBody name=new StringBody(strUserName);
			StringBody email=new StringBody(strEmail);
			StringBody comment=new StringBody(strComments);
			StringBody captcha=new StringBody("5");

			reqEntity = new MultipartEntity();

			reqEntity.addPart("name", name);
			reqEntity.addPart("email", email);
			reqEntity.addPart("comment", comment);
			reqEntity.addPart("hidcaptcha", captcha);
			reqEntity.addPart("finalcaptcha", captcha);

		}
		catch(Exception e)
		{
			System.out.println("err" + e);
		}
	}



	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.btn_submit :
			if(etUserName.getText().toString().equalsIgnoreCase(""))
			{
				Toast.makeText(ContactUsActivity.this, "Please enter name.", Toast.LENGTH_SHORT).show();
				etUserName.requestFocus();
			}else if(etEmail.getText().toString().equalsIgnoreCase(""))
			{
				Toast.makeText(ContactUsActivity.this, "Please enter email.", Toast.LENGTH_SHORT).show();
				etEmail.requestFocus();
			}else if (!CommonUtils.isValidEmail(etEmail.getText().toString().trim())) {

				Toast.makeText(ContactUsActivity.this, "Please enter valid email.", Toast.LENGTH_SHORT).show();
				etEmail.requestFocus();
			}else if(etComments.getText().toString().equalsIgnoreCase(""))
			{
				Toast.makeText(ContactUsActivity.this, "Please enter comment.", Toast.LENGTH_SHORT).show();
				etComments.requestFocus();
			}else
			{
				strUserName=etUserName.getText().toString();
				strEmail=etEmail.getText().toString();
				strComments=etComments.getText().toString();

				APIAccess.fetchData(this, this, this);
			}
			break;

		}
	}
	@Override
	public String httpPost() {
		// TODO Auto-generated method stub
		String response="";

		postLogin();
		//http://m.sabakuch.info/api/login
		response=APIAccess.openConnection(ServiceUrl.SABAKUCH_CONTACT, reqEntity);

		return response;
	}

	@Override
	public String httpAfterPost(String str) {
		// TODO Auto-generated method stub
		if(str!=null)
		{
			if(SabaKuchParse.jsonStatus(str)==0)
			{
				Toast.makeText(ContactUsActivity.this, SabaKuchParse.jsonErrorMessage(str), Toast.LENGTH_SHORT).show();
			}
			else if(SabaKuchParse.jsonStatus(str)==1)
			{
				Toast.makeText(ContactUsActivity.this, SabaKuchParse.jsonErrorMessage(str), Toast.LENGTH_SHORT).show();
				finish();
			}

		}
		return null;
	}

}
