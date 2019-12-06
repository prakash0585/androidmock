package com.sabakuch.epaper.data;

import java.io.Serializable;

public class User implements Serializable {



	private String socialUserGender;
	private String userName;
	private String firstName;
	private String lastName;
	private String password;
	private String userEmail;
	private String actionforSignUp;
	private String sourcesignup;
	private String userImage;

	public User(String actionforSignUp,String sourcesignup, String userName, String firstName,
			String lastName, String password, String userEmail, String userImage, String socialUserGender) {
		super();
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.userEmail = userEmail;
		this.actionforSignUp = actionforSignUp;
		this.sourcesignup = sourcesignup;
		this.userImage = userImage;
		this.socialUserGender = socialUserGender;
	}

	public String getActionforSignUp() {
		return actionforSignUp;
	}

	public void setActionforSignUp(String actionforSignUp) {
		this.actionforSignUp = actionforSignUp;
	}

	public String getSourcesignup() {
		return sourcesignup;
	}

	public void setSourcesignup(String sourcesignup) {
		this.sourcesignup = sourcesignup;
	}

	public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

	public String getAction() {
		return actionforSignUp;
	}

	public void setAction(String action) {
		this.actionforSignUp = action;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getSocialUserGender() {
		return socialUserGender;
	}

	public void setSocialUserGender(String socialUserGender) {
		this.socialUserGender = socialUserGender;
	}
}
