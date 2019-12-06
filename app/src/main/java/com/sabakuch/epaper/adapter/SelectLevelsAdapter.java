package com.sabakuch.epaper.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sabakuch.epaper.R;
import com.sabakuch.epaper.activity.PaymentWeb;
import com.sabakuch.epaper.activity.PaymentWebb;
import com.sabakuch.epaper.apputils.CommonUtils;
import com.sabakuch.epaper.constants.Constants;
import com.sabakuch.epaper.data.LevelsData;
import com.sabakuch.epaper.fragment.InstructionFragment;
import com.sabakuch.epaper.fragment.InstructionPay;
import com.sabakuch.epaper.fragment.Instructioncustom;
import com.sabakuch.epaper.fragment.Instructionpaid;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;


public class SelectLevelsAdapter extends RecyclerView.Adapter<SelectLevelsAdapter.MyViewHolder> {
    private final String examId, level1Status, level2Status, level3Status,slug;
    List<LevelsData> data = Collections.emptyList();
    int alpha = 0;
    private LayoutInflater inflater;
    private Context context;

    public SelectLevelsAdapter(Context context, List<LevelsData> data, String examId, String level1Status, String level2Status, String level3Status, String slug) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.examId = examId;
        this.level1Status = level1Status;
        this.level2Status = level2Status;
        this.level3Status = level3Status;
        this.slug = slug;
    }

    public void delete(int position) {
        //   data.remove(position);
        //    notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_select_levels, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        LevelsData current = data.get(position);
        holder.tvTestType.setText(current.name != null && current.name.length() > 0 ? current.name : "");
        holder.tvTestSubtype.setText(current.level_text != null && current.level_text.length() > 0 ? CommonUtils.stripHtml(current.level_text) : "");
        if (data.get(position).image != null && data.get(position).image.length() > 0) {
            Picasso.with(context).load(data.get(position).image.trim()).into(holder.ivLevels);
        }
        setViewsDisable(holder);
        if (position == 0) {
            holder.freebie.setText("FREE");
            holder.freebie.setVisibility(View.VISIBLE);
            holder.tvTestSubtype.setVisibility(View.GONE);

            if (data.get(position).check_exam_level.equalsIgnoreCase("false")) {
                setViewsEnable(holder);
            } else {
                setViewsEnable(holder);
            }
           /* if (level1Status != null) {
                setViewsEnable(holder);
            } else {
                setViewsDisable(holder);
            }*/
        }
        if (position == 1) {
            holder.tvTestSubtype.setVisibility(View.GONE);

            if (data.get(position).check_exam_level.equalsIgnoreCase("false")) {
                setViewsEnable(holder);
            } else {
                setViewsEnable(holder);
            }
           /* if ((level1Status != null && level1Status.equalsIgnoreCase("1"))) {
                setViewsEnable(holder);
            } else {
                setViewsDisable(holder);
            }*/
        }
        if (position == 2) {
            holder.tvTestSubtype.setVisibility(View.GONE);


            if (data.get(position).check_exam_level.equalsIgnoreCase("false")) {
                setViewsEnable(holder);
            } else {
                setViewsEnable(holder);
            }
            /*if ((level2Status != null && level2Status.equalsIgnoreCase("1"))) {
                setViewsEnable(holder);
            } else {
                setViewsDisable(holder);
            }*/
        }
        if (position == 3) {
            holder.freebie.setText("PREMIUM");
            holder.freebie.setVisibility(View.VISIBLE);
            if (data.get(position).is_user_pre_exam.equalsIgnoreCase("true")) {
                setViewsEnable(holder);
            } else {
                setViewsEnable(holder);
               /* Intent intentp=new Intent(context, PaymentWeb.class);
                intentp.putExtra(Constants.EXAM_ID, examId);
                context.startActivity(intentp);
*/
            }
        }


        if (position == 4) {
            if (data.get(position).is_user_custom_exam.equalsIgnoreCase("true")) {
                setViewsEnable(holder);
            } else {
                setViewsEnable(holder);
               /* Intent intentp=new Intent(context, PaymentWebb.class);
                intentp.putExtra(Constants.EXAM_ID, examId);
                context.startActivity(intentp);
*/
            }


        }
        if (position == 5) {
            if (data.get(position).is_user_custom_exam.equalsIgnoreCase("true")) {
                setViewsEnable(holder);
            } else {
                setViewsEnable(holder);
               /* Intent intentp=new Intent(context, PaymentWebb.class);
                intentp.putExtra(Constants.EXAM_ID, examId);
                context.startActivity(intentp);
*/
            }


        }
    }

    private void setViewsEnable(MyViewHolder holder) {
        holder.itemView.setEnabled(true);
        holder.ivLevels.setAlpha(1.0f);
        holder.tvTestType.setAlpha(1.0f);
        holder.tvTestSubtype.setAlpha(1.0f);
    }

    private void setViewsDisable(MyViewHolder holder) {
        holder.itemView.setEnabled(false);
        holder.ivLevels.setAlpha(0.7f);
        holder.tvTestType.setAlpha(0.7f);
        holder.tvTestSubtype.setAlpha(0.7f);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    protected void setFragment(Fragment fragment) {
        FragmentTransaction ftTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
        ftTransaction.replace(R.id.container_body, fragment)
                .addToBackStack(null);
        ftTransaction.commit();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTestType, tvTestSubtype,freebie;
        ImageView ivLevels;

        public MyViewHolder(final View itemView) {
            super(itemView);
            tvTestType = (TextView) itemView.findViewById(R.id.tv_test_type);
            freebie = (TextView) itemView.findViewById(R.id.freebie);
            tvTestSubtype = (TextView) itemView.findViewById(R.id.tv_test_subtype);
            ivLevels = (ImageView) itemView.findViewById(R.id.iv_levels);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(data.get(getAdapterPosition()).level_id.equalsIgnoreCase("1"))
                    {
                        if(data.get(getPosition()).check_exam_level.equalsIgnoreCase("false")
                                && data.get(getPosition()).is_user_custom_exam.equalsIgnoreCase("true"))

                        {
                            Fragment fragment = new Instructionpaid();
                            Bundle bundle = new Bundle();
                            bundle.putString(Constants.EXAM_ID, examId);
                            bundle.putString(Constants.SLUG, slug);
                            bundle.putString(Constants.LEVEL_ID, data.get(getAdapterPosition()).level_id);
                            fragment.setArguments(bundle);
                            if (context != null) {
                                setFragment(fragment);
                            }
                        }

                        if(data.get(getPosition()).check_exam_level.equalsIgnoreCase("false")
                                && data.get(getPosition()).is_user_custom_exam.equalsIgnoreCase("false"))

                        {
                            Fragment fragment = new InstructionFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString(Constants.EXAM_ID, examId);
                            bundle.putString(Constants.LEVEL_ID, data.get(getAdapterPosition()).level_id);
                            fragment.setArguments(bundle);
                            if (context != null) {
                                setFragment(fragment);
                            }
                        }

                        if(data.get(getPosition()).check_exam_level.equalsIgnoreCase("true"))

                        {
                            Intent intentp=new Intent(context, PaymentWeb.class);
                            intentp.putExtra(Constants.EXAM_ID, examId);
                            context.startActivity(intentp);

                        }


                    }
                    if(data.get(getAdapterPosition()).level_id.equalsIgnoreCase("2"))
                    {
                        if(data.get(getPosition()).check_exam_level.equalsIgnoreCase("false")
                                && data.get(getPosition()).is_user_custom_exam.equalsIgnoreCase("true"))

                        {
                            Fragment fragment = new Instructionpaid();
                            Bundle bundle = new Bundle();
                            bundle.putString(Constants.EXAM_ID, examId);
                            bundle.putString(Constants.SLUG, slug);
                            bundle.putString(Constants.LEVEL_ID, data.get(getAdapterPosition()).level_id);
                            fragment.setArguments(bundle);
                            if (context != null) {
                                setFragment(fragment);
                            }
                        }

                        if(data.get(getPosition()).check_exam_level.equalsIgnoreCase("false")
                                && data.get(getPosition()).is_user_custom_exam.equalsIgnoreCase("false"))

                        {
                            Fragment fragment = new InstructionFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString(Constants.EXAM_ID, examId);
                            bundle.putString(Constants.LEVEL_ID, data.get(getAdapterPosition()).level_id);
                            fragment.setArguments(bundle);
                            if (context != null) {
                                setFragment(fragment);
                            }
                        }

                        if(data.get(getPosition()).check_exam_level.equalsIgnoreCase("true"))

                        {
                            Intent intentp=new Intent(context, PaymentWeb.class);
                            intentp.putExtra(Constants.EXAM_ID, examId);
                            context.startActivity(intentp);

                        }


                    }
                    if(data.get(getAdapterPosition()).level_id.equalsIgnoreCase("3"))
                    {

                        if(data.get(getPosition()).check_exam_level.equalsIgnoreCase("false")
                                && data.get(getPosition()).is_user_custom_exam.equalsIgnoreCase("true"))

                        {
                            Fragment fragment = new Instructionpaid();
                            Bundle bundle = new Bundle();
                            bundle.putString(Constants.EXAM_ID, examId);
                            bundle.putString(Constants.SLUG, slug);
                            bundle.putString(Constants.LEVEL_ID, data.get(getAdapterPosition()).level_id);
                            fragment.setArguments(bundle);
                            if (context != null) {
                                setFragment(fragment);
                            }
                        }
                        if(data.get(getPosition()).check_exam_level.equalsIgnoreCase("false")
                                && data.get(getPosition()).is_user_custom_exam.equalsIgnoreCase("false"))

                        {
                            Fragment fragment = new InstructionFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString(Constants.EXAM_ID, examId);
                            bundle.putString(Constants.LEVEL_ID, data.get(getAdapterPosition()).level_id);
                            fragment.setArguments(bundle);
                            if (context != null) {
                                setFragment(fragment);
                            }
                        }

                        if(data.get(getPosition()).check_exam_level.equalsIgnoreCase("true"))

                        {
                            Intent intentp=new Intent(context, PaymentWeb.class);
                            intentp.putExtra(Constants.EXAM_ID, examId);
                            context.startActivity(intentp);

                        }


                    }

                    if(data.get(getAdapterPosition()).level_id.equalsIgnoreCase("4"))
                    {
                        if(data.get(getPosition()).is_user_pre_exam.equalsIgnoreCase("true")
                                && data.get(getPosition()).is_user_custom_exam.equalsIgnoreCase("true"))

                        {
                            Fragment fragment = new Instructionpaid();
                            Bundle bundle = new Bundle();
                            bundle.putString(Constants.EXAM_ID, examId);
                            bundle.putString(Constants.SLUG, slug);
                            bundle.putString(Constants.LEVEL_ID, data.get(getAdapterPosition()).level_id);
                            fragment.setArguments(bundle);
                            if (context != null) {
                                setFragment(fragment);
                            }
                        }

                        if(data.get(getPosition()).is_user_pre_exam.equalsIgnoreCase("true")
                                && data.get(getPosition()).is_user_custom_exam.equalsIgnoreCase("false"))

                        {
                            Fragment fragment = new InstructionPay();
                            Bundle bundle = new Bundle();
                            bundle.putString(Constants.EXAM_ID, examId);
                            bundle.putString(Constants.LEVEL_ID, data.get(getAdapterPosition()).level_id);
                            fragment.setArguments(bundle);
                            if (context != null) {
                                setFragment(fragment);
                            }
                        }

                        if(data.get(getPosition()).is_user_pre_exam.equalsIgnoreCase("false"))

                        {
                            Intent intentp=new Intent(context, PaymentWeb.class);
                            intentp.putExtra(Constants.EXAM_ID, examId);
                            context.startActivity(intentp);

                        }


                    }

                    if(data.get(getAdapterPosition()).level_id.equalsIgnoreCase("5"))
                    {
                        if(data.get(getPosition()).is_user_custom_exam.equalsIgnoreCase("true"))

                        {
                            Fragment fragment = new Instructioncustom();
                            Bundle bundle = new Bundle();
                            bundle.putString(Constants.EXAM_ID, examId);
                            bundle.putString(Constants.SLUG, slug);

                            bundle.putString(Constants.LEVEL_ID, data.get(getAdapterPosition()).level_id);
                            fragment.setArguments(bundle);
                            if (context != null) {
                                setFragment(fragment);
                            }
                        }

                        if(data.get(getPosition()).is_user_custom_exam.equalsIgnoreCase("false"))

                        {
                            Intent intentp=new Intent(context, PaymentWebb.class);
                            intentp.putExtra(Constants.EXAM_ID, examId);
                            context.startActivity(intentp);

                        }

                    }
                    if(data.get(getAdapterPosition()).level_id.equalsIgnoreCase("6"))
                    {
                        if(data.get(getPosition()).is_user_custom_exam.equalsIgnoreCase("true"))

                        {
                            Fragment fragment = new Instructionpaid();
                            Bundle bundle = new Bundle();
                            bundle.putString(Constants.EXAM_ID, examId);
                            bundle.putString(Constants.SLUG, slug);

                            bundle.putString(Constants.LEVEL_ID, data.get(getAdapterPosition()).level_id);
                            fragment.setArguments(bundle);
                            if (context != null) {
                                setFragment(fragment);
                            }
                        }

                        if(data.get(getPosition()).is_user_custom_exam.equalsIgnoreCase("false"))

                        {
                            Intent intentp=new Intent(context, PaymentWeb.class);
                            intentp.putExtra(Constants.EXAM_ID, examId);
                            context.startActivity(intentp);

                        }

                    }

                   /* else {


                        Fragment fragment = new InstructionFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.EXAM_ID, examId);
                        bundle.putString(Constants.LEVEL_ID, data.get(getAdapterPosition()).level_id);
                        fragment.setArguments(bundle);
                        if (context != null) {
                            setFragment(fragment);
                        }
                    }*/
                }
            });
        }
    }
}
