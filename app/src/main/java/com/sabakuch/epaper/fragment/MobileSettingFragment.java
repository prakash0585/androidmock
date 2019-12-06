package com.sabakuch.epaper.fragment;

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
import com.sabakuch.epaper.apputils.ServiceUrl;
import com.sabakuch.epaper.constants.Constants;
import com.sabakuch.epaper.data.CommonResponse;
import com.sabakuch.epaper.entitymime.MultipartEntity;
import com.sabakuch.epaper.entitymimecontent.StringBody;
import com.sabakuch.epaper.serviceclasses.OpenConnection;
import com.sabakuch.epaper.serviceclasses.ServiceInterface;

import org.json.JSONObject;

/**
 * Created by dell on 03-May-17.
 */
public class MobileSettingFragment extends Fragment implements View.OnClickListener, ServiceInterface {
    EditText ET, etOtp;
    Button btnSend;
    int a;
    String strUserID, strServerKey, streditetxt;
    int value;
    int UrlIndex = 0;
    private MultipartEntity reqEntity;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.mobile_active, container, false);
        CommonUtils.setTracking(MobileSettingFragment.class.getSimpleName());
        setId(rootView);
        setupToolbar(rootView);
        APIAccess.fetchPagingData(MobileSettingFragment.this, getActivity(), getActivity(), true);
        return rootView;

    }

    private void setupToolbar(View rootView) {

    }

    private void setId(View view) {

//        AppContoller.getInstance().pref=getActivity().getSharedPreferences(AppContoller.getInstance().PREF_NAME, 0);

        try {
            strUserID = CommonUtils.getStringPreferences(getActivity(), Constants.USER_ID);
            strServerKey = CommonUtils.getStringPreferences(getActivity(), Constants.SERVER_KEY);
        } catch (Exception e) {

        }

        ET = (EditText) view.findViewById(R.id.ednumber);
        etOtp = (EditText) view.findViewById(R.id.etOtp);
        btnSend = (Button) view.findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSend:
                if (etOtp.getVisibility() == View.GONE) {
                    if (btnSend.getText().toString().equals(getActivity().getResources().getString(R.string.send))) {
                        if (ET.getText().toString().equalsIgnoreCase("")) {
                            CommonUtils.showToast(getActivity(), "Please enter mobile number.");
                            ET.requestFocus();
                        } else if (!ET.getText().toString().matches("^[7-9][0-9]{9}$")) {
                            CommonUtils.showToast(getActivity(), "Please enter valid mobile number.");
                            ET.requestFocus();
                        } else {
                            streditetxt = ET.getText().toString();
                            UrlIndex = 1;
                            APIAccess.fetchData(MobileSettingFragment.this, getActivity(), getActivity());
                        }
                    }else  if (btnSend.getText().toString().equals(getActivity().getResources().getString(R.string.edit))) {
                        btnSend.setText(getActivity().getResources().getString(R.string.generate_OTP));
                        ET.setEnabled(true);
                    } else  if (btnSend.getText().toString().equals(getActivity().getResources().getString(R.string.generate_OTP))){
                        if (ET.getText().toString().equalsIgnoreCase("")) {
                            CommonUtils.showToast(getActivity(), "Please enter mobile number.");
                            ET.requestFocus();
                        } else if (!ET.getText().toString().matches("^[7-9][0-9]{9}$")) {
                            CommonUtils.showToast(getActivity(), "Please enter valid mobile number.");
                            ET.requestFocus();
                        } else {
                            streditetxt = ET.getText().toString();
                            UrlIndex = 1;
                            APIAccess.fetchData(MobileSettingFragment.this, getActivity(), getActivity());
                        }
//                btnSend.setText(getActivity().getResources().getString(R.string.send));
                    }
                } else {
                    if (etOtp.getText().toString().trim().equals("")) {
                        CommonUtils.showToast(getActivity(), "Please enter OTP");
                    } else if (etOtp.getText().toString().trim().contains(" ")) {
                        CommonUtils.showToast(getActivity(), "Please enter valid Otp");
                    } else {
                        UrlIndex = 2;
                        APIAccess.fetchData(MobileSettingFragment.this, getActivity(), getActivity());
                    }

                }
                break;
        }

    }


    public void Otpactive() {
        try {
            StringBody mobileNo = new StringBody(streditetxt);
            StringBody ID = new StringBody(strUserID);
            StringBody otp_generate = new StringBody("1");
            StringBody otp = new StringBody(etOtp.getText().toString().trim());

            reqEntity = new MultipartEntity();

            reqEntity.addPart("contact", mobileNo);
            reqEntity.addPart("otp_verify", otp_generate);
            reqEntity.addPart("otp", otp);
            reqEntity.addPart("userid", ID);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Mobileactive() {
        try {
            StringBody mobileNo = new StringBody(streditetxt);
            StringBody ID = new StringBody(strUserID);
            StringBody otp_generate = new StringBody("1");

            reqEntity = new MultipartEntity();

            reqEntity.addPart("contact", mobileNo);
            reqEntity.addPart("otp_generate", otp_generate);
            reqEntity.addPart("userid", ID);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public String httpPost() {
        String response = "";
        if (UrlIndex == 1) {
            Mobileactive();
            response = APIAccess.openConnection(ServiceUrl.SABAKUCH_MOBILE_ACTIVE, reqEntity);
        } else if (UrlIndex == 0) {
            response = OpenConnection.callUrl(ServiceUrl.SABAKUCH_MOBILE_ACTIVE + "userid/" + strUserID);
        } else if (UrlIndex == 2) {
            Otpactive();
            response = APIAccess.openConnection(ServiceUrl.SABAKUCH_MOBILE_ACTIVE, reqEntity);
        }
        return response;
    }

    @Override
    public String httpAfterPost(String str) {
        try {
            if (UrlIndex == 1) {
                JSONObject jsonObject = new JSONObject(str);
                value = jsonObject.getInt("success");
                if (value == 0) {
                    etOtp.setVisibility(View.GONE);
                    CommonUtils.showToast(getActivity(), jsonObject.getString("message"));
                    btnSend.setText(getActivity().getResources().getString(R.string.edit));
                } else {
                    etOtp.setVisibility(View.VISIBLE);
                    btnSend.setText(getActivity().getResources().getString(R.string.verify_otp));
                    ET.setEnabled(false);
                }
                CommonUtils.LogMsg(MobileSettingFragment.class.getSimpleName(), "inr: " + value);
            } else if (UrlIndex == 0) {
                CommonResponse mCommonResponse = CommonUtils.getCustomGson().fromJson(str, CommonResponse.class);
                if (mCommonResponse.getUserinfo() != null) {
                    if (mCommonResponse.getUserinfo().getContact() != null && !mCommonResponse.getUserinfo().getContact().equals("")) {

                        ET.setText(mCommonResponse.getUserinfo().getContact());
                        ET.setEnabled(false);
                    }else {
                        ET.setEnabled(true);
                        btnSend.setText(getActivity().getResources().getString(R.string.send));
                    }
                }
            } else if (UrlIndex == 2) {
                JSONObject jsonObject = new JSONObject(str);
                value = jsonObject.getInt("success");
                CommonUtils.showToast(getActivity(), jsonObject.getString("message"));

                UrlIndex = 0;
                APIAccess.fetchData(MobileSettingFragment.this, getActivity(), getActivity());

            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }
}
