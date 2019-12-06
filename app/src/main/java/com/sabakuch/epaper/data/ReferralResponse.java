package com.sabakuch.epaper.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dell on 31-Mar-17.
 */
public class ReferralResponse {

    @SerializedName("promotion")
    @Expose
    private Promotion promotion;

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public class Promotion {

        @SerializedName("success")
        @Expose
        private Integer success;
        @SerializedName("otp")
        @Expose
        private Integer otp;
        @SerializedName("message")
        @Expose
        private String message;

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

        public Integer getOtp() {
            return otp;
        }

        public void setOtp(Integer otp) {
            this.otp = otp;
        }
    }
}
