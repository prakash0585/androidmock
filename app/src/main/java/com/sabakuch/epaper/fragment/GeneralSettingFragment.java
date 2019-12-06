package com.sabakuch.epaper.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sabakuch.epaper.R;
import com.sabakuch.epaper.adapter.CitySpinnerAdapter;
import com.sabakuch.epaper.adapter.CountrySpinnerAdapter;
import com.sabakuch.epaper.adapter.GeneralSettingAdapter3;
import com.sabakuch.epaper.adapter.StateSpinnerAdapter;
import com.sabakuch.epaper.apiclass.APIAccess;
import com.sabakuch.epaper.apputils.CommonUtils;
import com.sabakuch.epaper.apputils.FontManager;
import com.sabakuch.epaper.apputils.SabaKuchParse;
import com.sabakuch.epaper.apputils.ServiceUrl;
import com.sabakuch.epaper.constants.Constants;
import com.sabakuch.epaper.data.CityResponse;
import com.sabakuch.epaper.data.CommonResponse;
import com.sabakuch.epaper.data.Country;
import com.sabakuch.epaper.data.CountryResponse;
import com.sabakuch.epaper.data.GeneralSettingData;
import com.sabakuch.epaper.data.StateResponse;
import com.sabakuch.epaper.entitymime.MultipartEntity;
import com.sabakuch.epaper.entitymimecontent.StringBody;
import com.sabakuch.epaper.serviceclasses.OpenConnection;
import com.sabakuch.epaper.serviceclasses.ServiceInterface;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by dell on 03-May-17.
 */
public class GeneralSettingFragment extends Fragment implements ServiceInterface, View.OnClickListener {
    private static final int DATE_DIALOG_ID = 0;
    public Spinner spCountryName;
    public Spinner spCityName, spState;
    public Spinner spTimeZone, spGender;
    public Button SettingSaveGeneral;
    public EditText edUsername, edGeneralEmail, edGeneralFirstName,
            edGeneralLastName, edGeneralMobileNumber, edGeneralAddress1,
            edGeneralAddress2, edtEducationInstitude, edtDegree, edtGrade,
            edtStartYear, edtEndYear, edtStream;
    public int mYear;
    public int mMonth;
    public int mDay;
    public String /* strfullname,stremail, */strfirstname, strlstamne, strmobil,
            strcountry, straddres1, straddres2, strschool, strgarde,
            strfiedstudy, strdegree, strfrom, strto, str_sptime, str_spcount,
            str_spcity, strGender = "";
    public TextView Usercalender;
    TextView tvCountryArrow, tvCityArrow, tvStateArrow, tvEdit, tvTimeArrow, tvGenderArrow;
    TextView tvUser_Dob;
    SwipeRefreshLayout swiperefresh;
    // String date="";
    String cityStr;
    ProgressDialog mProgressDialog;
    ArrayList<Country> timeList = new ArrayList<Country>();
    CountrySpinnerAdapter mCountrySpinnerAdapter;
    StateSpinnerAdapter mStateSpinnerAdapter;
    CitySpinnerAdapter mCitySpinnerAdapter;
    String countryId, stateId, cityId;
    GeneralSettingData generalList = new GeneralSettingData();
    Activity context;
    int intGender;
    int count = 0;
    int check = 0;
    int checkstate = 0;
    int checkcity = 0;
    private TextView tvMsg;
    private EditText etEmail;
    private Button dialogSendButton;
    private Button dialogCancelButton;
    private RadioGroup rg1;
    private RadioButton rbEnable, rbDisable;
    private String cityPosition;
    private ArrayList<CountryResponse.Country> country = new ArrayList<CountryResponse.Country>();
    private ArrayList<StateResponse.State> states = new ArrayList<StateResponse.State>();
    private ArrayList<CityResponse.City> cities = new ArrayList<CityResponse.City>();
    private GeneralSettingAdapter3 adapter3;
    // private GeneralSettingAdapter4 adapter4;
    private MultipartEntity reqEntity;
    private SimpleDateFormat dateFormatter;
    private String strUserID;
    private String strServerkey;
    private int index = 2;
    private int intDay, intMonth, intYear;
    private String strState = "0";
    private ArrayList<String> alAddSelectCity, alAddSelectId;
    private String educationId = "";
    private String apiDob;
    private ProgressDialog pb;
    private ArrayList<CityResponse.City> city = new ArrayList<CityResponse.City>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.general_setting_fragment, container, false);
        setId(rootView);
        setView(rootView);
        return rootView;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getActivity();
        strUserID = CommonUtils.getStringPreferences(getActivity(), Constants.USER_ID);
        strServerkey = CommonUtils.getStringPreferences(getActivity(), Constants.SERVER_KEY);
        CommonUtils.setTracking(GeneralSettingFragment.class.getSimpleName());
        index = 2;
        try {
            if (context != null && !context.isFinishing())
                if (CommonUtils.isOnline(context)) {
                    pb = ProgressDialog.show(context, "", "Processing...");
                    APIAccess.fetchPagingData(GeneralSettingFragment.this,
                            getActivity(), getActivity(), true);
                } else {
                    if (context != null && pb != null && pb.isShowing())
                        pb.dismiss();
                    CommonUtils.showToast(context, "Internet Connection Failed!");
                }

        } catch (Exception e) {

        }

    }

    private void setView(View rootView) {
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);


        alAddSelectCity = new ArrayList<String>();
        alAddSelectId = new ArrayList<String>();
        /*
         * adapter2=new GeneralSettingAdapter2(getActivity(), alAddSelectCity);
		 * spCityName.setAdapter(adapter2);
		 */

        SettingSaveGeneral = (Button) rootView.findViewById(R.id.btnSave);

        SettingSaveGeneral.setOnClickListener(this);

        Usercalender.setOnClickListener(this);
        tvEdit.setOnClickListener(this);

        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                index = 2;
                APIAccess.fetchPagingData(GeneralSettingFragment.this,
                        getActivity(), getActivity(), true);
            }
        });

        spTimeZone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                // str_sptime = spTimeZone.getSelectedItem().toString();
                if (timeList != null && timeList.size() > 0) {
                    if (position > 0) {
                        str_sptime = timeList.get(position - 1).strTimezoneid;
                    } else {
                        str_sptime = "";
                    }

                    // Log.e("str_sptime", " setting"+ str_sptime);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        spGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub

                intGender = spGender.getSelectedItemPosition();
                CommonUtils.LogMsg("spGender", "spGender" + intGender);
                if (intGender == 0) {
                    strGender = "1";
                } else if (intGender == 1) {
                    strGender = "2";
                } else {
                    strGender = "3";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        rbEnable.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                boolean checked = ((RadioButton) v).isChecked();
                if (checked) {
                    strState = "0";
                } else {
                    strState = "1";
                }

                // Toast.makeText(getActivity(), "enabled",
                // Toast.LENGTH_SHORT).show();

            }
        });

        rbDisable.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                boolean checked = ((RadioButton) v).isChecked();
                if (checked) {
                    strState = "1";
                } else {
                    strState = "0";
                }
                // Toast.makeText(getActivity(), "disabled",
                // Toast.LENGTH_SHORT).show();
            }
        });

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        Usercalender.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view,
                                                  int pickYear, int monthOfYear,
                                                  int dayOfMonth) {
                                Calendar newDate = Calendar.getInstance();
                                newDate.set(pickYear, monthOfYear, dayOfMonth);
                                // tvUser_Dob.setText(dateFormatter.format(newDate.getTime()));
                                tvUser_Dob.setText(pickYear + "-"
                                        + (monthOfYear + 1) + "-" + dayOfMonth);
                                // date =(new
                                // StringBuilder().append(year).append("-").append(monthOfYear+1).append("-").append(dayOfMonth).toString());
                                intDay = dayOfMonth;
                                intMonth = monthOfYear + 1;
                                intYear = pickYear;
                            }

                        }, mYear, mMonth, mDay);
                dpd.show();

            }
        });

        setCountrySpinnerAdapter();
        setStateSpinnerAdapter();
        setCitySpinnerAdapter();
    }

    private void setId(View rootView) {
        tvCountryArrow = (TextView) rootView.findViewById(R.id.tvCountryArrow);
        tvCityArrow = (TextView) rootView.findViewById(R.id.tvCityArrow);
        tvStateArrow = (TextView) rootView.findViewById(R.id.tvStateArrow);
        tvEdit = (TextView) rootView.findViewById(R.id.tvEdit);
        Usercalender = (TextView) rootView.findViewById(R.id.Usercalender);
        tvGenderArrow = (TextView) rootView.findViewById(R.id.tvGenderArrow);
        tvTimeArrow = (TextView) rootView.findViewById(R.id.tvTimeArrow);

        tvEdit.setTypeface(FontManager.getTypeface(getActivity(), FontManager.FONTAWESOME));
        Usercalender.setTypeface(FontManager.getTypeface(getActivity(), FontManager.FONTAWESOME));
        tvCountryArrow.setTypeface(FontManager.getTypeface(getActivity(), FontManager.FONTAWESOME));
        tvStateArrow.setTypeface(FontManager.getTypeface(getActivity(), FontManager.FONTAWESOME));
        tvCityArrow.setTypeface(FontManager.getTypeface(getActivity(), FontManager.FONTAWESOME));
        tvGenderArrow.setTypeface(FontManager.getTypeface(getActivity(), FontManager.FONTAWESOME));
        tvTimeArrow.setTypeface(FontManager.getTypeface(getActivity(), FontManager.FONTAWESOME));

        swiperefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);
        rg1 = (RadioGroup) rootView.findViewById(R.id.rg1);
        spCountryName = (Spinner) rootView.findViewById(R.id.spCountryName);
        spCityName = (Spinner) rootView.findViewById(R.id.spCityName);
        spState = (Spinner) rootView.findViewById(R.id.spState);
        spTimeZone = (Spinner) rootView.findViewById(R.id.spTimeZone);
        spGender = (Spinner) rootView.findViewById(R.id.spGender);
        edUsername = (EditText) rootView.findViewById(R.id.edUsername);
        edGeneralEmail = (EditText) rootView.findViewById(R.id.edGeneralEmail);
        edGeneralFirstName = (EditText) rootView
                .findViewById(R.id.edGeneralFirstName);
        edGeneralLastName = (EditText) rootView
                .findViewById(R.id.edGeneralLastName);
        edGeneralMobileNumber = (EditText) rootView
                .findViewById(R.id.edGeneralMobileNumber);
        edGeneralAddress1 = (EditText) rootView
                .findViewById(R.id.edGeneralAddress1);
        edGeneralAddress2 = (EditText) rootView
                .findViewById(R.id.edGeneralAddress2);
        tvUser_Dob = (TextView) rootView.findViewById(R.id.tvUser_Dob);
        edtEducationInstitude = (EditText) rootView
                .findViewById(R.id.edtEducationInstitude);
        edtDegree = (EditText) rootView.findViewById(R.id.edtDegree);
        edtGrade = (EditText) rootView.findViewById(R.id.edtGrade);
        edtStartYear = (EditText) rootView.findViewById(R.id.edtStartYear);
        edtEndYear = (EditText) rootView.findViewById(R.id.edtEndYear);
        edtStream = (EditText) rootView.findViewById(R.id.edtStream);
        rbEnable = (RadioButton) rootView.findViewById(R.id.rbEnable);
        rbDisable = (RadioButton) rootView.findViewById(R.id.rbDisable);

    }

    private void setCountrySpinnerAdapter() {
        mCountrySpinnerAdapter = new CountrySpinnerAdapter(getActivity(),
                country);
        spCountryName.setAdapter(mCountrySpinnerAdapter);

        spCountryName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                check = check + 1;
                if (check > 0) {
                    countryId = country.get(position).getCountryid();
                    index = 1;
                    APIAccess.fetchPagingData(GeneralSettingFragment.this, getActivity(),
                            getActivity(), true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setStateSpinnerAdapter() {
        mStateSpinnerAdapter = new StateSpinnerAdapter(getActivity(), states);
        spState.setAdapter(mStateSpinnerAdapter);

        spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                checkstate = checkstate + 1;
                if (checkstate > 0) {
                    stateId = states.get(position).getStateid();
                    CommonUtils.LogMsg(GeneralSettingFragment.class.getSimpleName(), "state: " + stateId);
                    index = 7;
                    APIAccess.fetchPagingData(GeneralSettingFragment.this, getActivity(),
                            getActivity(), true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setCitySpinnerAdapter() {
        mCitySpinnerAdapter = new CitySpinnerAdapter(getActivity(), cities);
        spCityName.setAdapter(mCitySpinnerAdapter);

        spCityName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                checkcity = checkcity + 1;
                if (checkcity > 0)
                    cityId = cities.get(position).getCityid();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void postProfilegeneral() {
        String userid = strUserID;
        if (intDay == 0 && intMonth == 0 && intYear == 0) {
            intYear = Integer.parseInt(apiDob.substring(0, 4));
            intMonth = Integer.parseInt(apiDob.substring(5, 7));
            intDay = Integer.parseInt(apiDob.substring(8, 10));
        }
        try {

            StringBody firstname = new StringBody(strfirstname);
            StringBody lstname = new StringBody(strlstamne);
            // StringBody email=new StringBody(stremail);
            StringBody address1 = new StringBody(straddres1);
            StringBody address2 = new StringBody(straddres2);
            // StringBody dob=new StringBody(date);
            StringBody user_id = new StringBody(userid);
            StringBody degree = new StringBody(strdegree);
            StringBody fieldstudy = new StringBody(strfiedstudy);
            StringBody grade = new StringBody(strgarde);
            StringBody from = new StringBody(strfrom);
            StringBody to = new StringBody(strto);
            StringBody school = new StringBody(strschool);
            StringBody mobile = new StringBody(strmobil);
            CommonUtils.LogMsg(GeneralSettingFragment.class.getSimpleName(), "Gender: " + strGender + "countryid: " + countryId + " \nstate id: " + stateId + "\ncity: " + cityId);
            StringBody country = new StringBody(
                    (countryId != null && countryId.length() > 0) ? countryId
                            : "");
            StringBody city = new StringBody(
                    (cityId != null && cityId.length() > 0) ? cityId : "");
            StringBody state = new StringBody(
                    (stateId != null && stateId.length() > 0) ? stateId : "");
            StringBody timezone = new StringBody(str_sptime);
            StringBody gender = new StringBody(strGender);
            StringBody day = new StringBody(String.valueOf(intDay));
            StringBody month = new StringBody(String.valueOf(intMonth));
            StringBody year = new StringBody(String.valueOf(intYear));
            StringBody strEducationId = new StringBody(educationId);
            StringBody enable;
            if (rbDisable.isChecked())
                enable = new StringBody("1");
            else
                enable = new StringBody("0");

            reqEntity = new MultipartEntity();

            reqEntity.addPart("fname", firstname);
            reqEntity.addPart("lname", lstname);
            reqEntity.addPart("contact", mobile);
            reqEntity.addPart("birthday_month", month);
            reqEntity.addPart("birthday_day", day);
            reqEntity.addPart("birthday_year", year);
            reqEntity.addPart("gender", gender);
            reqEntity.addPart("add1", address1);
            reqEntity.addPart("add2", address2);
            reqEntity.addPart("userid", user_id);
            reqEntity.addPart("country", country);
            reqEntity.addPart("state", state);
            reqEntity.addPart("city", city);
            reqEntity.addPart("time_zone", timezone);
            reqEntity.addPart("degree", degree);
            reqEntity.addPart("field_study", fieldstudy);
            reqEntity.addPart("grade", grade);
            reqEntity.addPart("from_date", from);
            reqEntity.addPart("to_date", to);
            reqEntity.addPart("school", school);
            reqEntity.addPart("enable", enable);
            reqEntity.addPart("education_id", strEducationId);

        } catch (Exception e) {
            // System.out.println("err" + e);
            e.printStackTrace();
        }

    }

    void postEmail() {
        try {
            reqEntity = new MultipartEntity();
            StringBody email = new StringBody(etEmail.getText().toString().trim());
            StringBody userid = new StringBody(strUserID);
            reqEntity.addPart("email", email);
            reqEntity.addPart("userid", userid);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSave:

                index = 5;
                checkValidation();
                break;
            case R.id.btnSend:

                if (etEmail.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter email.", Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                } else if (!CommonUtils.isValidEmail(etEmail.getText().toString().trim())) {

                    Toast.makeText(getActivity(), "Please enter valid email.", Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                } else {
                    index = 8;
                    APIAccess.fetchData(GeneralSettingFragment.this, getActivity(),
                            getActivity());

                }
                break;
            case R.id.tvEdit:
                showDialog();
                break;
        }

    }


    private void showDialog() {

        // custom dialog
        final Dialog mdialog = new Dialog(getActivity());
        mdialog.setContentView(R.layout.dialog_edit_email);
        mdialog.setTitle("Message");

        // set the custom dialog components - text, image and button
        tvMsg = (TextView) mdialog.findViewById(R.id.tvMsg);
        etEmail = (EditText) mdialog.findViewById(R.id.etEmail);

        dialogSendButton = (Button) mdialog.findViewById(R.id.btnSend);
        dialogSendButton.setOnClickListener(this);
        dialogCancelButton = (Button) mdialog.findViewById(R.id.btnCancel);
        // if button is clicked, close the custom dialog
        dialogCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdialog.dismiss();
            }
        });

        mdialog.show();


    }

    @Override
    public String httpPost() {
        // TODO Auto-generated method stub

        String response = "";
        if (index == 0) {
            response = OpenConnection.callUrl(ServiceUrl.SABAKUCH_CountryList);

        } else if (index == 1) {
            response = OpenConnection.callUrl(ServiceUrl.SABAKUCH_StateList
                    + countryId);

        } else if (index == 2) {
            response = OpenConnection
                    .callUrl(ServiceUrl.SABAKUCH_GENERAL_SETTING_TIMEZONE);
        } else if (index == 3) {
            response = OpenConnection
                    .callUrl(ServiceUrl.SABAKUCH_PROFILE_SETTINGS
                            + strUserID);
        }

        // Log.d("RespoNCEEEEEEEEE", response);

        else if (index == 5) {
            postProfilegeneral();

            response = APIAccess.openConnection(
                    ServiceUrl.SABAKUCH_PROFILE_SETTINGS, reqEntity);

            // String urllll = StaticData.SABAKUCH_GENERAL_SETTING_GETDATA;

            // Log.d("urllllllllll", "urllll"+response);

        } else if (index == 6) {
            response = OpenConnection
                    .callUrl(ServiceUrl.SABAKUCH_PROFILE_SETTINGS
                            + strUserID);
        } else if (index == 7) {
//            cityStr = ServiceUrl.SABAKUCH_CityList + stateId;
            response = OpenConnection.callUrl(ServiceUrl.SABAKUCH_CityList
                    + stateId);
            pb.dismiss();

        } else if (index == 8) {
            postEmail();

            response = APIAccess.openConnection(
                    ServiceUrl.SABAKUCH_PROFILE_SETTINGS + "/put", reqEntity);
        }
        return response;
    }

    @Override
    public String httpAfterPost(String str) {
        // TODO Auto-generated method stub

        if (str != null) {
            if (index == 0) {
                country.clear();
                CountryResponse mCountryResponse = CommonUtils.getCustomGson()
                        .fromJson(str, CountryResponse.class);

                // CommonUtils.LogMsg(
                // CreatePageFormFragment.class.getSimpleName(),
                // "Country list: "
                // + CommonUtils.getCustomeGson().toJson(
                // mCountryResponse));
                if (mCountryResponse != null) {
                    country.addAll(mCountryResponse.getFeeds().getCountry());

                }
                mCountrySpinnerAdapter.notifyDataSetChanged();
                for (int i = 0; i < country.size(); i++) {
                    if (country.get(i).getCountryid()
                            .equalsIgnoreCase(generalList.strCountry_id)) {

                        countryId = country.get(i).getCountryid();
                        spCountryName.setSelection(i);
                    }
//                        else
//                        countryId = country.get(0).getCountryid();
                }
//                stateId = "";
//                cityId = "";
                index = 1;
                APIAccess.fetchPagingData(GeneralSettingFragment.this,
                        getActivity(), getActivity(), true);


            } else if (index == 1) {
                states.clear();
                StateResponse mStateResponse = CommonUtils.getCustomGson()
                        .fromJson(str, StateResponse.class);
                if (mStateResponse != null && mStateResponse.getFeeds() != null
                        && mStateResponse != null
                        && mStateResponse.getFeeds().getStates() != null) {
                    states.addAll(mStateResponse.getFeeds().getStates());

                }

                for (int i = 0; i < states.size(); i++) {
                    if (states.get(i).getStateid()
                            .equalsIgnoreCase(generalList.strStateId)) {
                        stateId = states.get(i).getStateid();
                        spState.setSelection(i);

                    }
//                    else
//                        stateId = states.get(0).getStateid();

                }
                mStateSpinnerAdapter.notifyDataSetChanged();
                if (states.size() == 0) {
                    StateResponse.State mState = mStateResponse.new State();
                    mState.setStateid("");
                    mState.setName("No State Available");
                    states.add(mState);
                }
//                stateId = states.get(0).getStateid();
//                cityId = "1";

                index = 7;
                APIAccess.fetchPagingData(GeneralSettingFragment.this,
                        getActivity(), getActivity(), true);

            } else if (index == 2) {
                index = 3;
                APIAccess.fetchPagingData(GeneralSettingFragment.this,
                        getActivity(), getActivity(), true);

                timeList = SabaKuchParse.parseTimeData(str);

                if (timeList.size() > 0) {
                    ArrayList<String> alAddSelectTimezone = new ArrayList<String>();
                    alAddSelectTimezone.add("Select Timezone");
                    for (int i = 0; i < timeList.size(); i++) {
                        String timezone = timeList.get(i).strTimezonename + " "
                                + timeList.get(i).strCountryname;
                        alAddSelectTimezone.add(timezone);
                    }

                    adapter3 = new GeneralSettingAdapter3(getActivity(),
                            alAddSelectTimezone);

                    spTimeZone.setAdapter(adapter3);

                }
            } else if (index == 3) {

                if (context != null && pb != null && pb.isShowing())
                    pb.dismiss();

                generalList = SabaKuchParse.parseProfileData(str);

                edUsername
                        .setText((generalList.strUsername == null || generalList.strUsername
                                .equalsIgnoreCase("null")) ? ""
                                : generalList.strUsername);
                edGeneralEmail
                        .setText((generalList.strEmail == null || generalList.strEmail
                                .equalsIgnoreCase("null")) ? ""
                                : generalList.strEmail);
                edGeneralFirstName
                        .setText((generalList.strFname == null || generalList.strFname
                                .equalsIgnoreCase("null")) ? ""
                                : generalList.strFname);
                edGeneralLastName
                        .setText((generalList.strlname == null || generalList.strlname
                                .equalsIgnoreCase("null")) ? ""
                                : generalList.strlname);
                edGeneralMobileNumber
                        .setText((generalList.strContact == null || generalList.strContact
                                .equalsIgnoreCase("null")) ? ""
                                : generalList.strContact);
                edGeneralAddress1
                        .setText((generalList.strAddress1 == null || generalList.strAddress1
                                .equalsIgnoreCase("null")) ? ""
                                : generalList.strAddress1);
                edGeneralAddress2
                        .setText((generalList.strAddress2 == null || generalList.strAddress2
                                .equalsIgnoreCase("null")) ? ""
                                : generalList.strAddress2);
                tvUser_Dob
                        .setText((generalList.strDob == null
                                || generalList.strDob.equalsIgnoreCase("null") || generalList.strDob
                                .equalsIgnoreCase("0000-00-00")) ? ""
                                : generalList.strDob);
                if (!(generalList.strDob == null
                        || generalList.strDob.equalsIgnoreCase("null") || generalList.strDob
                        .equalsIgnoreCase("0000-00-00"))) {
                    apiDob = generalList.strDob;
                }
                if (generalList.strDateBirthEnable != null
                        && generalList.strDateBirthEnable.length() > 0
                        && generalList.strDateBirthEnable.equalsIgnoreCase("0")) {
                    rbEnable.setChecked(true);
                    rbDisable.setChecked(false);
                } else {
                    rbEnable.setChecked(false);
                    rbDisable.setChecked(true);
                }

                if (generalList.arr != null && generalList.arr.size() > 0) {

                    edtEducationInstitude
                            .setText((generalList.arr.get(0).strSchool == null || generalList.arr
                                    .get(0).strSchool.equalsIgnoreCase("null")) ? ""
                                    : generalList.arr.get(0).strSchool);
                    edtDegree
                            .setText((generalList.arr.get(0).strDegree == null || generalList.arr
                                    .get(0).strDegree.equalsIgnoreCase("null")) ? ""
                                    : generalList.arr.get(0).strDegree);
                    edtGrade.setText((generalList.arr.get(0).strGrade == null || generalList.arr
                            .get(0).strGrade.equalsIgnoreCase("null")) ? ""
                            : generalList.arr.get(0).strGrade);
                    edtStartYear
                            .setText((generalList.arr.get(0).strFrom_date == null
                                    || generalList.arr.get(0).strFrom_date
                                    .equalsIgnoreCase("null") || generalList.arr
                                    .get(0).strFrom_date
                                    .equalsIgnoreCase("0000")) ? ""
                                    : generalList.arr.get(0).strFrom_date);
                    edtEndYear
                            .setText((generalList.arr.get(0).strTo_date == null
                                    || generalList.arr.get(0).strTo_date
                                    .equalsIgnoreCase("null") || generalList.arr
                                    .get(0).strTo_date.equalsIgnoreCase("0000")) ? ""
                                    : generalList.arr.get(0).strTo_date);
                    edtStream
                            .setText((generalList.arr.get(0).strField_study == null || generalList.arr
                                    .get(0).strField_study
                                    .equalsIgnoreCase("null")) ? ""
                                    : generalList.arr.get(0).strField_study);
                    educationId = generalList.arr.get(0).strEducation_id;
                }

                if (generalList.strGender != null
                        && generalList.strGender.length() > 0
                        && generalList.strGender.equalsIgnoreCase("1")) {
                    strGender = "1";
                    spGender.setSelection(0);
                } else if (generalList.strGender != null
                        && generalList.strGender.length() > 0
                        && generalList.strGender.equalsIgnoreCase("2")) {
                    strGender = "2";
                    spGender.setSelection(1);
                } else {
                    strGender = "3";
                    spGender.setSelection(2);
                }

                CommonUtils.saveStringPreferences(context,
                        Constants.FNAME, generalList.strFname);
                edGeneralFirstName.setSelection(edGeneralFirstName.getText()
                        .toString().length());
                edGeneralLastName.setSelection(edGeneralLastName.getText()
                        .toString().length());
                edGeneralMobileNumber.setSelection(edGeneralMobileNumber
                        .getText().toString().length());
                edGeneralAddress1.setSelection(edGeneralAddress1.getText()
                        .toString().length());
                edGeneralAddress2.setSelection(edGeneralAddress2.getText()
                        .toString().length());
                edtEducationInstitude.setSelection(edtEducationInstitude
                        .getText().toString().length());
                edtDegree.setSelection(edtDegree.getText().toString().length());
                edtGrade.setSelection(edtGrade.getText().toString().length());
                edtStartYear.setSelection(edtStartYear.getText().toString()
                        .length());
                edtEndYear.setSelection(edtEndYear.getText().toString()
                        .length());
                edtStream.setSelection(edtStream.getText().toString().length());

                // String ss = generalList.strTime_zone;

                for (int i = 0; i < timeList.size(); i++) {
                    if (timeList.get(i).strTimezoneid
                            .equalsIgnoreCase(generalList.strTime_zone)) {
                        str_sptime = timeList.get(i).strTimezoneid;
                        spTimeZone.setSelection(i + 1);
                        CommonUtils.LogMsg(
                                GeneralSettingFragment.class.getSimpleName(),
                                "straddres1 timezone" + str_sptime);

                    }
                }

                index = 0;
                APIAccess.fetchData(GeneralSettingFragment.this, getActivity(),
                        getActivity());

            } else if (index == 5) {

                CommonResponse mCommonResponse = CommonUtils.getCustomGson().fromJson(str, CommonResponse.class);
                if (mCommonResponse.getSuccess() == 1) {
                    CommonUtils.showToast(getActivity(), mCommonResponse.getMessage());
                    index = 2;
                    APIAccess.fetchData(GeneralSettingFragment.this, getActivity(),
                            getActivity());
                } else {
                    CommonUtils.showToast(getActivity(), mCommonResponse.getMessage());
                }

            } else if (index == 7) {
                cities.clear();
                CityResponse mCityResponse = CommonUtils.getCustomGson()
                        .fromJson(str, CityResponse.class);

                CommonUtils.LogMsg(
                        GeneralSettingFragment.class.getSimpleName(),
                        " cities "
                                + CommonUtils.getCustomGson().toJson(
                                mCityResponse));
                if (mCityResponse != null) {
                    if (mCityResponse.getFeeds().getCities() != null
                            && mCityResponse.getFeeds().getCities().size() != 0)
                        cities.addAll(mCityResponse.getFeeds().getCities());
                }
                if (cities.size() == 0) {
                    CityResponse.City mCity = mCityResponse.new City();
                    mCity.setCityid("");
                    mCity.setCityname("No City Available");
                    cities.add(mCity);
                }

                mCitySpinnerAdapter.notifyDataSetChanged();
                for (int i = 0; i < cities.size(); i++) {
                    if (cities.get(i).getCityid()
                            .equalsIgnoreCase(generalList.strCity_id)) {
                        cityId = cities.get(i).getCityid();
                        spCityName.setSelection(i);
                    }
//                    else
//                        cityId = cities.get(0).getCityid();
                }

            } else if (index == 8) {
                CommonResponse mCommonResponse = CommonUtils.getCustomGson().fromJson(str, CommonResponse.class);
                if (mCommonResponse.getSuccess() == 1) {
                    dialogCancelButton.setText("Ok");
                    dialogSendButton.setVisibility(View.GONE);
                    etEmail.setVisibility(View.GONE);
                    tvMsg.setVisibility(View.VISIBLE);
                    tvMsg.setText(mCommonResponse.getMessage());
                    index = 3;
                    APIAccess.fetchData(GeneralSettingFragment.this, getActivity(), getActivity());
                } else {
                    dialogSendButton.setVisibility(View.GONE);
                    tvMsg.setVisibility(View.VISIBLE);
                    tvMsg.setText(mCommonResponse.getMessage());
                }
            }
        }
        pb.dismiss();
        swiperefresh.setRefreshing(false);
        return null;
    }


    private void checkValidation() {

        if (edGeneralFirstName.getText().toString().equalsIgnoreCase("")) {
            CommonUtils.showToast(getActivity(), "Please enter first name.");
            edGeneralFirstName.requestFocus();
        } else if (edGeneralFirstName.getText().toString()
                .matches(".*[^a-z^A-Z].*")) {
            Toast.makeText(getActivity(),
                    "Invalid first name only a-z allowed..", Toast.LENGTH_SHORT)
                    .show();
            edGeneralFirstName.requestFocus();
        } else if (edGeneralFirstName.getText().toString().length() < 3) {
            Toast.makeText(getActivity(),
                    "First name should be atleast 3 characters.",
                    Toast.LENGTH_SHORT).show();
            edGeneralFirstName.requestFocus();
        } else if (edGeneralLastName.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please enter last name.",
                    Toast.LENGTH_SHORT).show();
            edGeneralLastName.requestFocus();
        } else if (edGeneralLastName.getText().toString()
                .matches(".*[^a-z^A-Z].*")) {
            Toast.makeText(getActivity(),
                    "Invalid last name only a-z allowed.", Toast.LENGTH_SHORT)
                    .show();
            edGeneralLastName.requestFocus();
        } else if (edGeneralLastName.getText().toString().length() < 3) {
            Toast.makeText(getActivity(),
                    "Last name should be atleast 3 characters.",
                    Toast.LENGTH_SHORT).show();
            edGeneralLastName.requestFocus();
        }
//        else if (edGeneralMobileNumber.getText().toString()
//                .equalsIgnoreCase("")) {
//            Toast.makeText(getActivity(), "Please enter mobile number.",
//                    Toast.LENGTH_SHORT).show();
//            edGeneralMobileNumber.requestFocus();
//        } else if (edGeneralMobileNumber.getText().toString()
//                .matches(".*[^0-9].*")) {
//            Toast.makeText(getActivity(),
//                    "Invalid mobile number only 0-9 allowed.",
//                    Toast.LENGTH_SHORT).show();
//            edGeneralMobileNumber.requestFocus();
//        } else if (edGeneralMobileNumber.getText().toString().length() < 10) {
//            Toast.makeText(getActivity(),
//                    "Please enter 10 digits mobile number.", Toast.LENGTH_SHORT)
//                    .show();
//            edGeneralMobileNumber.requestFocus();
//        }
        /*
		 * else if(spCountryName.getSelectedItem().toString().equalsIgnoreCase(
		 * "Select Country")) { Toast.makeText(getActivity(),
		 * "Mobile number should be atleast 10 digits.",
		 * Toast.LENGTH_SHORT).show(); edGeneralMobileNumber.requestFocus(); }
		 */
        else if (edGeneralAddress1.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please enter address 1.",
                    Toast.LENGTH_SHORT).show();
            edGeneralAddress1.requestFocus();
        } else {
            // strfullname=edUsername.getText().toString();
            strfirstname = edGeneralFirstName.getText().toString();
            strlstamne = edGeneralLastName.getText().toString();
            // stremail=edGeneralEmail.getText().toString();
            straddres1 = edGeneralAddress1.getText().toString();
            straddres2 = edGeneralAddress2.getText().toString();
            strdegree = edtDegree.getText().toString();
            strfiedstudy = edtStream.getText().toString();
            strmobil = edGeneralMobileNumber.getText().toString();
            strschool = edtEducationInstitude.getText().toString();
            strfrom = edtStartYear.getText().toString();
            strto = edtEndYear.getText().toString();
            strgarde = edtGrade.getText().toString();
            // Log.e("straddres1", "straddres1 city"+str_spcity);
            // Log.e("straddres1", "straddres1 country"+str_spcount);
            // Log.e("straddres1", "straddres1 timezone"+str_sptime);
            index = 5;

            APIAccess.fetchData(GeneralSettingFragment.this, getActivity(),
                    getActivity());
        }
    }


}
