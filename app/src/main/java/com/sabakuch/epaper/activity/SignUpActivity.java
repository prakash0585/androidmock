package com.sabakuch.epaper.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sabakuch.epaper.R;
import com.sabakuch.epaper.apiclass.APIAccess;
import com.sabakuch.epaper.apputils.CommonUtils;
import com.sabakuch.epaper.apputils.SabaKuchParse;
import com.sabakuch.epaper.apputils.ServiceUrl;
import com.sabakuch.epaper.constants.Constants;
import com.sabakuch.epaper.data.SignupData;
import com.sabakuch.epaper.entitymime.MultipartEntity;
import com.sabakuch.epaper.entitymimecontent.StringBody;
import com.sabakuch.epaper.serviceclasses.ServiceInterface;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener,ServiceInterface {

    private EditText etFirstName,etLastName,etUsername,etEmail,etPassword,etConfirmPassword/*,etAnswer*/;
    private TextView tvDOB,tvSubmit,tvTnC,tv_IMG_DOB;
    private CheckBox cbIAgree;
    private RadioGroup rgGender;
    private Context context;
    private MultipartEntity reqEntity;
    private LinearLayout llDOB;
    private RadioButton rbMale,rbFemale;
    private String strFname,strLname,strUserName,strEmail,strPass,strConfirm,date;
    private String strGender="";
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        context=SignUpActivity.this;
        CommonUtils.setTracking(SignUpActivity.class.getSimpleName());
        getId();
        setDateTimeField();
    }
    private void setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                if(dateFormatter.format(newDate.getTime())!=null && dateFormatter.format(newDate.getTime()).length()>0) {
                    tvDOB.setText(dateFormatter.format(newDate.getTime()));
                }
                date =(new StringBuilder().append(year).append("-").append(monthOfYear+1).append("-").append(dayOfMonth).toString());

            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private void getId() {
        tvTnC = (TextView) findViewById(R.id.tvTnC);
        llDOB = (LinearLayout) findViewById(R.id.llDOB);
        tv_IMG_DOB = (TextView) findViewById(R.id.tv_IMG_DOB);
        etFirstName=(EditText)findViewById(R.id.et_first_name);
        etLastName=(EditText)findViewById(R.id.et_last_name);
        etUsername=(EditText)findViewById(R.id.et_user_name);
        etEmail=(EditText)findViewById(R.id.et_email);
        etPassword=(EditText)findViewById(R.id.et_password);
        etConfirmPassword=(EditText)findViewById(R.id.et_confirm_password);
        //   etAnswer=(EditText)findViewById(R.id.et_answer);
        tvDOB=(TextView)findViewById(R.id.tv_dob);
        tvSubmit=(TextView)findViewById(R.id.tv_submit);
        rgGender=(RadioGroup) findViewById(R.id.rg_gender);
        rbMale=(RadioButton) findViewById(R.id.rb_male);
        rbFemale=(RadioButton) findViewById(R.id.rb_female);
        cbIAgree=(CheckBox) findViewById(R.id.cb_i_agree);
        tv_IMG_DOB.setTypeface(CommonUtils.getCustomTypeFace(this));


        llDOB.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);

        tvTnC.setClickable(true);
//        https://sabakuch.com/cms/index/terms-and-conditions/
        tvTnC.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "<a href='https://sabakuch.com/cms/index/terms-and-conditions/'>I agree to the terms &amp; conditions</a>";
        tvTnC.setText(Html.fromHtml(text));
    }
    public void postSignup()
    {
        if (rbMale.isChecked()){
            strGender="1";
        }else{
            strGender="2";
        }
        try
        {
            StringBody firstName=new StringBody(strFname);
          //  StringBody lastName=new StringBody(strLname);
            StringBody username=new StringBody(strUserName);
         //   StringBody email=new StringBody(strEmail);
            StringBody password=new StringBody(strPass);
        //    StringBody confirmPassword=new StringBody(strConfirm);
        //    StringBody dob=new StringBody(date);
            StringBody source = new StringBody("android_emock");
            StringBody gender = new StringBody(strGender);

            reqEntity = new MultipartEntity();

            reqEntity.addPart("name", firstName);
         //   reqEntity.addPart("lname", lastName);
            reqEntity.addPart("mobile", username);
         //   reqEntity.addPart("email", email);
            reqEntity.addPart("password", password);
        //    reqEntity.addPart("cpassword", confirmPassword);
       //     reqEntity.addPart("dob", dob);
            reqEntity.addPart("sourse_signup",source);
            reqEntity.addPart("gender", gender);
        }
        catch(Exception e)
        {
            System.out.println("err" + e);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.llDOB:
                fromDatePickerDialog.show();
                break;
            case R.id.tv_submit:
                checkValidation();
                break;

        }
    }

    private void checkValidation() {
        if(etFirstName.getText().toString().equalsIgnoreCase(""))
        {
            Toast.makeText(SignUpActivity.this, "Please enter Name.", Toast.LENGTH_SHORT).show();
            etFirstName.requestFocus();
        }

        else if(etFirstName.getText().toString().length()<3)
        {
            Toast.makeText(SignUpActivity.this, "Name should be atleast 3 characters.", Toast.LENGTH_SHORT).show();
            etFirstName.requestFocus();
        }
/*

        else if(etLastName.getText().toString().equalsIgnoreCase(""))
        {
            Toast.makeText(SignUpActivity.this, "Please enter last name.", Toast.LENGTH_SHORT).show();
            etLastName.requestFocus();
        }
        else if (etLastName.getText().toString().matches(".*[^a-z^A-Z].*")) {
            Toast.makeText(SignUpActivity.this, "Invalid last name only a-z allowed.", Toast.LENGTH_SHORT).show();
            etLastName.requestFocus();
        }else if(etLastName.getText().toString().length()<3)
        {
            Toast.makeText(SignUpActivity.this, "Last name should be atleast 3 characters.", Toast.LENGTH_SHORT).show();
            etLastName.requestFocus();
        }*/else if(etUsername.getText().toString().equalsIgnoreCase(""))
        {
            Toast.makeText(SignUpActivity.this, "Please enter Mobile Number.", Toast.LENGTH_SHORT).show();
            etUsername.requestFocus();

        }/*else if(etUsername.getText().toString().substring(0, 1).matches("\\d"))
        {
            Toast.makeText(SignUpActivity.this, "Username should start with alphabet.", Toast.LENGTH_SHORT).show();
            etUsername.requestFocus();
        }else if (etUsername.getText().toString().matches(".*[^a-z^A-Z^0-9^_].*")) {
            Toast.makeText(SignUpActivity.this, "Invalid username only a-z, 0-9 & _ are allowed.", Toast.LENGTH_SHORT).show();
            etUsername.requestFocus();
        }*/else if(etUsername.getText().toString().length()<10)
        {
            Toast.makeText(SignUpActivity.this, "Mobile Number must be of 10 characters.", Toast.LENGTH_SHORT).show();
            etUsername.requestFocus();
        }

        /*else if(etEmail.getText().toString().equalsIgnoreCase(""))
        {
            Toast.makeText(SignUpActivity.this, "Please enter email.", Toast.LENGTH_SHORT).show();
            etEmail.requestFocus();
        }else if (!CommonUtils.isValidEmail(etEmail.getText().toString().trim())) {

            Toast.makeText(SignUpActivity.this, "Please enter valid email.", Toast.LENGTH_SHORT).show();
            etEmail.requestFocus();
        }*/
        else if(etPassword.getText().toString().equalsIgnoreCase(""))
        {
            Toast.makeText(SignUpActivity.this, "Please enter Password.", Toast.LENGTH_SHORT).show();
            etPassword.requestFocus();
        }else if(etPassword.getText().toString().length()>0 && etPassword.getText().toString().length()<4){
            Toast.makeText(getApplicationContext(), "Password should be atleast 4 characters.", Toast.LENGTH_LONG).show();
            etPassword.requestFocus();
        }/*else if(etConfirmPassword.getText().toString().equalsIgnoreCase(""))
        {
            Toast.makeText(SignUpActivity.this, "Please enter confirm password.", Toast.LENGTH_SHORT).show();
            etConfirmPassword.requestFocus();
        }else if(!etPassword.getText().toString().equals(etConfirmPassword.getText().toString()))
        {
            Toast.makeText(SignUpActivity.this, "Password & confirm password does not match.", Toast.LENGTH_SHORT).show();
            etConfirmPassword.requestFocus();

        }else if(tvDOB.getText().toString().equals("D.O.B"))
        {
            Toast.makeText(SignUpActivity.this, "Please select date of birth.", Toast.LENGTH_SHORT).show();
            tvDOB.requestFocus();

        }*/else if(!rbMale.isChecked() && !rbFemale.isChecked())
        {
            Toast.makeText(SignUpActivity.this, "Please select gender.", Toast.LENGTH_SHORT).show();

        }
			else if (!cbIAgree.isChecked()) {
            Toast.makeText(SignUpActivity.this, "Please check terms and conditions.", Toast.LENGTH_SHORT).show();
        }

        else
        {
            strFname=etFirstName.getText().toString();
         //   strLname=etLastName.getText().toString();
            strUserName=etUsername.getText().toString();
          //  strEmail=etEmail.getText().toString();
            strPass=etPassword.getText().toString();
          //  strConfirm=etConfirmPassword.getText().toString();

            CommonUtils.hideKeyboard(SignUpActivity.this);

            APIAccess.fetchData(this, this, this);


        }
    }

    @Override
    public String httpPost() {
        postSignup();
        String response=APIAccess.openConnection(ServiceUrl.SABAKUCH_SIGNUP, reqEntity);
        Log.e("request", "request"+response);
        System.out.println("#################################"+response);

        return response;
    }

    @Override
    public String httpAfterPost(String str) {
        if(str!=null)
        {
            if(SabaKuchParse.jsonStatus(str)==1)
            {
                Intent movetologin = new Intent(SignUpActivity.this, OtpSignup.class);
                movetologin.putExtra("fname",strFname);
                movetologin.putExtra("mobile",strUserName);
                movetologin.putExtra("password",strPass);
                movetologin.putExtra("sourse_signup","android_emock");
                movetologin.putExtra("gender",strGender);
                startActivity(movetologin);
                finish();


                /*SignupData obj=SabaKuchParse.parseSignupdata(str);

                CommonUtils.saveStringPreferences(SignUpActivity.this, Constants.USER_ID, obj.strUserid);
                CommonUtils.saveStringPreferences(SignUpActivity.this,Constants.USER_NAME, strUserName);
                CommonUtils.saveStringPreferences(SignUpActivity.this,Constants.SERVER_KEY, CommonUtils.ServerKey(obj.strUserid, strUserName));

                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                CommonUtils.saveLoginPref(context, Constants.IS_LOGIN, true);
                finish();*/

            }else if(SabaKuchParse.jsonStatus(str)==0)
            {
                Toast.makeText(SignUpActivity.this, SabaKuchParse.jsonErrorMessage(str), Toast.LENGTH_SHORT).show();
            }
        }
        return null;
    }
}