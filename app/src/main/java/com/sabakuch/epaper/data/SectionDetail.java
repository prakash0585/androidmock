package com.sabakuch.epaper.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SectionDetail {

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


    public class Sectiondetail {

        @SerializedName("is_section")
        @Expose
        private String isSection;

        public String getIsSection() {
            return isSection;
        }

        public void setIsSection(String isSection) {
            this.isSection = isSection;
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
}