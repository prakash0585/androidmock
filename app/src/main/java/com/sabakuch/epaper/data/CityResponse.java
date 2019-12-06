package com.sabakuch.epaper.data;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityResponse {

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

		@SerializedName("cities")
		@Expose
		private ArrayList<City> cities = new ArrayList<City>();

		public ArrayList<City> getCities() {
			return cities;
		}

		public void setCities(ArrayList<City> cities) {
			this.cities = cities;
		}

	}

	public class City {

		@SerializedName("cityid")
		@Expose
		private String cityid;
		@SerializedName("cityname")
		@Expose
		private String cityname;

		public String getCityid() {
			return cityid;
		}

		public void setCityid(String cityid) {
			this.cityid = cityid;
		}

		public String getCityname() {
			return cityname;
		}

		public void setCityname(String cityname) {
			this.cityname = cityname;
		}

	}
}
