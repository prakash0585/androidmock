package com.sabakuch.epaper.data;

import java.util.ArrayList;

public class SelectExamsData {

    public String exam_id;
    public String exam_name;
    public String slug;
    private String headerTitle;
    public String examimage;
    public String is_premium;
    public String is_user_pre_exam;
    public String is_custom;
    public String is_user_custom_exam;
    public String solution_id;
    public String solution_name;
    public String solution_image;
    public String set_id;
    public String set_name;
    public String pdfname;

    private ArrayList<String> allItemsInSection;


    private ArrayList<Integer> textColor;


    public SelectExamsData() {

    }
    public SelectExamsData(String headerTitle, ArrayList<String> allItemsInSection) {
        this.headerTitle = headerTitle;
        this.allItemsInSection = allItemsInSection;

    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public ArrayList<String> getAllItemsInSection() {
        return allItemsInSection;
    }

    public void setAllItemsInSection(ArrayList<String> allItemsInSection) {
        this.allItemsInSection = allItemsInSection;
    }


    public void setTextColor(ArrayList<Integer> textColor) {
        this.textColor = textColor;
    }
    public ArrayList<Integer> getTextColor() {
        return textColor;
    }

}
