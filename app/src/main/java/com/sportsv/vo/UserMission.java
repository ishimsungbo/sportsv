package com.sportsv.vo;

import java.util.Date;

public class UserMission {

	private int usermissionid;
	private int missionid;
	private int uid;
	private String subject;
	private String descroption;
	private String uploadflag;
	private String youtubeaddr;
	private String passflag;
	private String videoaddr;
	private Date creationdate;

	public UserMission(){}

	public UserMission(int usermissionid, int missionid, int uid, String subject, String descroption, String uploadflag, String youtubeaddr, String passflag, String videoaddr, Date creationdate) {
		this.usermissionid = usermissionid;
		this.missionid = missionid;
		this.uid = uid;
		this.subject = subject;
		this.descroption = descroption;
		this.uploadflag = uploadflag;
		this.youtubeaddr = youtubeaddr;
		this.passflag = passflag;
		this.videoaddr = videoaddr;
		this.creationdate = creationdate;
	}

	public int getUsermissionid() {
		return usermissionid;
	}

	public void setUsermissionid(int usermissionid) {
		this.usermissionid = usermissionid;
	}

	public int getMissionid() {
		return missionid;
	}

	public void setMissionid(int missionid) {
		this.missionid = missionid;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescroption() {
		return descroption;
	}

	public void setDescroption(String descroption) {
		this.descroption = descroption;
	}

	public String getUploadflag() {
		return uploadflag;
	}

	public void setUploadflag(String uploadflag) {
		this.uploadflag = uploadflag;
	}

	public String getYoutubeaddr() {
		return youtubeaddr;
	}

	public void setYoutubeaddr(String youtubeaddr) {
		this.youtubeaddr = youtubeaddr;
	}

	public String getPassflag() {
		return passflag;
	}

	public void setPassflag(String passflag) {
		this.passflag = passflag;
	}

	public String getVideoaddr() {
		return videoaddr;
	}

	public void setVideoaddr(String videoaddr) {
		this.videoaddr = videoaddr;
	}

	public Date getCreationdate() {
		return creationdate;
	}

	public void setCreationdate(Date creationdate) {
		this.creationdate = creationdate;
	}
}