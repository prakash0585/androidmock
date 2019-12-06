package com.sabakuch.epaper.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.sabakuch.epaper.R;
import com.sabakuch.epaper.apiclass.APIAccess;
import com.sabakuch.epaper.application.SabakuchEmockApplication;
import com.sabakuch.epaper.apputils.CommonUtils;
import com.sabakuch.epaper.apputils.SabaKuchParse;
import com.sabakuch.epaper.apputils.ServiceUrl;
import com.sabakuch.epaper.constants.Constants;
import com.sabakuch.epaper.fragment.ProfileSettingFragmentNew;
import com.sabakuch.epaper.fragment.ReferralFragment;
import com.sabakuch.epaper.serviceclasses.OpenConnection;
import com.sabakuch.epaper.serviceclasses.ServiceInterface;


public class SettingsFragmentNews extends Fragment implements View.OnClickListener, ServiceInterface {
    String strUserID, strUserName;
    private View rootView;
    private TextView tvProfileSettings, tvSigninSignout;
    private Context context;
    private LinearLayout llLoginDetail;
    private TextView tvName, tv_about_us, tv_contact_us, tv_rate_us, tvVersion, tvReferralCode;

    private int UrlIndex;
    private boolean isLogin;

    public SettingsFragmentNews() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        context = getActivity();
        strUserID = CommonUtils.getStringPreferences(context, Constants.USER_ID);
        strUserName = CommonUtils.getStringPreferences(getActivity(), Constants.FNAME);

        setId();
        return rootView;
    }

    private void setId() {
        tvProfileSettings = (TextView) rootView.findViewById(R.id.tv_profile_settings);
        tvSigninSignout = (TextView) rootView.findViewById(R.id.tv_signin_signout);
        tv_about_us = (TextView) rootView.findViewById(R.id.tv_about_us);
        tv_contact_us = (TextView) rootView.findViewById(R.id.tv_contact_us);
        tv_rate_us = (TextView) rootView.findViewById(R.id.tv_rate_us);
        tvVersion = (TextView) rootView.findViewById(R.id.tvVersion);
        tvName = (TextView) rootView.findViewById(R.id.tvName);
        llLoginDetail = (LinearLayout) rootView.findViewById(R.id.llLoginDetail);
        tvReferralCode = (TextView) rootView.findViewById(R.id.tvReferralCode);

        tvProfileSettings.setOnClickListener(this);
        tvSigninSignout.setOnClickListener(this);
        tv_contact_us.setOnClickListener(this);
        tv_rate_us.setOnClickListener(this);
        tv_about_us.setOnClickListener(this);
        tvReferralCode.setOnClickListener(this);

        isLogin = CommonUtils.getLoginPreferences(getActivity(), Constants.IS_LOGIN);
        if (isLogin) {
            tvProfileSettings.setEnabled(true);
            tvProfileSettings.setVisibility(View.VISIBLE);
            llLoginDetail.setVisibility(View.VISIBLE);
            tvSigninSignout.setText(getResources().getString(R.string.nav_item_sign_out));
            tvName.setText(strUserName);
        } else {
            tvProfileSettings.setEnabled(false);
            llLoginDetail.setVisibility(View.GONE);
            tvProfileSettings.setVisibility(View.GONE);
            llLoginDetail.setVisibility(View.GONE);
            tvSigninSignout.setText(getResources().getString(R.string.sign_in));
        }

        setupToolbar();

        PackageInfo pInfo = null;
        try {
            pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = pInfo.versionName;
        if (version != null && !version.equals("")) {
            tvVersion.setText("Version: " + version);
        } else {
            tvVersion.setVisibility(View.GONE);
        }

    }

    private void setupToolbar() {
        Toolbar mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        TextView mTitle = (TextView) mToolbar.findViewById(R.id.tv_title);
        mTitle.setVisibility(View.GONE);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        final ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(false);
        ab.setIcon(R.drawable.app_header);
        ab.setTitle("");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_profile_settings:
                setFragment(new ProfileSettingFragmentNew());
                break;
            case R.id.tv_signin_signout:
                if (isLogin) {
                    //signout
                    UrlIndex = 1;
                    APIAccess.fetchData(SettingsFragmentNews.this, getActivity(), getActivity());
                } else {
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                }
                break;
            case R.id.tv_rate_us:
                Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
                }
                break;
            case R.id.tvReferralCode:
                if (SabakuchEmockApplication.permission == 1)
                    setFragment(new ReferralFragment());
                else {
                    (new AlertDialog.Builder(getActivity())).setTitle("Permission Alert!")
                            .setMessage("Give Permission to Access this Feature")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
//                                    Intent intent = new Intent();
//                                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                                    Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
//                                    intent.setData(uri);
//                                    startActivity(intent);
                                    ((InstructionInstActivity) getActivity()).checkPermission();
                                }
                            }).setNegativeButton("Cancel", null).show();
                }
                break;

            case R.id.tv_contact_us:
                Intent intent = new Intent(getActivity(), ContactUsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;

            case R.id.tv_about_us:
                String url = "https://sabakuch.com/cms/index/aboutus";

                Intent ibaout = new Intent(Intent.ACTION_VIEW);
                ibaout.setData(Uri.parse(url));
                startActivity(ibaout);
                break;
        }
    }

    protected void setFragment(Fragment fragment) {
        FragmentTransaction ftTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        ftTransaction.replace(R.id.container_body, fragment)
                .addToBackStack(null);
        ftTransaction.commit();
    }

    @Override
    public String httpPost() {
        String response = "";
        if (UrlIndex == 1) {
            response = OpenConnection.callUrl(ServiceUrl.SERVER_BASE_URL + "logout/uid/" + strUserID);
        }

        return response;
    }


    @Override
    public String httpAfterPost(String str) {
        if (str != null) {
            if (UrlIndex == 1) {
// logout from facebook
                if (SabaKuchParse.getLogoutResponse(str) != null && SabaKuchParse.getLogoutResponse(str).equalsIgnoreCase("Logout")) {
                    Profile profile = Profile.getCurrentProfile();
                    if (profile != null) {
                        LoginManager.getInstance().logOut();
                    }
                    Intent intent = new Intent(context, LoginActivity.class);
                    CommonUtils.saveLoginPref(context, Constants.IS_LOGIN, false);
                    CommonUtils.saveStringPreferences(context, Constants.USER_ID, "");
                    CommonUtils.saveStringPreferences(context, Constants.USER_NAME, "");
                    CommonUtils.saveStringPreferences(context, Constants.SERVER_KEY, "");

                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    getActivity().finish();

                } else {
                    Toast.makeText(context, getResources().getString(R.string.server_not_responding), Toast.LENGTH_SHORT).show();
                }
            }
        }
        return null;
    }

}
