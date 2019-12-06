package com.sabakuch.epaper.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sabakuch.epaper.R;
import com.sabakuch.epaper.apiclass.APIAccess;
import com.sabakuch.epaper.apputils.CommonUtils;
import com.sabakuch.epaper.apputils.FontManager;
import com.sabakuch.epaper.apputils.SabaKuchParse;
import com.sabakuch.epaper.apputils.ServiceUrl;
import com.sabakuch.epaper.constants.Constants;
import com.sabakuch.epaper.entitymime.MultipartEntity;
import com.sabakuch.epaper.entitymimecontent.StringBody;
import com.sabakuch.epaper.serviceclasses.ServiceInterface;
import com.sabakuch.epaper.utils.OtpResendListener;

public class OtpActivity extends AppCompatActivity implements  ServiceInterface {
    private MultipartEntity reqEntity;

    String strOtpFinal, strOtp1, strOtp2, strOtp3, strOtp4, strOtp5, strOtp6;
    OtpResendListener mOtpResendListener;
    String strMobile,strCheck;
    private EditText etOtp1, etOtp2, etOtp3, etOtp4, etOtp5, etOtp6;
    private TextView tvSend, tvResend;
    private int urlIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        initializeComponent();

    }

    private void initializeComponent() {
        strMobile = getIntent().getStringExtra(Constants.Mobile);
        strCheck = getIntent().getStringExtra(Constants.Check);
        etOtp1 = (EditText) findViewById(R.id.etOtp1);
        etOtp2 = (EditText) findViewById(R.id.etOtp2);
        etOtp3 = (EditText) findViewById(R.id.etOtp3);
        etOtp4 = (EditText) findViewById(R.id.etOtp4);
        etOtp5 = (EditText) findViewById(R.id.etOtp5);
        etOtp6 = (EditText) findViewById(R.id.etOtp6);
        tvSend = (TextView) findViewById(R.id.tvSend);
        tvResend = (TextView) findViewById(R.id.tvResend);
        tvSend.setTypeface(FontManager.getTypeface(this, FontManager.FONTAWESOME));

        tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkNSendOtp();
            }
        });

        tvResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                urlIndex = 2;
                APIAccess.fetchData(OtpActivity.this, OtpActivity.this,
                        OtpActivity.this);
            }
        });

        setTextWatcher();
    }

    public void setListener(OtpResendListener mListener) {
        this.mOtpResendListener = mListener;
    }


    private void setTextWatcher() {
        etOtp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0)
                    etOtp2.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etOtp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0)
                    etOtp3.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etOtp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0)
                    etOtp4.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        etOtp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0)
                    etOtp5.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etOtp5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0)
                    etOtp6.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etOtp6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                CommonUtils.hideKeyboard(OtpActivity.this);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void checkNSendOtp() {

        if (etOtp1.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Please enter Otp Correctly", Toast.LENGTH_SHORT).show();
            etOtp1.requestFocus();
        } else if (etOtp2.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Please enter Otp Correctly", Toast.LENGTH_SHORT).show();
            etOtp2.requestFocus();
        } else if (etOtp3.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Please enter Otp Correctly", Toast.LENGTH_SHORT).show();
            etOtp3.requestFocus();
        } else if (etOtp4.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Please enter Otp Correctly", Toast.LENGTH_SHORT).show();
            etOtp4.requestFocus();
        } else if (etOtp5.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Please enter Otp Correctly", Toast.LENGTH_SHORT).show();
            etOtp5.requestFocus();
        } else if (etOtp6.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Please enter Otp Correctly", Toast.LENGTH_SHORT).show();
            etOtp6.requestFocus();
        } else {
            strOtp1 = etOtp1.getText().toString().trim();
            strOtp2 = etOtp2.getText().toString().trim();
            strOtp3 = etOtp3.getText().toString().trim();
            strOtp4 = etOtp4.getText().toString().trim();
            strOtp5 = etOtp5.getText().toString().trim();
            strOtp6 = etOtp6.getText().toString().trim();
            strOtpFinal = strOtp1 + strOtp2 + strOtp3 + strOtp4 + strOtp5 + strOtp6;
            urlIndex = 1;
            APIAccess.fetchData(OtpActivity.this, OtpActivity.this,
                    OtpActivity.this);

        }

    }


    void sendMobileNumber() {

        try {
            if (strCheck.equals("1"))
            {
                StringBody otpid = new StringBody("1");
                StringBody emailid = new StringBody("");
                StringBody phoneid = new StringBody(strMobile);

                reqEntity = new MultipartEntity();
                reqEntity.addPart("otp", otpid);
                reqEntity.addPart("email", emailid);
                reqEntity.addPart("phone", phoneid);
            }
            else {
                StringBody otpid = new StringBody("1");
                StringBody emailid = new StringBody(strMobile);
                StringBody phoneid = new StringBody("");

                reqEntity = new MultipartEntity();
                reqEntity.addPart("otp", otpid);
                reqEntity.addPart("email", emailid);
                reqEntity.addPart("phone", phoneid);
            }
        } catch (Exception e) {
            System.out.println("err" + e);
        }

    }

    void sendMobileNumberWithOtp() {

        try {
            if (strCheck.equals("1"))
            {
                StringBody otpsendid = new StringBody("1");
                StringBody emailid = new StringBody("");
                StringBody phoneid = new StringBody(strMobile);
                StringBody otpid = new StringBody(strOtpFinal);

                reqEntity = new MultipartEntity();
                reqEntity.addPart("otp_send", otpsendid);
                reqEntity.addPart("email", emailid);
                reqEntity.addPart("phone", phoneid);
                reqEntity.addPart("otp", otpid);
            }
            else
            {
                StringBody otpsendid = new StringBody("1");
                StringBody emailid = new StringBody(strMobile);
                StringBody phoneid = new StringBody("");
                StringBody otpid = new StringBody(strOtpFinal);

                reqEntity = new MultipartEntity();
                reqEntity.addPart("otp_send", otpsendid);
                reqEntity.addPart("email", emailid);
                reqEntity.addPart("phone", phoneid);
                reqEntity.addPart("otp", otpid);
            }


        } catch (Exception e) {
            System.out.println("err" + e);
        }



    }




    @Override
    public String httpPost() {
        String response = "";
        if (urlIndex == 1) {
            sendMobileNumberWithOtp();
            response = APIAccess.openConnection(ServiceUrl.SABAKUCH_FORGOTPASS, reqEntity);
            System.out.println("@@@@@@@@@@@@@@@@@@@"+response);

        }
        else if (urlIndex == 2) {
            sendMobileNumber();
            response = APIAccess.openConnection(ServiceUrl.SABAKUCH_FORGOTPASS, reqEntity);
            System.out.println("@@@@@@@@@@@@@@@@@@@"+response);

        }
        return response;    }

    @Override
    public String httpAfterPost(String str) {
        if (str != null) {
            if (urlIndex == 1) {
                if (SabaKuchParse.jsonStatus(str) == 0) {
                    Toast.makeText(OtpActivity.this, SabaKuchParse.jsonErrorMessage(str),
                            Toast.LENGTH_SHORT).show();
                } else if (SabaKuchParse.jsonStatus(str) == 1) {
                    Toast.makeText(OtpActivity.this, SabaKuchParse.jsonErrorMessage(str),
                            Toast.LENGTH_SHORT).show();
                    Intent mIntent = new Intent(this, ChangePasswordActivity.class);
                    mIntent.putExtra(Constants.Mobile, strMobile);
                    mIntent.putExtra(Constants.Check, strCheck);
                    startActivity(mIntent);
                    finish();
                }
            }
            else if (urlIndex == 2) {
                if (SabaKuchParse.jsonStatus(str) == 0) {
                    Toast.makeText(OtpActivity.this, SabaKuchParse.jsonErrorMessage(str),
                            Toast.LENGTH_SHORT).show();
                } else if (SabaKuchParse.jsonStatus(str) == 1) {
                    Toast.makeText(OtpActivity.this, SabaKuchParse.jsonErrorMessage(str),
                            Toast.LENGTH_SHORT).show();

                }
            }
        }
        return null;    }
}

