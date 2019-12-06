package com.sabakuch.epaper.apputils;

import android.util.Log;

import com.sabakuch.epaper.data.Country;
import com.sabakuch.epaper.data.DashboardData;
import com.sabakuch.epaper.data.GeneralEducationData;
import com.sabakuch.epaper.data.GeneralSettingData;
import com.sabakuch.epaper.data.InstructionsData;
import com.sabakuch.epaper.data.LevelDetailData;
import com.sabakuch.epaper.data.LevelsData;
import com.sabakuch.epaper.data.LoginData;
import com.sabakuch.epaper.data.PaperDetailData;
import com.sabakuch.epaper.data.SectionDetailData;
import com.sabakuch.epaper.data.SelectExamsData;
import com.sabakuch.epaper.data.SignupData;
import com.sabakuch.epaper.data.TestPaperData;
import com.sabakuch.epaper.data.TestResultData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SabaKuchParse {

    public static int jsonStatus(String res) {
        try {
            JSONObject jsonObj = new JSONObject(res);
            return jsonObj.getInt("success");

        } catch (JSONException e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
            return 2;
        }
    }

    // social login status
    public static int jsonStatusSocialLogin(String res) {
        try {
            JSONObject jsonObj = new JSONObject(res);
            return jsonObj.getJSONObject("user").getInt("success");

        } catch (JSONException e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
            return 2;
        }
    }


    public static String jsonErrorMessage(String res) {
        try {
            JSONObject jsonObj = new JSONObject(res);
            return jsonObj.getString("message");
        } catch (JSONException e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
            return " ";
        }
    }

    public static String jsonErrorMessageSocialLogin(String res) {
        try {
            JSONObject jsonObj = new JSONObject(res);
            return jsonObj.getJSONObject("user").getString("message");
        } catch (JSONException e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
            return " ";
        }
    }

    public static LoginData parseSocialLoginData(String res) {
        LoginData obj = new LoginData();

        try {
            JSONObject jsonObj = new JSONObject(res);

            try {
                obj.strUserId = jsonObj.getJSONObject("user").getString("userid");
            } catch (Exception e) {

            }
            try {
                obj.strUserName = jsonObj.getJSONObject("user").getString("username");
            } catch (Exception e) {

            }
            try {
                obj.strMessage = jsonObj.getJSONObject("user").getString("message");
            } catch (Exception e) {

            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
        }
        return obj;
    }

    public static int parsePageCount(String str) {
        try {
            JSONObject jsonObj = new JSONObject(str);
            return (jsonObj.getInt("page_count"));
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getResponseCode(String str) {
        try {
            JSONObject jsonObj = new JSONObject(str);
            return (jsonObj.getString("status"));
        } catch (JSONException e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
            return " ";
        }
    }


    public static String getLogoutResponse(String str) {
        try {
            JSONObject jsonObj = new JSONObject(str);
            return (jsonObj.getString("message"));
        } catch (JSONException e) {

            e.printStackTrace();
            return " ";
        }
    }

    public static LoginData parseLoginData(String res) {
        LoginData obj = new LoginData();

        try {
            JSONObject jsonObj = new JSONObject(res);

            try {
                obj.strUserId = jsonObj.getString("userid");
            } catch (Exception e) {

            }
            try {
                obj.strUserName = jsonObj.getString("username");
            } catch (Exception e) {

            }
            try {
                obj.strMessage = jsonObj.getString("message");
            } catch (Exception e) {

            }

            try {
                obj.fname = jsonObj.getString("fname");
            } catch (Exception e) {

            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
        }
        return obj;
    }

    public static SignupData parseSignupdata(String res) {
        SignupData obj = new SignupData();

        try {
            JSONObject jsonObj = new JSONObject(res);

            try {
                obj.strUserid = jsonObj.getString("userid");
            } catch (Exception e) {

            }


        } catch (JSONException e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
        }
        return obj;
    }

    public static ArrayList<Country> parseCountryData(String stri) {
        // TODO Auto-generated method stub

        JSONArray jsonArray;
        ArrayList<Country> arry = new ArrayList<Country>();

        try {

            JSONObject jsonObj = new JSONObject(stri);
            jsonArray = jsonObj.getJSONArray("country");

            System.out.println("size of country" + jsonArray.length());

            for (int i = 0; i < jsonArray.length(); i++) {

                Country obj = new Country();
                JSONObject jsonInnerObj = jsonArray.getJSONObject(i);

                try {
                    obj.strName = jsonInnerObj.getString("name");
                } catch (Exception e) {

                }
                try {
                    obj.strCountryid = jsonInnerObj.getString("countryid");
                } catch (Exception e) {

                }

                arry.add(obj);
            }

        } catch (Exception e) {

        }

        return arry;
    }


	/*public static ProfileSettingData parseProfileData(String strg) {
        // TODO Auto-generated method stub


		ProfileSettingData obj=new ProfileSettingData();
		JSONArray jsonArray;

		try {
			JSONObject jsonObj=new JSONObject(strg);

			try
			{
				obj.strFname=jsonObj.getJSONArray("general").getJSONObject(0).getString("fname");
			}catch(Exception e)
			{

			}
			try
			{
				obj.strlname=jsonObj.getJSONArray("general").getJSONObject(0).getString("lname");
			}catch(Exception e)
			{

			}
			try
			{
				obj.strUsername=jsonObj.getJSONArray("general").getJSONObject(0).getString("username");
			}catch(Exception e)
			{

			}
			try
			{
				obj.strEmail=jsonObj.getJSONArray("general").getJSONObject(0).getString("email");
			}catch(Exception e)
			{

			}
			try
			{
				obj.strContact=jsonObj.getJSONArray("general").getJSONObject(0).getString("contact");
			}catch(Exception e)
			{

			}
			try
			{
				obj.strDob=jsonObj.getJSONArray("general").getJSONObject(0).getString("dob");
			}catch(Exception e)
			{
			}
			try
			{
				obj.strGender=jsonObj.getJSONArray("general").getJSONObject(0).getString("gender");
			}catch(Exception e)
			{

			}
			try
			{
				obj.strTime_zone=jsonObj.getJSONArray("general").getJSONObject(0).getString("time_zone");
			}catch(Exception e)
			{

			}
			try
			{
				obj.strSignup_step=jsonObj.getJSONArray("general").getJSONObject(0).getString("signup_step");
			}catch(Exception e)
			{
			}
			try
			{
				obj.strDateBirthEnable=jsonObj.getJSONArray("general").getJSONObject(0).getString("date_birth_enable");
			}catch(Exception e)
			{

			}


			try
			{
				obj.strAddress1=jsonObj.getJSONArray("address").getJSONObject(0).getString("address1");
			}catch(Exception e)
			{

			}
			try
			{
				obj.strAddress2=jsonObj.getJSONArray("address").getJSONObject(0).getString("address2");
			}catch(Exception e)
			{

			}
			try
			{
				obj.strCountry_id=jsonObj.getJSONArray("address").getJSONObject(0).getString("country_id");
			}catch(Exception e)
			{

			}

			try{
				obj.strCity_id = jsonObj.getJSONArray("address").getJSONObject(0).getString("city_id");
			}catch(Exception e){

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}*/

    public static ArrayList<Country> parseCityData(String stri) {
        // TODO Auto-generated method stub

        JSONArray jsonArray;
        ArrayList<Country> arry = new ArrayList<Country>();

        try {

            JSONObject jsonObj = new JSONObject(stri);
            jsonArray = jsonObj.getJSONArray("city");

            System.out.println("size of country" + jsonArray.length());

            for (int i = 0; i < jsonArray.length(); i++) {

                Country obj = new Country();
                JSONObject jsonInnerObj = jsonArray.getJSONObject(i);

                try {
                    obj.strCityname = jsonInnerObj.getString("cityname");
                } catch (Exception e) {

                }
                try {
                    obj.strCountryid = jsonInnerObj.getString("countryid");
                } catch (Exception e) {

                }

                try {

                    obj.strCityid = jsonInnerObj.getString("cityid");

                } catch (Exception e) {

                }

                arry.add(obj);
            }
        } catch (Exception e) {

        }

        return arry;
    }

    public static ArrayList<Country> parseTimeData(String strii) {
        // TODO Auto-generated method stub

        JSONArray jsonArray;
        ArrayList<Country> arry = new ArrayList<Country>();


        try {

            JSONObject jsonObj = new JSONObject(strii);
            jsonArray = jsonObj.getJSONArray("time");

            System.out.println("size of time" + jsonArray.length());

            for (int i = 0; i < jsonArray.length(); i++) {

                Country obj = new Country();
                JSONObject jsonInnerObj = jsonArray.getJSONObject(i);

                try {
                    obj.strTimezoneid = jsonInnerObj.getString("timezoneid");
                } catch (Exception e) {

                }
                try {
                    obj.strTimezonename = jsonInnerObj.getString("timezonename");
                } catch (Exception e) {

                }

                try {

                    obj.strCountryname = jsonInnerObj.getString("countryname");


                } catch (Exception e) {

                }
                arry.add(obj);
            }

        } catch (Exception e) {

        }

        return arry;
    }

    public static GeneralSettingData parseProfileData(String strg) {
        // TODO Auto-generated method stub


        GeneralSettingData obj = new GeneralSettingData();
        JSONArray jsonArray;

        try {
            JSONObject jsonObj = new JSONObject(strg);

            obj.arr = new ArrayList<GeneralEducationData>();

            try {
                obj.strFname = jsonObj.getJSONArray("general").getJSONObject(0).getString("fname");
            } catch (Exception e) {

            }
            try {
                obj.strlname = jsonObj.getJSONArray("general").getJSONObject(0).getString("lname");
            } catch (Exception e) {

            }
            try {
                obj.strUsername = jsonObj.getJSONArray("general").getJSONObject(0).getString("username");
            } catch (Exception e) {

            }
            try {
                obj.strEmail = jsonObj.getJSONArray("general").getJSONObject(0).getString("email");
            } catch (Exception e) {

            }
            try {
                obj.strContact = jsonObj.getJSONArray("general").getJSONObject(0).getString("contact");
            } catch (Exception e) {

            }
            try {
                obj.strDob = jsonObj.getJSONArray("general").getJSONObject(0).getString("dob");
            } catch (Exception e) {
            }
            try {
                obj.strGender = jsonObj.getJSONArray("general").getJSONObject(0).getString("gender");
            } catch (Exception e) {

            }
            try {
                obj.strTime_zone = jsonObj.getJSONArray("general").getJSONObject(0).getString("time_zone");
            } catch (Exception e) {

            }
            try {
                obj.strSignup_step = jsonObj.getJSONArray("general").getJSONObject(0).getString("signup_step");
            } catch (Exception e) {

            }

            try {
                obj.strDateBirthEnable = jsonObj.getJSONArray("general").getJSONObject(0).getString("date_birth_enable");
            } catch (Exception e) {

            }


            try {
                obj.strAddress1 = jsonObj.getJSONArray("address").getJSONObject(0).getString("address1");
            } catch (Exception e) {

            }
            try {
                obj.strAddress2 = jsonObj.getJSONArray("address").getJSONObject(0).getString("address2");
            } catch (Exception e) {

            }
            try {
                obj.strCountry_id = jsonObj.getJSONArray("address").getJSONObject(0).getString("country_id");
            } catch (Exception e) {

            }

            try {
                obj.strStateId = jsonObj.getJSONArray("address").getJSONObject(0).getString("state_id");
            } catch (Exception e) {

            }

            try {
                obj.strCity_id = jsonObj.getJSONArray("address").getJSONObject(0).getString("city_id");
            } catch (Exception e) {

            }


            for (int i = 0; i < jsonObj.getJSONArray("education").length(); i++) {
                GeneralEducationData eObj = new GeneralEducationData();
                try {
                    eObj.strEducation_id = jsonObj.getJSONArray("education").getJSONObject(i).getString("education_id");
                } catch (Exception e) {

                }

                try {
                    eObj.strUser_id = jsonObj.getJSONArray("education").getJSONObject(i).getString("user_id");
                } catch (Exception e) {

                }

                try {
                    eObj.strFrom_date = jsonObj.getJSONArray("education").getJSONObject(i).getString("from_date");
                } catch (Exception e) {

                }

                try {
                    eObj.strTo_date = jsonObj.getJSONArray("education").getJSONObject(i).getString("to_date");
                } catch (Exception e) {

                }

                try {
                    eObj.strDegree = jsonObj.getJSONArray("education").getJSONObject(i).getString("degree");
                } catch (Exception e) {

                }

                try {
                    eObj.strField_study = jsonObj.getJSONArray("education").getJSONObject(i).getString("field_study");
                } catch (Exception e) {

                }

                try {
                    eObj.strSchool = jsonObj.getJSONArray("education").getJSONObject(i).getString("school");
                } catch (Exception e) {

                }

                try {
                    eObj.strGrade = jsonObj.getJSONArray("education").getJSONObject(i).getString("grade");
                } catch (Exception e) {

                }


                obj.arr.add(eObj);

            }


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        return obj;
    }

    //Select Exams Data
    public static ArrayList<SelectExamsData> parseExamsData(String str) {
        JSONArray jsonArray;
        ArrayList<SelectExamsData> arr = new ArrayList<SelectExamsData>();
        try {
            JSONObject jsonObj = new JSONObject(str);
            jsonArray = jsonObj.getJSONObject("_embedded").getJSONArray("exam_list");

            System.out.println("size of array" + jsonArray.length());

            for (int i = 0; i < jsonArray.length(); i++) {

                SelectExamsData obj = new SelectExamsData();
                JSONObject jsonInnerObj = jsonArray.getJSONObject(i);
                try {
                    obj.exam_id = jsonInnerObj.getString("exam_id");
                } catch (Exception e) {
                }
                try {
                    obj.is_premium = jsonInnerObj.getString("is_premium");
                } catch (Exception e) {
                }

                try {
                    obj.is_user_pre_exam = jsonInnerObj.getString("is_user_pre_exam");
                } catch (Exception e) {
                }
                try {
                    obj.is_custom = jsonInnerObj.getString("is_custom");
                } catch (Exception e) {
                }
                try {
                    obj.is_user_custom_exam = jsonInnerObj.getString("is_user_custom_exam");
                } catch (Exception e) {
                }
                try {
                    obj.exam_name = jsonInnerObj.getString("exam_name");
                } catch (Exception e) {
                }

                try {
                    obj.slug = jsonInnerObj.getString("slug");
                } catch (Exception e) {
                }
                try {
                    obj.examimage = jsonInnerObj.getString("examimage");
                } catch (Exception e) {
                }

                try {
                    obj.set_id = jsonInnerObj.getString("set_id");
                } catch (Exception e) {
                }

                try {
                    obj.set_name = jsonInnerObj.getString("set_name");
                } catch (Exception e) {
                }

                try {
                    obj.pdfname = jsonInnerObj.getString("pdfname");
                } catch (Exception e) {
                }

                arr.add(obj);
            }

        } catch (Exception e) {
        }
        return arr;
    }

    //Select Solution Data
    public static SelectExamsData parseSolutionData(String str) {

        JSONArray jsonArray;
        ArrayList<SelectExamsData> arr = new ArrayList<SelectExamsData>();
        SelectExamsData obj = new SelectExamsData();
        try {
            JSONObject jsonObj = new JSONObject(str);
            jsonArray = jsonObj.getJSONObject("_embedded").getJSONArray("exam_list");

            System.out.println("size of array" + jsonArray.length());

            for (int i = 0; i < jsonArray.length(); i++) {


                JSONObject jsonInnerObj = jsonArray.getJSONObject(i);
                try {
                    obj.solution_id = jsonInnerObj.getString("solution_id");
                } catch (Exception e) {
                }
                try {
                    obj.solution_name = jsonInnerObj.getString("solution_name");
                } catch (Exception e) {
                }
                try {
                    obj.solution_image = jsonInnerObj.getString("solution_image");
                } catch (Exception e) {
                }


                arr.add(obj);
            }

        } catch (Exception e) {
        }
        return obj;
    }

    //Select Levels Data
    public static ArrayList<LevelsData> parseLevelsData(String str) {
        JSONArray jsonArray;
        ArrayList<LevelsData> arr = new ArrayList<LevelsData>();
        try {
            JSONObject jsonObj = new JSONObject(str);
            jsonArray = jsonObj.getJSONObject("_embedded").getJSONArray("level_list");

            System.out.println("size of array" + jsonArray.length());

            for (int i = 0; i < jsonArray.length(); i++) {

                LevelsData obj = new LevelsData();

                JSONObject jsonInnerObj = jsonArray.getJSONObject(i);

                try {
                    obj.level_id = jsonInnerObj.getString("level_id");
                } catch (Exception e) {
                }
                try {
                    obj.name = jsonInnerObj.getString("name");
                } catch (Exception e) {
                }
                try {
                    obj.image = jsonInnerObj.getString("image");
                } catch (Exception e) {
                }
                try {
                    obj.level_text = jsonInnerObj.getString("level_text");
                } catch (Exception e) {
                }
                try {
                    obj.is_user_pre_exam = jsonInnerObj.getString("is_user_pre_exam");
                } catch (Exception e) {
                }
                try {
                    obj.is_premium = jsonInnerObj.getString("is_premium");
                } catch (Exception e) {
                }
                try {
                    obj.check_exam_level = jsonInnerObj.getString("check_exam_level");
                } catch (Exception e) {
                }
                try {
                    obj.check_customise_exam = jsonInnerObj.getString("check_customise_exam");
                } catch (Exception e) {
                }
                try {
                    obj.is_user_custom_exam = jsonInnerObj.getString("is_user_custom_exam");
                } catch (Exception e) {
                }
                arr.add(obj);
            }

        } catch (Exception e) {

        }
        return arr;
    }

    //Online Test Paper Data
    public static ArrayList<TestPaperData> parseTestPaperData(String str) {
        JSONArray jsonArray;
        ArrayList<TestPaperData> arr = new ArrayList<TestPaperData>();
        try {
            JSONObject jsonObj = new JSONObject(str);
            jsonArray = jsonObj.getJSONObject("_embedded").getJSONArray("questions");

            System.out.println("size of array" + jsonArray.length());

            for (int i = 0; i < jsonArray.length(); i++) {

                TestPaperData obj = new TestPaperData();

                JSONObject jsonInnerObj = jsonArray.getJSONObject(i);

                try {
                    obj.qb_id = jsonInnerObj.getString("qb_id");
                } catch (Exception e) {
                }
                try {
                    obj.question = jsonInnerObj.getString("question");
                } catch (Exception e) {
                }
                try {
                    obj.option_a = jsonInnerObj.getString("option_a");
                } catch (Exception e) {
                }
                try {
                    obj.option_b = jsonInnerObj.getString("option_b");
                } catch (Exception e) {
                }
                try {
                    obj.option_c = jsonInnerObj.getString("option_c");
                } catch (Exception e) {
                }
                try {
                    obj.option_d = jsonInnerObj.getString("option_d");
                } catch (Exception e) {
                }
                try {
                    obj.assign_id = jsonInnerObj.getString("assign_id");
                } catch (Exception e) {
                }

                arr.add(obj);
            }

        } catch (Exception e) {

        }
        return arr;
    }

    //Instructions Data
    public static ArrayList<InstructionsData> parseInstructionsData(String str) {
        JSONArray jsonArray;
        ArrayList<InstructionsData> arr = new ArrayList<InstructionsData>();
        try {
            JSONObject jsonObj = new JSONObject(str);
            jsonArray = jsonObj.getJSONObject("_embedded").getJSONArray("instructions");

            System.out.println("size of array" + jsonArray.length());

            for (int i = 0; i < jsonArray.length(); i++) {

                InstructionsData obj = new InstructionsData();

                JSONObject jsonInnerObj = jsonArray.getJSONObject(i);

                try {
                    obj.level_id = jsonInnerObj.getString("level_id");
                } catch (Exception e) {
                }
                try {
                    obj.paper_id = jsonInnerObj.getString("paper_id");
                } catch (Exception e) {
                }
                try {
                    obj.instructions_id = jsonInnerObj.getString("instructions_id");
                } catch (Exception e) {
                }
                try {
                    obj.instruction = jsonInnerObj.getString("instruction");
                } catch (Exception e) {
                }

                arr.add(obj);
            }

        } catch (Exception e) {

        }
        return arr;
    }

    //Section Detail Data
    public static ArrayList<SectionDetailData> parseSectionDetailData(String str) {
        JSONArray jsonArray;
        ArrayList<SectionDetailData> arr = new ArrayList<SectionDetailData>();
        try {
            JSONObject jsonObj = new JSONObject(str);
            jsonArray = jsonObj.getJSONObject("_embedded").getJSONArray("sectiondetail");

            System.out.println("size of array" + jsonArray.length());

            for (int i = 0; i < jsonArray.length(); i++) {

                SectionDetailData obj = new SectionDetailData();

                JSONObject jsonInnerObj = jsonArray.getJSONObject(i);

				/*try
                {
					obj.is_section=jsonInnerObj.getString("is_section");
				}catch(Exception e)
				{
				}*/
                try {
                    obj.sectionid = jsonInnerObj.getString("sectionid");
                } catch (Exception e) {
                }
                try {
                    obj.sec_name = jsonInnerObj.getString("sec_name");
                } catch (Exception e) {
                }

                try {
                    obj.part_id = jsonInnerObj.getString("part_id");
                } catch (Exception e) {
                }
                try {
                    obj.name = jsonInnerObj.getString("name");
                } catch (Exception e) {
                }


			/*	try
                {
					obj.paper_id=jsonInnerObj.getString("paper_id");
				}catch(Exception e)
				{
				}
				try
				{
					obj.instructions_id=jsonInnerObj.getString("instructions_id");
				}catch(Exception e)
				{
				}
				try
				{
					obj.instruction=jsonInnerObj.getString("instruction");
				}catch(Exception e)
				{
				}*/

                arr.add(obj);
            }

        } catch (Exception e) {

        }
        return arr;
    }

    //Paper Detail Data
    public static ArrayList<PaperDetailData> parsePaperDetailData(String str) {
        JSONArray jsonArray;
        ArrayList<PaperDetailData> arr = new ArrayList<PaperDetailData>();
        try {
            JSONObject jsonObj = new JSONObject(str);
            jsonArray = jsonObj.getJSONObject("_embedded").getJSONArray("paperdetail");

            System.out.println("size of array" + jsonArray.length());

            for (int i = 0; i < jsonArray.length(); i++) {

                PaperDetailData obj = new PaperDetailData();

                JSONObject jsonInnerObj = jsonArray.getJSONObject(i);

                try {
                    obj.paper_id = jsonInnerObj.getString("paper_id");
                } catch (Exception e) {
                }
                try {
                    obj.total_marks = jsonInnerObj.getString("total_marks");
                } catch (Exception e) {
                }
                try {
                    obj.exam_id = jsonInnerObj.getString("exam_id");
                } catch (Exception e) {
                }
                try {
                    obj.time_duration = jsonInnerObj.getString("time_duration");
                } catch (Exception e) {
                }
                try {
                    obj.exam_name = jsonInnerObj.getString("exam_name");
                    Log.e("exam_name", "exam_name parse" + obj.exam_name);
                } catch (Exception e) {
                }
                try {
                    obj.obtained_marks = jsonInnerObj.getString("obtained_marks");
                } catch (Exception e) {
                }
                arr.add(obj);
            }

        } catch (Exception e) {

        }
        return arr;
    }

    // Test Paper status
    public static String jsonTestPaperResult(String res) {
        try {
            JSONObject jsonObj = new JSONObject(res);
            return jsonObj.getJSONObject("data").getString("last_paper_id");
        } catch (JSONException e) {
            e.printStackTrace();
            return " ";
        }
    }

    //Paper Detail Data
    public static ArrayList<TestResultData> parseTestResultData(String str) {
        JSONArray jsonArray;
        ArrayList<TestResultData> arr = new ArrayList<TestResultData>();
        try {
            JSONObject jsonObj = new JSONObject(str);
            jsonArray = jsonObj.getJSONObject("_embedded").getJSONArray("answers");

            System.out.println("size of array" + jsonArray.length());

            for (int i = 0; i < jsonArray.length(); i++) {

                TestResultData obj = new TestResultData();

                JSONObject jsonInnerObj = jsonArray.getJSONObject(i);

                try {
                    obj.start_date = jsonInnerObj.getString("start_date");
                } catch (Exception e) {
                }
                try {
                    obj.start_time = jsonInnerObj.getString("start_time");
                } catch (Exception e) {
                }
                try {
                    obj.end_date = jsonInnerObj.getString("end_date");
                } catch (Exception e) {
                }
                try {
                    obj.end_time = jsonInnerObj.getString("end_time");
                } catch (Exception e) {
                }
                try {
                    obj.time_taken = jsonInnerObj.getString("time_taken");
                } catch (Exception e) {
                }
                try {
                    obj.no_of_question = jsonInnerObj.getString("no_of_question");
                } catch (Exception e) {
                }
                try {
                    obj.answered_question = jsonInnerObj.getString("answered_question");
                } catch (Exception e) {
                }
                try {
                    obj.correct_answers = jsonInnerObj.getString("correct_answers");
                } catch (Exception e) {
                }
                try {
                    obj.negative_marking = jsonInnerObj.getString("negative_marking");
                } catch (Exception e) {
                }
                try {
                    obj.total_marks = jsonInnerObj.getString("total_marks");
                } catch (Exception e) {
                }
                try {
                    obj.obtained_marks = jsonInnerObj.getString("obtained_marks");
                } catch (Exception e) {
                }
                try {
                    obj.exam_name = jsonInnerObj.getString("exam_name");
                } catch (Exception e) {
                }
                try {
                    obj.levelid = jsonInnerObj.getString("levelid");
                } catch (Exception e) {
                }
                try {
                    obj.incorrect_answers = jsonInnerObj.getString("incorrect_answers");
                } catch (Exception e) {
                }
                try {
                    obj.examid = jsonInnerObj.getString("examid");
                } catch (Exception e) {
                }

                arr.add(obj);
            }

        } catch (Exception e) {

        }
        return arr;
    }

    //Review Paper Detail Data
    public static ArrayList<PaperDetailData> parseReviewPaperDetailData(String str) {
        JSONArray jsonArray;
        ArrayList<PaperDetailData> arr = new ArrayList<PaperDetailData>();
        try {
            JSONObject jsonObj = new JSONObject(str);
            jsonArray = jsonObj.getJSONObject("_embedded").getJSONArray("testreview");

            System.out.println("size of array" + jsonArray.length());

            for (int i = 0; i < jsonArray.length(); i++) {

                PaperDetailData obj = new PaperDetailData();

                JSONObject jsonInnerObj = jsonArray.getJSONObject(i);

                try {
                    obj.time_duration = jsonInnerObj.getString("time_duration");
                } catch (Exception e) {
                }
                try {
                    obj.exam_name = jsonInnerObj.getString("exam_name");
                } catch (Exception e) {
                }
                try {
                    obj.start_time = jsonInnerObj.getString("start_time");
                } catch (Exception e) {
                }
                try {
                    obj.end_time = jsonInnerObj.getString("end_time");
                } catch (Exception e) {
                }
                try {
                    obj.level_id = jsonInnerObj.getString("level_id");
                } catch (Exception e) {
                }
                arr.add(obj);
            }

        } catch (Exception e) {

        }
        return arr;
    }

    //Online Review Test Paper Data
    public static ArrayList<TestPaperData> parseReviewTestPaperData(String str) {
        JSONArray jsonArray;
        ArrayList<TestPaperData> arr = new ArrayList<TestPaperData>();
        try {
            JSONObject jsonObj = new JSONObject(str);
            jsonArray = jsonObj.getJSONObject("_embedded").getJSONArray("testreview");

            System.out.println("size of array" + jsonArray.length());

            for (int i = 0; i < jsonArray.length(); i++) {

                TestPaperData obj = new TestPaperData();

                JSONObject jsonInnerObj = jsonArray.getJSONObject(i);

                try {
                    obj.qb_id = jsonInnerObj.getString("qb_id");
                } catch (Exception e) {
                }
                try {
                    obj.question = jsonInnerObj.getString("question");
                } catch (Exception e) {
                }
                try {
                    obj.option_a = jsonInnerObj.getString("option_a");
                } catch (Exception e) {
                }
                try {
                    obj.option_b = jsonInnerObj.getString("option_b");
                } catch (Exception e) {
                }
                try {
                    obj.option_c = jsonInnerObj.getString("option_c");
                } catch (Exception e) {
                }
                try {
                    obj.option_d = jsonInnerObj.getString("option_d");
                } catch (Exception e) {
                }
                try {
                    obj.answer = jsonInnerObj.getString("answer");
                } catch (Exception e) {
                }
                try {
                    obj.answer_option = jsonInnerObj.getString("answer_option");
                } catch (Exception e) {
                }
                try {
                    obj.solution = jsonInnerObj.getString("solution");
                } catch (Exception e) {
                }
                try {
                    obj.uitype = jsonInnerObj.getInt("uitype");
                } catch (Exception e) {
                }



                try {
                    JSONArray jArray = jsonInnerObj.getJSONArray("check");
                    if (jArray != null) {
                        for (int j=0;j<jArray.length();j++){
                            obj.check.add(jArray.getString(j));
                        }
                    }
                } catch (Exception e) {
                }

                try {
                    JSONArray jArray = jsonInnerObj.getJSONArray("check_option");
                    if (jArray != null) {
                        for (int j=0;j<jArray.length();j++){
                            obj.checkOption.add(jArray.getString(j));
                        }
                    }
                } catch (Exception e) {
                }
                arr.add(obj);
            }

        } catch (Exception e) {

        }
        return arr;
    }

    // Level Detail Data
    public static ArrayList<LevelDetailData> parseLevelDetailData(String str) {
        JSONArray jsonArray;
        ArrayList<LevelDetailData> arr = new ArrayList<LevelDetailData>();
        try {
            JSONObject jsonObj = new JSONObject(str);
            jsonArray = jsonObj.getJSONObject("_embedded").getJSONArray("userdetail");

            System.out.println("size of array" + jsonArray.length());

            for (int i = 0; i < jsonArray.length(); i++) {

                LevelDetailData obj = new LevelDetailData();

                JSONObject jsonInnerObj = jsonArray.getJSONObject(i);

                try {
                    obj.id = jsonInnerObj.getString("id");
                } catch (Exception e) {
                }
                try {
                    obj.user_id = jsonInnerObj.getString("user_id");
                } catch (Exception e) {
                }
                try {
                    obj.username = jsonInnerObj.getString("username");
                } catch (Exception e) {
                }
                try {
                    obj.level_1 = jsonInnerObj.getString("level_1");
                } catch (Exception e) {
                }
                try {
                    obj.level_2 = jsonInnerObj.getString("level_2");
                } catch (Exception e) {
                }
                try {
                    obj.level_3 = jsonInnerObj.getString("level_3");
                } catch (Exception e) {
                }
                try {
                    obj.exam_id = jsonInnerObj.getString("exam_id");
                } catch (Exception e) {
                }
                arr.add(obj);
            }

        } catch (Exception e) {

        }
        return arr;
    }


    // Dashboard Data
    public static ArrayList<DashboardData> parseDashboardData(String str) {
        JSONArray jsonArray;
        ArrayList<DashboardData> arr = new ArrayList<DashboardData>();
        try {
            JSONObject jsonObj = new JSONObject(str);
            jsonArray = jsonObj.getJSONObject("_embedded").getJSONArray("dashboard");

            System.out.println("size of array" + jsonArray.length());

            for (int i = 0; i < jsonArray.length(); i++) {

                DashboardData obj = new DashboardData();

                JSONObject jsonInnerObj = jsonArray.getJSONObject(i);

                try {
                    obj.exam_id = jsonInnerObj.getString("exam_id");
                } catch (Exception e) {
                }
                try {
                    obj.level_id = jsonInnerObj.getString("level_id");
                } catch (Exception e) {
                }
                try {
                    obj.exam_name = jsonInnerObj.getString("exam_name");
                } catch (Exception e) {
                }
                try {
                    obj.last_paper_id = jsonInnerObj.getString("last_paper_id");
                } catch (Exception e) {
                }
                try {
                    obj.exam_date = jsonInnerObj.getString("exam_date");
                } catch (Exception e) {
                }
                try {
                    obj.total_attempts = jsonInnerObj.getString("total_attempts");
                } catch (Exception e) {
                }
                try {
                    obj.no_of_attempts = jsonInnerObj.getString("no_of_attempts");
                } catch (Exception e) {
                }
                try {
                    obj.start_date = jsonInnerObj.getString("start_date");
                } catch (Exception e) {
                }
                try {
                    obj.expiry_date = jsonInnerObj.getString("expiry_date");
                } catch (Exception e) {
                }
                try {
                    obj.score_id = jsonInnerObj.getString("score");
                } catch (Exception e) {
                }
                arr.add(obj);
            }

        } catch (Exception e) {

        }
        return arr;
    }


    // Dashboard Datapre
    public static ArrayList<DashboardData> parseDashboardDatapre(String str) {
        JSONArray jsonArray;
        ArrayList<DashboardData> arr = new ArrayList<DashboardData>();
        try {
            JSONObject jsonObj = new JSONObject(str);
            jsonArray = jsonObj.getJSONObject("_embedded").getJSONArray("dashboard");

            System.out.println("size of array" + jsonArray.length());

            for (int i = 0; i < jsonArray.length(); i++) {

                DashboardData obj = new DashboardData();

                JSONObject jsonInnerObj = jsonArray.getJSONObject(i);

                try {
                    obj.exam_id = jsonInnerObj.getString("exam_id");
                } catch (Exception e) {
                }
                try {
                    obj.no_of_attempts = jsonInnerObj.getString("no_of_attempts");
                } catch (Exception e) {
                }
                try {
                    obj.exam_name = jsonInnerObj.getString("exam_name");
                } catch (Exception e) {
                }
                try {
                    obj.start_date = jsonInnerObj.getString("start_date");
                } catch (Exception e) {
                }
                try {
                    obj.expiry_date = jsonInnerObj.getString("expiry_date");
                } catch (Exception e) {
                }
                try {
                    obj.total_attempts = jsonInnerObj.getString("total_attempts");
                } catch (Exception e) {
                }
                arr.add(obj);
            }

        } catch (Exception e) {

        }
        return arr;
    }
    // Dashboard DataPay
    public static ArrayList<DashboardData> parseDashboardDataa(String str) {
        JSONArray jsonArray;
        ArrayList<DashboardData> arr = new ArrayList<DashboardData>();
        try {
            JSONObject jsonObj = new JSONObject(str);
            jsonArray = jsonObj.getJSONObject("_embedded").getJSONArray("dashboard");
            System.out.println("size of array" + jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonInnerObj = jsonArray.getJSONObject(i);
                JSONArray jArray = jsonInnerObj.getJSONArray("attept_details");
                System.out.println("size of array" + jArray.length());

                    for (int j=0;j<jArray.length();j++){
                        DashboardData obj = new DashboardData();

                        JSONObject jsonInnerObjj = jArray.getJSONObject(j);

                        try {
                            obj.exam_id = jsonInnerObjj.getString("exam_id");
                        } catch (Exception e) {
                        }
                        try {
                            obj.level_id = jsonInnerObjj.getString("level_id");
                        } catch (Exception e) {
                        }
                        try {
                            obj.exam_name = jsonInnerObjj.getString("exam_name");
                        } catch (Exception e) {
                        }
                        try {
                            obj.last_paper_id = jsonInnerObjj.getString("last_paper_id");
                        } catch (Exception e) {
                        }
                        try {
                            obj.exam_date = jsonInnerObjj.getString("exam_date");
                        } catch (Exception e) {
                        }
                        arr.add(obj);


                    }




            }

        } catch (Exception e) {

        }
        return arr;
    }

    //Info Data
    public static ArrayList<InstructionsData> parseInstructionsDataInfo(String str) {
        JSONArray jsonArray;
        ArrayList<InstructionsData> arr = new ArrayList<InstructionsData>();
        try {
            JSONObject jsonObj = new JSONObject(str);
            jsonArray = jsonObj.getJSONObject("_embedded").getJSONArray("exam_list");

            System.out.println("size of array" + jsonArray.length());

            for (int i = 0; i < jsonArray.length(); i++) {

                InstructionsData obj = new InstructionsData();

                JSONObject jsonInnerObj = jsonArray.getJSONObject(i);

                try {
                    obj.exam_name = jsonInnerObj.getString("exam_name");
                } catch (Exception e) {
                }
                try {
                    obj.slug = jsonInnerObj.getString("slug");
                } catch (Exception e) {
                }
                try {
                    obj.exam_info = jsonInnerObj.getString("exam_info");
                } catch (Exception e) {
                }


                arr.add(obj);
            }

        } catch (Exception e) {

        }
        return arr;
    }

}