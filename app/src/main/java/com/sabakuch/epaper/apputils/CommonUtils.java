package com.sabakuch.epaper.apputils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sabakuch.epaper.activity.LoginActivity;
import com.sabakuch.epaper.application.SabakuchEmockApplication;
import com.sabakuch.epaper.constants.Constants;
import com.sabakuch.epaper.data.Contact;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static java.util.Locale.getDefault;


public class CommonUtils {

    //private static Dialog dialog;
    public static final String PICK_DATE_FORMAT = "MM/dd/yyyy  hh:mm A";
    private static String IMAGE_DIRECTORY_NAME = "Sabakuch File Upload";
    private static String TAG = "CommonUtils Class";
//	private static PopupWindow popupWindow;


    public static void showInternetConnectionAlert(Context context) {

        //     Toast.makeText(context, context.getString(R.string.please_check_your_internet_connection), Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, String string) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }

    /**
     * Method for checking Internet Connection
     */

    public static boolean isOnline(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {

            return false;
        }
        return true;
    }

    /**
     * Method for checking text that is it a valid mail address or not
     */
    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target)
                    .matches();
        }
    }

    public static String getStringPreferences(Activity context, String key) {

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key, "");

    }

    public static boolean getBoolPreferences(Context context, String key) {
        SharedPreferences loginPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return loginPreferences.getBoolean(key, false);

    }

    public static String getStringPreferences(Context context, String key) {

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key, "");

    }

    public static void saveStringPreferences(Context context, String key,
                                             String value) {

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();

    }

    public static void savePreferences(Context context, String key, String value) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();

    }

    public static int getIntPreferences(Activity context, String key) {

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(key, 0);

    }

    public static int getIntPreferences(Context context, String key) {

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(key, 0);

    }

    public static String getPreferences(Activity context, String key) {

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key, "");

    }

    public static String getPreferences(Context context, String key) {

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key, "");

    }

    public static void saveIntPreferences(Context context, String key, int value) {

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();

    }

    public static void saveLoginPref(Context context, String key, boolean value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getLoginPreferences(Context context, String key) {

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(key, false);

    }

    /**
     * this method is used for hide the keyboard
     */

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null) {
            inputManager.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * Gets the app shared preference.
     *
     * @param aContext the a context
     * @return the app shared preference
     */

    public static SharedPreferences getAppSharedPreference(final Context aContext) {
        SharedPreferences sp = null;

        if (null != aContext) {
            sp = aContext.getSharedPreferences(Constants.APP_SHARED_PREF_NAME, Context.MODE_PRIVATE);
        }

        return sp;
    }


    /**
     * Gets the app shared preference.
     *
     * @return the app shared preference
     */
    public static SharedPreferences getAppSharedPreference() {
        return getAppSharedPreference(SabakuchEmockApplication.getAppInstance().getApplicationContext());
    }

    /**
     * Clear app shared preference.
     */
    public static void clearAppSharedPreference() {
        Context context = SabakuchEmockApplication.getAppInstance().getApplicationContext();
        SharedPreferences sp = getAppSharedPreference(context);

        if (null != sp) {
            SharedPreferences.Editor ed = sp.edit();
            ed.clear();
            ed.commit();
        }
    }

    public static String ServerKey(String strUserId, String strUserName) {

        String original = strUserId + "_" + strUserName + "_SBK";
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(original.getBytes());
            byte[] digest = md.digest();
            StringBuffer sb = new StringBuffer();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return " ";
        }
    }

    public static String getTimeDifference(String start_time, String end_time) {

        //DateTimeUtils obj = new DateTimeUtils();
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String timeTaken = null;
        try {
            //	2016-10-04 00:13:51 yyyy-MM-dd HH:mm:ss
            try {
                Date startTime = simpleDateFormat.parse(start_time);
                Date endTime = simpleDateFormat.parse(end_time);

                timeTaken = printDifference(startTime, endTime);

            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeTaken;
    }

    //1 minute = 60 seconds
    //1 hour = 60 x 60 = 3600
    //1 day = 3600 x 24 = 86400
    public static String printDifference(Date startDate, Date endDate) {
        //milliseconds
        long difference = endDate.getTime() - startDate.getTime();

        CommonUtils.LogMsg(TAG, "startDate : " + startDate);
        CommonUtils.LogMsg(TAG, "endDate : " + endDate);
        CommonUtils.LogMsg(TAG, "difference : " + difference);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = difference / daysInMilli;
        difference = difference % daysInMilli;

        long elapsedHours = difference / hoursInMilli;
        difference = difference % hoursInMilli;

        long elapsedMinutes = difference / minutesInMilli;
        difference = difference % minutesInMilli;

        long elapsedSeconds = difference / secondsInMilli;

        System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays,
                elapsedHours, elapsedMinutes, elapsedSeconds);

        //String timeTaken = "%d days, %d hours, %d minutes, %d seconds%n";

        String timeTaken = String.format("%02d:%02d:%02d", elapsedHours/*+":"+*/, elapsedMinutes/*+":"+*/, elapsedSeconds);
        CommonUtils.LogMsg("CommonUtils", "timeTaken" + timeTaken);
        return timeTaken;
    }

    public static String getFormatedDate(String examDate) {
        /*SimpleDateFormat originalFormat = new SimpleDateFormat("mm dd, yyyy");
	//	SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy MM dd HH:mm:ss" );
		Date date = null;
		try {
		//	Apr 23, 2016
			try {
				date = originalFormat.parse(examDate);
				System.out.println("Old Format :   " + date + " " +originalFormat.format(date));
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}

			//System.out.println("New Format :   " + targetFormat.format(date));

		} catch (ParseException ex) {
			// Handle Exception.
		}
	return date;*/


        Date initDate = null;
        try {
            initDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(examDate);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
        String parsedDate = formatter.format(initDate);
        CommonUtils.LogMsg(TAG, parsedDate);

//Log.e("initDate","initDate"+initDate);
        return parsedDate;
    }

    public static Uri getOutputMediaFileUri(int type, Activity activity) {
        return Uri.fromFile(getOutputMediaFile(type, activity));
    }



	/*public static void loginAlert(final Context context) {
		Intent intent = new Intent(context, LoginActivity.class);
		intent.putExtra(Constants.SCREEN_NAME,"loginAlert");
		context.startActivity(intent);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

	}*/







/*	public static void navigateToChannel(String user_id) {
		FriendEvent friendEvent= new FriendEvent();
		friendEvent.friendId=user_id;
		EventBus.getDefault().post(friendEvent);

		Fragment fragment = new AlbumFullViewFragment();
		Bundle bundle = new Bundle();
		bundle.putString(Constants.FRIEND_USER_ID,user_id);
		fragment.setArguments(bundle);
		setFragment(fragment);
	}*/

	/*public static void navigateToChannel(Activity context) {
		final Intent intent = new Intent(context, SearchActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		intent.setAction(Constants.NAVIGATE_SEARCH);
		context.startActivity(intent);
	}*/

/*	public static void loginAlert(final Activity context) {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage("Login to access this feature.")*//*.setCancelable(false).setTitle(title)*//*

				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
							Intent intent = new Intent(context, LoginActivity.class);
							context.startActivity(intent);
							intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					}

				}).setNegativeButton("No", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int id) {

			}
		});

		try {
			builder.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}*/

    private static File getOutputMediaFile(int type, Activity activity) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                getDefault()).format(new Date());
        File mediaFile;
	/*	if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ strUserID + timeStamp + ".jpg");}

		else {
			return null;
		}*/
        String strUserID = CommonUtils.getStringPreferences(activity, Constants.USER_ID);
        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + strUserID + timeStamp + ".jpg");

        return mediaFile;
    }
	/*public static Bitmap getBitmapFromURL(final String src) {
		try {
			URL url = new URL(src);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(input);
			return myBitmap;
		} catch (IOException e) {
			// Log exception
			return null;
		}
	}*/

    /*public static Bitmap getBitmapFromURL(final String src,Context context) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        String fname=new File(context.getFilesDir(), src).getAbsolutePath();
        Bitmap myBitmap = BitmapFactory.decodeFile(fname,options);
        return myBitmap;
    }*/
    public static String getExtension(String fileName) {
        String filename = fileName;
        String filenameArray[] = filename.split("\\.");
        String extension = filenameArray[filenameArray.length - 1];
        System.out.println(extension);
        return extension;
    }

    /**
     * Gets the corresponding path to a file from the given content:// URI
     *
     * @param selectedVideoUri The content:// URI to find the file path from
     * @param contentResolver  The content resolver to use to perform the query.
     * @return the file path as a string
     */
    private static String getFilePathFromContentUri(Uri selectedVideoUri,
                                                    ContentResolver contentResolver) {
        String filePath;
        String[] filePathColumn = {MediaStore.MediaColumns.DATA};

        Cursor cursor = contentResolver.query(selectedVideoUri, filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        filePath = cursor.getString(columnIndex);
        cursor.close();
        return filePath;
    }

    public static String getSelectedVideo(Uri audioUri, Activity context) {
        //	Uri audioUri;

        //Selected image returned from another activity
        // A parameter I pass myself to know whether or not I'm being "shared via" or
        // whether I'm working internally to my app (fromData = working internally)

        //	audioUri = imageReturnedIntent.getData();


        CommonUtils.LogMsg(TAG, "SelectedVideoUri = " + audioUri);

        String filePath;

        String scheme = audioUri.getScheme();
        ContentResolver contentResolver = context.getContentResolver();
        long videoId;

        // If we are sent file://something or content://org.openintents.filemanager/mimetype/something...
        if (scheme.equals("file") || (scheme.equals("content") && audioUri.getEncodedAuthority().equals("org.openintents.filemanager"))) {

            // Get the path
            filePath = audioUri.getPath();

            // Trim the path if necessary
            // openintents filemanager returns content://org.openintents.filemanager/mimetype//mnt/sdcard/xxxx.mp4
            if (filePath.startsWith("/mimetype/")) {
                String trimmedFilePath = filePath.substring("/mimetype/".length());
                filePath = trimmedFilePath.substring(trimmedFilePath.indexOf("/"));
            }

            // Get the video ID from the path
            //	videoId = getVideoIdFromFilePath(filePath, contentResolver);

        } else if (scheme.equals("content")) {

            // If we are given another content:// URI, look it up in the media provider
            videoId = Long.valueOf(audioUri.getLastPathSegment());
            filePath = getFilePathFromContentUri(audioUri, contentResolver);

        } else {
            CommonUtils.LogMsg(TAG, "Failed to load URI " + audioUri.toString());
            return "";
        }
        return filePath;
    }

    // twitter image decoding
    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bmp = getEncodedImageString(input);
            return bmp;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;

    }

    /**
     * This code is used for changing Image file to Encoded Image string
     */

    public static Bitmap getEncodedImageString(InputStream input) {

        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inSampleSize = calculateInSampleSize(options, 300, 300);
        options.inPurgeable = true;
        Bitmap bm = BitmapFactory.decodeStream(input, null, options);


        return bm;

    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 2;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }


        return inSampleSize;
    }

    public static CharSequence stripHtml(String s) {
        return Html.fromHtml(s).toString()/*.replace('\n', (char) 32)*/
                .replace((char) 160, (char) 32).replace((char) 65532, (char) 32).trim();
    }

    public static void loginAlertDialog(final Activity context, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(context, LoginActivity.class);
                intent.putExtra(Constants.STATE_MAINTAIN, true);
                context.startActivity(intent);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //context.finish();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static Gson getCustomGson() {

        return new GsonBuilder().create();
    }

    public static void LogMsg(String TAG, String msg) {
        Log.e(TAG, msg);
    }

    public static boolean appInstalledOrNot(String uri, Context ctx) {
        PackageManager pm = ctx.getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }

    public static Typeface getCustomTypeFace(Context ctx) {
        return FontManager.getTypeface(ctx, FontManager.FONTAWESOME);
    }

    public static void storeContact(Context context,  ArrayList<Contact> favorites) {
// used for store arrayList in json format
        SharedPreferences settings;
        Editor editor;
        settings = context.getSharedPreferences(Constants.PREFS_NAME,Context.MODE_PRIVATE);
        editor = settings.edit();
        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);
        editor.putString(Constants.FAVORITES, jsonFavorites);
        editor.commit();
    }

    public static ArrayList<Contact> loadContact(Context context) {
// used for retrieving arraylist from json formatted string
        SharedPreferences settings;
        List favorites;
        settings = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
		if (settings.contains(Constants.FAVORITES)) {
			String jsonFavorites = settings.getString(Constants.FAVORITES, null);
        Gson gson = new Gson();
        Contact[] favoriteItems = gson.fromJson(jsonFavorites, Contact[].class);
        favorites = Arrays.asList(favoriteItems);
        favorites = new ArrayList(favorites);
		} else
			return null;
        return (ArrayList<Contact>) favorites;
    }

    /*public static String getRealPathFromURI(Uri contentUri, Activity context) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(context, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }*/
    private boolean getSelectedVideo(Intent imageReturnedIntent, boolean fromData, Activity context) {

        Uri audioUri;

        //Selected image returned from another activity
        // A parameter I pass myself to know whether or not I'm being "shared via" or
        // whether I'm working internally to my app (fromData = working internally)

        audioUri = imageReturnedIntent.getData();


        Log.d(TAG, "SelectedVideoUri = " + audioUri);

        String filePath;

        String scheme = audioUri.getScheme();
        ContentResolver contentResolver = context.getContentResolver();
        long videoId;

        // If we are sent file://something or content://org.openintents.filemanager/mimetype/something...
        if (scheme.equals("file") || (scheme.equals("content") && audioUri.getEncodedAuthority().equals("org.openintents.filemanager"))) {

            // Get the path
            filePath = audioUri.getPath();

            // Trim the path if necessary
            // openintents filemanager returns content://org.openintents.filemanager/mimetype//mnt/sdcard/xxxx.mp4
            if (filePath.startsWith("/mimetype/")) {
                String trimmedFilePath = filePath.substring("/mimetype/".length());
                filePath = trimmedFilePath.substring(trimmedFilePath.indexOf("/"));
            }

            // Get the video ID from the path
            videoId = getVideoIdFromFilePath(filePath, contentResolver);

        } else if (scheme.equals("content")) {

            // If we are given another content:// URI, look it up in the media provider
            videoId = Long.valueOf(audioUri.getLastPathSegment());
            filePath = getFilePathFromContentUri(audioUri, contentResolver);

        } else {
            Log.d(TAG, "Failed to load URI " + audioUri.toString());
            return false;
        }

        return true;
    }

    /**
     * Gets the MediaStore video ID of a given file on external storage
     *
     * @param filePath        The path (on external storage) of the file to resolve the ID of
     * @param contentResolver The content resolver to use to perform the query.
     * @return the video ID as a long
     */
    private long getVideoIdFromFilePath(String filePath,
                                        ContentResolver contentResolver) {


        long videoId;
        Log.d(TAG, "Loading file " + filePath);

        // This returns us content://media/external/videos/media (or something like that)
        // I pass in "external" because that's the MediaStore's name for the external
        // storage on my device (the other possibility is "internal")
        Uri videosUri = MediaStore.Video.Media.getContentUri("external");

        Log.d(TAG, "videosUri = " + videosUri.toString());

        String[] projection = {MediaStore.Video.VideoColumns._ID};

        // TODO This will break if we have no matching item in the MediaStore.
        Cursor cursor = contentResolver.query(videosUri, projection, MediaStore.Video.VideoColumns.DATA + " LIKE ?", new String[]{filePath}, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(projection[0]);
        videoId = cursor.getLong(columnIndex);

        CommonUtils.LogMsg(TAG, "Video ID is " + videoId);
        cursor.close();
        return videoId;
    }

    public enum IdType {
        NA(0),
        Artist(1),
        Album(2),
        Playlist(3);

        public final int mId;

        IdType(final int id) {
            mId = id;
        }

        public static IdType getTypeById(int id) {
            for (IdType type : values()) {
                if (type.mId == id) {
                    return type;
                }
            }

            throw new IllegalArgumentException("Unrecognized id: " + id);
        }
    }

    public static void setTracking(String name){
        // Obtain the shared Tracker instance.

        Tracker mTracker = SabakuchEmockApplication.getDefaultTracker();
        LogMsg(TAG, "Setting screen name: " + name);
        mTracker.setScreenName("Name~" + name);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
}
