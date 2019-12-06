package com.sabakuch.epaper.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sabakuch.epaper.R;
import com.sabakuch.epaper.constants.Constants;
import com.sabakuch.epaper.data.DashboardData;
import com.sabakuch.epaper.fragment.MyDashboardFragment;

import java.util.ArrayList;

public class ExamListAdapter extends RecyclerView.Adapter<ExamListAdapter.MyViewHolder>  {
    ArrayList<DashboardData> data;
    private LayoutInflater inflater;
    private String exmid;
    Activity context;
    public ExamListAdapter(Activity context, ArrayList<DashboardData> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.examrowexam, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final DashboardData current = data.get(position);


        if (current.exam_name != null && !current.exam_name.equals("")) {
            holder.tvName.setText(current.exam_name);
        }


            holder.tvName.setTag(holder);
            {
                holder.tvName.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        ExamListAdapter.MyViewHolder vholder = (ExamListAdapter.MyViewHolder)v.getTag();
                        int position = vholder.getPosition();


                           exmid = data.get(position).exam_id;


                            Fragment fragment = new MyDashboardFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString(Constants.EXAM_ID, exmid);
                            fragment.setArguments(bundle);
                            setFragment(fragment);



                    }
                });

            }










    }



    @Override
    public int getItemCount() {
        return data.size();
    }






    protected void setFragment(Fragment fragment) {
        FragmentTransaction ftTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
        ftTransaction.replace(R.id.container_body, fragment)
                .addToBackStack("back");
        ftTransaction.commit();
    }


    class MyViewHolder extends RecyclerView.ViewHolder  {
        TextView tvName;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);


        }


    }


}
