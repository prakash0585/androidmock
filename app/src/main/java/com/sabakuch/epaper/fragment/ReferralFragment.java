package com.sabakuch.epaper.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sabakuch.epaper.R;
import com.sabakuch.epaper.activity.MainActivity;
import com.sabakuch.epaper.adapter.CountryCodesSpinnerAdapter;
import com.sabakuch.epaper.apiclass.APIAccess;
import com.sabakuch.epaper.application.SabakuchEmockApplication;
import com.sabakuch.epaper.apputils.CommonUtils;
import com.sabakuch.epaper.apputils.FontManager;
import com.sabakuch.epaper.apputils.ServiceUrl;
import com.sabakuch.epaper.constants.Constants;
import com.sabakuch.epaper.data.CountryCodeData;
import com.sabakuch.epaper.data.ReferralResponse;
import com.sabakuch.epaper.entitymime.MultipartEntity;
import com.sabakuch.epaper.entitymimecontent.StringBody;
import com.sabakuch.epaper.serviceclasses.OpenConnection;
import com.sabakuch.epaper.serviceclasses.ServiceInterface;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by dell on 31-Mar-17.
 */
public class ReferralFragment extends Fragment implements ServiceInterface, View.OnClickListener {
    int UrlIndex = 0;
    String identifier = "";
    String identifierIMSI = "";
    String identifierSerialNum = "";
    String identifierAndroid_id = "";
    MultipartEntity reqEntity;
    CountryCodesSpinnerAdapter mSpinner;
    LinearLayout ll_mobile_verify, ll_verify_otp, ll_referral;
    String strMobileNumber = "", strOtp = "";
    int mCountryCode = 100;
    String deviceType = "Android";
    private EditText etCode, etMobileNumber, etVerifyOtp;
    private Button btnApply;
    private TextView btnMobileApply, tvRemove, btnVerifyOtpApply, tvResend;
    private Spinner spCodes;
    private ArrayList<CountryCodeData.Country> country = new ArrayList<CountryCodeData.Country>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_referral_code, container, false);
        CommonUtils.setTracking(ReferralFragment.class.getSimpleName());
        setInitializeComponent(rootView);
        return rootView;
    }


    void setInitializeComponent(View rootView) {
        spCodes = (Spinner) rootView.findViewById(R.id.spCodes);
        etCode = (EditText) rootView.findViewById(R.id.etCode);
        btnApply = (Button) rootView.findViewById(R.id.btnApply);
        etMobileNumber = (EditText) rootView.findViewById(R.id.etMobileNumber);
        btnMobileApply = (TextView) rootView.findViewById(R.id.btnMobileApply);
        btnVerifyOtpApply = (TextView) rootView.findViewById(R.id.btnVerifyOtpApply);
        etVerifyOtp = (EditText) rootView.findViewById(R.id.etVerifyOtp);
        tvResend = (TextView) rootView.findViewById(R.id.tvResend);
        tvRemove = (TextView) rootView.findViewById(R.id.tvRemove);
        ll_mobile_verify = (LinearLayout) rootView.findViewById(R.id.ll_mobile_verify);
        ll_verify_otp = (LinearLayout) rootView.findViewById(R.id.ll_verify_otp);
        ll_referral = (LinearLayout) rootView.findViewById(R.id.ll_referral);

        tvRemove.setTypeface(FontManager.getTypeface(getActivity(), FontManager.FONTAWESOME));
        btnMobileApply.setTypeface(FontManager.getTypeface(getActivity(), FontManager.FONTAWESOME));
        btnVerifyOtpApply.setTypeface(FontManager.getTypeface(getActivity(), FontManager.FONTAWESOME));

        tvRemove.setOnClickListener(this);
        btnMobileApply.setOnClickListener(this);
        btnVerifyOtpApply.setOnClickListener(this);
        tvResend.setOnClickListener(this);


        SabakuchEmockApplication.permission = CommonUtils.getIntPreferences(getActivity(), Constants.Permission);
        if (SabakuchEmockApplication.permission == 1) {
            sendCode();
        } else {
            (new AlertDialog.Builder(getActivity())).setTitle("Permission Alert!")
                    .setMessage("Give Permission to Access this Feature")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ((MainActivity) getActivity()).checkPermission();
                        }
                    }).setNegativeButton("Cancel", null).show();
        }
        btnApply.setOnClickListener(this);
        setAdapter();
        setMobileTextWatcher();
        setOtpTextWatcher();
        UrlIndex = 1;
        APIAccess.fetchData(this, getActivity(), getActivity());

    }

    private void setOtpTextWatcher() {
        etVerifyOtp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etVerifyOtp.getText().toString().trim().length() == 5) {
                    strOtp = etVerifyOtp.getText().toString();
                } else {

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setMobileTextWatcher() {
        etMobileNumber.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etMobileNumber.getText().toString().equals("")) {
                    tvRemove.setVisibility(View.GONE);
                } else {
                    tvRemove.setVisibility(View.VISIBLE);
                    strMobileNumber = etMobileNumber.getText().toString();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setAdapter() {
        mSpinner = new CountryCodesSpinnerAdapter(getActivity(), country);
        spCodes.setAdapter(mSpinner);
    }

    void sendCode() {
        TelephonyManager tm = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        if (tm != null) {
            identifier = tm.getDeviceId();
            identifierIMSI = tm.getSubscriberId();
            try {
                Class<?> c = Class.forName("android.os.SystemProperties");
                Method get = c.getMethod("get", String.class, String.class);
                identifierSerialNum = (String) (get.invoke(c, "ro.serialno", "unknown"));
            } catch (Exception ignored) {
            }
            identifierAndroid_id = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        } else
            identifier = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        CommonUtils.LogMsg(ReferralFragment.class.getSimpleName(), "identifier: " + identifier + " identifierIMSI: " + identifierIMSI + " identifierSerialNum: " + identifierSerialNum + " identifierAndroid_id: " + identifierAndroid_id);
    }

    public void postReferral() {
        try {
            StringBody phone = new StringBody(strMobileNumber);
            StringBody imei = new StringBody(identifier);
            StringBody imsi = new StringBody(identifierIMSI);
            StringBody source = new StringBody("android_emock");
            StringBody serialnum = new StringBody(identifierSerialNum);
            StringBody android_id = new StringBody(identifierAndroid_id);
            StringBody device_type = new StringBody(deviceType);
            StringBody referalcode = new StringBody(etCode.getText().toString());
            reqEntity = new MultipartEntity();
            reqEntity.addPart("imei", imei);
            reqEntity.addPart("imsi", imsi);
            reqEntity.addPart("source", source);
            reqEntity.addPart("phone", phone);
            reqEntity.addPart("serialnum", serialnum);
            reqEntity.addPart("android_id", android_id);
            reqEntity.addPart("device_type", device_type);
            reqEntity.addPart("referalcode", referalcode);
            CommonUtils.LogMsg(ReferralFragment.class.getSimpleName(), "imei: " + identifier + " imsi: " + identifierIMSI + " serialnum: " + identifierSerialNum + " android_id: " + identifierAndroid_id + " phone:" + strMobileNumber + " source: " + "android_emock" + " referalcode:" + etCode.getText().toString());
        } catch (Exception e) {
//            System.out.println("err" + e);
        }
    }


    public void postMobile() {
        try {
            StringBody otp = new StringBody("otp");
            StringBody phone = new StringBody(strMobileNumber);
            StringBody source = new StringBody("android_emock");
            mCountryCode = spCodes.getSelectedItemPosition();
            StringBody countrycode = new StringBody(country.get(mCountryCode).getPhonecode());
            reqEntity = new MultipartEntity();
            reqEntity.addPart("type", otp);
            reqEntity.addPart("phone", phone);
            reqEntity.addPart("source", source);
            reqEntity.addPart("countrycode", countrycode);
            CommonUtils.LogMsg(ReferralFragment.class.getSimpleName(), "type: opt phone: " + strMobileNumber + " source: " + "android_emock" + " countrycode: " + country.get(mCountryCode).getPhonecode());

        } catch (Exception e) {
        }
    }


    public void postOTP() {
        try {
            StringBody type = new StringBody("otpcheck");
            StringBody source = new StringBody("android_emock");
            StringBody phone = new StringBody(strMobileNumber);
            StringBody otp = new StringBody(strOtp);
            reqEntity = new MultipartEntity();
            reqEntity.addPart("type", type);
            reqEntity.addPart("source", source);
            reqEntity.addPart("phone", phone);
            reqEntity.addPart("otp", otp);
            CommonUtils.LogMsg(ReferralFragment.class.getSimpleName(), "type: otpcheck phone: " + strMobileNumber + " source: " + "android_emock" + "otp:" + strOtp);

        } catch (Exception e) {
//            System.out.println("err" + e);
        }
    }

    @Override
    public String httpPost() {
        String response = "";
        try {
            if (UrlIndex == 0) {
                postReferral();
                response = APIAccess.openConnection(ServiceUrl.SABAKUCH_REFERRAL, reqEntity);
            } else if (UrlIndex == 1) {
                response = OpenConnection.callUrl(ServiceUrl.SABAKUCH_WORLDRECORD + "param=phonecode");
            } else if (UrlIndex == 2) {
                postMobile();
                response = APIAccess.openConnection(ServiceUrl.SABAKUCH_REFERRAL, reqEntity);
            } else if (UrlIndex == 3) {
                postOTP();
                response = APIAccess.openConnection(ServiceUrl.SABAKUCH_REFERRAL, reqEntity);
            }
        } catch (Exception e) {

        }
        return response;
    }

    @Override
    public String httpAfterPost(String str) {
        if (str != null) {
            if (UrlIndex == 0) {
                etCode.setText("");
                CommonUtils.LogMsg(ReferralFragment.class.getSimpleName(), str);
                ReferralResponse mReferralResponse = CommonUtils.getCustomGson().fromJson(str, ReferralResponse.class);
                if (mReferralResponse.getPromotion().getSuccess() != null) {
                    if (mReferralResponse.getPromotion().getSuccess() == 1) {
                        Toast.makeText(getActivity(), mReferralResponse.getPromotion().getMessage(), Toast.LENGTH_SHORT).show();
//                        ((DrawerActivity) getActivity()).setMainFragment();

                    } else {
                        Toast.makeText(getActivity(), mReferralResponse.getPromotion().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            } else if (UrlIndex == 1) {
                try {
                    CountryCodeData mCountryCodeData = CommonUtils.getCustomGson().fromJson(str, CountryCodeData.class);
                    if (mCountryCodeData.getSuccess() == 1) {
                        country.addAll(mCountryCodeData.getCountry());
                        mSpinner.notifyDataSetChanged();
                        spCodes.setSelection(mCountryCode);
                    } else {

                    }
                } catch (Exception e) {

                }
            } else if (UrlIndex == 2) {
                try {
                    etMobileNumber.setEnabled(false);
                    btnMobileApply.setVisibility(View.GONE);
                    spCodes.setEnabled(false);
                    tvRemove.setVisibility(View.GONE);
                    ReferralResponse mReferralResponse = CommonUtils.getCustomGson().fromJson(str, ReferralResponse.class);
                    if (mReferralResponse.getPromotion().getSuccess() != null) {
                        if (mReferralResponse.getPromotion().getSuccess() == 1) {
                            if (mReferralResponse.getPromotion().getOtp() == 1) {
                                Toast.makeText(getActivity(), mReferralResponse.getPromotion().getMessage(), Toast.LENGTH_SHORT).show();
                                setOtpHideMobile();
                            } else
                                Toast.makeText(getActivity(), mReferralResponse.getPromotion().getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), mReferralResponse.getPromotion().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                } catch (Exception e) {

                }
            } else if (UrlIndex == 3) {
                try {
                    etVerifyOtp.setText("");
                    ReferralResponse mReferralResponse = CommonUtils.getCustomGson().fromJson(str, ReferralResponse.class);
                    if (mReferralResponse.getPromotion().getSuccess() != null) {
                        if (mReferralResponse.getPromotion().getSuccess() == 1) {
                            Toast.makeText(getActivity(), mReferralResponse.getPromotion().getMessage(), Toast.LENGTH_SHORT).show();
                            setReferralHideOTP();

                        } else {
                            Toast.makeText(getActivity(), mReferralResponse.getPromotion().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                } catch (Exception e) {

                }
            }
        }
        return null;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvRemove:
                etMobileNumber.setText("");
                break;
            case R.id.tvResend:
                if (strMobileNumber.trim().length() == 10) {
                    UrlIndex = 2;
                    APIAccess.fetchData(this, getActivity(), getActivity());
                } else {
                    CommonUtils.showToast(getActivity(), "Please enter 10-digit number");
                }
                break;
            case R.id.btnMobileApply:
                if (strMobileNumber.trim().length() == 10) {
                    UrlIndex = 2;
                    APIAccess.fetchData(this, getActivity(), getActivity());
                } else {
                    CommonUtils.showToast(getActivity(), "Please enter 10-digit number");
                }
                CommonUtils.hideKeyboard(getActivity());
                break;
            case R.id.btnVerifyOtpApply:
                if (strOtp.trim().length() == 5) {
                    UrlIndex = 3;
                    APIAccess.fetchData(this, getActivity(), getActivity());
                } else {
                    CommonUtils.showToast(getActivity(), "Please enter valid otp");
                }
                CommonUtils.hideKeyboard(getActivity());
                break;
            case R.id.btnApply:
                if (!etCode.getText().toString().trim().equals("") && !etCode.getText().toString().contains(" ")) {
                    UrlIndex = 0;
                    APIAccess.fetchData(this, getActivity(), getActivity());
                } else {
                    Toast.makeText(getActivity(), "Please enter valid referral code", Toast.LENGTH_SHORT).show();
                }
                CommonUtils.hideKeyboard(getActivity());
                break;
        }
    }

    void setOtpHideMobile() {
        if (ll_mobile_verify.getVisibility() == View.VISIBLE) {
            ll_verify_otp.setVisibility(View.VISIBLE);
        } else {

        }
    }

    void setReferralHideOTP() {
        if (ll_verify_otp.getVisibility() == View.VISIBLE) {
            ll_verify_otp.setVisibility(View.GONE);
            ll_referral.setVisibility(View.VISIBLE);
        } else {

        }
    }
}
