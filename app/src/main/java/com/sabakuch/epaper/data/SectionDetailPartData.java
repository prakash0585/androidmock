package com.sabakuch.epaper.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by dell on 26-Apr-17.
 */
public class SectionDetailPartData {

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


    public class Section {

        @SerializedName("sectionid")
        @Expose
        private String sectionid;
        @SerializedName("sec_name")
        @Expose
        private String secName;
        @SerializedName("uitype")
        @Expose
        private Integer uitype;
        @SerializedName("instructions")
        @Expose
        private ArrayList<Instruction> instructions = null;

        public String getSectionid() {
            return sectionid;
        }

        public void setSectionid(String sectionid) {
            this.sectionid = sectionid;
        }

        public String getSecName() {
            return secName;
        }

        public void setSecName(String secName) {
            this.secName = secName;
        }

        public Integer getUitype() {
            return uitype;
        }

        public void setUitype(Integer uitype) {
            this.uitype = uitype;
        }

        public ArrayList<Instruction> getInstructions() {
            return instructions;
        }

        public void setInstructions(ArrayList<Instruction> instructions) {
            this.instructions = instructions;
        }

    }

    public class Instruction {

        @SerializedName("level_id")
        @Expose
        private String levelId;
        @SerializedName("exam_id")
        @Expose
        private String examId;
        @SerializedName("instructions_id")
        @Expose
        private String instructionsId;
        @SerializedName("instruction")
        @Expose
        private String instruction;

        public String getLevelId() {
            return levelId;
        }

        public void setLevelId(String levelId) {
            this.levelId = levelId;
        }

        public String getExamId() {
            return examId;
        }

        public void setExamId(String examId) {
            this.examId = examId;
        }

        public String getInstructionsId() {
            return instructionsId;
        }

        public void setInstructionsId(String instructionsId) {
            this.instructionsId = instructionsId;
        }

        public String getInstruction() {
            return instruction;
        }

        public void setInstruction(String instruction) {
            this.instruction = instruction;
        }

    }


    public class Sectiondetail {

        @SerializedName("part_id")
        @Expose
        private String partId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("sections")
        @Expose
        private ArrayList<Section> sections = new ArrayList<>();

        public String getPartId() {
            return partId;
        }

        public void setPartId(String partId) {
            this.partId = partId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ArrayList<Section> getSections() {
            return sections;
        }

        public void setSections(ArrayList<Section> sections) {
            this.sections = sections;
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

        @SerializedName("sectiondetail")
        @Expose
        private ArrayList<Sectiondetail> sectiondetail = new ArrayList<>();

        public ArrayList<Sectiondetail> getSectiondetail() {
            return sectiondetail;
        }

        public void setSectiondetail(ArrayList<Sectiondetail> sectiondetail) {
            this.sectiondetail = sectiondetail;
        }

    }
}
