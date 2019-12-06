package com.sabakuch.epaper.data;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StateResponse {

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

		@SerializedName("states")
		@Expose
		private ArrayList<State> states = new ArrayList<State>();

		public ArrayList<State> getStates() {
			return states;
		}

		public void setStates(ArrayList<State> states) {
			this.states = states;
		}

	}

	public class State {

		@SerializedName("stateid")
		@Expose
		private String stateid;
		@SerializedName("name")
		@Expose
		private String name;

		public String getStateid() {
			return stateid;
		}

		public void setStateid(String stateid) {
			this.stateid = stateid;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}
}
