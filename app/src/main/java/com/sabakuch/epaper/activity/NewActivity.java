package com.sabakuch.epaper.activity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sabakuch.epaper.R;

import org.json.JSONException;
import org.json.JSONObject;

public class NewActivity extends AppCompatActivity

{
    private WebView MyWeb;
    private Toolbar mToolbar;
    ProgressBar pbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);




      // String strUserID = CommonUtils.getStringPreferences(NewActivity.this, Constants.USER_ID);
      //  String strUserName = CommonUtils.getStringPreferences(NewActivity.this, Constants.USER_NAME);

       // Toast.makeText(NewActivity.this, strUserID, Toast.LENGTH_SHORT).show();
       // Toast.makeText(NewActivity.this, strUserName, Toast.LENGTH_SHORT).show();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        pbar = (ProgressBar)findViewById(R.id.progressBar1);
        MyWeb = (WebView) findViewById(R.id.WebView);


        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.app_header);
        getSupportActionBar().setTitle("");
         new SignUpAsync().execute();





    }


    public class WebViewClient extends android.webkit.WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)

        {

            // TODO Auto-generated method stub
            view.loadUrl(url);
            return true;
        }
        @Override

        public void onPageFinished(WebView view, String url)
        {

            // TODO Auto-generated method stub

            super.onPageFinished(view, url);
            pbar.setVisibility(View.GONE);

        }

    }

    public class SignUpAsync extends AsyncTask<String, Void, String>

    {
        private ProgressDialog dialog;
        private String response = null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                dialog = new ProgressDialog(NewActivity.this);
                // dialog.setTitle("Please Wait..");
                dialog.setMessage("Loading...");
                dialog.setCancelable(false);
                dialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                response = CallSignUpService();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (dialog != null) {
                dialog.dismiss();

            }

            if (result == null) {

                Toast.makeText(getApplicationContext(),
                        "server problem please try again..", Toast.LENGTH_SHORT)
                        .show();

                return;
            }

            try
            {


                JSONObject respObj = new JSONObject(result);

                String responseresult = respObj.getString("success");

                if (responseresult != null && !responseresult.equals("") && responseresult.equals("1"))

                {

                    Toast.makeText(getApplicationContext(), "You have logged in successfully !", Toast.LENGTH_SHORT).show();


                    MyWeb.getSettings().setJavaScriptEnabled(true);
                    MyWeb.getSettings().setSaveFormData(true);
                    MyWeb.setWebViewClient(new WebViewClient());
                    MyWeb.loadUrl("https://sabakuch.com/elearning/mock/g-k-test/testquest/vinayyour?exam_id=8");



                   /* Intent intent = new Intent(NewActivity.this, WebVew.class);

                    startActivity(intent);*/


                }

                else
                {
                    Toast.makeText(getApplicationContext(), "You have  not logged in successfully !", Toast.LENGTH_SHORT).show();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        private String CallSignUpService() {
            String url ="https://sabakuch.com/login/login-ajax";
            Log.e("", "URL IS NOW " + url);

            try {
                //emailString = emailText.getText().toString();
                //passwordString = passwordText.getText().toString();
                HttpClientWritten client = new HttpClientWritten(url,NewActivity.this);
                client.connectForMultipart();
                client.addFormPart("username", "anurag");
                client.addFormPart("password", "anurag");

                client.finishMultipart();
                response = client.getResponse();
                System.out.println("Login response ###################################################"+response);
              //  System.out.println("email or password  ###################################################"+emailString+"      "+passwordString);


            } catch (Throwable t) {
                t.printStackTrace();
            }

            return response;
        }

    }


}
