package com.sabakuch.epaper.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.sabakuch.epaper.R;
import com.sabakuch.epaper.apputils.CommonUtils;
import com.sabakuch.epaper.data.QuestionDataResponse;
import com.sabakuch.epaper.fragment.OnlineTestPaperSectionFragment;

import java.util.ArrayList;

public class OnlinePartAdapter extends BaseAdapter {
    Context cxt;
    OnClickListener mOnClickListener;
    StringBuilder stringBuilder;
    private ArrayList<QuestionDataResponse.Question> data;

    public OnlinePartAdapter(Context cxt, ArrayList<QuestionDataResponse.Question> question,
                             OnClickListener mOnClickListener) {
        this.cxt = cxt;
        this.data = question;
        this.mOnClickListener = mOnClickListener;
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
        PlanetHolder mPlanetHolder = new PlanetHolder();

        if (itemView == null) {

            itemView = View.inflate(cxt, R.layout.row_online_test_part_paper, null);
            mPlanetHolder.llet = (LinearLayout) itemView.findViewById(R.id.llet);
            mPlanetHolder.tv_test_name = (TextView) itemView.findViewById(R.id.tv_test_name);
            mPlanetHolder.tvTestQuestionNo = (TextView) itemView.findViewById(R.id.tv_test_question_no);
            mPlanetHolder.tvQuestion = (TextView) itemView.findViewById(R.id.tv_test_question);
            mPlanetHolder.rgTestOptions = (RadioGroup) itemView.findViewById(R.id.rg_test_options);
            mPlanetHolder.rgTestOption1 = (RadioButton) itemView.findViewById(R.id.rg_test_option1);
            mPlanetHolder.rgTestOption2 = (RadioButton) itemView.findViewById(R.id.rg_test_option2);
            mPlanetHolder.rgTestOption3 = (RadioButton) itemView.findViewById(R.id.rg_test_option3);
            mPlanetHolder.rgTestOption4 = (RadioButton) itemView.findViewById(R.id.rg_test_option4);
            mPlanetHolder.wvQuestion = (WebView) itemView.findViewById(R.id.wv_question);
            mPlanetHolder.wvTestOption1 = (WebView) itemView.findViewById(R.id.wv_test_option1);
            mPlanetHolder.wvTestOption2 = (WebView) itemView.findViewById(R.id.wv_test_option2);
            mPlanetHolder.wvTestOption3 = (WebView) itemView.findViewById(R.id.wv_test_option3);
            mPlanetHolder.wvTestOption4 = (WebView) itemView.findViewById(R.id.wv_test_option4);
            mPlanetHolder.llTestOption1 = (LinearLayout) itemView.findViewById(R.id.ll_test_option1);
            mPlanetHolder.llTestOption2 = (LinearLayout) itemView.findViewById(R.id.ll_test_option2);
            mPlanetHolder.llTestOption3 = (LinearLayout) itemView.findViewById(R.id.ll_test_option3);
            mPlanetHolder.llTestOption4 = (LinearLayout) itemView.findViewById(R.id.ll_test_option4);
            mPlanetHolder.wv_check_test_option1 = (WebView) itemView.findViewById(R.id.wv_check_test_option1);
            mPlanetHolder.wv_check_test_option2 = (WebView) itemView.findViewById(R.id.wv_check_test_option2);
            mPlanetHolder.wv_check_test_option3 = (WebView) itemView.findViewById(R.id.wv_check_test_option3);
            mPlanetHolder.wv_check_test_option4 = (WebView) itemView.findViewById(R.id.wv_check_test_option4);
            mPlanetHolder.llCheckbox = (LinearLayout) itemView.findViewById(R.id.llCheckbox);
            mPlanetHolder.llCheckbox1 = (LinearLayout) itemView.findViewById(R.id.llCheckbox1);
            mPlanetHolder.llCheckbox2 = (LinearLayout) itemView.findViewById(R.id.llCheckbox2);
            mPlanetHolder.llCheckbox3 = (LinearLayout) itemView.findViewById(R.id.llCheckbox3);
            mPlanetHolder.llCheckbox4 = (LinearLayout) itemView.findViewById(R.id.llCheckbox4);
            mPlanetHolder.chk1 = (CheckBox) itemView.findViewById(R.id.chk1);
            mPlanetHolder.chk2 = (CheckBox) itemView.findViewById(R.id.chk2);
            mPlanetHolder.chk3 = (CheckBox) itemView.findViewById(R.id.chk3);
            mPlanetHolder.chk4 = (CheckBox) itemView.findViewById(R.id.chk4);
            mPlanetHolder.etInput = (EditText) itemView.findViewById(R.id.etInput);
            itemView.setTag(mPlanetHolder);
        } else
            mPlanetHolder = (PlanetHolder) itemView.getTag();

        final QuestionDataResponse.Question current = data.get(position);
//        SectionDetailPartData.Sectiondetail sectiondetail = sectiondetailarr.get(current.getSectionposition());
        if (!current.isInstruction()) {
            mPlanetHolder.tvQuestion.setTag(position);
            CommonUtils.LogMsg(OnlinePartAdapter.class.getSimpleName(),"current pos" + position + current.getQuestion());
            mPlanetHolder.tvTestQuestionNo.setText(current.getQuestionNo() + ". ");


            if (current.getQuestion() != null && current.getQuestion().length() > 0) {
                mPlanetHolder.wvQuestion.loadDataWithBaseURL(null, current.getQuestion().trim(), "text/html", "utf-8", null);
                mPlanetHolder.wvQuestion.setWebChromeClient(new WebChromeClient());
                mPlanetHolder.wvQuestion.setWebViewClient(new WebViewClient());
                mPlanetHolder.wvQuestion.getSettings().setJavaScriptEnabled(true);
            }

            // UI type == 1 when answers need to be radio button
            if (current.getUiType() == 1) {
                CommonUtils.LogMsg(OnlinePartAdapter.class.getSimpleName(), " current uitype: " + current.getUiType());
                mPlanetHolder.llCheckbox.setVisibility(View.GONE);
                mPlanetHolder.llet.setVisibility(View.GONE);
                mPlanetHolder.rgTestOptions.setVisibility(View.VISIBLE);

                if (current.getOptionA() != null && current.getOptionA().length() > 0) {
                    mPlanetHolder.wvTestOption1.loadDataWithBaseURL(null, current.getOptionA().trim(), "text/html", "utf-8", null);
                    mPlanetHolder.wvTestOption1.setWebChromeClient(new WebChromeClient());
                    mPlanetHolder.wvTestOption1.setWebViewClient(new WebViewClient());
                    mPlanetHolder.wvTestOption1.getSettings().setJavaScriptEnabled(true);
                }
                if (current.getOptionB() != null && current.getOptionB().length() > 0) {
                    mPlanetHolder.wvTestOption2.loadDataWithBaseURL(null, current.getOptionB().trim(), "text/html", "utf-8", null);
                    mPlanetHolder.wvTestOption2.setWebChromeClient(new WebChromeClient());
                    mPlanetHolder.wvTestOption2.setWebViewClient(new WebViewClient());
                    mPlanetHolder.wvTestOption2.getSettings().setJavaScriptEnabled(true);
                }
                if (current.getOptionC() != null && current.getOptionC().length() > 0) {
                    mPlanetHolder.wvTestOption3.loadDataWithBaseURL(null, current.getOptionC().trim(), "text/html", "utf-8", null);
                    mPlanetHolder.wvTestOption3.setWebChromeClient(new WebChromeClient());
                    mPlanetHolder.wvTestOption3.setWebViewClient(new WebViewClient());
                    mPlanetHolder.wvTestOption3.getSettings().setJavaScriptEnabled(true);
                }
                if (current.getOptionD() != null && current.getOptionD().length() > 0) {
                    mPlanetHolder.wvTestOption4.loadDataWithBaseURL(null, current.getOptionD().trim(), "text/html", "utf-8", null);
                    mPlanetHolder.wvTestOption4.setWebChromeClient(new WebChromeClient());
                    mPlanetHolder.wvTestOption4.setWebViewClient(new WebViewClient());
                    mPlanetHolder.wvTestOption4.getSettings().setJavaScriptEnabled(true);
                }

                if (current.getIsAnswered()) {
                    switch (current.getCheckedId()) {
                        case R.id.rg_test_option1:
                            mPlanetHolder.rgTestOption1.setChecked(true);
                            mPlanetHolder.rgTestOption2.setChecked(false);
                            mPlanetHolder.rgTestOption3.setChecked(false);
                            mPlanetHolder.rgTestOption4.setChecked(false);
                            break;
                        case R.id.rg_test_option2:
                            mPlanetHolder.rgTestOption2.setChecked(true);
                            mPlanetHolder.rgTestOption1.setChecked(false);
                            mPlanetHolder.rgTestOption3.setChecked(false);
                            mPlanetHolder.rgTestOption4.setChecked(false);
                            break;
                        case R.id.rg_test_option3:
                            mPlanetHolder.rgTestOption3.setChecked(true);
                            mPlanetHolder.rgTestOption2.setChecked(false);
                            mPlanetHolder.rgTestOption1.setChecked(false);
                            mPlanetHolder.rgTestOption4.setChecked(false);
                            break;
                        case R.id.rg_test_option4:
                            mPlanetHolder.rgTestOption4.setChecked(true);
                            mPlanetHolder.rgTestOption2.setChecked(false);
                            mPlanetHolder.rgTestOption3.setChecked(false);
                            mPlanetHolder.rgTestOption1.setChecked(false);
                            break;
                    }

                } else {
                    mPlanetHolder.rgTestOption4.setChecked(false);
                    mPlanetHolder.rgTestOption2.setChecked(false);
                    mPlanetHolder.rgTestOption3.setChecked(false);
                    mPlanetHolder.rgTestOption1.setChecked(false);
                }
                final PlanetHolder holder = mPlanetHolder;

                mPlanetHolder.llTestOption1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = position;
                        QuestionDataResponse.Question que = data.get(pos);
                        que.setIsAnswered(true);
                        que.setCheckedId(holder.rgTestOption1.getId());
                        if (que.getSectionposition() == 0) {
                            OnlineTestPaperSectionFragment.selectedAnswers1.set(position, "a");
                            OnlineTestPaperSectionFragment.selectedQuestionId1.set(position, que.getQbId());
                        } else if (que.getSectionposition() == 1) {
                            OnlineTestPaperSectionFragment.selectedAnswers2.set(position, "a");
                            OnlineTestPaperSectionFragment.selectedQuestionId2.set(position, que.getQbId());
                        } else if (que.getSectionposition() == 2) {
                            OnlineTestPaperSectionFragment.selectedAnswers3.set(position, "a");
                            OnlineTestPaperSectionFragment.selectedQuestionId3.set(position, que.getQbId());
                        }
                        holder.rgTestOption1.setChecked(true);
                        holder.rgTestOption2.setChecked(false);
                        holder.rgTestOption3.setChecked(false);
                        holder.rgTestOption4.setChecked(false);
                    }
                });


                mPlanetHolder.llTestOption2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = position;
                        QuestionDataResponse.Question que = data.get(pos);
                        que.setIsAnswered(true);
                        que.setCheckedId(holder.rgTestOption2.getId());
                        if (que.getSectionposition() == 0) {
                            OnlineTestPaperSectionFragment.selectedAnswers1.set(position, "b");
                            OnlineTestPaperSectionFragment.selectedQuestionId1.set(position, que.getQbId());
                        } else if (que.getSectionposition() == 1) {
                            OnlineTestPaperSectionFragment.selectedAnswers2.set(position, "b");
                            OnlineTestPaperSectionFragment.selectedQuestionId2.set(position, que.getQbId());
                        } else if (que.getSectionposition() == 2) {
                            OnlineTestPaperSectionFragment.selectedAnswers3.set(position, "b");
                            OnlineTestPaperSectionFragment.selectedQuestionId3.set(position, que.getQbId());
                        }
                        holder.rgTestOption2.setChecked(true);
                        holder.rgTestOption1.setChecked(false);
                        holder.rgTestOption3.setChecked(false);
                        holder.rgTestOption4.setChecked(false);
                    }
                });

                mPlanetHolder.llTestOption3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = position;
                        QuestionDataResponse.Question que = data.get(pos);
                        que.setIsAnswered(true);
                        que.setCheckedId(holder.rgTestOption3.getId());
                        if (que.getSectionposition() == 0) {
                            OnlineTestPaperSectionFragment.selectedAnswers1.set(position, "c");
                            OnlineTestPaperSectionFragment.selectedQuestionId1.set(position, que.getQbId());
                        } else if (que.getSectionposition() == 1) {
                            OnlineTestPaperSectionFragment.selectedAnswers2.set(position, "c");
                            OnlineTestPaperSectionFragment.selectedQuestionId2.set(position, que.getQbId());
                        } else if (que.getSectionposition() == 2) {
                            OnlineTestPaperSectionFragment.selectedAnswers3.set(position, "c");
                            OnlineTestPaperSectionFragment.selectedQuestionId3.set(position, que.getQbId());
                        }
                        holder.rgTestOption3.setChecked(true);
                        holder.rgTestOption2.setChecked(false);
                        holder.rgTestOption1.setChecked(false);
                        holder.rgTestOption4.setChecked(false);
                    }
                });

                mPlanetHolder.llTestOption4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = position;
                        QuestionDataResponse.Question que = data.get(pos);
                        que.setIsAnswered(true);
                        que.setCheckedId(holder.rgTestOption4.getId());
                        if (que.getSectionposition() == 0) {
                            OnlineTestPaperSectionFragment.selectedAnswers1.set(position, "d");
                            OnlineTestPaperSectionFragment.selectedQuestionId1.set(position, que.getQbId());
                        } else if (que.getSectionposition() == 1) {
                            OnlineTestPaperSectionFragment.selectedAnswers2.set(position, "d");
                            OnlineTestPaperSectionFragment.selectedQuestionId2.set(position, que.getQbId());
                        } else if (que.getSectionposition() == 2) {
                            OnlineTestPaperSectionFragment.selectedAnswers3.set(position, "d");
                            OnlineTestPaperSectionFragment.selectedQuestionId3.set(position, que.getQbId());
                        }
                        holder.rgTestOption4.setChecked(true);
                        holder.rgTestOption2.setChecked(false);
                        holder.rgTestOption3.setChecked(false);
                        holder.rgTestOption1.setChecked(false);
                    }
                });
            } else if (current.getUiType() == 2) {
                // UI type == 2 when answers need to be check box
                mPlanetHolder.llCheckbox.setVisibility(View.VISIBLE);
                mPlanetHolder.rgTestOptions.setVisibility(View.GONE);
                mPlanetHolder.llet.setVisibility(View.GONE);

                if (current.getOptionA() != null && current.getOptionA().length() > 0) {
                    mPlanetHolder.wv_check_test_option1.loadDataWithBaseURL(null, current.getOptionA().trim(), "text/html", "utf-8", null);
                    mPlanetHolder.wv_check_test_option1.setWebChromeClient(new WebChromeClient());
                    mPlanetHolder.wv_check_test_option1.setWebViewClient(new WebViewClient());
                    mPlanetHolder.wv_check_test_option1.getSettings().setJavaScriptEnabled(true);
                }
                if (current.getOptionB() != null && current.getOptionB().length() > 0) {
                    mPlanetHolder.wv_check_test_option2.loadDataWithBaseURL(null, current.getOptionB().trim(), "text/html", "utf-8", null);
                    mPlanetHolder.wv_check_test_option2.setWebChromeClient(new WebChromeClient());
                    mPlanetHolder.wv_check_test_option2.setWebViewClient(new WebViewClient());
                    mPlanetHolder.wv_check_test_option2.getSettings().setJavaScriptEnabled(true);
                }
                if (current.getOptionC() != null && current.getOptionC().length() > 0) {
                    mPlanetHolder.wv_check_test_option3.loadDataWithBaseURL(null, current.getOptionC().trim(), "text/html", "utf-8", null);
                    mPlanetHolder.wv_check_test_option3.setWebChromeClient(new WebChromeClient());
                    mPlanetHolder.wv_check_test_option3.setWebViewClient(new WebViewClient());
                    mPlanetHolder.wv_check_test_option3.getSettings().setJavaScriptEnabled(true);
                }
                if (current.getOptionD() != null && current.getOptionD().length() > 0) {
                    mPlanetHolder.wv_check_test_option4.loadDataWithBaseURL(null, current.getOptionD().trim(), "text/html", "utf-8", null);
                    mPlanetHolder.wv_check_test_option4.setWebChromeClient(new WebChromeClient());
                    mPlanetHolder.wv_check_test_option4.setWebViewClient(new WebViewClient());
                    mPlanetHolder.wv_check_test_option4.getSettings().setJavaScriptEnabled(true);
                }

                mPlanetHolder.chk1.setChecked(data.get(position).isChecked1());
                mPlanetHolder.chk2.setChecked(data.get(position).isChecked2());
                mPlanetHolder.chk3.setChecked(data.get(position).isChecked3());
                mPlanetHolder.chk4.setChecked(data.get(position).isChecked4());

                final PlanetHolder holder = mPlanetHolder;
                mPlanetHolder.chk1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos1 = position;
                        QuestionDataResponse.Question que = data.get(pos1);
                        que.setIsAnswered(true);

                        // if checked then assign on array too to update the array
                        if (holder.chk1.isChecked()) {
                            holder.chk1.setChecked(true);
                            que.setChecked1(true);
                            data.set(pos1, que);
                        } else {
                            holder.chk1.setChecked(false);
                            que.setChecked1(false);
                            data.set(pos1, que);
                        }

                        // If answer_opt null or blank then add on specific position
                        // else filter with checkIfthereOnNot Method
                        if (que.getAnswer_opt() != null && !que.getAnswer_opt().equals("")) {
                            StringBuilder sb = new StringBuilder(que.getAnswer_opt());
                            stringBuilder = checkIfThereOrNot(sb, "a");
                            que.setAnswer_opt(stringBuilder.toString());
                            data.set(pos1, que);
                        } else {
                            que.setAnswer_opt("a");
                            data.set(pos1, que);
                        }

                        notifyDataSetChanged();
                        CommonUtils.LogMsg(OnlinePartAdapter.class.getSimpleName(), "pos: " + pos1 + " chkList: " + data.get(pos1).getAnswer_opt());
                    }
                });
                mPlanetHolder.chk2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos1 = position;
                        QuestionDataResponse.Question que = data.get(pos1);
                        que.setIsAnswered(true);

                        // if checked then assign on array too to update the array
                        if (holder.chk2.isChecked()) {
                            holder.chk2.setChecked(true);
                            que.setChecked2(true);
                            data.set(pos1, que);
                        } else {
                            holder.chk2.setChecked(false);
                            que.setChecked2(false);
                            data.set(pos1, que);
                        }
                        // If answer_opt null or blank then add on specific position
                        // else filter with checkIfthereOnNot Method
                        if (que.getAnswer_opt() != null && !que.getAnswer_opt().equals("")) {
                            StringBuilder sb = new StringBuilder(que.getAnswer_opt());
                            stringBuilder = checkIfThereOrNot(sb, "b");
                            que.setAnswer_opt(stringBuilder.toString());
                            data.set(pos1, que);
                        } else {
                            que.setAnswer_opt("b");
                            data.set(pos1, que);
                        }

                        notifyDataSetChanged();
                        CommonUtils.LogMsg(OnlinePartAdapter.class.getSimpleName(), "pos: " + pos1 + " chkList: " + data.get(pos1).getAnswer_opt());
                    }
                });

                mPlanetHolder.chk3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos1 = position;
                        QuestionDataResponse.Question que = data.get(pos1);
                        que.setIsAnswered(true);

                        // if checked then assign on array too to update the array
                        if (holder.chk3.isChecked()) {
                            holder.chk3.setChecked(true);
                            que.setChecked3(true);
                            data.set(pos1, que);
                        } else {
                            holder.chk3.setChecked(false);
                            que.setChecked3(false);
                            data.set(pos1, que);
                        }

                        // If answer_opt null or blank then add on specific position
                        // else filter with checkIfthereOnNot Method
                        if (que.getAnswer_opt() != null && !que.getAnswer_opt().equals("")) {
                            StringBuilder sb = new StringBuilder(que.getAnswer_opt());
                            stringBuilder = checkIfThereOrNot(sb, "c");
                            que.setAnswer_opt(stringBuilder.toString());
                            data.set(pos1, que);
                        } else {
                            que.setAnswer_opt("c");
                            data.set(pos1, que);
                        }

                        notifyDataSetChanged();
                        CommonUtils.LogMsg(OnlinePartAdapter.class.getSimpleName(), "pos: " + pos1 + " chkList: " + data.get(pos1).getAnswer_opt());
                    }
                });


                mPlanetHolder.chk4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos1 = position;
                        QuestionDataResponse.Question que = data.get(pos1);
                        que.setIsAnswered(true);
                        // if checked then assign on array too to update the array
                        if (holder.chk4.isChecked()) {
                            holder.chk4.setChecked(true);
                            que.setChecked4(true);
                            data.set(pos1, que);
                        } else {
                            holder.chk4.setChecked(false);
                            que.setChecked4(false);
                            data.set(pos1, que);
                        }

                        // If answer_opt null or blank then add on specific position
                        // else filter with checkIfthereOnNot Method
                        if (que.getAnswer_opt() != null && !que.getAnswer_opt().equals("")) {
                            StringBuilder sb = new StringBuilder(que.getAnswer_opt());
                            stringBuilder = checkIfThereOrNot(sb, "d");
                            que.setAnswer_opt(stringBuilder.toString());
                            data.set(pos1, que);
                        } else {
                            que.setAnswer_opt("d");
                            data.set(pos1, que);
                        }
                        notifyDataSetChanged();
                        CommonUtils.LogMsg(OnlinePartAdapter.class.getSimpleName(), "pos: " + pos1 + " chkList: " + data.get(pos1).getAnswer_opt());
                    }
                });


            } else if (current.getUiType() == 3) {
                // UI type == 3 when answers need to be Integer enable edittext for it
                mPlanetHolder.llCheckbox.setVisibility(View.GONE);
                mPlanetHolder.rgTestOptions.setVisibility(View.GONE);
                mPlanetHolder.llet.setVisibility(View.VISIBLE);
                mPlanetHolder.etInput.setText(current.getAnswer_opt());
                int maxLength = 1;
                mPlanetHolder.etInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
                final PlanetHolder holder = mPlanetHolder;

                mPlanetHolder.etInput.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        int pos1 = position;
                        QuestionDataResponse.Question que = data.get(pos1);
                        // check edittext max length
                        // and assign to array for update and save
                        if (count < 2) {

                            que.setIsAnswered(true);

                            que.setChecked4(true);
                            data.set(pos1, que);
                            CommonUtils.LogMsg(OnlinePartAdapter.class.getSimpleName(), "count<2: " + s.toString());
                            que.setAnswer_opt(s.toString());
                            data.set(pos1, que);

                            notifyDataSetChanged();
                        } else if (count == 0) {
                            que.setChecked4(false);
                            data.set(pos1, que);
                        } else {
                            que.setChecked4(false);
                            data.set(pos1, que);
                            CommonUtils.showToast(cxt, "Cannot enter more than limit");
                        }
                        CommonUtils.LogMsg(OnlinePartAdapter.class.getSimpleName(), "s " + s.toString());

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
            mPlanetHolder.tv_test_name.setVisibility(View.GONE);
            mPlanetHolder.tvTestQuestionNo.setVisibility(View.VISIBLE);
            mPlanetHolder.wvQuestion.setVisibility(View.VISIBLE);
        } else {
            // visible only if we want to show instructions else hide
            mPlanetHolder.llCheckbox.setVisibility(View.GONE);
            mPlanetHolder.rgTestOptions.setVisibility(View.GONE);
            mPlanetHolder.llet.setVisibility(View.GONE);
            mPlanetHolder.tv_test_name.setVisibility(View.VISIBLE);
            mPlanetHolder.tv_test_name.setText(current.getPart_name());
            mPlanetHolder.tvTestQuestionNo.setVisibility(View.GONE);
            if (current.getInstruction_data()!=null && !current.getInstruction_data().trim().equals("")) {
                mPlanetHolder.wvQuestion.setVisibility(View.VISIBLE);
                mPlanetHolder.wvQuestion.loadDataWithBaseURL(null, current.getInstruction_data().trim(), "text/html", "utf-8", null);
                mPlanetHolder.wvQuestion.setWebChromeClient(new WebChromeClient());
                mPlanetHolder.wvQuestion.setWebViewClient(new WebViewClient());
                mPlanetHolder.wvQuestion.getSettings().setJavaScriptEnabled(true);
            }else {
                mPlanetHolder.wvQuestion.setVisibility(View.GONE);
            }
        }

        return itemView;

    }


    public StringBuilder delete(StringBuilder sb, String s, int charL) {
        int start = sb.indexOf(s, charL);
        if (start < 0)
            return sb;

        sb.deleteCharAt(start);
        return sb;
    }

    public StringBuilder append(StringBuilder sb, String s) {
        sb.append(s);
        return sb;

    }

    // to check if character is present on current string or not
    // return updated string
    public StringBuilder checkIfThereOrNot(StringBuilder sb, String s) {

//        int start = sb.indexOf(s);
        int start = s.length();
        if (start == 0) {
            sb = append(sb, s);
        } else {
            if (sb.indexOf(s) != -1) {
                // char is present
                int charL = sb.indexOf(s);

                CommonUtils.LogMsg(OnlinePartAdapter.class.getSimpleName(), "charL: " + charL);

                CommonUtils.LogMsg(OnlinePartAdapter.class.getSimpleName(), "charL: " + (sb.indexOf(",", charL - 1)) + " " + sb.charAt(charL));
                if ((sb.indexOf(",", charL - 1)) != -1) {
                    CommonUtils.LogMsg(OnlinePartAdapter.class.getSimpleName(), "charL-1: " + charL);
                    charL = charL - 1;
                    sb = delete(sb, ",", charL);
                    CommonUtils.LogMsg(OnlinePartAdapter.class.getSimpleName(), "sb: " + sb.toString());

                }
                if ((sb.indexOf(",", 0)) == 0) {
                    CommonUtils.LogMsg(OnlinePartAdapter.class.getSimpleName(), " , on 0 pos");

                    sb = delete(sb, ",", 0);
                }
                charL = sb.indexOf(s);
                sb = delete(sb, s, charL);
            } else {
                // char is not present
                sb = append(sb, "," + s);
            }

        }

        return sb;
    }

    private class PlanetHolder {
        TextView tvQuestion, tvTestQuestionNo, tv_test_name;
        RadioButton rgTestOption1, rgTestOption2, rgTestOption3, rgTestOption4;
        RadioGroup rgTestOptions;
        WebView wvQuestion, wvTestOption1, wvTestOption2, wvTestOption3, wvTestOption4,
                wv_check_test_option1, wv_check_test_option2, wv_check_test_option3, wv_check_test_option4;
        LinearLayout llTestOption1, llTestOption2, llTestOption3, llTestOption4, llCheckbox, llCheckbox1, llCheckbox2,
                llCheckbox3, llCheckbox4, llet;
        CheckBox chk1, chk2, chk3, chk4;
        EditText etInput;
    }
}
