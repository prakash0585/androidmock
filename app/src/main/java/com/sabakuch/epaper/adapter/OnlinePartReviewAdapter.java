package com.sabakuch.epaper.adapter;

import android.content.Context;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sabakuch.epaper.R;
import com.sabakuch.epaper.apputils.CommonUtils;
import com.sabakuch.epaper.data.TestPaperData;

import java.util.ArrayList;
import java.util.List;


public class OnlinePartReviewAdapter extends BaseAdapter {
    boolean isClicked = false;
    private LayoutInflater inflater;
    private Context context;
    private List<TestPaperData> data;
    private SparseIntArray checked = new SparseIntArray();
//    String[] arr = {"a","b","c","d"};

    public OnlinePartReviewAdapter(Context context, List<TestPaperData> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    public void delete(int position) {
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        MyViewHolder holder;
        if (itemView == null) {

            itemView = View.inflate(context, R.layout.row_test_part_review, null);
            holder = new MyViewHolder();
            holder.tvTestQuestionNo = (TextView) itemView.findViewById(R.id.tv_test_question_no);
            holder.llet = (LinearLayout) itemView.findViewById(R.id.llet);
            holder.tv_test_name = (TextView) itemView.findViewById(R.id.tv_test_name);
            holder.tvTestQuestionNo = (TextView) itemView.findViewById(R.id.tv_test_question_no);
            holder.tvQuestion = (TextView) itemView.findViewById(R.id.tv_test_question);
            holder.rgTestOptions = (LinearLayout) itemView.findViewById(R.id.rg_test_options);
            holder.rgTestOption1 = (TextView) itemView.findViewById(R.id.rg_test_option1);
            holder.rgTestOption2 = (TextView) itemView.findViewById(R.id.rg_test_option2);
            holder.rgTestOption3 = (TextView) itemView.findViewById(R.id.rg_test_option3);
            holder.rgTestOption4 = (TextView) itemView.findViewById(R.id.rg_test_option4);
            holder.rgTestOption1.setTypeface(CommonUtils.getCustomTypeFace(context));
            holder.rgTestOption2.setTypeface(CommonUtils.getCustomTypeFace(context));
            holder.rgTestOption3.setTypeface(CommonUtils.getCustomTypeFace(context));
            holder.rgTestOption4.setTypeface(CommonUtils.getCustomTypeFace(context));
            holder.wvQuestion = (WebView) itemView.findViewById(R.id.wv_question);
            holder.wvTestOption1 = (WebView) itemView.findViewById(R.id.wv_test_option1);
            holder.wvTestOption2 = (WebView) itemView.findViewById(R.id.wv_test_option2);
            holder.wvTestOption3 = (WebView) itemView.findViewById(R.id.wv_test_option3);
            holder.wvTestOption4 = (WebView) itemView.findViewById(R.id.wv_test_option4);
            holder.llTestOption1 = (LinearLayout) itemView.findViewById(R.id.ll_test_option1);
            holder.llTestOption2 = (LinearLayout) itemView.findViewById(R.id.ll_test_option2);
            holder.llTestOption3 = (LinearLayout) itemView.findViewById(R.id.ll_test_option3);
            holder.llTestOption4 = (LinearLayout) itemView.findViewById(R.id.ll_test_option4);
            holder.etInput = (TextView) itemView.findViewById(R.id.tvInput);

            holder.tvViewSolution = (TextView) itemView.findViewById(R.id.tv_view_solution);
            holder.wvAnswer = (WebView) itemView.findViewById(R.id.wv_answer);
            itemView.setTag(holder);
        } else
            holder = (MyViewHolder) itemView.getTag();


        TestPaperData current = data.get(position);

//        holder.setData(current, position);//solution button visible/gone status
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
        holder.tvViewSolution.setTag(position);
        if (current.isClicked) {
            holder.wvAnswer.setVisibility(View.VISIBLE);
        } else {
            holder.wvAnswer.setVisibility(View.GONE);
        }

        final MyViewHolder holder1 = holder;
        holder.tvViewSolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isClicked) {
                    // Slide up!
                    TranslateAnimation anim = new TranslateAnimation(0, 0, 100, 0);
                    anim.setFillAfter(true);
                    anim.setDuration(200);
                    holder1.wvAnswer.startAnimation(anim);
                    holder1.wvAnswer.setVisibility(View.VISIBLE);

                    isClicked = true;

                    int pos = (int) v.getTag();
                    TestPaperData que = data.get(pos);
                    que.isClicked = true;
                } else {
                    holder1.wvAnswer.setVisibility(View.GONE);

                    isClicked = false;
                }
            }
        });


        if (current.uitype == 1) {
            holder.rgTestOptions.setVisibility(View.VISIBLE);
            holder.llet.setVisibility(View.GONE);
            if (current.answer != null && current.answer.trim().equalsIgnoreCase("a")) {
                holder.rgTestOption1.setTextColor(context.getResources().getColor(R.color._green));
                holder.rgTestOption1.setText(context.getResources().getString(R.string.fa_check_circle_o));
                if (current.answer_option != null && current.answer_option.trim().equalsIgnoreCase("a")) {
                    holder.rgTestOption2.setTextColor(context.getResources().getColor(R.color.black));
                    holder.rgTestOption2.setText(context.getResources().getString(R.string.fa_circle_o));
                    holder.rgTestOption3.setTextColor(context.getResources().getColor(R.color.black));
                    holder.rgTestOption3.setText(context.getResources().getString(R.string.fa_circle_o));
                    holder.rgTestOption4.setTextColor(context.getResources().getColor(R.color.black));
                    holder.rgTestOption4.setText(context.getResources().getString(R.string.fa_circle_o));
                } else if (current.answer_option != null && current.answer_option.trim().equalsIgnoreCase("b")) {
                    holder.rgTestOption2.setTextColor(context.getResources().getColor(R.color.red));
                    holder.rgTestOption2.setText(context.getResources().getString(R.string.fa_times_circle_o));
                    holder.rgTestOption3.setTextColor(context.getResources().getColor(R.color.black));
                    holder.rgTestOption3.setText(context.getResources().getString(R.string.fa_circle_o));
                    holder.rgTestOption4.setTextColor(context.getResources().getColor(R.color.black));
                    holder.rgTestOption4.setText(context.getResources().getString(R.string.fa_circle_o));
                } else if (current.answer_option != null && current.answer_option.trim().equalsIgnoreCase("c")) {
                    holder.rgTestOption2.setTextColor(context.getResources().getColor(R.color.black));
                    holder.rgTestOption2.setText(context.getResources().getString(R.string.fa_circle_o));
                    holder.rgTestOption3.setTextColor(context.getResources().getColor(R.color.red));
                    holder.rgTestOption3.setText(context.getResources().getString(R.string.fa_times_circle_o));
                    holder.rgTestOption4.setTextColor(context.getResources().getColor(R.color.black));
                    holder.rgTestOption4.setText(context.getResources().getString(R.string.fa_circle_o));
                } else if (current.answer_option != null && current.answer_option.trim().equalsIgnoreCase("d")) {
                    holder.rgTestOption2.setTextColor(context.getResources().getColor(R.color.black));
                    holder.rgTestOption2.setText(context.getResources().getString(R.string.fa_circle_o));
                    holder.rgTestOption3.setTextColor(context.getResources().getColor(R.color.black));
                    holder.rgTestOption3.setText(context.getResources().getString(R.string.fa_circle_o));
                    holder.rgTestOption4.setTextColor(context.getResources().getColor(R.color.red));
                    holder.rgTestOption4.setText(context.getResources().getString(R.string.fa_times_circle_o));

                }
            } else if (current.answer != null && current.answer.trim().equalsIgnoreCase("b")) {
                holder.rgTestOption2.setTextColor(context.getResources().getColor(R.color._green));
                holder.rgTestOption2.setText(context.getResources().getString(R.string.fa_check_circle_o));
                if (current.answer_option != null && current.answer_option.trim().equalsIgnoreCase("a")) {
                    holder.rgTestOption1.setTextColor(context.getResources().getColor(R.color.red));
                    holder.rgTestOption1.setText(context.getResources().getString(R.string.fa_times_circle_o));
                    holder.rgTestOption3.setTextColor(context.getResources().getColor(R.color.black));
                    holder.rgTestOption3.setText(context.getResources().getString(R.string.fa_circle_o));
                    holder.rgTestOption4.setTextColor(context.getResources().getColor(R.color.black));
                    holder.rgTestOption4.setText(context.getResources().getString(R.string.fa_circle_o));
                } else if (current.answer_option != null && current.answer_option.trim().equalsIgnoreCase("b")) {
                    holder.rgTestOption1.setTextColor(context.getResources().getColor(R.color.black));
                    holder.rgTestOption1.setText(context.getResources().getString(R.string.fa_circle_o));
                    holder.rgTestOption3.setTextColor(context.getResources().getColor(R.color.black));
                    holder.rgTestOption3.setText(context.getResources().getString(R.string.fa_circle_o));
                    holder.rgTestOption4.setTextColor(context.getResources().getColor(R.color.black));
                    holder.rgTestOption4.setText(context.getResources().getString(R.string.fa_circle_o));
                } else if (current.answer_option != null && current.answer_option.trim().equalsIgnoreCase("c")) {
                    holder.rgTestOption3.setTextColor(context.getResources().getColor(R.color.red));
                    holder.rgTestOption3.setText(context.getResources().getString(R.string.fa_times_circle_o));
                    holder.rgTestOption1.setTextColor(context.getResources().getColor(R.color.black));
                    holder.rgTestOption1.setText(context.getResources().getString(R.string.fa_circle_o));
                    holder.rgTestOption4.setTextColor(context.getResources().getColor(R.color.black));
                    holder.rgTestOption4.setText(context.getResources().getString(R.string.fa_circle_o));
                } else if (current.answer_option != null && current.answer_option.trim().equalsIgnoreCase("d")) {
                    holder.rgTestOption4.setTextColor(context.getResources().getColor(R.color.red));
                    holder.rgTestOption4.setText(context.getResources().getString(R.string.fa_times_circle_o));
                    holder.rgTestOption1.setTextColor(context.getResources().getColor(R.color.black));
                    holder.rgTestOption1.setText(context.getResources().getString(R.string.fa_circle_o));
                    holder.rgTestOption3.setTextColor(context.getResources().getColor(R.color.black));
                    holder.rgTestOption3.setText(context.getResources().getString(R.string.fa_circle_o));
                }
            } else if (current.answer != null && current.answer.trim().equalsIgnoreCase("c")) {
                holder.rgTestOption3.setTextColor(context.getResources().getColor(R.color._green));
                holder.rgTestOption3.setText(context.getResources().getString(R.string.fa_check_circle_o));
                if (current.answer_option != null && current.answer_option.trim().equalsIgnoreCase("a")) {
                    holder.rgTestOption1.setTextColor(context.getResources().getColor(R.color.red));
                    holder.rgTestOption1.setText(context.getResources().getString(R.string.fa_times_circle_o));
                    holder.rgTestOption2.setTextColor(context.getResources().getColor(R.color.black));
                    holder.rgTestOption2.setText(context.getResources().getString(R.string.fa_circle_o));
                    holder.rgTestOption4.setTextColor(context.getResources().getColor(R.color.black));
                    holder.rgTestOption4.setText(context.getResources().getString(R.string.fa_circle_o));
                } else if (current.answer_option != null && current.answer_option.trim().equalsIgnoreCase("b")) {
                    holder.rgTestOption2.setTextColor(context.getResources().getColor(R.color.red));
                    holder.rgTestOption2.setText(context.getResources().getString(R.string.fa_times_circle_o));
                    holder.rgTestOption1.setTextColor(context.getResources().getColor(R.color.black));
                    holder.rgTestOption1.setText(context.getResources().getString(R.string.fa_circle_o));
                    holder.rgTestOption4.setTextColor(context.getResources().getColor(R.color.black));
                    holder.rgTestOption4.setText(context.getResources().getString(R.string.fa_circle_o));
                } else if (current.answer_option != null && current.answer_option.trim().equalsIgnoreCase("c")) {
                    holder.rgTestOption2.setTextColor(context.getResources().getColor(R.color.black));
                    holder.rgTestOption2.setText(context.getResources().getString(R.string.fa_circle_o));
                    holder.rgTestOption1.setTextColor(context.getResources().getColor(R.color.black));
                    holder.rgTestOption1.setText(context.getResources().getString(R.string.fa_circle_o));
                    holder.rgTestOption4.setTextColor(context.getResources().getColor(R.color.black));
                    holder.rgTestOption4.setText(context.getResources().getString(R.string.fa_circle_o));
                } else if (current.answer_option != null && current.answer_option.trim().equalsIgnoreCase("d")) {
                    holder.rgTestOption4.setTextColor(context.getResources().getColor(R.color.red));
                    holder.rgTestOption4.setText(context.getResources().getString(R.string.fa_times_circle_o));
                    holder.rgTestOption2.setTextColor(context.getResources().getColor(R.color.black));
                    holder.rgTestOption2.setText(context.getResources().getString(R.string.fa_circle_o));
                    holder.rgTestOption1.setTextColor(context.getResources().getColor(R.color.black));
                    holder.rgTestOption1.setText(context.getResources().getString(R.string.fa_circle_o));
                }
            } else if (current.answer != null && current.answer.trim().equalsIgnoreCase("d")) {
                holder.rgTestOption4.setTextColor(context.getResources().getColor(R.color._green));
                holder.rgTestOption4.setText(context.getResources().getString(R.string.fa_check_circle_o));
                if (current.answer_option != null && current.answer_option.trim().equalsIgnoreCase("a")) {
                    holder.rgTestOption1.setTextColor(context.getResources().getColor(R.color.red));
                    holder.rgTestOption1.setText(context.getResources().getString(R.string.fa_times_circle_o));
                    holder.rgTestOption2.setTextColor(context.getResources().getColor(R.color.black));
                    holder.rgTestOption2.setText(context.getResources().getString(R.string.fa_circle_o));
                    holder.rgTestOption3.setTextColor(context.getResources().getColor(R.color.black));
                    holder.rgTestOption3.setText(context.getResources().getString(R.string.fa_circle_o));
                } else if (current.answer_option != null && current.answer_option.trim().equalsIgnoreCase("b")) {
                    holder.rgTestOption1.setTextColor(context.getResources().getColor(R.color.black));
                    holder.rgTestOption1.setText(context.getResources().getString(R.string.fa_circle_o));
                    holder.rgTestOption2.setTextColor(context.getResources().getColor(R.color.red));
                    holder.rgTestOption2.setText(context.getResources().getString(R.string.fa_times_circle_o));
                    holder.rgTestOption3.setTextColor(context.getResources().getColor(R.color.black));
                    holder.rgTestOption3.setText(context.getResources().getString(R.string.fa_circle_o));
                } else if (current.answer_option != null && current.answer_option.trim().equalsIgnoreCase("c")) {
                    holder.rgTestOption1.setTextColor(context.getResources().getColor(R.color.black));
                    holder.rgTestOption1.setText(context.getResources().getString(R.string.fa_circle_o));
                    holder.rgTestOption2.setTextColor(context.getResources().getColor(R.color.black));
                    holder.rgTestOption2.setText(context.getResources().getString(R.string.fa_circle_o));
                    holder.rgTestOption3.setTextColor(context.getResources().getColor(R.color.red));
                    holder.rgTestOption3.setText(context.getResources().getString(R.string.fa_times_circle_o));
                } else if (current.answer_option != null && current.answer_option.trim().equalsIgnoreCase("d")) {
                    holder.rgTestOption2.setTextColor(context.getResources().getColor(R.color.black));
                    holder.rgTestOption2.setText(context.getResources().getString(R.string.fa_circle_o));
                    holder.rgTestOption1.setTextColor(context.getResources().getColor(R.color.black));
                    holder.rgTestOption1.setText(context.getResources().getString(R.string.fa_circle_o));
                    holder.rgTestOption3.setTextColor(context.getResources().getColor(R.color.black));
                    holder.rgTestOption3.setText(context.getResources().getString(R.string.fa_circle_o));
                }
            }
        } else if (current.uitype == 3) {
            holder.rgTestOptions.setVisibility(View.GONE);
            holder.llet.setVisibility(View.VISIBLE);
            if (current.answer_option != null)
                holder.etInput.setText(current.answer_option);
        } else if (current.uitype == 2) {
            holder.rgTestOptions.setVisibility(View.VISIBLE);
            holder.llet.setVisibility(View.GONE);
            if (current.check != null && current.checkOption != null) {
                ArrayList<String> set = new ArrayList<>();
                ArrayList<String> set1 = new ArrayList<>();




                ArrayList<String> arr = new ArrayList<String>();
                arr.add("a");
                arr.add("b");
                arr.add("c");
                arr.add("d");


                for (String x : current.check) {
                    if(set1.equals(x.trim())){
                        arr.remove(x);
                    }

                }
                for (String x : current.checkOption) {
                    if (set1.contains(x.trim()))
                        arr.remove(x);
                }


                for (Object i : arr.toArray()) {
                    CommonUtils.LogMsg(OnlinePartReviewAdapter.class.getSimpleName(), "black to: "+i );
                    if (i.equals("a")) {
                        holder.rgTestOption1.setTextColor(context.getResources().getColor(R.color.black));
                        holder.rgTestOption1.setText(context.getResources().getString(R.string.fa_square_o));
                    } else if (i.equals("b")) {
                        holder.rgTestOption2.setTextColor(context.getResources().getColor(R.color.black));
                        holder.rgTestOption2.setText(context.getResources().getString(R.string.fa_square_o));
                    } else if (i.equals("c")) {
                        holder.rgTestOption3.setTextColor(context.getResources().getColor(R.color.black));
                        holder.rgTestOption3.setText(context.getResources().getString(R.string.fa_square_o));
                    } else if (i.equals("d")) {
                        holder.rgTestOption4.setTextColor(context.getResources().getColor(R.color.black));
                        holder.rgTestOption4.setText(context.getResources().getString(R.string.fa_square_o));
                    }
                }


                for (String x : current.checkOption) {
                    set.add(x.trim());
                }
                for (String x : current.check) {
                    if (set.contains(x.trim()))
                        set.remove(x);
                }
                //odd numbers
//                for (Object i : set.toArray())
//                    CommonUtils.LogMsg(OnlinePartReviewAdapter.class.getSimpleName(), i + ",");

                for (Object i : set.toArray()) {
                    CommonUtils.LogMsg(OnlinePartReviewAdapter.class.getSimpleName(), "red to: "+i );
                    if (i.equals("a")) {
                        holder.rgTestOption1.setTextColor(context.getResources().getColor(R.color.red));
                        holder.rgTestOption1.setText(context.getResources().getString(R.string.fa_window_close_o));
                    } else if (i.equals("b")) {
                        holder.rgTestOption2.setTextColor(context.getResources().getColor(R.color.red));
                        holder.rgTestOption2.setText(context.getResources().getString(R.string.fa_window_close_o));
                    } else if (i.equals("c")) {
                        holder.rgTestOption3.setTextColor(context.getResources().getColor(R.color.red));
                        holder.rgTestOption3.setText(context.getResources().getString(R.string.fa_window_close_o));
                    } else if (i.equals("d")) {
                        holder.rgTestOption4.setTextColor(context.getResources().getColor(R.color.red));
                        holder.rgTestOption4.setText(context.getResources().getString(R.string.fa_window_close_o));
                    }
                }

                CommonUtils.LogMsg(OnlinePartReviewAdapter.class.getSimpleName(), "check: " + position + " " + CommonUtils.getCustomGson().toJson(current.check) + "check option arr: " + CommonUtils.getCustomGson().toJson(current.checkOption));
                for (int i = 0; i < current.check.size(); i++) {
                    CommonUtils.LogMsg(OnlinePartReviewAdapter.class.getSimpleName(), "uiType 2 check: " + i + " " + current.check.get(i) + " check option " + current.checkOption);

                    if (current.check.get(i).trim().equals("a")) {
                        holder.rgTestOption1.setTextColor(context.getResources().getColor(R.color._green));
                        holder.rgTestOption1.setText(context.getResources().getString(R.string.fa_check_square_o));
                    }

                    if (current.check.get(i).trim().equals("b")) {
                        holder.rgTestOption2.setTextColor(context.getResources().getColor(R.color._green));
                        holder.rgTestOption2.setText(context.getResources().getString(R.string.fa_check_square_o));
                    }

                    if (current.check.get(i).trim().equals("c")) {
                        holder.rgTestOption3.setTextColor(context.getResources().getColor(R.color._green));
                        holder.rgTestOption3.setText(context.getResources().getString(R.string.fa_check_square_o));
                    }

                    if (current.check.get(i).trim().equals("d")) {
                        holder.rgTestOption4.setTextColor(context.getResources().getColor(R.color._green));
                        holder.rgTestOption4.setText(context.getResources().getString(R.string.fa_check_square_o));
                    }
                }


            }
        }

        return itemView;
    }


    // To animate view slide out from bottom to top
    public void slideToTop(View itemView, View wvAnswer) {
        TranslateAnimation animate = new TranslateAnimation(0, 0, 0, -itemView.getHeight());
        animate.setDuration(500);
        animate.setFillAfter(true);
        itemView.startAnimation(animate);
        wvAnswer.setVisibility(View.VISIBLE);
    }


    class MyViewHolder {
        TextView /*tvTestQuestion,*/tvTestQuestionNo, tvViewSolution, tv_test_name, tvQuestion;
        TextView rgTestOption1, rgTestOption2, rgTestOption3, rgTestOption4;
        LinearLayout rgTestOptions;
        LinearLayout llTestOption1, llTestOption2, llTestOption3, llTestOption4, llet;
        WebView wvQuestion, wvTestOption1, wvTestOption2, wvTestOption3, wvTestOption4, wvAnswer;

        TextView etInput;
        private boolean isClicked;


    }

}
