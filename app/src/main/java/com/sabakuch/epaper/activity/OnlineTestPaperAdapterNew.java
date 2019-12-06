package com.sabakuch.epaper.activity;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.sabakuch.epaper.R;
import com.sabakuch.epaper.adapter.OnlineTestPaperAdapter;
import com.sabakuch.epaper.apputils.CommonUtils;
import com.sabakuch.epaper.data.TestPaperData;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class OnlineTestPaperAdapterNew extends RecyclerView.Adapter<OnlineTestPaperAdapterNew.MyViewHolder> {
    private final Activity context;
    private int sectionPosition;
    private List<TestPaperData> data;
    private LayoutInflater inflater;
    private Html.ImageGetter imgGetter = new Html.ImageGetter() {

        public Drawable getDrawable(String source) {
            try {
                InputStream is = (InputStream) new URL(source).getContent();
                Drawable d = Drawable.createFromStream(is, "https://sabakuch.com/mock_paper/admin/public/editor/ckeditor4/plugins/ckeditor_wiris/integration/showimage.php?formula=53f8bb8543927b4e7acbd33efb5fde17&amp;cw=276&amp;ch=36&amp;cb=24");
                d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                return d;
            } catch (Exception e) {
                return null;
            }

        }
    };

    public OnlineTestPaperAdapterNew(Activity context, List<TestPaperData> data, int sectionPosition) {
        this.context = context;
        this.data = data;
        this.sectionPosition = sectionPosition;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_online_test_section_new, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final TestPaperData current = data.get(position);
        CommonUtils.LogMsg(OnlineTestPaperAdapter.class.getSimpleName(), "data: " + CommonUtils.getCustomGson().toJson(current));
        holder.setData(current, position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        final TextView tvQuestion, tvTestQuestionNo/*, tvCircularText*/;
        RadioButton rgTestOption1, rgTestOption2, rgTestOption3, rgTestOption4;
        RadioGroup rgTestOptions;
        WebView wvQuestion, wvTestOption1, wvTestOption2, wvTestOption3, wvTestOption4;
        LinearLayout llTestOption1, llTestOption2, llTestOption3, llTestOption4;

        public MyViewHolder(final View itemView) {
            super(itemView);
            tvTestQuestionNo = (TextView) itemView.findViewById(R.id.tv_test_question_no);
            tvQuestion = (TextView) itemView.findViewById(R.id.tv_test_question);
            rgTestOptions = (RadioGroup) itemView.findViewById(R.id.rg_test_options);
            rgTestOption1 = (RadioButton) itemView.findViewById(R.id.rg_test_option1);
            rgTestOption2 = (RadioButton) itemView.findViewById(R.id.rg_test_option2);
            rgTestOption3 = (RadioButton) itemView.findViewById(R.id.rg_test_option3);
            rgTestOption4 = (RadioButton) itemView.findViewById(R.id.rg_test_option4);
            wvQuestion = (WebView) itemView.findViewById(R.id.wv_question);
            wvTestOption1 = (WebView) itemView.findViewById(R.id.wv_test_option1);
            wvTestOption2 = (WebView) itemView.findViewById(R.id.wv_test_option2);
            wvTestOption3 = (WebView) itemView.findViewById(R.id.wv_test_option3);
            wvTestOption4 = (WebView) itemView.findViewById(R.id.wv_test_option4);
            llTestOption1 = (LinearLayout) itemView.findViewById(R.id.ll_test_option1);
            llTestOption2 = (LinearLayout) itemView.findViewById(R.id.ll_test_option2);
            llTestOption3 = (LinearLayout) itemView.findViewById(R.id.ll_test_option3);
            llTestOption4 = (LinearLayout) itemView.findViewById(R.id.ll_test_option4);
        }

        public void setData(final TestPaperData current, final int position) {
            tvQuestion.setTag(position);
            if (current.Qno != null && !current.Qno.equals("")) {
                if (current.Qno.equals("-1")) {
                    tvTestQuestionNo.setText("");
                    if (current.isPassage) {
                        wvQuestion.loadDataWithBaseURL(null, current.passage.trim(), "text/html", "utf-8", null);
                        wvQuestion.setWebChromeClient(new WebChromeClient());
                        wvQuestion.setWebViewClient(new WebViewClient());
                        wvQuestion.getSettings().setJavaScriptEnabled(true);
                        rgTestOptions.setVisibility(View.GONE);
                    } else
                        rgTestOptions.setVisibility(View.VISIBLE);
                } else {
                    tvTestQuestionNo.setText(current.Qno + ". ");
                    rgTestOptions.setVisibility(View.VISIBLE);
                }
            } else {
                rgTestOptions.setVisibility(View.VISIBLE);
                tvTestQuestionNo.setText((position + 1) + ". ");
            }

            if (current.question != null && current.question.length() > 0) {
                wvQuestion.loadDataWithBaseURL(null, current.question.trim(), "text/html", "utf-8", null);
                wvQuestion.setWebChromeClient(new WebChromeClient());
                wvQuestion.setWebViewClient(new WebViewClient());
                wvQuestion.getSettings().setJavaScriptEnabled(true);
            }
            if (current.option_a != null && current.option_a.length() > 0) {
                wvTestOption1.loadDataWithBaseURL(null, current.option_a.trim(), "text/html", "utf-8", null);
                wvTestOption1.setWebChromeClient(new WebChromeClient());
                wvTestOption1.setWebViewClient(new WebViewClient());
                wvTestOption1.getSettings().setJavaScriptEnabled(true);
            }
            if (current.option_b != null && current.option_b.length() > 0) {
                wvTestOption2.loadDataWithBaseURL(null, current.option_b.trim(), "text/html", "utf-8", null);
                wvTestOption2.setWebChromeClient(new WebChromeClient());
                wvTestOption2.setWebViewClient(new WebViewClient());
                wvTestOption2.getSettings().setJavaScriptEnabled(true);
            }
            if (current.option_c != null && current.option_c.length() > 0) {
                wvTestOption3.loadDataWithBaseURL(null, current.option_c.trim(), "text/html", "utf-8", null);
                wvTestOption3.setWebChromeClient(new WebChromeClient());
                wvTestOption3.setWebViewClient(new WebViewClient());
                wvTestOption3.getSettings().setJavaScriptEnabled(true);
            }
            if (current.option_d != null && current.option_d.length() > 0) {
                wvTestOption4.loadDataWithBaseURL(null, current.option_d.trim(), "text/html", "utf-8", null);
                wvTestOption4.setWebChromeClient(new WebChromeClient());
                wvTestOption4.setWebViewClient(new WebViewClient());
                wvTestOption4.getSettings().setJavaScriptEnabled(true);
            }

            if (current.isAnswered) {
                switch (current.checkedId) {
                    case R.id.rg_test_option1:
                        rgTestOption1.setChecked(true);
                        rgTestOption2.setChecked(false);
                        rgTestOption3.setChecked(false);
                        rgTestOption4.setChecked(false);
                        break;
                    case R.id.rg_test_option2:
                        rgTestOption2.setChecked(true);
                        rgTestOption1.setChecked(false);
                        rgTestOption3.setChecked(false);
                        rgTestOption4.setChecked(false);
                        break;
                    case R.id.rg_test_option3:
                        rgTestOption3.setChecked(true);
                        rgTestOption2.setChecked(false);
                        rgTestOption1.setChecked(false);
                        rgTestOption4.setChecked(false);
                        break;
                    case R.id.rg_test_option4:
                        rgTestOption4.setChecked(true);
                        rgTestOption2.setChecked(false);
                        rgTestOption3.setChecked(false);
                        rgTestOption1.setChecked(false);
                        break;
                }

            } else {
                rgTestOption4.setChecked(false);
                rgTestOption2.setChecked(false);
                rgTestOption3.setChecked(false);
                rgTestOption1.setChecked(false);
            }

            llTestOption1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = position;
                    TestPaperData que = data.get(pos);
                    que.isAnswered = true;
                    que.checkedId = rgTestOption1.getId();
                    if (sectionPosition == 0) {
                        OnlineTestPaperSectionFragmentNew.selectedAnswers1.set(position, "a");
                        OnlineTestPaperSectionFragmentNew.selectedQuestionId1.set(position, que.qb_id);
                    } else if (sectionPosition == 1) {
                        OnlineTestPaperSectionFragmentNew.selectedAnswers2.set(position, "a");
                        OnlineTestPaperSectionFragmentNew.selectedQuestionId2.set(position, que.qb_id);
                    } else if (sectionPosition == 2) {
                        OnlineTestPaperSectionFragmentNew.selectedAnswers3.set(position, "a");
                        OnlineTestPaperSectionFragmentNew.selectedQuestionId3.set(position, que.qb_id);
                    } else if (sectionPosition == 3) {
                        OnlineTestPaperSectionFragmentNew.selectedAnswers4.set(position, "a");
                        OnlineTestPaperSectionFragmentNew.selectedQuestionId4.set(position, que.qb_id);
                    }
                    rgTestOption1.setChecked(true);
                    rgTestOption2.setChecked(false);
                    rgTestOption3.setChecked(false);
                    rgTestOption4.setChecked(false);
                }
            });


            llTestOption2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = position;
                    TestPaperData que = data.get(pos);
                    que.isAnswered = true;
                    que.checkedId = rgTestOption2.getId();
                    if (sectionPosition == 0) {
                        OnlineTestPaperSectionFragmentNew.selectedAnswers1.set(position, "b");
                        OnlineTestPaperSectionFragmentNew.selectedQuestionId1.set(position, que.qb_id);
                    } else if (sectionPosition == 1) {
                        OnlineTestPaperSectionFragmentNew.selectedAnswers2.set(position, "b");
                        OnlineTestPaperSectionFragmentNew.selectedQuestionId2.set(position, que.qb_id);
                    } else if (sectionPosition == 2) {
                        OnlineTestPaperSectionFragmentNew.selectedAnswers3.set(position, "b");
                        OnlineTestPaperSectionFragmentNew.selectedQuestionId3.set(position, que.qb_id);
                    } else if (sectionPosition == 3) {
                        OnlineTestPaperSectionFragmentNew.selectedAnswers4.set(position, "b");
                        OnlineTestPaperSectionFragmentNew.selectedQuestionId4.set(position, que.qb_id);
                    }
                    rgTestOption2.setChecked(true);
                    rgTestOption1.setChecked(false);
                    rgTestOption3.setChecked(false);
                    rgTestOption4.setChecked(false);
                }
            });

            llTestOption3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = position;
                    TestPaperData que = data.get(pos);
                    que.isAnswered = true;
                    que.checkedId = rgTestOption3.getId();
                    if (sectionPosition == 0) {
                        OnlineTestPaperSectionFragmentNew.selectedAnswers1.set(position, "c");
                        OnlineTestPaperSectionFragmentNew.selectedQuestionId1.set(position, que.qb_id);
                    } else if (sectionPosition == 1) {
                        OnlineTestPaperSectionFragmentNew.selectedAnswers2.set(position, "c");
                        OnlineTestPaperSectionFragmentNew.selectedQuestionId2.set(position, que.qb_id);
                    } else if (sectionPosition == 2) {
                        OnlineTestPaperSectionFragmentNew.selectedAnswers3.set(position, "c");
                        OnlineTestPaperSectionFragmentNew.selectedQuestionId3.set(position, que.qb_id);
                    } else if (sectionPosition == 3) {
                        OnlineTestPaperSectionFragmentNew.selectedAnswers4.set(position, "c");
                        OnlineTestPaperSectionFragmentNew.selectedQuestionId4.set(position, que.qb_id);
                    }
                    rgTestOption3.setChecked(true);
                    rgTestOption2.setChecked(false);
                    rgTestOption1.setChecked(false);
                    rgTestOption4.setChecked(false);
                }
            });

            llTestOption4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = position;
                    TestPaperData que = data.get(pos);
                    que.isAnswered = true;
                    que.checkedId = rgTestOption4.getId();
                    if (sectionPosition == 0) {
                        OnlineTestPaperSectionFragmentNew.selectedAnswers1.set(position, "d");
                        OnlineTestPaperSectionFragmentNew.selectedQuestionId1.set(position, que.qb_id);
                    } else if (sectionPosition == 1) {
                        OnlineTestPaperSectionFragmentNew.selectedAnswers2.set(position, "d");
                        OnlineTestPaperSectionFragmentNew.selectedQuestionId2.set(position, que.qb_id);
                    } else if (sectionPosition == 2) {
                        OnlineTestPaperSectionFragmentNew.selectedAnswers3.set(position, "d");
                        OnlineTestPaperSectionFragmentNew.selectedQuestionId3.set(position, que.qb_id);
                    } else if (sectionPosition == 3) {
                        OnlineTestPaperSectionFragmentNew.selectedAnswers4.set(position, "d");
                        OnlineTestPaperSectionFragmentNew.selectedQuestionId4.set(position, que.qb_id);
                    }
                    rgTestOption4.setChecked(true);
                    rgTestOption2.setChecked(false);
                    rgTestOption3.setChecked(false);
                    rgTestOption1.setChecked(false);
                }
            });
        }
    }

}
