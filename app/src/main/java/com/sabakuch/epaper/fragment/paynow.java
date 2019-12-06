package com.sabakuch.epaper.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.sabakuch.epaper.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class paynow extends Fragment {
    private View rootView;
    private Activity context;
    private String examId = "";
    private String slug = "";
    public paynow() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =inflater.inflate(R.layout.fragment_paynow, container, false);


        ImageView paytm=(ImageView)rootView.findViewById(R.id.paytm);
        ImageView payu=(ImageView)rootView.findViewById(R.id.payu);
        ImageView neft=(ImageView)rootView.findViewById(R.id.neft);
        paytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                  setFragment(new paytm());
                Toast.makeText(context, "You have selected Paytm ", Toast.LENGTH_SHORT).show();
            }
        });
        payu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                setFragment(new payu());
                Toast.makeText(context, "You have selected PayU money ", Toast.LENGTH_SHORT).show();

            }
        });
        neft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                setFragment(new neft());
                Toast.makeText(context, "You have selected NEFT  ", Toast.LENGTH_SHORT).show();

            }
        });


        return rootView;
    }

    protected void setFragment(Fragment fragment) {
        FragmentTransaction ftTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
        ftTransaction.add(R.id.container_body, fragment)
                .addToBackStack(null);
        ftTransaction.commit();
    }
}
