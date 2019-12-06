package com.sabakuch.epaper.data;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryResponse {

	@SerializedName("feeds")
	@Expose
	private Feeds feeds;

	public Feeds getFeeds() {
		return feeds;
	}

	public void setFeeds(Feeds feeds) {
		this.feeds = feeds;
	}

	public class Feeds {

		@SerializedName("country")
		@Expose
		private ArrayList<Country> country = new ArrayList<Country>();

		public ArrayList<Country> getCountry() {
			return country;
		}

		public void setCountry(ArrayList<Country> country) {
			this.country = country;
		}

	}

	public class Country {

		@SerializedName("countryid")
		@Expose
		private String countryid;
		@SerializedName("name")
		@Expose
		private String name;

		public String getCountryid() {
			return countryid;
		}

		public void setCountryid(String countryid) {
			this.countryid = countryid;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}
}
