package com.sabakuch.epaper.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.sabakuch.epaper.R;
import com.sabakuch.epaper.apiclass.APIAccess;
import com.sabakuch.epaper.apputils.CommonUtils;
import com.sabakuch.epaper.apputils.SabaKuchParse;
import com.sabakuch.epaper.apputils.ServiceUrl;
import com.sabakuch.epaper.constants.Constants;
import com.sabakuch.epaper.entitymime.MultipartEntity;
import com.sabakuch.epaper.entitymimecontent.StringBody;
import com.sabakuch.epaper.serviceclasses.ServiceInterface;

import java.io.UnsupportedEncodingException;

/**
 * Created by dell on 03-May-17.
 */
public class PasswordSettingFragment extends Fragment implements ServiceInterface {

    EditText edOldPass, edNewPass, edConfirmPass;

    Button SettingSavePassword;
    private String strUserID, strServerKey;
    private MultipartEntity reqEntity;
    private Context context;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profilechangepassword, container, false);
        CommonUtils.setTracking(PasswordSettingFragment.class.getSimpleName());
        setId(rootView);
        return rootView;

    }


    private void setId(View rootView) {

        try {
            strUserID = CommonUtils.getStringPreferences(getActivity(), Constants.USER_ID);
            strServerKey = CommonUtils.getStringPreferences(getActivity(), Constants.SERVER_KEY);
        } catch (Exception e) {


        }
        context = getActivity();
        edOldPass = (EditText) rootView.findViewById(R.id.edOldPass);
        edNewPass = (EditText) rootView.findViewById(R.id.edNewPass);
        edConfirmPass = (EditText) rootView.findViewById(R.id.edConfirmPass);

        SettingSavePassword = (Button) rootView.findViewById(R.id.btnSave);
        SettingSavePassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                validations();

            }
        });
    }

    protected void validations() {
        // TODO Auto-generated method stub

		/* userid
         old_password
		 password
		 cpassword*/
        if (edOldPass.getText().toString().equalsIgnoreCase("")) {
            CommonUtils.showToast(context, "Please enter old password.");
            edOldPass.requestFocus();
        } else if (edNewPass.getText().toString().equalsIgnoreCase("")) {
            CommonUtils.showToast(context, "Please enter new password.");
            edNewPass.requestFocus();
        } else if (edNewPass.getText().toString().length() > 0 && edNewPass.getText().toString().length() < 4) {
            CommonUtils.showToast(context, "Password should be atleast 4 characters.");
            edNewPass.requestFocus();
        } else if (edConfirmPass.getText().toString().equalsIgnoreCase("")) {
            CommonUtils.showToast(context, "Please enter confirm password.");
            edConfirmPass.requestFocus();
        } else if (!edNewPass.getText().toString().equals(edConfirmPass.getText().toString())) {
            CommonUtils.showToast(context, "Password & confirm password does not match.");
            edConfirmPass.requestFocus();

        } else {
            APIAccess.fetchData(PasswordSettingFragment.this, getActivity(), getActivity());
        }
    }

    private void getRequest() {
        String usrid = strUserID.toString();
        String oldpass = edOldPass.getText().toString().trim();
        String nwpass = edNewPass.getText().toString().trim();
        String cnfmpass = edConfirmPass.getText().toString().trim();

        reqEntity = new MultipartEntity();

        try {
            StringBody userid = new StringBody(usrid);
            StringBody old_password = new StringBody(oldpass);
            StringBody password = new StringBody(nwpass);
            StringBody cpassword = new StringBody(cnfmpass);

            reqEntity.addPart("userid", userid);
            reqEntity.addPart("old_password", old_password);
            reqEntity.addPart("password", password);
            reqEntity.addPart("cpassword", cpassword);


        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    @Override
    public String httpPost() {
        // TODO Auto-generated method stub

        String response = "";

        getRequest();

        response = APIAccess.openConnection(ServiceUrl.SABAKUCH_PASSWORD_CHANGE_SETTING, reqEntity);

        return response;
    }


    @Override
    public String httpAfterPost(final String str) {
        // TODO Auto-generated method stub

        if (str != null) {
            if (SabaKuchParse.jsonStatus(str) == 1) {

                //	Toast.makeText(getActivity(), "your password has been changed", Toast.LENGTH_SHORT).show();
                CommonUtils.showToast(context, "Password updated successfully.");
                edOldPass.setText("");
                edNewPass.setText("");
                edConfirmPass.setText("");

            } else if (SabaKuchParse.jsonStatus(str) == 0) {
                CommonUtils.showToast(context, SabaKuchParse.jsonErrorMessage(str));

				/*getActivity().runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(getActivity(),SabaKuchParse.jsonErrorMessage(str), Toast.LENGTH_SHORT).show();
					}
				});*/

                System.out.print(SabaKuchParse.jsonErrorMessage(str));
            }
        }
        return null;
    }
}
