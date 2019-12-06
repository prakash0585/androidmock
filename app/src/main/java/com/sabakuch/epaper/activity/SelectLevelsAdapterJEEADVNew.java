package com.sabakuch.epaper.activity;

import android.content.Context;
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
import com.sabakuch.epaper.apputils.CommonUtils;
import com.sabakuch.epaper.constants.Constants;
import com.sabakuch.epaper.data.LevelsData;
import com.sabakuch.epaper.fragment.InstructionFragment;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;


public class SelectLevelsAdapterJEEADVNew extends RecyclerView.Adapter<SelectLevelsAdapterJEEADVNew.MyViewHolder> {
    private final String examId, level1Status, level2Status, level3Status;
    List<LevelsData> data = Collections.emptyList();
    int alpha = 0;
    private LayoutInflater inflater;
    private Context context;

    public SelectLevelsAdapterJEEADVNew(Context context, List<LevelsData> data, String examId, String level1Status, String level2Status, String level3Status) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.examId = examId;
        this.level1Status = level1Status;
        this.level2Status = level2Status;
        this.level3Status = level3Status;
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
            if (level1Status != null) {
                setViewsEnable(holder);
            } else {
                setViewsDisable(holder);
            }
        }
        if (position == 1) {
            if ((level1Status != null && level1Status.equalsIgnoreCase("1"))) {
                setViewsEnable(holder);
            } else {
                setViewsDisable(holder);
            }
        }
        if (position == 2) {
            if ((level2Status != null && level2Status.equalsIgnoreCase("1"))) {
                setViewsEnable(holder);
            } else {
                setViewsDisable(holder);
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
        TextView tvTestType, tvTestSubtype;
        ImageView ivLevels;

        public MyViewHolder(final View itemView) {
            super(itemView);
            tvTestType = (TextView) itemView.findViewById(R.id.tv_test_type);
            tvTestSubtype = (TextView) itemView.findViewById(R.id.tv_test_subtype);
            ivLevels = (ImageView) itemView.findViewById(R.id.iv_levels);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Fragment fragment = new InstructionFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.EXAM_ID, "9");
                    bundle.putString(Constants.LEVEL_ID, data.get(getAdapterPosition()).level_id);
                    fragment.setArguments(bundle);
                    if (context != null) {
                        setFragment(fragment);
                    }
                   /* Intent i=new Intent(context,InstructionInstActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.EXAM_ID, examId);
                    bundle.putString(Constants.LEVEL_ID, data.get(getAdapterPosition()).level_id);
                    if (context != null) {
                        context.startActivity(i);
                    }*/
                }
            });
        }
    }
}
