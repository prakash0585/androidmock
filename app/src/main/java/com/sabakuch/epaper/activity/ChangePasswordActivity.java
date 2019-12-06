package com.sabakuch.epaper.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sabakuch.epaper.R;
import com.sabakuch.epaper.apiclass.APIAccess;
import com.sabakuch.epaper.apputils.CommonUtils;
import com.sabakuch.epaper.apputils.SabaKuchParse;
import com.sabakuch.epaper.apputils.ServiceUrl;
import com.sabakuch.epaper.constants.Constants;
import com.sabakuch.epaper.entitymime.MultipartEntity;
import com.sabakuch.epaper.entitymimecontent.StringBody;
import com.sabakuch.epaper.serviceclasses.ServiceInterface;

public class ChangePasswordActivity extends AppCompatActivity implements ServiceInterface, View.OnClickListener {

    private int urlIndex;
    private MultipartEntity reqEntity;

    EditText edConfirmPass;
    String strMobile,strCheck;
    Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        setId();

    }
    private void setId() {
        if (getIntent() != null)
            strMobile = getIntent().getStringExtra(Constants.Mobile);
        strCheck = getIntent().getStringExtra(Constants.Check);


        edConfirmPass = (EditText) findViewById(R.id.edConfirmPass);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);

    }

    protected void validations() {

        if (edConfirmPass.getText().toString().trim().equalsIgnoreCase(""))
        {
            CommonUtils.showToast(this, "Please enter confirm password.");
            edConfirmPass.requestFocus();
        }
        else
        {
            urlIndex = 1;
            APIAccess.fetchData(ChangePasswordActivity.this, ChangePasswordActivity.this,
                    ChangePasswordActivity.this);
        }
    }


    void sendNewPassword() {


        try {
            if (strCheck.equals("1"))
            {
                StringBody passwordchanageid = new StringBody("1");
                StringBody emailid = new StringBody("");
                StringBody phoneid = new StringBody(strMobile);
                StringBody passwordid = new StringBody(edConfirmPass.getText().toString());

                reqEntity = new MultipartEntity();
                reqEntity.addPart("password_chanage", passwordchanageid);
                reqEntity.addPart("email", emailid);
                reqEntity.addPart("phone", phoneid);
                reqEntity.addPart("password", passwordid);
            }
            else
            {

                StringBody passwordchanageid = new StringBody("1");
                StringBody emailid = new StringBody(strMobile);
                StringBody phoneid = new StringBody("");
                StringBody passwordid = new StringBody(edConfirmPass.getText().toString());

                reqEntity = new MultipartEntity();
                reqEntity.addPart("password_chanage", passwordchanageid);
                reqEntity.addPart("email", emailid);
                reqEntity.addPart("phone", phoneid);
                reqEntity.addPart("password", passwordid);
            }



        } catch (Exception e) {
            System.out.println("err" + e);
        }




    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSave:
                validations();
                break;
        }
    }

    @Override
    public String httpPost() {
        String response = "";
        if (urlIndex == 1) {
            sendNewPassword();
            response = APIAccess.openConnection(ServiceUrl.SABAKUCH_FORGOTPASS, reqEntity);
            System.out.println("@@@@@@@@@@@@@@@@@@@"+response);

        }

        return response;     }

    @Override
    public String httpAfterPost(String str) {
        if (str != null) {
            if (urlIndex == 1) {
                if (SabaKuchParse.jsonStatus(str) == 0) {
                    Toast.makeText(ChangePasswordActivity.this, SabaKuchParse.jsonErrorMessage(str),
                            Toast.LENGTH_SHORT).show();
                } else if (SabaKuchParse.jsonStatus(str) == 1) {
                    Toast.makeText(ChangePasswordActivity.this, SabaKuchParse.jsonErrorMessage(str),
                            Toast.LENGTH_SHORT).show();
                    finish();

                }
            }

        }
        return null;
    }
}
