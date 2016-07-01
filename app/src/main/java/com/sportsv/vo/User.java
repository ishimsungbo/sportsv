package com.sportsv.vo;


public class User {

	private int uid;
	private String useremail;
	private String username;
	private String snsid;
	private String snsname;
	private String snstype;
	private String phone;
	private String password;
	private String profileimgurl;
	private String googleemail;
	private int location;
	private String apppushflag;
	private String teampushflag;
	private int teamid;
	private int commontokenid;
	private String creationdate;
	private String change_creationdate;

	//DB
	private String fcmToken;


	public User() {
	}

	public User(String useremail) {
		this.useremail = useremail;
	}

	public User(int uid, String useremail, String username, String snsid, String snsname, String snstype, String phone, String password, String profileimgurl, String googleemail, int location, String apppushflag, String teampushflag, int teamid, int commontokenid, String creationdate, String change_creationdate, String fcmToken) {
		this.uid = uid;
		this.useremail = useremail;
		this.username = username;
		this.snsid = snsid;
		this.snsname = snsname;
		this.snstype = snstype;
		this.phone = phone;
		this.password = password;
		this.profileimgurl = profileimgurl;
		this.googleemail = googleemail;
		this.location = location;
		this.apppushflag = apppushflag;
		this.teampushflag = teampushflag;
		this.teamid = teamid;
		this.commontokenid = commontokenid;
		this.creationdate = creationdate;
		this.change_creationdate = change_creationdate;
		this.fcmToken = fcmToken;
	}

	public String getFcmToken() {
		return fcmToken;
	}

	public void setFcmToken(String fcmToken) {
		this.fcmToken = fcmToken;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getUseremail() {
		return useremail;
	}

	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSnsid() {
		return snsid;
	}

	public void setSnsid(String snsid) {
		this.snsid = snsid;
	}

	public String getSnsname() {
		return snsname;
	}

	public void setSnsname(String snsname) {
		this.snsname = snsname;
	}

	public String getSnstype() {
		return snstype;
	}

	public void setSnstype(String snstype) {
		this.snstype = snstype;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProfileimgurl() {
		return profileimgurl;
	}

	public void setProfileimgurl(String profileimgurl) {
		this.profileimgurl = profileimgurl;
	}

	public String getGoogleemail() {
		return googleemail;
	}

	public void setGoogleemail(String googleemail) {
		this.googleemail = googleemail;
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public String getApppushflag() {
		return apppushflag;
	}

	public void setApppushflag(String apppushflag) {
		this.apppushflag = apppushflag;
	}

	public String getTeampushflag() {
		return teampushflag;
	}

	public void setTeampushflag(String teampushflag) {
		this.teampushflag = teampushflag;
	}

	public int getTeamid() {
		return teamid;
	}

	public void setTeamid(int teamid) {
		this.teamid = teamid;
	}


	public String getCreationdate() {
		return creationdate;
	}

	public void setCreationdate(String creationdate) {
		this.creationdate = creationdate;
	}

	public String getChange_creationdate() {
		return change_creationdate;
	}

	public void setChange_creationdate(String change_creationdate) {
		this.change_creationdate = change_creationdate;
	}

	public int getCommontokenid() {
		return commontokenid;
	}

	public void setCommontokenid(int commontokenid) {
		this.commontokenid = commontokenid;
	}

	@Override
	public String toString() {
		return "User{" +
				"uid=" + uid +
				", useremail='" + useremail + '\'' +
				", username='" + username + '\'' +
				", snsid='" + snsid + '\'' +
				", snsname='" + snsname + '\'' +
				", snstype='" + snstype + '\'' +
				", phone='" + phone + '\'' +
				", password='" + password + '\'' +
				", profileimgurl='" + profileimgurl + '\'' +
				", googleemail='" + googleemail + '\'' +
				", location=" + location +
				", apppushflag='" + apppushflag + '\'' +
				", teampushflag='" + teampushflag + '\'' +
				", teamid=" + teamid +
				", commontokenid=" + commontokenid +
				", creationdate='" + creationdate + '\'' +
				", change_creationdate='" + change_creationdate + '\'' +
				", fcmToken='" + fcmToken + '\'' +
				'}';
	}
}