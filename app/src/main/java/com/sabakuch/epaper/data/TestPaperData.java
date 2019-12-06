package com.sabakuch.epaper.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TestPaperData implements Serializable{

    public String qb_id;
    public String question;
    public String option_a;
    public String option_b;
    public String option_c;
    public String option_d;
    public String assign_id;

    public String Qno ="";
    public String answer;
    public String answer_option;
    public int checkedId = -1;
    public boolean isAnswered;
    public boolean isClicked;
    public String q_id; //key for API
    public String answer_opt; //key for API
    public String solution;

    public String exam_id;
    public String exam_name;
    private String headerTitle;

    public String level_id;
    public String name;
    public String image;
    public String level_text;
    public int uitype;
    public ArrayList<String> checkOption  = new ArrayList<String>();
    public ArrayList<String> check  = new ArrayList<String>();
    public boolean isPassage =  false;
    public String passageid;
    public String passagetitle;
    public String passage;
    public String passage_text;

    //  public boolean checkboxStatus;

    private ArrayList<String> allItemsInSection;


    private ArrayList<Integer> textColor;


    public TestPaperData() {

    }
    public TestPaperData(String headerTitle, ArrayList<String> allItemsInSection) {
        this.headerTitle = headerTitle;
        this.allItemsInSection = allItemsInSection;

    }

    public boolean isPassage() {
        return isPassage;
    }

    public void setPassage(boolean passage) {
        isPassage = passage;
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

    public ArrayList<String> getCheckOption() {
        return checkOption;
    }

    public void setCheckOption(ArrayList<String> checkOption) {
        this.checkOption = checkOption;
    }

    public ArrayList<String> getCheck() {
        return check;
    }

    public void setCheck(ArrayList<String> check) {
        this.check = check;
    }


    public class PassageResponse {

        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("detail")
        @Expose
        private String detail;
        @SerializedName("_links")
        @Expose
        private Links links;
        @SerializedName("_embedded")
        @Expose
        private Embedded embedded;
        @SerializedName("page_count")
        @Expose
        private Integer pageCount;
        @SerializedName("page_size")
        @Expose
        private Integer pageSize;
        @SerializedName("total_items")
        @Expose
        private Integer totalItems;
        @SerializedName("page")
        @Expose
        private Integer page;

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public Links getLinks() {
            return links;
        }

        public void setLinks(Links links) {
            this.links = links;
        }

        public Embedded getEmbedded() {
            return embedded;
        }

        public void setEmbedded(Embedded embedded) {
            this.embedded = embedded;
        }

        public Integer getPageCount() {
            return pageCount;
        }

        public void setPageCount(Integer pageCount) {
            this.pageCount = pageCount;
        }

        public Integer getPageSize() {
            return pageSize;
        }

        public void setPageSize(Integer pageSize) {
            this.pageSize = pageSize;
        }

        public Integer getTotalItems() {
            return totalItems;
        }

        public void setTotalItems(Integer totalItems) {
            this.totalItems = totalItems;
        }

        public Integer getPage() {
            return page;
        }

        public void setPage(Integer page) {
            this.page = page;
        }

    }


    public class Question {

        @SerializedName("qb_id")
        @Expose
        private String qbId;
        @SerializedName("question")
        @Expose
        private String question;
        @SerializedName("option_a")
        @Expose
        private String optionA;
        @SerializedName("option_b")
        @Expose
        private String optionB;
        @SerializedName("option_c")
        @Expose
        private String optionC;
        @SerializedName("option_d")
        @Expose
        private String optionD;
        private String assign_id;

        public String getAssign_id() {
            return assign_id;
        }

        public void setAssign_id(String assign_id) {
            this.assign_id = assign_id;
        }

        public String getQbId() {
            return qbId;
        }

        public void setQbId(String qbId) {
            this.qbId = qbId;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getOptionA() {
            return optionA;
        }

        public void setOptionA(String optionA) {
            this.optionA = optionA;
        }

        public String getOptionB() {
            return optionB;
        }

        public void setOptionB(String optionB) {
            this.optionB = optionB;
        }

        public String getOptionC() {
            return optionC;
        }

        public void setOptionC(String optionC) {
            this.optionC = optionC;
        }

        public String getOptionD() {
            return optionD;
        }

        public void setOptionD(String optionD) {
            this.optionD = optionD;
        }

    }


    public class Self {

        @SerializedName("href")
        @Expose
        private String href;

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

    }
    public class Passage {

        @SerializedName("passage_text")
        @Expose
        private String passagetext;
        @SerializedName("qb_id")
        @Expose
        private String qbId;
        @SerializedName("question")
        @Expose
        private String question;
        @SerializedName("option_a")
        @Expose
        private String optionA;
        @SerializedName("option_b")
        @Expose
        private String optionB;
        @SerializedName("option_c")
        @Expose
        private String optionC;
        @SerializedName("option_d")
        @Expose
        private String optionD;
        @SerializedName("assign_id")
        @Expose
        private String assign_id;
        @SerializedName("passageid")
        @Expose
        private String passageid;





        @SerializedName("passagetitle")
        @Expose
        private String passagetitle;
        @SerializedName("passage")
        @Expose
        private String passage;
        @SerializedName("questions")
        @Expose
        private List<Question> questions = new ArrayList<>();



        public String getPassagetitle() {
            return passagetitle;
        }

        public void setPassagetitle(String passagetitle) {
            this.passagetitle = passagetitle;
        }

        public String getPassage() {
            return passage;
        }

        public void setPassage(String passage) {
            this.passage = passage;
        }

        public List<Question> getQuestions() {
            return questions;
        }

        public void setQuestions(List<Question> questions) {
            this.questions = questions;
        }

        public String getPassagetext() {
            return passagetext;
        }

        public void setPassagetext(String passagetext) {
            this.passagetext = passagetext;
        }

        public String getQbId() {
            return qbId;
        }

        public void setQbId(String qbId) {
            this.qbId = qbId;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getOptionA() {
            return optionA;
        }

        public void setOptionA(String optionA) {
            this.optionA = optionA;
        }

        public String getOptionB() {
            return optionB;
        }

        public void setOptionB(String optionB) {
            this.optionB = optionB;
        }

        public String getOptionC() {
            return optionC;
        }

        public void setOptionC(String optionC) {
            this.optionC = optionC;
        }

        public String getOptionD() {
            return optionD;
        }

        public void setOptionD(String optionD) {
            this.optionD = optionD;
        }

        public String getAssign_id() {
            return assign_id;
        }

        public void setAssign_id(String assign_id) {
            this.assign_id = assign_id;
        }

        public String getPassageid() {
            return passageid;
        }

        public void setPassageid(String passageid) {
            this.passageid = passageid;
        }
    }
/*    public class Passage {

        @SerializedName("passageid")
        @Expose
        private String passageid;
        @SerializedName("passagetitle")
        @Expose
        private String passagetitle;
        @SerializedName("passage")
        @Expose
        private String passage;
        @SerializedName("questions")
        @Expose
        private List<Question> questions = new ArrayList<>();

        public String getPassageid() {
            return passageid;
        }

        public void setPassageid(String passageid) {
            this.passageid = passageid;
        }

        public String getPassagetitle() {
            return passagetitle;
        }

        public void setPassagetitle(String passagetitle) {
            this.passagetitle = passagetitle;
        }

        public String getPassage() {
            return passage;
        }

        public void setPassage(String passage) {
            this.passage = passage;
        }

        public List<Question> getQuestions() {
            return questions;
        }

        public void setQuestions(List<Question> questions) {
            this.questions = questions;
        }

    }*/


    public class Links {

        @SerializedName("self")
        @Expose
        private Self self;
        @SerializedName("first")
        @Expose
        private First first;
        @SerializedName("last")
        @Expose
        private Last last;

        public Self getSelf() {
            return self;
        }

        public void setSelf(Self self) {
            this.self = self;
        }

        public First getFirst() {
            return first;
        }

        public void setFirst(First first) {
            this.first = first;
        }

        public Last getLast() {
            return last;
        }

        public void setLast(Last last) {
            this.last = last;
        }

    }


    public class Last {

        @SerializedName("href")
        @Expose
        private String href;

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

    }

    public class First {

        @SerializedName("href")
        @Expose
        private String href;

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

    }


    public class Embedded {

        @SerializedName("passage")
        @Expose
        private ArrayList<Passage> passage = new ArrayList<>();

        public ArrayList<Passage> getPassage() {
            return passage;
        }

        public void setPassage(ArrayList<Passage> passage) {
            this.passage = passage;
        }

    }
}
