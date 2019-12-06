package com.sabakuch.epaper.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sabakuch.epaper.R;
import com.sabakuch.epaper.apiclass.APIAccess;
import com.sabakuch.epaper.apputils.SabaKuchParse;
import com.sabakuch.epaper.apputils.ServiceUrl;
import com.sabakuch.epaper.constants.Constants;
import com.sabakuch.epaper.entitymime.MultipartEntity;
import com.sabakuch.epaper.entitymimecontent.StringBody;
import com.sabakuch.epaper.serviceclasses.ServiceInterface;

public class SelectOtpActivity extends AppCompatActivity implements View.OnClickListener , ServiceInterface {
    String strMobile;
    private int urlIndex = 0;
    private MultipartEntity reqEntity;

    Button btnConfirm,btnSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_otp);
        btnSend = (Button) findViewById(R.id.btnSend);
        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        strMobile = getIntent().getStringExtra(Constants.Mobile);
        btnSend.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
    } @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSend:

                urlIndex = 1;
                APIAccess.fetchData(SelectOtpActivity.this, SelectOtpActivity.this,
                        SelectOtpActivity.this);

                break;
            case R.id.btnConfirm:
                urlIndex = 2;
                APIAccess.fetchData(SelectOtpActivity.this, SelectOtpActivity.this,
                        SelectOtpActivity.this);

                break;


        }
    }
    void sendMobileNumber() {
        try {
            StringBody emailid = new StringBody("");
            StringBody phoneid = new StringBody(strMobile);
            StringBody otpid = new StringBody("1");

            reqEntity = new MultipartEntity();
            reqEntity.addPart("otp", otpid);
            reqEntity.addPart("email", emailid);
            reqEntity.addPart("phone", phoneid);

        } catch (Exception e) {
            System.out.println("err" + e);
        }

    }
    void sendEmailNumber() {
        try {
            StringBody emailid = new StringBody(strMobile);
            StringBody phoneid = new StringBody("");
            StringBody otpid = new StringBody("1");

            reqEntity = new MultipartEntity();
            reqEntity.addPart("otp", otpid);
            reqEntity.addPart("email", emailid);
            reqEntity.addPart("phone", phoneid);

        } catch (Exception e) {
            System.out.println("err" + e);
        }

    }
    @Override
    public String httpPost() {
        String response = "";
        if (urlIndex == 1) {
            sendMobileNumber();
            response = APIAccess.openConnection(ServiceUrl.SABAKUCH_FORGOTPASS, reqEntity);
            System.out.println("@@@@@@@@@@@@@@@@@@@"+response);
        }
        else if (urlIndex == 2) {
            sendEmailNumber();
            response = APIAccess.openConnection(ServiceUrl.SABAKUCH_FORGOTPASS, reqEntity);
            System.out.println("@@@@@@@@@@@@@@@@@@@"+response);

        }
        return response;
    }

    @Override
    public String httpAfterPost(String str) {
        if (str != null) {
            if (urlIndex == 1) {
                if (SabaKuchParse.jsonStatus(str) == 0) {
                    Toast.makeText(SelectOtpActivity.this, SabaKuchParse.jsonErrorMessage(str),
                            Toast.LENGTH_SHORT).show();
                } else if (SabaKuchParse.jsonStatus(str) == 1) {
                    Toast.makeText(SelectOtpActivity.this, SabaKuchParse.jsonErrorMessage(str), Toast.LENGTH_SHORT).show();
                    Intent mIntent = new Intent(SelectOtpActivity.this, OtpActivity.class);
                    mIntent.putExtra(Constants.Mobile, strMobile);
                    mIntent.putExtra(Constants.Check, "1");
                    startActivity(mIntent);
                    finish();
                }
            }
            else if (urlIndex == 2) {
                if (SabaKuchParse.jsonStatus(str) == 0) {
                    Toast.makeText(SelectOtpActivity.this, SabaKuchParse.jsonErrorMessage(str),
                            Toast.LENGTH_SHORT).show();
                } else if (SabaKuchParse.jsonStatus(str) == 1) {
                    Toast.makeText(SelectOtpActivity.this, SabaKuchParse.jsonErrorMessage(str), Toast.LENGTH_SHORT).show();
                    Intent mIntent = new Intent(SelectOtpActivity.this, OtpActivity.class);
                    mIntent.putExtra(Constants.Mobile, strMobile);
                    mIntent.putExtra(Constants.Check, "2");
                    startActivity(mIntent);
                    finish();
                }
            }
        }
        return null;    }
}

