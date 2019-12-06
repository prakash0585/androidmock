package com.sabakuch.epaper.apputils;


public class ServiceUrl {


    public static final String SERVER_BASE_URL = "https://sabakuch.com/m/api/";
    //public static final String SERVER_BASE_MUSIC_URL = "http://192.168.2.10/sbk/music/";
    public static final String SERVER_BASE_MUSIC_URL = "https://sabakuch.com/music/";
    public static final String SERVER_BASE_CONTACT = SERVER_BASE_MUSIC_URL + "apicontact";
    public static final String SERVER_BASE_TEST_PAPER_URL = "https://sabakuch.com/mock_paper/api/";
    public static final String SABAKUCH_SIGNUP = SERVER_BASE_URL + "signupmobile/";
    public static final String SABAKUCH_LOGIN = SERVER_BASE_URL + "login/";
    public static final String SABAKUCH_FORGOTPASS = SERVER_BASE_URL + "forgot/";
    public static final String SABAKUCH_PROFILE_SETTINGS = SERVER_BASE_URL + "profile/";
    public static final String SABAKUCH_PASSWORD_CHANGE_SETTING = SERVER_BASE_URL + "profilepass/";
    public static final String SABAKUCH_MOBILE_ACTIVE = SERVER_BASE_URL + "profilemobile/";

    public static final String SABAKUCH_REFERRAL = SERVER_BASE_MUSIC_URL + "apipromotion";
    public static final String SABAKUCH_WORLDRECORD = SERVER_BASE_MUSIC_URL + "apiworldrecord?";

    public static final String SABAKUCH_CountryList = SERVER_BASE_URL + "worldrecord/get?country=1";
    public static final String SABAKUCH_StateList = SERVER_BASE_URL + "worldrecord/get?state=1&countryid=";
    public static final String SABAKUCH_CityList = SERVER_BASE_URL + "worldrecord/get?city=1&stateid=";

    public static final String SABAKUCH_GENERAL_SETTING_COUNTRY = "http://sabakuch.com/public/json/country.json";
    public static final String SABAKUCH_GENERAL_SETTING_CITY = "http://sabakuch.com/public/json/city.json";
    public static final String SABAKUCH_GENERAL_SETTING_TIMEZONE = "http://m.sabakuch.com/public/json/timezone.json";
    public static final String SABAKUCH_SOCIAL_LOGIN = SERVER_BASE_MUSIC_URL + "apisociallogin";
    public static final String SABAKUCH_EXAM_LIST = SERVER_BASE_TEST_PAPER_URL + "exam_list";
    public static final String SABAKUCH_LEVEL_LIST = SERVER_BASE_TEST_PAPER_URL + "level_list";
    public static final String SABAKUCH_INSTRUCTIONS = SERVER_BASE_TEST_PAPER_URL + "instructions";
    public static final String SABAKUCH_USER_DETAIL = SERVER_BASE_TEST_PAPER_URL + "userdetail";
    public static final String SABAKUCH_SECTION_DETAIL = SERVER_BASE_TEST_PAPER_URL + "sectiondetail";
    public static final String SABAKUCH_PAPER_DETAIL = SERVER_BASE_TEST_PAPER_URL + "paperdetail";
    public static final String SABAKUCH_PAPER_QUESTIONS = SERVER_BASE_TEST_PAPER_URL + "questions";
    public static final String SABAKUCH_PAPER_ANSWERS = SERVER_BASE_TEST_PAPER_URL + "answers";
    public static final String SABAKUCH_PAPER_REVIEW = SERVER_BASE_TEST_PAPER_URL + "testreview";
    public static final String SABAKUCH_PAPER_PASSAGE = SERVER_BASE_TEST_PAPER_URL + "passage";
    public static final String SABAKUCH_PAPER_MY_DASHBOARD = SERVER_BASE_TEST_PAPER_URL + "dashboard";
    public static final String SABAKUCH_CONTACT = SERVER_BASE_URL + "contact/";
    public static final String SABAKUCH_SOLUTION = SERVER_BASE_TEST_PAPER_URL + "exam_list?solution=1";
    public static final String SABAKUCH_SOLUTION_SET = SERVER_BASE_TEST_PAPER_URL + "exam_list?solution=1&set=1&solution_id=";
    //	https://sabakuch.com/mock_paper/api/exam_list?solution=1&answer=1&set_id=1&solution_id=1
    public static final String SABAKUCH_SOLUTION_SET_ANSWER = SERVER_BASE_TEST_PAPER_URL + "exam_list?solution=1&answer=1";
    //	https://sabakuch.com/mock_paper/api/exam_list?solution=1&sol_pdf=1&set_id=1&solution_id=1
    public static final String SABAKUCH_SOLUTION_SET_SOLUTION = SERVER_BASE_TEST_PAPER_URL + "exam_list?solution=1&sol_pdf=1";


}


