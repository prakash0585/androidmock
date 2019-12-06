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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sabakuch.epaper.R;
import com.sabakuch.epaper.constants.Constants;
import com.sabakuch.epaper.data.DashboardData;
import com.sabakuch.epaper.fragment.MyDashboardFragmentPremium;
import java.util.Calendar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ExamListAdapterPremium extends RecyclerView.Adapter<ExamListAdapterPremium.MyViewHolder>  {
    ArrayList<DashboardData> data;
    private LayoutInflater inflater;
    private String exmid,levelids;
    Activity context;
    public ExamListAdapterPremium(Activity context, ArrayList<DashboardData> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.examrowexamnew, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final DashboardData current = data.get(position);


        if (current.exam_name != null && !current.exam_name.equals("")) {
            holder.tvName.setText(current.exam_name);
        }

        if (current.total_attempts != null && !current.total_attempts.equals("") &&
                current.no_of_attempts != null && !current.no_of_attempts.equals("")) {
            holder.tvName12.setText(current.total_attempts+"/"+current.no_of_attempts);
        }

        if (current.start_date != null && !current.start_date.equals("") &&
                current.expiry_date != null && !current.expiry_date.equals(""))
        {
            Calendar calendar = Calendar.getInstance();

            SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = myFormat.format(calendar.getTime());
            String dateBeforeString = strDate;
            String dateAfterString = current.expiry_date;

            try {
                Date dateBefore = myFormat.parse(dateBeforeString);
                Date dateAfter = myFormat.parse(dateAfterString);
                long difference = dateAfter.getTime() - dateBefore.getTime();
                float daysBetween = (difference / (1000*60*60*24));

                holder.tvName1.setText(daysBetween+" days");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        holder.listexm.setTag(holder);
        {
            holder.listexm.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    ExamListAdapterPremium.MyViewHolder vholder = (ExamListAdapterPremium.MyViewHolder)v.getTag();
                    int position = vholder.getPosition();


                    exmid = data.get(position).exam_id;
                    levelids = data.get(position).level_id;


                    Fragment fragment = new MyDashboardFragmentPremium();
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.EXAM_ID, exmid);
                    bundle.putString(Constants.LEVEL_ID, levelids);
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
        TextView tvName1;
        TextView tvName12;
        LinearLayout listexm;

        public MyViewHolder(View itemView) {
            super(itemView);
            listexm = (LinearLayout) itemView.findViewById(R.id.listexm);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvName1 = (TextView) itemView.findViewById(R.id.tvName1);
            tvName12 = (TextView) itemView.findViewById(R.id.tvName12);


        }


    }


}
