package com.sabakuch.epaper.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by dell on 17-Apr-17.
 */
public class CountryCodeData {

    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("country")
    @Expose
    private ArrayList<Country> country = new ArrayList<Country>();

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Country> getCountry() {
        return country;
    }

    public void setCountry(ArrayList<Country> country) {
        this.country = country;
    }

    public class Country {

        @SerializedName("countryid")
        @Expose
        private String countryid;
        @SerializedName("countryname")
        @Expose
        private String countryname;
        @SerializedName("phonecode")
        @Expose
        private String phonecode;

        public String getCountryid() {
            return countryid;
        }

        public void setCountryid(String countryid) {
            this.countryid = countryid;
        }

        public String getCountryname() {
            return countryname;
        }

        public void setCountryname(String countryname) {
            this.countryname = countryname;
        }

        public String getPhonecode() {
            return phonecode;
        }

        public void setPhonecode(String phonecode) {
            this.phonecode = phonecode;
        }

    }
}
