package com.sabakuch.epaper.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sabakuch.epaper.R;
import com.sabakuch.epaper.apiclass.APIAccess;
import com.sabakuch.epaper.apputils.CommonUtils;
import com.sabakuch.epaper.apputils.SabaKuchParse;
import com.sabakuch.epaper.apputils.ServiceUrl;
import com.sabakuch.epaper.constants.Constants;
import com.sabakuch.epaper.data.SelectExamsData;
import com.sabakuch.epaper.serviceclasses.OpenConnection;
import com.sabakuch.epaper.serviceclasses.ServiceInterface;

import java.util.ArrayList;

/**
 * Created by dell on 01-Apr-17.
 */
public class AnsSolFragment extends Fragment implements ServiceInterface {
    View rootView;
    String strExamId, strSetId;
    TextView tvAnswer, tvSolution;
    int UrlIndex;
    private Activity context;
    ArrayList<SelectExamsData> arrListData = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_ans_sol, container, false);
        context = getActivity();
        CommonUtils.setTracking(AnsSolFragment.class.getSimpleName());
        setId();
        setupToolbar();
        return rootView;
    }

    private void setId() {
        tvAnswer = (TextView) rootView.findViewById(R.id.tvAnswer);
        tvSolution = (TextView) rootView.findViewById(R.id.tvSolution);


        if (getArguments() != null) {
            Bundle mBundle = getArguments();
            strExamId = mBundle.getString(Constants.EXAM_ID);
            strSetId = mBundle.getString(Constants.LEVEL_ID);
        }

        tvAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UrlIndex = 1;
                APIAccess.fetchData(AnsSolFragment.this, getActivity(), getActivity());
            }
        });

        tvSolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UrlIndex = 2;
                APIAccess.fetchData(AnsSolFragment.this, getActivity(), getActivity());
            }
        });
    }

    protected void setFragment(Fragment fragment) {
        FragmentTransaction ftTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
        ftTransaction.replace(R.id.container_body, fragment)
                .addToBackStack("back");
        ftTransaction.commit();
    }

    private void setupToolbar()

    {
        Toolbar mToolbar = (Toolbar) context.findViewById(R.id.toolbar);
        TextView mTitle = (TextView) mToolbar.findViewById(R.id.tv_title);
        mTitle.setVisibility(View.GONE);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        final ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(false);
        ab.setIcon(R.drawable.app_header);
        ab.setTitle("");
    }

    @Override
    public String httpPost() {
        String response = "";
        try {
            if (UrlIndex == 1) {
                response = OpenConnection.callUrl(ServiceUrl.SABAKUCH_SOLUTION_SET_ANSWER + "&set_id=" + strSetId + "&solution_id=" + strExamId);
            } else if (UrlIndex == 2) {
                response = OpenConnection.callUrl(ServiceUrl.SABAKUCH_SOLUTION_SET_SOLUTION + "&set_id=" + strSetId + "&solution_id=" + strExamId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public String httpAfterPost(String str) {
        if(str!=null){
            ArrayList<SelectExamsData> arrData = SabaKuchParse.parseExamsData(str);


            if (arrData.size() > 0) {
                if (arrListData != null && arrListData.size() > 0) {
                    arrListData.clear();
                }
                arrListData.addAll(arrData);
                if(arrListData.get(0).pdfname!=null && !arrListData.get(0).pdfname.equals("")){
                    Fragment fragment = new WebViewFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.URL,  arrListData.get(0).pdfname);
                    Log.e("TAG","pdf:" + arrListData.get(0).pdfname);

                    fragment.setArguments(bundle);
                    if (context != null) {
                        setFragment(fragment);
                    }
                }
            }
            Log.e("TAG","mSelectExamsData: "+"arrListData: "+ arrListData.size()+ CommonUtils.getCustomGson().toJson(arrListData));


        }
        return null;
    }
}
