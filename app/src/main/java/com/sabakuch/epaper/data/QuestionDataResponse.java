package com.sabakuch.epaper.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by dell on 26-Apr-17.
 */
public class QuestionDataResponse {

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

    }public class Last {

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
        @SerializedName("next")
        @Expose
        private Next next;

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

        public Next getNext() {
            return next;
        }

        public void setNext(Next next) {
            this.next = next;
        }

    }


    public class Next {

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


    public class Question {
        Integer uiType=1;
        boolean isAnswered = false;
        Integer checkedId ;
        int sectionposition=0;
        int selectionposition = 0;
        boolean isInstruction = false;
        String part_name;
        String instruction_data ;
        int QuestionNo=-1;
        String answer_opt="";
        String assign_id;
        boolean checked1 = false;
        boolean checked2 = false;
        boolean checked3 = false;
        boolean checked4 = false;

        public String getAssign_id() {
            return assign_id;
        }

        public void setAssign_id(String assign_id) {
            this.assign_id = assign_id;
        }

        public boolean isChecked1() {
            return checked1;
        }

        public void setChecked1(boolean checked1) {
            this.checked1 = checked1;
        }

        public boolean isChecked2() {
            return checked2;
        }

        public void setChecked2(boolean checked2) {
            this.checked2 = checked2;
        }

        public boolean isChecked3() {
            return checked3;
        }

        public void setChecked3(boolean checked3) {
            this.checked3 = checked3;
        }

        public boolean isChecked4() {
            return checked4;
        }

        public void setChecked4(boolean checked4) {
            this.checked4 = checked4;
        }

        public String getAnswer_opt() {
            return answer_opt;
        }

        public void setAnswer_opt(String answer_opt) {
            this.answer_opt = answer_opt;
        }

        public String getPart_name() {
            return part_name;
        }

        public void setPart_name(String part_name) {
            this.part_name = part_name;
        }

        public String getInstruction_data() {
            return instruction_data;
        }

        public void setInstruction_data(String instruction_data) {
            this.instruction_data = instruction_data;
        }

        public int getQuestionNo() {
            return QuestionNo;
        }

        public void setQuestionNo(int questionNo) {
            QuestionNo = questionNo;
        }

        public boolean isInstruction() {
            return isInstruction;
        }

        public void setInstruction(boolean instruction) {
            isInstruction = instruction;
        }



        @SerializedName("section_id")
        @Expose
        private String section_id;

        public int getSelectionposition() {
            return selectionposition;
        }

        public void setSelectionposition(int selectionposition) {
            this.selectionposition = selectionposition;
        }

        public boolean isAnswered() {
            return isAnswered;
        }

        public void setAnswered(boolean answered) {
            isAnswered = answered;
        }

        public int getSectionposition() {
            return sectionposition;
        }

        public void setSectionposition(int sectionposition) {
            this.sectionposition = sectionposition;
        }

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

        public String getSection_id() {
            return section_id;
        }

        public void setSection_id(String section_id) {
            this.section_id = section_id;
        }

        public boolean getIsAnswered() {
            return isAnswered;
        }

        public void setIsAnswered(boolean isAnswered) {
            this.isAnswered = isAnswered;
        }

        public Integer getCheckedId() {
            return checkedId;
        }

        public void setCheckedId(Integer checkedId) {
            this.checkedId = checkedId;
        }

        public Integer getUiType() {
            return uiType;
        }

        public void setUiType(Integer uiType) {
            this.uiType = uiType;
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

        public class Answer {
            String id;
            ArrayList<String> answer = new ArrayList<>();

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public ArrayList<String> getAnswer() {
                return answer;
            }

            public void setAnswer(ArrayList<String> answer) {
                this.answer = answer;
            }
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

    public class Embedded {

        @SerializedName("questions")
        @Expose
        private ArrayList<Question> questions = new ArrayList<>();

        public ArrayList<Question> getQuestions() {
            return questions;
        }

        public void setQuestions(ArrayList<Question> questions) {
            this.questions = questions;
        }

    }
}
