package com.sabakuch.epaper.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidviewhover.BlurLayout;
import com.sabakuch.epaper.R;
import com.sabakuch.epaper.activity.PaymentWeb;
import com.sabakuch.epaper.activity.PaymentWebb;
import com.sabakuch.epaper.apiclass.APIAccess;
import com.sabakuch.epaper.apputils.CommonUtils;
import com.sabakuch.epaper.apputils.SabaKuchParse;
import com.sabakuch.epaper.apputils.ServiceUrl;
import com.sabakuch.epaper.constants.Constants;
import com.sabakuch.epaper.data.SelectExamsData;
import com.sabakuch.epaper.entitymime.MultipartEntity;
import com.sabakuch.epaper.entitymimecontent.StringBody;
import com.sabakuch.epaper.fragment.InstructionPay;
import com.sabakuch.epaper.fragment.Instructioncustom;
import com.sabakuch.epaper.fragment.Instructionpaid;
import com.sabakuch.epaper.fragment.SelectLevelsFragment;
import com.sabakuch.epaper.serviceclasses.ServiceInterface;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SelectExamsAdapter extends RecyclerView.Adapter<SelectExamsAdapter.MyViewHolder> implements ServiceInterface {
    ArrayList<SelectExamsData> data/* = Collections.emptyList()*/;
    private LayoutInflater inflater;
    private Activity context;
    private MultipartEntity reqEntity;
    private boolean isClicked = false;
    private String strExamId,premium,custom,preexam,slug, strSolId, strSetId;
    String strUserIDss;
   // String sspay = "";
    public SelectExamsAdapter(Activity context, ArrayList<SelectExamsData> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

//    public void delete(int position) {
//        data.remove(position);
//        notifyItemRemoved(position);
//    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_select_exam_section, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        SelectExamsData current = data.get(position);
        if (current.examimage != null && current.examimage.length() > 0) {
            String examImage = current.examimage;
//            if (examImage.contains(" ")) {
//                examImage = examImage.replaceAll(" ", "%20");
//                examImage = addToString(examImage, '.', "_android");
//            }
            Picasso.with(context).load(examImage).networkPolicy(NetworkPolicy.NO_CACHE).into(holder.ivPaper);
        } else if (current.solution_image != null && !current.solution_image.equals("")) {
            Picasso.with(context).load(current.solution_image).networkPolicy(NetworkPolicy.NO_CACHE).into(holder.ivPaper);
        }

        if (current.set_name != null && !current.set_name.equals("")) {
            holder.tvName.setVisibility(View.VISIBLE);
            holder.ivPaper.setVisibility(View.GONE);
            holder.tvName.setText(current.set_name);
        } else {
            holder.tvName.setVisibility(View.GONE);
            holder.ivPaper.setVisibility(View.VISIBLE);
        }

        strExamId = data.get(position).exam_id;
        premium = data.get(position).is_premium;
        custom = data.get(position).is_custom;
        strUserIDss = CommonUtils.getStringPreferences(context, Constants.USER_ID);

      //  setUpMap();

        if (premium.equalsIgnoreCase("true")&&custom.equalsIgnoreCase("false") )
        {
          //  View hover = inflater.inflate(R.layout.hover_sample, null);
          //  holder.mSampleLayout.setHoverView(hover);


            BlurLayout.setGlobalDefaultDuration(450);
            View hover = inflater.inflate(R.layout.hover_sample, null);
            hover.findViewById(R.id.heart).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Boolean isLogin = CommonUtils.getLoginPreferences(context, Constants.IS_LOGIN);
                    if (isLogin)
                    {


                        if (data.get(position).is_user_pre_exam == "false" || data.get(position).is_user_pre_exam  .equalsIgnoreCase("false") || data.get(position).is_user_pre_exam .equals("false"))
                        {
                            strExamId = data.get(position).exam_id;
                            Intent intentp=new Intent(context, PaymentWeb.class);
                            intentp.putExtra(Constants.EXAM_ID, strExamId);
                            context.startActivity(intentp);

                        }


                        if (data.get(position).is_user_pre_exam == "true" || data.get(position).is_user_pre_exam  .equalsIgnoreCase("true") || data.get(position).is_user_pre_exam .equals("true"))
                        {

                            strExamId = data.get(position).exam_id;
                            Fragment fragment = new InstructionPay();
                            Bundle bundle = new Bundle();
                            bundle.putString(Constants.EXAM_ID, strExamId);
                            bundle.putString(Constants.LEVEL_ID, "4");
                            fragment.setArguments(bundle);
                            setFragment(fragment);

                        }

                    }

                    else
                    {
                        CommonUtils.loginAlertDialog(context,context.getResources().getString(R.string.please_login_to_continue));
                    }



                }
            });
            hover.findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Boolean isLogin = CommonUtils.getLoginPreferences(context, Constants.IS_LOGIN);
                    if (isLogin) {

                            strExamId = data.get(position).exam_id;
                            slug = data.get(position).slug;

                            //  Toast.makeText(v.getContext(), "Chapter = " + strExamId, Toast.LENGTH_SHORT).show();

                            Fragment fragment = new SelectLevelsFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString(Constants.EXAM_ID, strExamId);
                            bundle.putString(Constants.SLUG, slug);

                            fragment.setArguments(bundle);
                            setFragment(fragment);

                    }
                    else{
                        CommonUtils.loginAlertDialog(context,context.getResources().getString(R.string.please_login_to_continue));
                    }


                }
            });
            holder. mSampleLayout.setHoverView(hover);
            holder. mSampleLayout.setBlurDuration(550);
            holder. mSampleLayout.addChildAppearAnimator(hover, R.id.heart, Techniques.FlipInX, 550, 0);
            holder. mSampleLayout.addChildAppearAnimator(hover, R.id.share, Techniques.FlipInX, 550, 250);

            holder. mSampleLayout.addChildDisappearAnimator(hover, R.id.heart, Techniques.FlipOutX, 550, 500);
            holder. mSampleLayout.addChildDisappearAnimator(hover, R.id.share, Techniques.FlipOutX, 550, 250);




        }
       else if (custom.equalsIgnoreCase("true"))
        {
            //  View hover = inflater.inflate(R.layout.hover_sample, null);
            //  holder.mSampleLayout.setHoverView(hover);


            BlurLayout.setGlobalDefaultDuration(450);
            View hover = inflater.inflate(R.layout.hover_samples, null);
            hover.findViewById(R.id.heart).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Boolean isLogin = CommonUtils.getLoginPreferences(context, Constants.IS_LOGIN);
                    if (isLogin)
                    {


                        if (data.get(position).is_user_pre_exam == "false" || data.get(position).is_user_pre_exam  .equalsIgnoreCase("false") || data.get(position).is_user_pre_exam .equals("false"))
                        {
                            strExamId = data.get(position).exam_id;
                            Intent intentp=new Intent(context, PaymentWeb.class);
                            intentp.putExtra(Constants.EXAM_ID, strExamId);
                            context.startActivity(intentp);

                        }




                        if (data.get(position).is_user_pre_exam == "true" || data.get(position).is_user_pre_exam  .equalsIgnoreCase("true") || data.get(position).is_user_pre_exam .equals("true"))
                        {

                            strExamId = data.get(position).exam_id;
                            slug = data.get(position).slug;
                            Fragment fragment = new Instructionpaid();
                            Bundle bundle = new Bundle();
                            bundle.putString(Constants.EXAM_ID, strExamId);
                            bundle.putString(Constants.SLUG, slug);

                            bundle.putString(Constants.LEVEL_ID, "4");
                            fragment.setArguments(bundle);
                            setFragment(fragment);

                        }

                    }

                    else
                    {
                        CommonUtils.loginAlertDialog(context,context.getResources().getString(R.string.please_login_to_continue));
                    }



                }
            });
            hover.findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Boolean isLogin = CommonUtils.getLoginPreferences(context, Constants.IS_LOGIN);
                    if (isLogin) {

                        strExamId = data.get(position).exam_id;
                        slug = data.get(position).slug;

                        //  Toast.makeText(v.getContext(), "Chapter = " + strExamId, Toast.LENGTH_SHORT).show();

                        Fragment fragment = new SelectLevelsFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.EXAM_ID, strExamId);
                        bundle.putString(Constants.SLUG, slug);

                        fragment.setArguments(bundle);
                        setFragment(fragment);

                    }
                    else{
                        CommonUtils.loginAlertDialog(context,context.getResources().getString(R.string.please_login_to_continue));
                    }


                }
            });
            hover.findViewById(R.id.shareit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Boolean isLogin = CommonUtils.getLoginPreferences(context, Constants.IS_LOGIN);
                    if (isLogin)
                    {


                        if (data.get(position).is_user_custom_exam == "false" || data.get(position).is_user_custom_exam  .equalsIgnoreCase("false") || data.get(position).is_user_custom_exam .equals("false"))
                        {
                            strExamId = data.get(position).exam_id;
                            Intent intentp=new Intent(context, PaymentWebb.class);
                            intentp.putExtra(Constants.EXAM_ID, strExamId);
                            context.startActivity(intentp);

                        }


                        if (data.get(position).is_user_custom_exam == "true" || data.get(position).is_user_custom_exam  .equalsIgnoreCase("true") || data.get(position).is_user_custom_exam .equals("true"))
                        {

                            strExamId = data.get(position).exam_id;
                            slug = data.get(position).slug;
                            Fragment fragment = new Instructioncustom();
                            Bundle bundle = new Bundle();
                            bundle.putString(Constants.EXAM_ID, strExamId);
                            bundle.putString(Constants.SLUG, slug);
                            bundle.putString(Constants.LEVEL_ID, "5");
                            fragment.setArguments(bundle);
                            setFragment(fragment);

                        }

                    }

                    else
                    {
                        CommonUtils.loginAlertDialog(context,context.getResources().getString(R.string.please_login_to_continue));
                    }



                }
            });
            holder. mSampleLayout.setHoverView(hover);
            holder. mSampleLayout.setBlurDuration(550);
            holder. mSampleLayout.addChildAppearAnimator(hover, R.id.heart, Techniques.FlipInX, 550, 0);
            holder. mSampleLayout.addChildAppearAnimator(hover, R.id.share, Techniques.FlipInX, 550, 250);
            holder. mSampleLayout.addChildAppearAnimator(hover, R.id.shareit, Techniques.FlipInX, 550, 500);

            holder. mSampleLayout.addChildDisappearAnimator(hover, R.id.heart, Techniques.FlipOutX, 550, 500);
            holder. mSampleLayout.addChildDisappearAnimator(hover, R.id.share, Techniques.FlipOutX, 550, 250);
            holder. mSampleLayout.addChildDisappearAnimator(hover, R.id.shareit, Techniques.FlipOutX, 550, 0);




        }
        else {
            holder.ivPaper.setTag(holder);
            {
                holder.ivPaper.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        SelectExamsAdapter.MyViewHolder vholder = (SelectExamsAdapter.MyViewHolder)v.getTag();
                        int position = vholder.getPosition();

                        Boolean isLogin = CommonUtils.getLoginPreferences(context, Constants.IS_LOGIN);
                        if (isLogin) {


                            strExamId = data.get(position).exam_id;
                            slug = data.get(position).slug;
                            Fragment fragment = new SelectLevelsFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString(Constants.EXAM_ID, strExamId);
                            bundle.putString(Constants.SLUG, slug);
                            fragment.setArguments(bundle);
                            setFragment(fragment);
                        }

                        else{
                            CommonUtils.loginAlertDialog(context,context.getResources().getString(R.string.please_login_to_continue));
                        }


                    }
                });

            }



        }


    }

    public String addToString(String source, char separator, String toBeInserted) {
        int index = source.lastIndexOf(separator);
        if (index >= 0 && index < source.length())
            return source.substring(0, index) + toBeInserted + source.substring(index);
        else {
            throw new IndexOutOfBoundsException("");
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void inProgressDialog(String strExamId)
    {
        String message = "";
        if (strExamId.equalsIgnoreCase("9")) {
            message = context.getResources().getString(R.string.this_paper_would_be_available);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(message);
        builder.setPositiveButton(context.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    protected void setFragment(Fragment fragment) {
        FragmentTransaction ftTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
        ftTransaction.replace(R.id.container_body, fragment)
                .addToBackStack("back");
        ftTransaction.commit();
    }

    @Override
    public String httpPost() {

        String response = "";
        postUserDetail();
        response = APIAccess.openConnection(ServiceUrl.SABAKUCH_USER_DETAIL, reqEntity);
        return response;
    }

    public void postUserDetail() {
        try {
            String strUserID = CommonUtils.getStringPreferences(context, Constants.USER_ID);
            String strUserName = CommonUtils.getStringPreferences(context, Constants.USER_NAME);
            StringBody userId = new StringBody(strUserID);
            StringBody username = new StringBody(strUserName);
            StringBody levelId = new StringBody("0");
            StringBody examId = new StringBody(strExamId);

            CommonUtils.LogMsg(SelectExamsAdapter.class.getSimpleName(), "user_id: " + strUserID + " username: " + strUserName + " level_id: " + "0" + " exam_id: " + strExamId);
            reqEntity = new MultipartEntity();

            reqEntity.addPart("user_id", userId);
            reqEntity.addPart("username", username);
            reqEntity.addPart("level_id", levelId);
            reqEntity.addPart("exam_id", examId);

        } catch (Exception e)
        {
            System.out.println("err" + e);
        }
    }

    @Override
    public String httpAfterPost(String str) {
        isClicked = false;
        if (str != null) {
            if (SabaKuchParse.getResponseCode(str) != null && SabaKuchParse.getResponseCode(str).equalsIgnoreCase("200")) {
                Fragment fragment = new SelectLevelsFragment();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.EXAM_ID, strExamId);
                fragment.setArguments(bundle);
//                if (context != null) {
                setFragment(fragment);
//                }
            } else {
                Toast.makeText(context, context.getResources().getString(R.string.server_not_responding), Toast.LENGTH_SHORT).show();
            }
        }
        return null;
    }

    class MyViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/ {
        ImageView ivPaper;
        TextView tvName;
        BlurLayout mSampleLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivPaper = (ImageView) itemView.findViewById(R.id.iv_paper);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            mSampleLayout = (BlurLayout)itemView.findViewById(R.id.blur_layout);

          /*  BlurLayout.setGlobalDefaultDuration(450);
            View hover = LayoutInflater.from(itemView.getContext()).inflate(R.layout.hover_sample, null);
            hover.findViewById(R.id.heart).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!isClicked) {
                        isClicked = true;
                        strExamId = data.get(getAdapterPosition()).exam_id;
                        Fragment fragment = new InstructionFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.EXAM_ID, strExamId);
                        bundle.putString(Constants.LEVEL_ID, "4");
                        fragment.setArguments(bundle);
                        setFragment(fragment);
                    }
                    isClicked = false;

                  //  context.startActivity(new Intent(context, PaymentWeb.class));
                  *//*  Toast.makeText(v.getContext(), "Chapter = " + String.valueOf(getAdapterPosition()+1), Toast.LENGTH_SHORT).show();
                    YoYo.with(Techniques.Tada)
                            .duration(550)
                            .playOn(v);*//*
                }
            });
            hover.findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!isClicked) {
                        isClicked = true;
                        strExamId = data.get(getAdapterPosition()).exam_id;
                        Fragment fragment = new SelectLevelsFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.EXAM_ID, strExamId);
                        fragment.setArguments(bundle);
                        setFragment(fragment);
                    }
                    isClicked = false;

                  *//*  Toast.makeText(v.getContext(), "Chapter = " + String.valueOf(getAdapterPosition()+1), Toast.LENGTH_SHORT).show();

                    YoYo.with(Techniques.Swing)
                            .duration(550)
                            .playOn(v);*//*
                }
            });
            mSampleLayout.setHoverView(hover);
            mSampleLayout.setBlurDuration(550);
            mSampleLayout.addChildAppearAnimator(hover, R.id.heart, Techniques.FlipInX, 550, 0);
            mSampleLayout.addChildAppearAnimator(hover, R.id.share, Techniques.FlipInX, 550, 250);

            mSampleLayout.addChildDisappearAnimator(hover, R.id.heart, Techniques.FlipOutX, 550, 500);
            mSampleLayout.addChildDisappearAnimator(hover, R.id.share, Techniques.FlipOutX, 550, 250);

*/


           // itemView.setOnClickListener(this);
        }

  /*      @Override
        public void onClick(View v) {
      *//*      if (!isClicked) {
                isClicked = true;
                strExamId = data.get(getAdapterPosition()).exam_id;
                Fragment fragment = new SelectLevelsFragment();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.EXAM_ID, strExamId);
                fragment.setArguments(bundle);
                setFragment(fragment);
            }
            isClicked = false;*//*
        }*/
    }




//    private void setUpMap()
//    {
//        // Retrieve the city data from the web service
//        // In a worker thread since it's a network operation.
//        new Thread(new Runnable()
//        {
//            public void run()
//            {
//                try
//                {
//                    retrieveAndAddCities();
//                }
//                catch (IOException e)
//                {
//                    Log.e("LOG", "Cannot retrive cities", e);
//                    return;
//
//                }
//            }
//        }).start();
//    }
//
//    protected void retrieveAndAddCities() throws IOException
//
//    {
//        HttpURLConnection conn = null;
//        final StringBuilder json = new StringBuilder();
//        try
//        {
//            // Connect to the web service
//            URL url = new URL("https://sabakuch.com/mock_paper/api/userdetail?primum_userid="+strUserIDss);
//
//            System.out.println("##############################################"+url);
//
//            SSLContext sslcontext = null;
//            try {
//                sslcontext = SSLContext.getInstance("TLSv1");
//            } catch (NoSuchAlgorithmException e) {
//                e.printStackTrace();
//            }
//            try {
//                sslcontext.init(null, null, null);
//
//            }
//            catch (KeyManagementException e)
//
//            {
//                e.printStackTrace();
//            }
//            SSLSocketFactory NoSSLv3Factory = new NoSSLv3SocketFactory(sslcontext.getSocketFactory());
//
//            HttpsURLConnection.setDefaultSSLSocketFactory(NoSSLv3Factory);
//            conn = (HttpsURLConnection) url.openConnection();
//
//
//        //    conn = (HttpURLConnection) url.openConnection();
//            //   conn.setRequestMethod("GET");
//
//            conn.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
//            conn.setRequestProperty("Accept","*/*");
//            InputStreamReader in = new InputStreamReader(conn.getInputStream());
//
//            // Read the JSON data into the StringBuilder
//            int read;
//            char[] buff = new char[1024];
//            while ((read = in.read(buff)) != -1)
//            {
//                json.append(buff, 0, read);
//            }
//        } catch (IOException e)
//        {
//            Log.e("TAG", "Error connecting to service", e);
//            throw new IOException("Error connecting to service", e);
//        } finally {
//            if (conn != null)
//            {
//                conn.disconnect();
//            }
//        }
//
//        // Create markers for the city data.
//        // Must run this on the UI thread since it's a UI operation.
//        runOnUiThread(new Runnable()
//        {
//            public void run()
//            {
//                try
//                {
//                    createMarkersFromJson(json.toString());
//                }
//                catch (JSONException e)
//                {
//                    Log.e("TAG", "Error processing JSON", e);
//                }
//            }
//        });
//    }
//
//    void createMarkersFromJson(String json) throws JSONException
//
//    {
//
//
//
//        JSONObject jsonobject = new JSONObject(json);
//        sspay = jsonobject.getString("status");
//        System.out.println("@@@@@@@@@@@@@@@####" +sspay);
///*
//        JSONObject json1=jsonobject.getJSONObject(("_embedded"));
//
//        JSONArray jsonarray = json1.getJSONArray("userdetail");
//
//        for (int i = 0; i < jsonarray.length(); i++) {
//            JSONObject jsonObject2 = jsonarray.getJSONObject(i);
//             sspay = jsonObject2.getString("trans_status");
//            System.out.println("@@@@@@@@@@@@@@@####" +sspay);
//         //   Toast.makeText(context, sspay, Toast.LENGTH_SHORT).show();
//
//
//
//        }*/
//
//
//
//
//    }


}
