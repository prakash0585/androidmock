package com.sabakuch.epaper.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.IntentSender;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.sabakuch.epaper.R;
import com.sabakuch.epaper.apiclass.APIAccess;
import com.sabakuch.epaper.apputils.CommonUtils;
import com.sabakuch.epaper.apputils.SabaKuchParse;
import com.sabakuch.epaper.apputils.ServiceUrl;
import com.sabakuch.epaper.constants.Constants;
import com.sabakuch.epaper.data.LoginData;
import com.sabakuch.epaper.data.User;
import com.sabakuch.epaper.entitymime.MultipartEntity;
import com.sabakuch.epaper.entitymimecontent.StringBody;
import com.sabakuch.epaper.serviceclasses.ServiceInterface;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor>, OnClickListener, ServiceInterface, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final int RC_SIGN_IN = 0;
    TextView mEmailSignInButton, tvFacebook, tvGooglePlus, tvForgotPassword, tvSignUp;
    String socialUserId, socialUserProfilePic, socialUserType,socialUserSource, socialUserName, socialUserEmail, socialUserGender, socialUserDOB,
            socialUserFirstName, socialUserLastName;
    int MY_PERMISSIONS_REQUEST_GET_ACCOUNTS = 4;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private Activity context;
    //   private View mProgressView;
    private int urlIndex = 0;
    private View mLoginFormView;
    private Intent intent;
    private String strUserName, strPassword;
    private Dialog dialog;
    private String strEmailID;
    private MultipartEntity reqEntity;
    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;
    private ConnectionResult mConnectionResult;
    private boolean mIntentInProgress;
    private boolean isGplusLogin = false;
    private boolean mSignInClicked;
    private boolean isUseWebViewForAuthentication = false;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private User userData;
    private TextView Btabout, bthelp, btterms, btcontactus, btpolices, btmusic, btsabmail, sn, chat, e_learning, Images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = LoginActivity.this;

        FacebookSdk.sdkInitialize(context);
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_login);
        CommonUtils.setTracking(LoginActivity.class.getSimpleName());
        checkGetAccountsPermission();
        // login with gplus
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();
        signInWithGplus();
        getId();
    }

    public void checkGetAccountsPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.GET_ACCOUNTS)
                != PackageManager.PERMISSION_GRANTED) {
            CommonUtils.LogMsg(LoginActivity.class.getSimpleName(), "Oncheck permission");

            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.GET_ACCOUNTS},
                    MY_PERMISSIONS_REQUEST_GET_ACCOUNTS);

            // MY_PERMISSIONS_REQUEST_READ_PHONE_STATE is an
            // app-defined int constant. The callback method gets the
            // result of the request.
//            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_GET_ACCOUNTS) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted, yay! Do the
                // contacts-related task you need to do.
//                CommonUtils.saveIntPreferences(MainActivity.this, Constants.ReadPermission, 1);
//                Intent intent = new Intent(this, ContactService.class);
//                // add infos for the service which file to download and where to store
//                startService(intent);
            } else {
                // permission denied, boo! Disable the
                // functionality that depends on this permission.
//                CommonUtils.saveIntPreferences(MainActivity.this, Constants.ReadPermission, 2);
            }

            return;
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void getId() {
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        //  populateAutoComplete();
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mEmailSignInButton = (TextView) findViewById(R.id.tv_signin);
        mLoginFormView = findViewById(R.id.login_form);
        tvFacebook = (TextView) findViewById(R.id.tv_facebook);
        //  tvTwitter = (TextView) findViewById(R.id.tv_twitter);
        tvGooglePlus = (TextView) findViewById(R.id.tv_google_plus);
        tvForgotPassword = (TextView) findViewById(R.id.tv_forgot_password);
        tvSignUp = (TextView) findViewById(R.id.tv_signup);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        facebookLogin();
        TextView tvHeaderTitle = (TextView) findViewById(R.id.tv_header_title);
        String screenName = getIntent().getStringExtra(Constants.SCREEN_NAME);

        mEmailSignInButton.setOnClickListener(this);
        tvFacebook.setOnClickListener(this);
        tvGooglePlus.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
        footerListeners();
    }

    private void footerListeners() {
        btcontactus = (TextView) findViewById(R.id.contactus);
        bthelp = (TextView) findViewById(R.id.helpss);
        btterms = (TextView) findViewById(R.id.termcondition);
        Btabout = (TextView) findViewById(R.id.Aboutuss);
        btpolices = (TextView) findViewById(R.id.policies);
        btmusic = (TextView) findViewById(R.id.Musics);
        btsabmail = (TextView) findViewById(R.id.Sabmail);
        sn = (TextView) findViewById(R.id.sn);
        chat = (TextView) findViewById(R.id.chat);
        e_learning = (TextView) findViewById(R.id.e_learning);
        Images = (TextView) findViewById(R.id.Images);

        sn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Use package name which we want to check
                boolean isAppInstalled = CommonUtils.appInstalledOrNot(
                        "com.sabakuch", LoginActivity.this);

                if (isAppInstalled) {
                    // This intent will help you to launch if the package is
                    // already installed
                    Intent LaunchIntent = getPackageManager()
                            .getLaunchIntentForPackage("com.sabakuch");
                    startActivity(LaunchIntent);

                    CommonUtils.LogMsg(MainActivity.class.getSimpleName(),
                            "Application is already installed.");
                } else {
                    // Do whatever we want to do if application not installed
                    // For example, Redirect to play store
                    String url = "https://sabakuch.com/m/";

                    Intent ibaout = new Intent(Intent.ACTION_VIEW);
                    ibaout.setData(Uri.parse(url));
                    startActivity(ibaout);
                    CommonUtils.LogMsg(MainActivity.class.getSimpleName(),
                            "Application is not currently installed.");
                }
            }
        });

        e_learning.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Use package name which we want to check
                boolean isAppInstalled = CommonUtils.appInstalledOrNot(
                        "com.sabakuch.elearning", LoginActivity.this);

                if (isAppInstalled) {
                    // This intent will help you to launch if the package is
                    // already installed
                    Intent LaunchIntent = getPackageManager()
                            .getLaunchIntentForPackage("com.sabakuch.elearning");
                    startActivity(LaunchIntent);

                    CommonUtils.LogMsg(MainActivity.class.getSimpleName(),
                            "Application is already installed.");
                } else {
                    // Do whatever we want to do if application not installed
                    // For example, Redirect to play store
                    String url = "https://sabakuch.com/m/elearning/";

                    Intent ibaout = new Intent(Intent.ACTION_VIEW);
                    ibaout.setData(Uri.parse(url));
                    startActivity(ibaout);
                    CommonUtils.LogMsg(MainActivity.class.getSimpleName(),
                            "Application is not currently installed.");
                }
            }
        });

        chat.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://sabakuch.com/myzone/";

                Intent ibaout = new Intent(Intent.ACTION_VIEW);
                ibaout.setData(Uri.parse(url));
                startActivity(ibaout);
            }
        });

        Images.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String url = "https://sabakuch.com/images/";

                Intent ibaout = new Intent(Intent.ACTION_VIEW);
                ibaout.setData(Uri.parse(url));
                startActivity(ibaout);
            }
        });
        Btabout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String url = "https://sabakuch.com/cms/index/aboutus";

                Intent ibaout = new Intent(Intent.ACTION_VIEW);
                ibaout.setData(Uri.parse(url));
                startActivity(ibaout);
            }
        });


        btcontactus.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            /*	String url = "http://sabakuch.com/cms/index/contact";

				Intent iction = new Intent(Intent.ACTION_VIEW);
				iction.setData(Uri.parse(url));
				startActivity(iction);*/

                Intent intent = new Intent(LoginActivity.this, ContactUsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


        btpolices.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String url = "https://sabakuch.com/cms/index/policies-and-terms/";

                Intent ipolices = new Intent(Intent.ACTION_VIEW);
                ipolices.setData(Uri.parse(url));
                startActivity(ipolices);
            }
        });

        //
        bthelp.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String url = "https://sabakuch.com/cms/index/help";
                Intent ibhelp = new Intent(Intent.ACTION_VIEW);
                ibhelp.setData(Uri.parse(url));
                startActivity(ibhelp);
            }
        });
        btterms.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //		String url = "http://sabakuch.com/cms/index/privacy";
                String url = "https://sabakuch.com/cms/index/terms-and-conditions/";
                Intent iterm = new Intent(Intent.ACTION_VIEW);
                iterm.setData(Uri.parse(url));
                startActivity(iterm);
            }
        });

        btmusic.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String url = "https://music.sabakuch.com/";
                Intent imusic = new Intent(Intent.ACTION_VIEW);
                imusic.setData(Uri.parse(url));
                startActivity(imusic);
            }
        });

        btsabmail.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String url = "https://sabmail.com/";
                Intent isabmail = new Intent(Intent.ACTION_VIEW);
                isabmail.setData(Uri.parse(url));
                startActivity(isabmail);
            }
        });
    }


    private void attemptLogin() {
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            //  mEmailView.setError(getString(R.string.error_field_required));
            Toast.makeText(LoginActivity.this, getResources().getString(R.string.please_enter_username), Toast.LENGTH_SHORT).show();
            focusView = mEmailView;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        else if (TextUtils.isEmpty(password)) {
            //   mPasswordView.setError(getString(R.string.error_invalid_password));
            Toast.makeText(LoginActivity.this, getResources().getString(R.string.please_enter_password), Toast.LENGTH_SHORT).show();

            focusView = mPasswordView;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            strUserName = email;
            strPassword = password;
            CommonUtils.hideKeyboard(LoginActivity.this);
            urlIndex = 0;
            APIAccess.fetchData(this, this, this);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_forgot_password:
               // showForgotPasswordDialog();
                Intent mIntent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                startActivity(mIntent);
                break;
            case R.id.tv_signup:
                intent = new Intent(context, SignUpActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_facebook:
                if (CommonUtils.isOnline(context)) {
                    loginButton.performClick();
                } else {
                    Toast.makeText(context, getResources().getString(R.string.error_no_connection), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_google_plus:
                if (CommonUtils.isOnline(context)) {
                    isGplusLogin = false;
                    if (mGoogleApiClient.isConnected()) {
                        mGoogleApiClient.disconnect();
                        Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
                        mGoogleApiClient.connect();
                    } else {
                        // Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
                        mGoogleApiClient.connect();
                    }
                } else {
                    Toast.makeText(context,
                            getResources().getString(R.string.error_no_connection),
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_signin:
                attemptLogin();
                break;


        }
    }

    private void facebookLogin() {
        loginButton.setReadPermissions("email");
        loginButton.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        //System.out.println("onSuccess");

                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object,
                                                            GraphResponse response) {

                                        Log.i("LoginActivity",
                                                response.toString());
                                        try {
                                            try {
                                                try {
                                                    socialUserId = object.getString("id");
                                                    socialUserType = "fb";
                                                    socialUserSource="fb_android_emock";

                                                    if (object.has("email")) {
                                                        socialUserEmail = object.getString("email");
                                                    } else {
                                                        socialUserEmail = " ";
                                                    }
                                                    socialUserFirstName = object.getString("first_name");
                                                    socialUserLastName = object.getString("last_name");
                                                    socialUserGender ="";
                                                  //  socialUserGender = object.getString("gender");
                                                    userData = new User(socialUserType,socialUserSource,
                                                            "", socialUserFirstName, socialUserLastName, socialUserId, socialUserEmail, "", socialUserGender);

                                                    CommonUtils.hideKeyboard(LoginActivity.this);
                                                    urlIndex = 2;
                                                    APIAccess.fetchData(LoginActivity.this, LoginActivity.this, LoginActivity.this);

                                                } catch (Exception e) {

                                                    e.printStackTrace();
                                                }

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }


                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields",
                                "id,first_name,last_name,email,gender");

                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        System.out.println("onCancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        System.out.println("onError");
                        Log.v("LoginActivity", exception.getCause().toString());
                    }
                });
    }

    public void showForgotPasswordDialog() {
        dialog = new Dialog(LoginActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_forgot_password);
        dialog.setCancelable(false);
        final EditText etEmailid = (EditText) dialog.findViewById(R.id.edEmailID);
        TextView tvTitle = (TextView) dialog.findViewById(R.id.title);
        final Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        final Button btnSubmit = (Button) dialog.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (etEmailid.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(LoginActivity.this, "Please enter email.", Toast.LENGTH_SHORT).show();

                } else if (!CommonUtils.isValidEmail(etEmailid.getText().toString().trim())) {
                    Toast.makeText(LoginActivity.this, "Please enter valid email.", Toast.LENGTH_SHORT).show();

                } else {
                    strEmailID = etEmailid.getText().toString();
                    urlIndex = 1;
                    APIAccess.fetchData(LoginActivity.this, LoginActivity.this, LoginActivity.this);
                    dialog.dismiss();
                }
            }
        });
        btnCancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void postLogin() {
        try {
            StringBody username = new StringBody(strUserName);
            StringBody password = new StringBody(strPassword);

            reqEntity = new MultipartEntity();

            reqEntity.addPart("username", username);
            reqEntity.addPart("password", password);

        } catch (Exception e) {
            System.out.println("err" + e);
        }
    }

    public void postForgotPass() {
        try {
            StringBody emailid = new StringBody(strEmailID);

            reqEntity = new MultipartEntity();
            reqEntity.addPart("email", emailid);

        } catch (Exception e) {
            System.out.println("err" + e);
        }
    }

    public void postSocialLogin() {
        try {
            reqEntity = new MultipartEntity();

            if ((socialUserId != null && socialUserId.length() > 0)) {
                StringBody strSocialUserId = new StringBody(socialUserId);
                reqEntity.addPart("app_id", strSocialUserId);
            }
            if ((socialUserType != null && socialUserType.length() > 0)) {
                StringBody strSocialUserType = new StringBody(socialUserType);
                reqEntity.addPart("type", strSocialUserType);
            }

            if ((socialUserSource != null && socialUserSource.length() > 0)) {
                StringBody strSocialUserSource = new StringBody(socialUserSource);
                reqEntity.addPart("sourcesignup", strSocialUserSource);
            }
            if ((socialUserFirstName != null && socialUserFirstName.length() > 0)) {
                StringBody strSocialUserFirstName = new StringBody(socialUserFirstName);
                reqEntity.addPart("fname", strSocialUserFirstName);
            }
            if ((socialUserLastName != null && socialUserLastName.length() > 0)) {
                StringBody strSocialUserLastName = new StringBody(socialUserLastName);
                reqEntity.addPart("lname", strSocialUserLastName);
            }
            if ((socialUserName != null && socialUserName.length() > 0)) {
                StringBody strSocialUserName = new StringBody(socialUserName);
                reqEntity.addPart("username", strSocialUserName);
            }
            if ((socialUserEmail != null && socialUserEmail.length() > 0)) {
                StringBody strSocialUserEmail = new StringBody(socialUserEmail);
                reqEntity.addPart("email", strSocialUserEmail);
            }
            if ((socialUserGender != null && socialUserGender.length() > 0)) {
                StringBody strSocialUserGender = new StringBody(socialUserGender);
                reqEntity.addPart("gender", strSocialUserGender);
            }
            if ((socialUserDOB != null && socialUserDOB.length() > 0)) {
                StringBody strSocialUserDOB = new StringBody(socialUserDOB);
                reqEntity.addPart("dob", strSocialUserDOB);
            }

        } catch (Exception e) {
            System.out.println("err" + e);
        }
    }

    @Override
    public String httpPost() {
        String response = "";
        if (urlIndex == 0) {
            postLogin();
            response = APIAccess.openConnection(ServiceUrl.SABAKUCH_LOGIN, reqEntity);

        } else if (urlIndex == 1) {
            postForgotPass();
            response = APIAccess.openConnection(ServiceUrl.SABAKUCH_FORGOTPASS, reqEntity);
        } else if (urlIndex == 2) {
            postSocialLogin();
            response = APIAccess.openConnection(ServiceUrl.SABAKUCH_SOCIAL_LOGIN, reqEntity);
        }
        return response;
    }

    @Override
    public String httpAfterPost(String str) {
        if (str != null) {
            if (urlIndex == 0) {
                if (SabaKuchParse.jsonStatus(str) == 1) {
                    boolean state = getIntent().getBooleanExtra(Constants.STATE_MAINTAIN, false);

                    LoginData obj = SabaKuchParse.parseLoginData(str);
                    CommonUtils.saveStringPreferences(LoginActivity.this, Constants.USER_ID, obj.strUserId);
                    CommonUtils.saveStringPreferences(LoginActivity.this, Constants.USER_NAME, obj.strUserName);
                    CommonUtils.saveStringPreferences(LoginActivity.this, Constants.FNAME, obj.fname);
                    CommonUtils.saveStringPreferences(LoginActivity.this, Constants.SERVER_KEY, CommonUtils.ServerKey(obj.strUserId, obj.strUserName));
                    if (!state) {
                        intent = new Intent(context, MainActivity.class);
                        intent.putExtra("username",strUserName);
                        intent.putExtra("password",strPassword);
                        startActivity(intent);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();
                    }
                    CommonUtils.saveLoginPref(context, Constants.IS_LOGIN, true);
                    finish();
                } else {
                    if (SabaKuchParse.jsonErrorMessage(str) != null && SabaKuchParse.jsonErrorMessage(str).length() > 0)
                        Toast.makeText(LoginActivity.this, SabaKuchParse.jsonErrorMessage(str), Toast.LENGTH_SHORT).show();
                }
            } else if (urlIndex == 1) {
                if (SabaKuchParse.jsonStatus(str) == 0) {
                    Toast.makeText(LoginActivity.this, SabaKuchParse.jsonErrorMessage(str), Toast.LENGTH_SHORT).show();
                } else if (SabaKuchParse.jsonStatus(str) == 1) {
                    Toast.makeText(LoginActivity.this, SabaKuchParse.jsonErrorMessage(str), Toast.LENGTH_SHORT).show();
                }
            } else if (urlIndex == 2) {
                LoginData obj = SabaKuchParse.parseSocialLoginData(str);
                CommonUtils.saveStringPreferences(LoginActivity.this, Constants.USER_ID, obj.strUserId);
                CommonUtils.saveStringPreferences(LoginActivity.this, Constants.USER_NAME, obj.strUserName);
                if (obj.fname != null && !obj.fname.equals("")) {
                    CommonUtils.saveStringPreferences(LoginActivity.this, Constants.FNAME, obj.fname);
                } else {
                    CommonUtils.saveStringPreferences(LoginActivity.this, Constants.FNAME, obj.strUserName);
                }
                CommonUtils.saveStringPreferences(LoginActivity.this, Constants.SERVER_KEY, CommonUtils.ServerKey(obj.strUserId, obj.strUserName));
                CommonUtils.LogMsg(LoginActivity.class.getSimpleName(), "username: " + obj.strUserName);
                intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                CommonUtils.saveLoginPref(context, Constants.IS_LOGIN, true);
                finish();
            }
        }
        return null;
    }

    protected void onStart() {
        super.onStart();
    }

    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * Method to resolve any signin errors
     */
    private void resolveSignInError() {

        if (mConnectionResult != null && mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
                isGplusLogin = true;
                mGoogleApiClient.connect();
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            } catch (Exception e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
                    0).show();
            return;
        }

        if (!mIntentInProgress) {
            // Store the ConnectionResult for later usage
            mConnectionResult = result;

            if (mSignInClicked || !isGplusLogin) {
                // The user has already clicked 'sign-in' so we attempt to
                // resolve all
                // errors until the user is signed in, or they cancel.
                resolveSignInError();
            }
        }

    }

    @Override
    public void onConnected(Bundle arg0) {
        mSignInClicked = false;
    /*    Toast.makeText(this, "Connection established with google plus!",
                Toast.LENGTH_SHORT).show();*/

        // Get user's information
        getProfileInformation();

        // Update the UI after signin
        // updateUI(true);

    }

    /**
     * Fetching user's information name, email, profile pic
     */
    private void getProfileInformation() {
        try {
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                Person currentPerson = Plus.PeopleApi
                        .getCurrentPerson(mGoogleApiClient);
                socialUserName = currentPerson.getDisplayName();
                socialUserProfilePic = currentPerson.getImage().getUrl();
                socialUserFirstName = currentPerson.getName().getGivenName();
                socialUserLastName = currentPerson.getName().getFamilyName();
                socialUserEmail = Plus.AccountApi.getAccountName(mGoogleApiClient);
                socialUserGender = "" + currentPerson.getGender();
                socialUserId = currentPerson.getId();
                socialUserType = "g";
                socialUserSource="g_android_emock";
                userData = new User(socialUserType,socialUserSource,
                        socialUserName, socialUserFirstName, socialUserLastName, socialUserId, socialUserEmail, socialUserProfilePic, socialUserGender);

                CommonUtils.hideKeyboard(context);
                urlIndex = 2;
                APIAccess.fetchData(LoginActivity.this, LoginActivity.this, LoginActivity.this);

            } else {
                Toast.makeText(getApplicationContext(),
                        "Person information is null", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
        // updateUI(false);
    }

    /**
     * Sign-in into google
     */
    private void signInWithGplus() {

        if (!mGoogleApiClient.isConnecting()) {
            mSignInClicked = true;
            resolveSignInError();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {


            case RC_SIGN_IN:
                if (resultCode != RESULT_OK) {
                    mSignInClicked = false;
                }

                mIntentInProgress = false;

                if (!mGoogleApiClient.isConnecting()) {
                    mGoogleApiClient.connect();
                }
                break;
            default:
                callbackManager.onActivityResult(requestCode, resultCode, data);
                break;
        }

    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }
}

