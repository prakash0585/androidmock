package com.sabakuch.epaper.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.sabakuch.epaper.data.SignupData;
import com.sabakuch.epaper.entitymime.MultipartEntity;
import com.sabakuch.epaper.entitymimecontent.StringBody;
import com.sabakuch.epaper.serviceclasses.ServiceInterface;
import com.sabakuch.epaper.utils.OtpResendListener;


/**
 * Created by dell on 06-Sep-17.
 */
public class OtpSignup extends AppCompatActivity implements  ServiceInterface {
    private MultipartEntity reqEntity;

    String strOtpFinal, strOtp1, strOtp2, strOtp3, strOtp4, strOtp5, strOtp6;
    String strFname, strUserName, strPass, date, strGender, lati, longi,locat,source;
    OtpResendListener mOtpResendListener;
    private EditText etOtp1, etOtp2, etOtp3, etOtp4, etOtp5, etOtp6;
    private TextView tvSend, tvResend;
    private int urlIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_signup);
        strFname = getIntent().getStringExtra("fname");
        strUserName = getIntent().getStringExtra("mobile");
        strPass = getIntent().getStringExtra("password");
        source = getIntent().getStringExtra("sourse_signup");
        strGender = getIntent().getStringExtra("gender");

        initializeComponent();

    }

    private void initializeComponent() {

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
                APIAccess.fetchData(OtpSignup.this, OtpSignup.this,
                        OtpSignup.this);
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
                CommonUtils.hideKeyboard(OtpSignup.this);
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
            APIAccess.fetchData(OtpSignup.this, OtpSignup.this, OtpSignup.this);

        }

    }





    public void postSignup() {
        try {

            StringBody fname = new StringBody(strFname);
            //  StringBody lname = new StringBody(strLname);
            StringBody username = new StringBody(strUserName);
            //  StringBody email = new StringBody(strEmail);
            StringBody password = new StringBody(strPass);
            //  StringBody cpassword = new StringBody(strConfirm);
           // StringBody dob = new StringBody(date);
            StringBody source = new StringBody("android_emock");
            StringBody gender = new StringBody(strGender);
          //  StringBody latitude = new StringBody(lati);
          //  StringBody longitude = new StringBody(longi);
          //  StringBody locatation = new StringBody(locat);
            StringBody OtpFinal = new StringBody(strOtpFinal);

            reqEntity = new MultipartEntity();

            reqEntity.addPart("name", fname);
            //     reqEntity.addPart("lname", lname);
            reqEntity.addPart("mobile", username);
            //      reqEntity.addPart("email", email);
            reqEntity.addPart("password", password);
            //     reqEntity.addPart("cpassword", cpassword);
           // reqEntity.addPart("dob", dob);
            reqEntity.addPart("sourse_signup", source);
            reqEntity.addPart("gender", gender);
            //reqEntity.addPart("lat", latitude);
            //reqEntity.addPart("lon", longitude);
            //reqEntity.addPart("location", locatation);
            reqEntity.addPart("otp", OtpFinal);
        } catch (Exception e) {
            System.out.println("err" + e);
        }

    }

    @Override
    public String httpPost() {
        String response = "";
        if (urlIndex == 1) {
            postSignup();
            //String response=APIAccess.openConnection("http://sabakuch.info/m/api/signup/", reqEntity);
            response = APIAccess.openConnection(ServiceUrl.SABAKUCH_SIGNUP, reqEntity);
            CommonUtils.LogMsg(OtpSignup.class.getSimpleName(), "request" + response);

            System.out.println("#################################"+response);

        } 
        return response;
    }

    @Override
    public String httpAfterPost(String str) {
        // TODO Auto-generated method stub
        if (str != null) {
            if (urlIndex == 1) {
                if (SabaKuchParse.jsonStatus(str) == 1) {



                    SignupData obj=SabaKuchParse.parseSignupdata(str);

                    CommonUtils.saveStringPreferences(OtpSignup.this, Constants.USER_ID, obj.strUserid);
                    CommonUtils.saveStringPreferences(OtpSignup.this,Constants.USER_NAME, strUserName);
                    CommonUtils.saveStringPreferences(OtpSignup.this, Constants.FNAME, strFname);

                    CommonUtils.saveStringPreferences(OtpSignup.this,Constants.SERVER_KEY, CommonUtils.ServerKey(obj.strUserid, strUserName));

                    Intent intent = new Intent(OtpSignup.this, MainActivity.class);
                    startActivity(intent);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    CommonUtils.saveLoginPref(OtpSignup.this, Constants.IS_LOGIN, true);
                    finish();


                } else if (SabaKuchParse.jsonStatus(str) == 0) {
                    Toast.makeText(OtpSignup.this, SabaKuchParse.jsonErrorMessage(str), Toast.LENGTH_SHORT).show();
                }
            } 
        }
        return null;
    }
}
