package com.sportsv.vo;

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
	private String filename;
	private int grade;          //미션업로드점수
	private int passgrade;      //미션클리어점수
	private String creationdate;
	private String change_creationdate;

	public UserMission(){}

	public UserMission(int usermissionid, int missionid, int uid, String subject, String descroption, String uploadflag, String youtubeaddr, String passflag, String videoaddr, String filename, int grade, int passgrade, String creationdate, String change_creationdate) {
		this.usermissionid = usermissionid;
		this.missionid = missionid;
		this.uid = uid;
		this.subject = subject;
		this.descroption = descroption;
		this.uploadflag = uploadflag;
		this.youtubeaddr = youtubeaddr;
		this.passflag = passflag;
		this.videoaddr = videoaddr;
		this.filename = filename;
		this.grade = grade;
		this.passgrade = passgrade;
		this.creationdate = creationdate;
		this.change_creationdate = change_creationdate;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
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

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getPassgrade() {
		return passgrade;
	}

	public void setPassgrade(int passgrade) {
		this.passgrade = passgrade;
	}

	@Override
	public String toString() {
		return "UserMission{" +
				"usermissionid=" + usermissionid +
				", missionid=" + missionid +
				", uid=" + uid +
				", subject='" + subject + '\'' +
				", descroption='" + descroption + '\'' +
				", uploadflag='" + uploadflag + '\'' +
				", youtubeaddr='" + youtubeaddr + '\'' +
				", passflag='" + passflag + '\'' +
				", videoaddr='" + videoaddr + '\'' +
				", filename='" + filename + '\'' +
				", grade=" + grade +
				", passgrade=" + passgrade +
				", creationdate='" + creationdate + '\'' +
				", change_creationdate='" + change_creationdate + '\'' +
				'}';
	}
}