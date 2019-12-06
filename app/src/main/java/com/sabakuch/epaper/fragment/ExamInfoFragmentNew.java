package com.sabakuch.epaper.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.sabakuch.epaper.R;
import com.sabakuch.epaper.apiclass.APIAccess;
import com.sabakuch.epaper.apputils.CommonUtils;
import com.sabakuch.epaper.apputils.SabaKuchParse;
import com.sabakuch.epaper.apputils.ServiceUrl;
import com.sabakuch.epaper.constants.Constants;
import com.sabakuch.epaper.data.InstructionsData;
import com.sabakuch.epaper.serviceclasses.OpenConnection;
import com.sabakuch.epaper.serviceclasses.ServiceInterface;

import java.util.ArrayList;

public class ExamInfoFragmentNew extends Fragment implements ServiceInterface {
    private View rootView;
    private WebView tvInstructions;
    private Activity context;
    private ArrayList<InstructionsData> arrData, arrListData = new ArrayList<>();
    private String examId="",slug="";
    private int UrlIndex;
    private ProgressDialog pb;


    public ExamInfoFragmentNew() {
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
        rootView = inflater.inflate(R.layout.fragment_coming, container, false);
        CommonUtils.setTracking(InstructionFragment.class.getSimpleName());
        Bundle bundle=getArguments();
        if(bundle!=null){
            examId=bundle.getString(Constants.EXAM_ID);
            slug=bundle.getString(Constants.SLUG);
        }
        setId();
        setupToolbar();









        try
        {
            if(context!=null&&!context.isFinishing())
                if (CommonUtils.isOnline(context)) {
                    pb = ProgressDialog.show(context, "", context.getResources().getString(R.string.loader_text));
                    APIAccess.fetchPagingData(ExamInfoFragmentNew.this, getActivity(), getActivity(),true);
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
        tvInstructions = (WebView) rootView.findViewById(R.id.tv_instructions);

        TextView tvhome1=(TextView)rootView.findViewById(R.id.tvhome);
        TextView tvtest1=(TextView)rootView.findViewById(R.id.tvtest);
        tvhome1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SelectExamsFragment());

            }
        });

        tvtest1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new SelectLevelsFragment();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.EXAM_ID, examId);
                bundle.putString(Constants.SLUG, slug);
                fragment.setArguments(bundle);
                setFragment(fragment);
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
        mTitle.setText("Exam - Information");

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


    @Override
    public String httpPost() {
        String response = "";
        try {
            if (UrlIndex == 0) {
                response = OpenConnection.callUrl(ServiceUrl.SABAKUCH_EXAM_LIST + "?examid=" + examId );
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
                    arrData = SabaKuchParse.parseInstructionsDataInfo(str);
                    if (arrData.size() > 0) {
                        if (arrData.get(0).exam_info != null && arrData.get(0).exam_info.length() > 0) {
                            tvInstructions.loadDataWithBaseURL(null, arrData.get(0).exam_info.trim(), "text/html", "utf-8", null);
                            tvInstructions.setWebChromeClient(new WebChromeClient());
                            tvInstructions.setWebViewClient(new WebViewClient());
                            tvInstructions.getSettings().setJavaScriptEnabled(true);
                        }

                    }
                    if(context!=null && pb!=null && pb.isShowing())
                        pb.dismiss();

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
