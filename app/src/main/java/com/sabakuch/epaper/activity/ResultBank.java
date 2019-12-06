package com.sabakuch.epaper.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sabakuch.epaper.R;
import com.sabakuch.epaper.apiclass.APIAccess;
import com.sabakuch.epaper.apputils.CommonUtils;
import com.sabakuch.epaper.apputils.SabaKuchParse;
import com.sabakuch.epaper.apputils.ServiceUrl;
import com.sabakuch.epaper.constants.Constants;
import com.sabakuch.epaper.data.TestResultData;
import com.sabakuch.epaper.serviceclasses.OpenConnection;
import com.sabakuch.epaper.serviceclasses.ServiceInterface;

import java.util.ArrayList;

public class ResultBank extends AppCompatActivity implements ServiceInterface {
    private View rootView;
    private TextView tvResultStatus,tvStartDate,tvStartTime,tvEndDate,tvEndTime,tvTotalQuestions,tvMarks,tvNegativeMarks,
            tvTimeTaken,tvCorrect,tvIncorrect,tvTestReview;
    private Activity context;
    private ArrayList<TestResultData> arrData;
    private String lastPaperId;
    private String timeTaken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_bank);

        context=ResultBank.this;
        CommonUtils.setTracking(ResultFragmentNew.class.getSimpleName());
        setId();
        setupToolbar();
        Bundle bundle = getIntent().getExtras();

        if(bundle!=null){
            lastPaperId=bundle.getString(Constants.LAST_PAPER_ID);
        }
        APIAccess.fetchData(ResultBank.this, getApplicationContext(), ResultBank.this);
    }

    private void setId() {
        tvResultStatus = (TextView) findViewById(R.id.tv_result_status);
        tvStartDate = (TextView) findViewById(R.id.tv_start_date);
        tvStartTime = (TextView) findViewById(R.id.tv_start_time);
        tvEndDate = (TextView) findViewById(R.id.tv_end_date);
        tvEndTime = (TextView) findViewById(R.id.tv_end_time);
        tvTotalQuestions = (TextView) findViewById(R.id.tv_total_questions);
        tvMarks = (TextView) findViewById(R.id.tv_marks);
        tvNegativeMarks = (TextView) findViewById(R.id.tv_negative_marks);
        tvTimeTaken = (TextView) findViewById(R.id.tv_time_taken);
        tvCorrect = (TextView) findViewById(R.id.tv_correct);
        tvIncorrect = (TextView) findViewById(R.id.tv_incorrect);
        tvTestReview = (TextView) findViewById(R.id.tv_test_review);

     /*   tvTestReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new TestReviewFragment();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.LAST_PAPER_ID, lastPaperId);
                fragment.setArguments(bundle);
                setFragment(fragment);
            }
        });*/
    }
   /* protected void setFragment(Fragment fragment) {
        FragmentTransaction ftTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        ftTransaction.replace(R.id.main_frame, fragment)
                .addToBackStack(null);
        ftTransaction.commit();
    }
*/
    private void setupToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setIcon(0);
        getSupportActionBar().setTitle("");
       TextView mTitle = (TextView) mToolbar.findViewById(R.id.tv_title);
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setText(getResources().getString(R.string.online_test));

        mTitle.setText(getResources().getString(R.string.result));
    }


    @Override
    public String httpPost() {
        String response = "";
        try {
            response = OpenConnection.callUrl(ServiceUrl.SABAKUCH_PAPER_ANSWERS+"?last_paper_id="+lastPaperId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public String httpAfterPost(String str) {
        if (str != null) {
            if (SabaKuchParse.getResponseCode(str) != null && SabaKuchParse.getResponseCode(str).equalsIgnoreCase("200")) {
                arrData = SabaKuchParse.parseTestResultData(str);
                if (arrData.size() > 0) {
                    tvStartDate.setText(arrData.get(0).start_date!=null&&arrData.get(0).start_date.length()>0?"Start Date : "+arrData.get(0).start_date:"");
                    tvStartTime.setText(arrData.get(0).start_time!=null&&arrData.get(0).start_time.length()>0?"Start time : "+arrData.get(0).start_time:"");
                    tvEndDate.setText(arrData.get(0).end_date!=null&&arrData.get(0).end_date.length()>0?"End Date : "+arrData.get(0).end_date:"");
                    tvEndTime.setText(arrData.get(0).end_time!=null&&arrData.get(0).end_time.length()>0?"End time : "+arrData.get(0).end_time:"");
                    tvTotalQuestions.setText(arrData.get(0).no_of_question!=null&&arrData.get(0).no_of_question.length()>0?"Total Questions : "+arrData.get(0).no_of_question:"");
                    tvMarks.setText((arrData.get(0).obtained_marks!=null&&arrData.get(0).obtained_marks.length()>0)&&
                            (arrData.get(0).total_marks!=null&&arrData.get(0).total_marks.length()>0)?"Marks : "+arrData.get(0).obtained_marks+"/"+arrData.get(0).total_marks:"");
                    tvNegativeMarks.setText(arrData.get(0).negative_marking!=null&&arrData.get(0).negative_marking.length()>0?"Negative Marks : "+arrData.get(0).negative_marking:"");
                    tvTimeTaken.setText(arrData.get(0).time_taken!=null&&arrData.get(0).time_taken.length()>0?"Time Taken : "+arrData.get(0).time_taken:"");
                    timeTaken=arrData.get(0).time_taken;
                    tvCorrect.setText(arrData.get(0).correct_answers!=null&&arrData.get(0).correct_answers.length()>0?"Correct : "+arrData.get(0).correct_answers:"");
                    tvIncorrect.setText(arrData.get(0).incorrect_answers!=null&&arrData.get(0).incorrect_answers.length()>0?"InCorrect : "+arrData.get(0).incorrect_answers:"");
                }
            }else{
                Toast.makeText(context,getResources().getString(R.string.server_not_responding),Toast.LENGTH_SHORT).show();
            }
        }

        return null;
    }
}
