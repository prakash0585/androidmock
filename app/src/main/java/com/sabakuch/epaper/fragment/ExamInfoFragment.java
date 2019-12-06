package com.sabakuch.epaper.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sabakuch.epaper.R;
import com.sabakuch.epaper.constants.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExamInfoFragment extends Fragment {
    private View rootView;

    private Activity context;
    private String examId = "";
    private String slug = "";
    public ExamInfoFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =inflater.inflate(R.layout.fragment_coming, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            examId = bundle.getString(Constants.EXAM_ID);
            slug = bundle.getString(Constants.SLUG);
        }

        Toolbar mToolbar = (Toolbar) context.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        final ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(false);
        ab.setIcon(0);
        TextView mTitle = (TextView) mToolbar.findViewById(R.id.tv_title);
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setText("Exam - Information");
        TextView tvhome1=(TextView)rootView.findViewById(R.id.tvhome);
        TextView tvtest1=(TextView)rootView.findViewById(R.id.tvtest);
        tvhome1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SelectExamsFragment());

            }
        });

        tvtest1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new SelectLevelsFragment();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.EXAM_ID, examId);
                bundle.putString(Constants.SLUG, slug);
                fragment.setArguments(bundle);
                setFragment(fragment);
            }
        });

        if (examId.equals("28") && slug.equals("ssc-cpo"))
        {
            rootView =inflater.inflate(R.layout.fragment_exam_info, container, false);
            mTitle.setText("Information");
            TextView tvhome=(TextView)rootView.findViewById(R.id.tvhome);
            TextView tvtest=(TextView)rootView.findViewById(R.id.tvtest);
            tvhome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setFragment(new SelectExamsFragment());

                }
            });

            tvtest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = new SelectLevelsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.EXAM_ID, examId);
                    bundle.putString(Constants.SLUG, slug);
                    fragment.setArguments(bundle);
                    setFragment(fragment);
                }
            });

        }


        else if (examId.equals("27")&& slug.equals("aiims-mbbs"))
        {
            rootView =inflater.inflate(R.layout.fragment_exam_information, container, false);
            mTitle.setText("Information");
            TextView tvhome=(TextView)rootView.findViewById(R.id.tvhome);
            TextView tvtest=(TextView)rootView.findViewById(R.id.tvtest);
            tvhome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setFragment(new SelectExamsFragment());

                }
            });

            tvtest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = new SelectLevelsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.EXAM_ID, examId);
                    bundle.putString(Constants.SLUG, slug);
                    fragment.setArguments(bundle);
                    setFragment(fragment);
                }
            });
        }
        else if (examId.equals("26")&& slug.equals("police-constable"))
        {
            rootView =inflater.inflate(R.layout.fragment_police, container, false);
            mTitle.setText("Information");
            TextView tvhome=(TextView)rootView.findViewById(R.id.tvhome);
            TextView tvtest=(TextView)rootView.findViewById(R.id.tvtest);
            tvhome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setFragment(new SelectExamsFragment());

                }
            });

            tvtest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = new SelectLevelsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.EXAM_ID, examId);
                    bundle.putString(Constants.SLUG, slug);
                    fragment.setArguments(bundle);
                    setFragment(fragment);
                }
            });
        }
        else if (examId.equals("22")&& slug.equals("RRB-Technicians-and-ALP"))
        {
            rootView =inflater.inflate(R.layout.fragment_rrb, container, false);
            mTitle.setText("Information");
            TextView tvhome=(TextView)rootView.findViewById(R.id.tvhome);
            TextView tvtest=(TextView)rootView.findViewById(R.id.tvtest);
            tvhome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setFragment(new SelectExamsFragment());

                }
            });

            tvtest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = new SelectLevelsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.EXAM_ID, examId);
                    bundle.putString(Constants.SLUG, slug);
                    fragment.setArguments(bundle);
                    setFragment(fragment);
                }
            });
        }
        else if (examId.equals("9")&& slug.equals("jee-advanced"))
        {
            rootView =inflater.inflate(R.layout.fragment_jee, container, false);
            mTitle.setText("Information");
            TextView tvhome=(TextView)rootView.findViewById(R.id.tvhome);
            TextView tvtest=(TextView)rootView.findViewById(R.id.tvtest);
            tvhome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setFragment(new SelectExamsFragment());

                }
            });

            tvtest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = new SelectLevelsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.EXAM_ID, examId);
                    bundle.putString(Constants.SLUG, slug);
                    fragment.setArguments(bundle);
                    setFragment(fragment);
                }
            });
        }
        else if (examId.equals("4")&& slug.equals("neet"))
        {
            rootView =inflater.inflate(R.layout.fragment_neet, container, false);
            mTitle.setText("Information");
            TextView tvhome=(TextView)rootView.findViewById(R.id.tvhome);
            TextView tvtest=(TextView)rootView.findViewById(R.id.tvtest);

            TextView ntt5=(TextView)rootView.findViewById(R.id.ntt5);
            ntt5.setMovementMethod(LinkMovementMethod.getInstance());



            tvhome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setFragment(new SelectExamsFragment());

                }
            });

            tvtest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = new SelectLevelsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.EXAM_ID, examId);
                    bundle.putString(Constants.SLUG, slug);
                    fragment.setArguments(bundle);
                    setFragment(fragment);
                }
            });
        }
        else if (examId.equals("11")&& slug.equals("clat"))
        {
            rootView =inflater.inflate(R.layout.fragment_clat, container, false);
            mTitle.setText("Information");
            TextView tvhome=(TextView)rootView.findViewById(R.id.tvhome);
            TextView tvtest=(TextView)rootView.findViewById(R.id.tvtest);
            TextView clat1=(TextView)rootView.findViewById(R.id.clat1);
            clat1.setMovementMethod(LinkMovementMethod.getInstance());

            clat1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = new SelectLevelsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.EXAM_ID, examId);
                    bundle.putString(Constants.SLUG, slug);
                    fragment.setArguments(bundle);
                    setFragment(fragment);
                }
            });

            tvhome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setFragment(new SelectExamsFragment());

                }
            });

            tvtest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = new SelectLevelsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.EXAM_ID, examId);
                    bundle.putString(Constants.SLUG, slug);
                    fragment.setArguments(bundle);
                    setFragment(fragment);
                }
            });
        }
        else if (examId.equals("17")&& slug.equals("ntse"))
        {
            rootView =inflater.inflate(R.layout.fragment_ntse, container, false);
            mTitle.setText("Information");
            TextView tvhome=(TextView)rootView.findViewById(R.id.tvhome);
            TextView tvtest=(TextView)rootView.findViewById(R.id.tvtest);
            tvhome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setFragment(new SelectExamsFragment());

                }
            });

            tvtest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = new SelectLevelsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.EXAM_ID, examId);
                    bundle.putString(Constants.SLUG, slug);
                    fragment.setArguments(bundle);
                    setFragment(fragment);
                }
            });
        }
        else if (examId.equals("2")&& slug.equals("jee-main"))
        {
            rootView =inflater.inflate(R.layout.fragment_jmain, container, false);
            mTitle.setText("Information");
            TextView tvhome=(TextView)rootView.findViewById(R.id.tvhome);
            TextView tvtest=(TextView)rootView.findViewById(R.id.tvtest);
            tvhome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setFragment(new SelectExamsFragment());

                }
            });

            tvtest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = new SelectLevelsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.EXAM_ID, examId);
                    bundle.putString(Constants.SLUG, slug);
                    fragment.setArguments(bundle);
                    setFragment(fragment);
                }
            });
        }
        else if (examId.equals("19")&& slug.equals("ca-cpt-session-1"))
        {
            rootView =inflater.inflate(R.layout.fragment_ca1, container, false);
            mTitle.setText("Information");
            TextView tvhome=(TextView)rootView.findViewById(R.id.tvhome);
            TextView tvtest=(TextView)rootView.findViewById(R.id.tvtest);
            tvhome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setFragment(new SelectExamsFragment());

                }
            });

            tvtest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = new SelectLevelsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.EXAM_ID, examId);
                    bundle.putString(Constants.SLUG, slug);
                    fragment.setArguments(bundle);
                    setFragment(fragment);
                }
            });
        }
        else if (examId.equals("20")&& slug.equals("ca-cpt-session-2"))
        {
            rootView =inflater.inflate(R.layout.fragment_ca2, container, false);
            mTitle.setText("Information");
            TextView tvhome=(TextView)rootView.findViewById(R.id.tvhome);
            TextView tvtest=(TextView)rootView.findViewById(R.id.tvtest);
            tvhome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setFragment(new SelectExamsFragment());

                }
            });

            tvtest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = new SelectLevelsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.EXAM_ID, examId);
                    bundle.putString(Constants.SLUG, slug);
                    fragment.setArguments(bundle);
                    setFragment(fragment);
                }
            });
        }
        else if (examId.equals("24")&& slug.equals("ssc-cgl"))
        {
            rootView =inflater.inflate(R.layout.fragment_scgl, container, false);
            mTitle.setText("Information");
            TextView tvhome=(TextView)rootView.findViewById(R.id.tvhome);
            TextView tvtest=(TextView)rootView.findViewById(R.id.tvtest);
            tvhome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setFragment(new SelectExamsFragment());

                }
            });

            tvtest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = new SelectLevelsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.EXAM_ID, examId);
                    bundle.putString(Constants.SLUG, slug);
                    fragment.setArguments(bundle);
                    setFragment(fragment);
                }
            });
        }
        else if (examId.equals("21")&& slug.equals("bank-po-main"))
        {
            rootView =inflater.inflate(R.layout.fragment_bankmain, container, false);
            mTitle.setText("Information");
            TextView tvhome=(TextView)rootView.findViewById(R.id.tvhome);
            TextView tvtest=(TextView)rootView.findViewById(R.id.tvtest);
            tvhome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setFragment(new SelectExamsFragment());

                }
            });

            tvtest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = new SelectLevelsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.EXAM_ID, examId);
                    bundle.putString(Constants.SLUG, slug);
                    fragment.setArguments(bundle);
                    setFragment(fragment);
                }
            });
        }
        else if (examId.equals("23")&& slug.equals("DU-JAT"))
        {
            rootView =inflater.inflate(R.layout.fragment_jat, container, false);
            mTitle.setText("Information");
            TextView tvhome=(TextView)rootView.findViewById(R.id.tvhome);
            TextView tvtest=(TextView)rootView.findViewById(R.id.tvtest);
            tvhome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setFragment(new SelectExamsFragment());

                }
            });

            tvtest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = new SelectLevelsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.EXAM_ID, examId);
                    bundle.putString(Constants.SLUG, slug);
                    fragment.setArguments(bundle);
                    setFragment(fragment);
                }
            });
        }
        else if (examId.equals("16")&& slug.equals("kvpy"))
        {
            rootView =inflater.inflate(R.layout.fragment_kvpy, container, false);
            mTitle.setText("Information");
            TextView tvhome=(TextView)rootView.findViewById(R.id.tvhome);
            TextView tvtest=(TextView)rootView.findViewById(R.id.tvtest);
            tvhome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setFragment(new SelectExamsFragment());

                }
            });

            tvtest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = new SelectLevelsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.EXAM_ID, examId);
                    bundle.putString(Constants.SLUG, slug);
                    fragment.setArguments(bundle);
                    setFragment(fragment);
                }
            });
        }
        else if (examId.equals("18")&& slug.equals("cat"))
        {
            rootView =inflater.inflate(R.layout.fragment_cat, container, false);
            mTitle.setText("Information");
            TextView tvhome=(TextView)rootView.findViewById(R.id.tvhome);
            TextView tvtest=(TextView)rootView.findViewById(R.id.tvtest);
            tvhome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setFragment(new SelectExamsFragment());

                }
            });

            tvtest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = new SelectLevelsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.EXAM_ID, examId);
                    bundle.putString(Constants.SLUG, slug);
                    fragment.setArguments(bundle);
                    setFragment(fragment);
                }
            });
        }
        else if (examId.equals("8")&& slug.equals("g-k-test"))
        {
            rootView =inflater.inflate(R.layout.fragment_infoo, container, false);
            mTitle.setText("Information");
            TextView tvhome=(TextView)rootView.findViewById(R.id.tvhome);
            TextView tvtest=(TextView)rootView.findViewById(R.id.tvtest);
            tvhome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setFragment(new SelectExamsFragment());

                }
            });

            tvtest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = new SelectLevelsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.EXAM_ID, examId);
                    bundle.putString(Constants.SLUG, slug);
                    fragment.setArguments(bundle);
                    setFragment(fragment);
                }
            });
        }
        else if (examId.equals("5")&& slug.equals("current-affairs"))
        {
            rootView =inflater.inflate(R.layout.fragment_info, container, false);
            mTitle.setText("Information");
            TextView tvhome=(TextView)rootView.findViewById(R.id.tvhome);
            TextView tvtest=(TextView)rootView.findViewById(R.id.tvtest);
            tvhome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setFragment(new SelectExamsFragment());

                }
            });

            tvtest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = new SelectLevelsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.EXAM_ID, examId);
                    bundle.putString(Constants.SLUG, slug);
                    fragment.setArguments(bundle);
                    setFragment(fragment);
                }
            });
        }

        else if (examId.equals("29")&& slug.equals("ibps-clerk-preliminary"))
        {
            rootView =inflater.inflate(R.layout.fragment_ibps, container, false);
            mTitle.setText("Information");
            TextView tvhome=(TextView)rootView.findViewById(R.id.tvhome);
            TextView tvtest=(TextView)rootView.findViewById(R.id.tvtest);
            tvhome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setFragment(new SelectExamsFragment());

                }
            });

            tvtest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = new SelectLevelsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.EXAM_ID, examId);
                    bundle.putString(Constants.SLUG, slug);
                    fragment.setArguments(bundle);
                    setFragment(fragment);
                }
            });
        }
        return rootView;
    }

    protected void setFragment(Fragment fragment) {
        FragmentTransaction ftTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
        ftTransaction.add(R.id.container_body, fragment)
                .addToBackStack(null);
        ftTransaction.commit();
    }
}
