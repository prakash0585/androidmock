package com.sabakuch.epaper.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sabakuch.epaper.R;
import com.sabakuch.epaper.activity.GPSTracker;
import com.sabakuch.epaper.adapter.SelectExamsAdapter;
import com.sabakuch.epaper.apiclass.APIAccess;
import com.sabakuch.epaper.apputils.CommonUtils;
import com.sabakuch.epaper.apputils.SabaKuchParse;
import com.sabakuch.epaper.apputils.ServiceUrl;
import com.sabakuch.epaper.constants.Constants;
import com.sabakuch.epaper.data.InstructionsData;
import com.sabakuch.epaper.entitymime.MultipartEntity;
import com.sabakuch.epaper.entitymimecontent.StringBody;
import com.sabakuch.epaper.serviceclasses.OpenConnection;
import com.sabakuch.epaper.serviceclasses.ServiceInterface;

import java.util.ArrayList;

public class InstructionFragment extends Fragment implements ServiceInterface {
    private View rootView;
    private TextView tvTitle,tvInstructions,tvStartTest;
    private Activity context;
    private ArrayList<InstructionsData> arrData, arrListData = new ArrayList<>();
    private String examId="",levelId="";
    private int UrlIndex;
    private String type="2";
    private ProgressDialog pb;
    private MultipartEntity reqEntity;

    GPSTracker gps;
    private static final int REQUEST_CODE_PERMISSION = 2;


    public InstructionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_instructions, container, false);
        CommonUtils.setTracking(InstructionFragment.class.getSimpleName());
        Bundle bundle=getArguments();
        if(bundle!=null){
            examId=bundle.getString(Constants.EXAM_ID);
            levelId=bundle.getString(Constants.LEVEL_ID);
        }
        setId();
        setupToolbar();




        try {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user, this condition will
                //execute every time, else your else part will work
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        gps = new GPSTracker(getActivity());

        // check if GPS enabled
        if(gps.canGetLocation()){

            double   latitude = gps.getLatitude();
            double  longitude = gps.getLongitude();
            String stringdouble= Double.toString(latitude);
            System.out.println("lati nad longi"+(stringdouble));
            System.out.println("lati nad longi"+String.valueOf(longitude));

            // \n is for new line
           // Toast.makeText(getActivity(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();


        }



        try
        {
            if(context!=null&&!context.isFinishing())
                if (CommonUtils.isOnline(context)) {
                    pb = ProgressDialog.show(context, "", context.getResources().getString(R.string.loader_text));
                    APIAccess.fetchPagingData(InstructionFragment.this, getActivity(), getActivity(),true);
                }	else {
                    if(context!=null && pb!=null && pb.isShowing())
                        pb.dismiss();
                    Toast.makeText(context, context.getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                }

        }catch(Exception e)
        {
        }
        return rootView;
    }

    private void setId() {
        tvInstructions = (TextView) rootView.findViewById(R.id.tv_instructions);
        tvStartTest = (TextView) rootView.findViewById(R.id.tv_start_test);
        tvStartTest.setEnabled(false);
        tvStartTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Boolean isLogin = CommonUtils.getLoginPreferences(getActivity(), Constants.IS_LOGIN);
                if (isLogin) {
                    Fragment fragment = new OnlineTestPaperSectionFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.EXAM_ID, examId);
                    bundle.putString(Constants.LEVEL_ID, levelId);
                    bundle.putString(Constants.EXAM_TYPE, type);
                    fragment.setArguments(bundle);
                    setFragment(fragment);
                }else{
                    CommonUtils.loginAlertDialog(context,getResources().getString(R.string.please_login_to_continue));
                }
            }
        });
    }

    private void setupToolbar() {
        Toolbar mToolbar = (Toolbar) context.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        final ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(false);
        ab.setIcon(0);
        TextView mTitle = (TextView) mToolbar.findViewById(R.id.tv_title);
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setText(getResources().getString(R.string.instruction));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    protected void setFragment(Fragment fragment) {
        FragmentTransaction ftTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        ftTransaction.replace(R.id.container_body, fragment)
                .addToBackStack(null);
        ftTransaction.commit();
    }

    public void postUserDetail() {
        try {
            String strUserID = CommonUtils.getStringPreferences(context, Constants.USER_ID);
            String strUserName = CommonUtils.getStringPreferences(context, Constants.USER_NAME);
            StringBody userId = new StringBody(strUserID);
            StringBody username = new StringBody(strUserName);
            StringBody levelId1 = new StringBody(levelId);
            StringBody examId1 = new StringBody(examId);

            CommonUtils.LogMsg(SelectExamsAdapter.class.getSimpleName(), "user_id: " + strUserID + " username: " + strUserName + " level_id: " + "0" + " exam_id: " + examId);
            reqEntity = new MultipartEntity();
            reqEntity.addPart("user_id", userId);
            reqEntity.addPart("username", username);
            reqEntity.addPart("level_id", levelId1);
            reqEntity.addPart("exam_id", examId1);

        } catch (Exception e) {
            System.out.println("err" + e);
        }
    }

    @Override
    public String httpPost() {
        String response = "";
        try {
            if (UrlIndex == 0) {
                response = OpenConnection.callUrl(ServiceUrl.SABAKUCH_INSTRUCTIONS + "?exam_id=" + examId + "&level_id=" + levelId);
            }else if(UrlIndex == 1){
                postUserDetail();
                response = APIAccess.openConnection(ServiceUrl.SABAKUCH_USER_DETAIL, reqEntity);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public String httpAfterPost(String str) {
        if (str != null) {
            if (SabaKuchParse.getResponseCode(str) != null && SabaKuchParse.getResponseCode(str).equalsIgnoreCase("200")) {
                if (UrlIndex == 0) {
                  arrData = SabaKuchParse.parseInstructionsData(str);
                    if (arrData.size() > 0) {
                        tvInstructions.setText(arrData.get(0).instruction != null && arrData.get(0).instruction.length() > 0 ? CommonUtils.stripHtml(arrData.get(0).instruction) : "");
                       // tvInstructions.loadDataWithBaseURL(null, arrData.get(0).instruction, "text/html", "utf-8", null);


                    }
                    tvStartTest.setEnabled(true);
                    if(context!=null && pb!=null && pb.isShowing())
                        pb.dismiss();

                    UrlIndex = 1;
                    APIAccess.fetchPagingData(InstructionFragment.this, getActivity(), getActivity(),true);
                }
            }else{
                if(context!=null && pb!=null && pb.isShowing())
                    pb.dismiss();
                Toast.makeText(context,getResources().getString(R.string.server_not_responding),Toast.LENGTH_SHORT).show();
            }
        }

        return null;
    }
}
