package com.sabakuch.epaper.data;

import java.io.Serializable;

/**
 * Created by dell on 04-Oct-16.
 */
public class PaperDetailData implements Serializable{

    // paper detail
    public String paper_id;
    public String no_of_questions;
    public String total_marks;
    public String exam_id;
    // paper review detail
    public String time_duration;
    public String exam_name;
    public String start_time;
    public String end_time;
    public String level_id;
    public String obtained_marks;
}
