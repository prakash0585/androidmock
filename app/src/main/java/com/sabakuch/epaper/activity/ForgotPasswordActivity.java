package com.sabakuch.epaper.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sabakuch.epaper.R;
import com.sabakuch.epaper.apiclass.APIAccess;
import com.sabakuch.epaper.apputils.SabaKuchParse;
import com.sabakuch.epaper.apputils.ServiceUrl;
import com.sabakuch.epaper.constants.Constants;
import com.sabakuch.epaper.entitymime.MultipartEntity;
import com.sabakuch.epaper.entitymimecontent.StringBody;
import com.sabakuch.epaper.serviceclasses.ServiceInterface;
import com.sabakuch.epaper.utils.OtpResendListener;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener,ServiceInterface, OtpResendListener {
    private MultipartEntity reqEntity;

    String email, verification, password, confirmPassword;
    OtpResendListener mOtpResendListener = null;
    String strOtp;
    private LinearLayout ll1, ll2;
    private EditText etNumber, etVerification, etPassword, etConfirmPassword;
    private Button btnSend, btnConfirm;
    private int urlIndex = 0;
    private String streditText;
    private int RequestCodeOTP = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initializeComponent();

    }
    private void initializeComponent() {
        ll1 = (LinearLayout) findViewById(R.id.ll1);
        ll2 = (LinearLayout) findViewById(R.id.ll2);
        etNumber = (EditText) findViewById(R.id.etNumber);
        etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnSend = (Button) findViewById(R.id.btnSend);
        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        btnSend.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
        OtpActivity mOtpActivity = new OtpActivity();
        mOtpActivity.setListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnSend:

                if (etNumber.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(this, "Please Enter Mobile Number or Email Id", Toast.LENGTH_SHORT).show();
                    etNumber.requestFocus();
                }else {
                    streditText = etNumber.getText().toString();
                    urlIndex = 1;
                    APIAccess.fetchData(ForgotPasswordActivity.this, ForgotPasswordActivity.this,
                            ForgotPasswordActivity.this);
                }
                break;
        }
    }

    void sendMobileNumber() {
        try {
            StringBody emailid = new StringBody(streditText);

            reqEntity = new MultipartEntity();
            reqEntity.addPart("email", emailid);

        } catch (Exception e) {
            System.out.println("err" + e);
        }

    }





    @Override
    public void resend(String otp) {

    }

    @Override
    public String httpPost() {
        String response = "";
        if (urlIndex == 1) {
            sendMobileNumber();
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
                    Toast.makeText(ForgotPasswordActivity.this, SabaKuchParse.jsonErrorMessage(str),
                            Toast.LENGTH_SHORT).show();
                } else if (SabaKuchParse.jsonStatus(str) == 1) {
                    //Toast.makeText(ForgotPasswordActivity.this, SabaKuchParse.jsonErrorMessage(str), Toast.LENGTH_SHORT).show();
                    Intent mIntent = new Intent(ForgotPasswordActivity.this, SelectOtpActivity.class);
                    mIntent.putExtra(Constants.Mobile, streditText);
                    startActivity(mIntent);
                    finish();
                }
            }
        }
        return null;

    }
}


