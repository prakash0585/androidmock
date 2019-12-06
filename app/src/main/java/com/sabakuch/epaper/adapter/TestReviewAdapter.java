package com.sabakuch.epaper.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sabakuch.epaper.R;
import com.sabakuch.epaper.apputils.CommonUtils;
import com.sabakuch.epaper.data.TestPaperData;

import java.util.List;


public class TestReviewAdapter extends RecyclerView.Adapter<TestReviewAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private List<TestPaperData> data;
    private SparseIntArray checked = new SparseIntArray();

    public TestReviewAdapter(Context context, List<TestPaperData> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    public void delete(int position) {
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_test_review, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    // To animate view slide out from bottom to top
    public void slideToTop(View itemView, View wvAnswer) {
        TranslateAnimation animate = new TranslateAnimation(0, 0, 0, -itemView.getHeight());
        animate.setDuration(500);
        animate.setFillAfter(true);
        itemView.startAnimation(animate);
        wvAnswer.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        TestPaperData current = data.get(position);

        holder.setData(current, position);//solution button visible/gone status
        holder.tvTestQuestionNo.setText((position + 1) + ". ");

        if (current.solution != null && current.solution.length() > 0) {
            holder.wvAnswer.loadDataWithBaseURL(null, ((current.answer != null && current.answer.length() > 0 ? "Answer : " + current.answer : "") + "\n" +
                    (current.solution != null && current.solution.length() > 0 ? current.solution.trim() : "")).trim(), "text/html", "utf-8", null);
        }

        if (current.question != null && current.question.length() > 0) {
            holder.wvQuestion.loadDataWithBaseURL(null, current.question.trim(), "text/html", "utf-8", null);
        }
        if (current.option_a != null && current.option_a.length() > 0) {
            holder.wvTestOption1.loadDataWithBaseURL(null, current.option_a.trim(), "text/html", "utf-8", null);
        }
        if (current.option_b != null && current.option_b.length() > 0) {
            holder.wvTestOption2.loadDataWithBaseURL(null, current.option_b.trim(), "text/html", "utf-8", null);
        }
        if (current.option_c != null && current.option_c.length() > 0) {
            holder.wvTestOption3.loadDataWithBaseURL(null, current.option_c.trim(), "text/html", "utf-8", null);
        }
        if (current.option_d != null && current.option_d.length() > 0) {
            holder.wvTestOption4.loadDataWithBaseURL(null, current.option_d.trim(), "text/html", "utf-8", null);
        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView /*tvTestQuestion,*/tvTestQuestionNo, tvViewSolution/*,tvAnswer*/;
        TextView rgTestOption1, rgTestOption2, rgTestOption3, rgTestOption4;
        LinearLayout rgTestOptions;
        WebView wvQuestion, wvTestOption1, wvTestOption2, wvTestOption3, wvTestOption4, wvAnswer;
        private boolean isClicked;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTestQuestionNo = (TextView) itemView.findViewById(R.id.tv_test_question_no);

            rgTestOptions = (LinearLayout) itemView.findViewById(R.id.rg_test_options);
            rgTestOption1 = (TextView) itemView.findViewById(R.id.rg_test_option1);
            rgTestOption2 = (TextView) itemView.findViewById(R.id.rg_test_option2);
            rgTestOption3 = (TextView) itemView.findViewById(R.id.rg_test_option3);
            rgTestOption4 = (TextView) itemView.findViewById(R.id.rg_test_option4);
            rgTestOption1.setTypeface(CommonUtils.getCustomTypeFace(context));
            rgTestOption2.setTypeface(CommonUtils.getCustomTypeFace(context));
            rgTestOption3.setTypeface(CommonUtils.getCustomTypeFace(context));
            rgTestOption4.setTypeface(CommonUtils.getCustomTypeFace(context));
            wvQuestion = (WebView) itemView.findViewById(R.id.wv_question);
            wvTestOption1 = (WebView) itemView.findViewById(R.id.wv_test_option1);
            wvTestOption2 = (WebView) itemView.findViewById(R.id.wv_test_option2);
            wvTestOption3 = (WebView) itemView.findViewById(R.id.wv_test_option3);
            wvTestOption4 = (WebView) itemView.findViewById(R.id.wv_test_option4);

            tvViewSolution = (TextView) itemView.findViewById(R.id.tv_view_solution);
            wvAnswer = (WebView) itemView.findViewById(R.id.wv_answer);
        }

        public void setData(TestPaperData current, final int position) {
            tvViewSolution.setTag(position);
            if (current.isClicked) {
                wvAnswer.setVisibility(View.VISIBLE);
            } else {
                wvAnswer.setVisibility(View.GONE);
            }

            tvViewSolution.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isClicked) {
                        // Slide up!
                        TranslateAnimation anim = new TranslateAnimation(0, 0, 100, 0);
                        anim.setFillAfter(true);
                        anim.setDuration(200);
                        wvAnswer.startAnimation(anim);
                        wvAnswer.setVisibility(View.VISIBLE);

                        isClicked = true;

                        int pos = (int) v.getTag();
                        TestPaperData que = data.get(pos);
                        que.isClicked = true;
                    } else {
                        wvAnswer.setVisibility(View.GONE);

                        isClicked = false;
                    }
                }
            });



            if (current.answer != null && current.answer.trim().equalsIgnoreCase("a")) {
                rgTestOption1.setTextColor(context.getResources().getColor(R.color._green));
                rgTestOption1.setText(context.getResources().getString(R.string.fa_check_circle_o));
                if (current.answer_option != null && current.answer_option.trim().equalsIgnoreCase("a")) {
                    rgTestOption2.setTextColor(context.getResources().getColor(R.color.black));
                    rgTestOption2.setText(context.getResources().getString(R.string.fa_circle_o));
                    rgTestOption3.setTextColor(context.getResources().getColor(R.color.black));
                    rgTestOption3.setText(context.getResources().getString(R.string.fa_circle_o));
                    rgTestOption4.setTextColor(context.getResources().getColor(R.color.black));
                    rgTestOption4.setText(context.getResources().getString(R.string.fa_circle_o));
                } else if (current.answer_option != null && current.answer_option.trim().equalsIgnoreCase("b")) {
                    rgTestOption2.setTextColor(context.getResources().getColor(R.color.red));
                    rgTestOption2.setText(context.getResources().getString(R.string.fa_times_circle_o));
                    rgTestOption3.setTextColor(context.getResources().getColor(R.color.black));
                    rgTestOption3.setText(context.getResources().getString(R.string.fa_circle_o));
                    rgTestOption4.setTextColor(context.getResources().getColor(R.color.black));
                    rgTestOption4.setText(context.getResources().getString(R.string.fa_circle_o));
                } else if (current.answer_option != null && current.answer_option.trim().equalsIgnoreCase("c")) {
                    rgTestOption2.setTextColor(context.getResources().getColor(R.color.black));
                    rgTestOption2.setText(context.getResources().getString(R.string.fa_circle_o));
                    rgTestOption3.setTextColor(context.getResources().getColor(R.color.red));
                    rgTestOption3.setText(context.getResources().getString(R.string.fa_times_circle_o));
                    rgTestOption4.setTextColor(context.getResources().getColor(R.color.black));
                    rgTestOption4.setText(context.getResources().getString(R.string.fa_circle_o));
                } else if (current.answer_option != null && current.answer_option.trim().equalsIgnoreCase("d")) {
                    rgTestOption2.setTextColor(context.getResources().getColor(R.color.black));
                    rgTestOption2.setText(context.getResources().getString(R.string.fa_circle_o));
                    rgTestOption3.setTextColor(context.getResources().getColor(R.color.black));
                    rgTestOption3.setText(context.getResources().getString(R.string.fa_circle_o));
                    rgTestOption4.setTextColor(context.getResources().getColor(R.color.red));
                    rgTestOption4.setText(context.getResources().getString(R.string.fa_times_circle_o));

                }
            } else if (current.answer != null && current.answer.trim().equalsIgnoreCase("b")) {
                rgTestOption2.setTextColor(context.getResources().getColor(R.color._green));
                rgTestOption2.setText(context.getResources().getString(R.string.fa_check_circle_o));
                if (current.answer_option != null && current.answer_option.trim().equalsIgnoreCase("a")) {
                    rgTestOption1.setTextColor(context.getResources().getColor(R.color.red));
                    rgTestOption1.setText(context.getResources().getString(R.string.fa_times_circle_o));
                    rgTestOption3.setTextColor(context.getResources().getColor(R.color.black));
                    rgTestOption3.setText(context.getResources().getString(R.string.fa_circle_o));
                    rgTestOption4.setTextColor(context.getResources().getColor(R.color.black));
                    rgTestOption4.setText(context.getResources().getString(R.string.fa_circle_o));
                } else if (current.answer_option != null && current.answer_option.trim().equalsIgnoreCase("b")) {
                    rgTestOption1.setTextColor(context.getResources().getColor(R.color.black));
                    rgTestOption1.setText(context.getResources().getString(R.string.fa_circle_o));
                    rgTestOption3.setTextColor(context.getResources().getColor(R.color.black));
                    rgTestOption3.setText(context.getResources().getString(R.string.fa_circle_o));
                    rgTestOption4.setTextColor(context.getResources().getColor(R.color.black));
                    rgTestOption4.setText(context.getResources().getString(R.string.fa_circle_o));
                } else if (current.answer_option != null && current.answer_option.trim().equalsIgnoreCase("c")) {
                    rgTestOption3.setTextColor(context.getResources().getColor(R.color.red));
                    rgTestOption3.setText(context.getResources().getString(R.string.fa_times_circle_o));
                    rgTestOption1.setTextColor(context.getResources().getColor(R.color.black));
                    rgTestOption1.setText(context.getResources().getString(R.string.fa_circle_o));
                    rgTestOption4.setTextColor(context.getResources().getColor(R.color.black));
                    rgTestOption4.setText(context.getResources().getString(R.string.fa_circle_o));
                } else if (current.answer_option != null && current.answer_option.trim().equalsIgnoreCase("d")) {
                    rgTestOption4.setTextColor(context.getResources().getColor(R.color.red));
                    rgTestOption4.setText(context.getResources().getString(R.string.fa_times_circle_o));
                    rgTestOption1.setTextColor(context.getResources().getColor(R.color.black));
                    rgTestOption1.setText(context.getResources().getString(R.string.fa_circle_o));
                    rgTestOption3.setTextColor(context.getResources().getColor(R.color.black));
                    rgTestOption3.setText(context.getResources().getString(R.string.fa_circle_o));
                }
            } else if (current.answer != null && current.answer.trim().equalsIgnoreCase("c")) {
                rgTestOption3.setTextColor(context.getResources().getColor(R.color._green));
                rgTestOption3.setText(context.getResources().getString(R.string.fa_check_circle_o));
                if (current.answer_option != null && current.answer_option.trim().equalsIgnoreCase("a")) {
                    rgTestOption1.setTextColor(context.getResources().getColor(R.color.red));
                    rgTestOption1.setText(context.getResources().getString(R.string.fa_times_circle_o));
                    rgTestOption2.setTextColor(context.getResources().getColor(R.color.black));
                    rgTestOption2.setText(context.getResources().getString(R.string.fa_circle_o));
                    rgTestOption4.setTextColor(context.getResources().getColor(R.color.black));
                    rgTestOption4.setText(context.getResources().getString(R.string.fa_circle_o));
                } else if (current.answer_option != null && current.answer_option.trim().equalsIgnoreCase("b")) {
                    rgTestOption2.setTextColor(context.getResources().getColor(R.color.red));
                    rgTestOption2.setText(context.getResources().getString(R.string.fa_times_circle_o));
                    rgTestOption1.setTextColor(context.getResources().getColor(R.color.black));
                    rgTestOption1.setText(context.getResources().getString(R.string.fa_circle_o));
                    rgTestOption4.setTextColor(context.getResources().getColor(R.color.black));
                    rgTestOption4.setText(context.getResources().getString(R.string.fa_circle_o));
                } else if (current.answer_option != null && current.answer_option.trim().equalsIgnoreCase("c")) {
                    rgTestOption2.setTextColor(context.getResources().getColor(R.color.black));
                    rgTestOption2.setText(context.getResources().getString(R.string.fa_circle_o));
                    rgTestOption1.setTextColor(context.getResources().getColor(R.color.black));
                    rgTestOption1.setText(context.getResources().getString(R.string.fa_circle_o));
                    rgTestOption4.setTextColor(context.getResources().getColor(R.color.black));
                    rgTestOption4.setText(context.getResources().getString(R.string.fa_circle_o));
                } else if (current.answer_option != null && current.answer_option.trim().equalsIgnoreCase("d")) {
                    rgTestOption4.setTextColor(context.getResources().getColor(R.color.red));
                    rgTestOption4.setText(context.getResources().getString(R.string.fa_times_circle_o));
                    rgTestOption2.setTextColor(context.getResources().getColor(R.color.black));
                    rgTestOption2.setText(context.getResources().getString(R.string.fa_circle_o));
                    rgTestOption1.setTextColor(context.getResources().getColor(R.color.black));
                    rgTestOption1.setText(context.getResources().getString(R.string.fa_circle_o));
                }
            } else if (current.answer != null && current.answer.trim().equalsIgnoreCase("d")) {
                rgTestOption4.setTextColor(context.getResources().getColor(R.color._green));
                rgTestOption4.setText(context.getResources().getString(R.string.fa_check_circle_o));
                if (current.answer_option != null && current.answer_option.trim().equalsIgnoreCase("a")) {
                    rgTestOption1.setTextColor(context.getResources().getColor(R.color.red));
                    rgTestOption1.setText(context.getResources().getString(R.string.fa_times_circle_o));
                    rgTestOption2.setTextColor(context.getResources().getColor(R.color.black));
                    rgTestOption2.setText(context.getResources().getString(R.string.fa_circle_o));
                    rgTestOption3.setTextColor(context.getResources().getColor(R.color.black));
                    rgTestOption3.setText(context.getResources().getString(R.string.fa_circle_o));
                } else if (current.answer_option != null && current.answer_option.trim().equalsIgnoreCase("b")) {
                    rgTestOption1.setTextColor(context.getResources().getColor(R.color.black));
                    rgTestOption1.setText(context.getResources().getString(R.string.fa_circle_o));
                    rgTestOption2.setTextColor(context.getResources().getColor(R.color.red));
                    rgTestOption2.setText(context.getResources().getString(R.string.fa_times_circle_o));
                    rgTestOption3.setTextColor(context.getResources().getColor(R.color.black));
                    rgTestOption3.setText(context.getResources().getString(R.string.fa_circle_o));
                } else if (current.answer_option != null && current.answer_option.trim().equalsIgnoreCase("c")) {
                    rgTestOption1.setTextColor(context.getResources().getColor(R.color.black));
                    rgTestOption1.setText(context.getResources().getString(R.string.fa_circle_o));
                    rgTestOption2.setTextColor(context.getResources().getColor(R.color.black));
                    rgTestOption2.setText(context.getResources().getString(R.string.fa_circle_o));
                    rgTestOption3.setTextColor(context.getResources().getColor(R.color.red));
                    rgTestOption3.setText(context.getResources().getString(R.string.fa_times_circle_o));
                } else if (current.answer_option != null && current.answer_option.trim().equalsIgnoreCase("d")) {
                    rgTestOption2.setTextColor(context.getResources().getColor(R.color.black));
                    rgTestOption2.setText(context.getResources().getString(R.string.fa_circle_o));
                    rgTestOption1.setTextColor(context.getResources().getColor(R.color.black));
                    rgTestOption1.setText(context.getResources().getString(R.string.fa_circle_o));
                    rgTestOption3.setTextColor(context.getResources().getColor(R.color.black));
                    rgTestOption3.setText(context.getResources().getString(R.string.fa_circle_o));
                }
            }

        }
    }

}
