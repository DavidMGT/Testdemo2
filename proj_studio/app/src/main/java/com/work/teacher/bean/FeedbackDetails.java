package com.work.teacher.bean;

import java.io.Serializable;

/**
 * 通知详情
 * 
 * @author 左丽姬
 */
public class FeedbackDetails implements Serializable {
	private String userid;
	private String truename;
	private String mobile;
	private String photo;
	private String agree;
	private String value1;
	private int tsp;

	public FeedbackDetails() {
		super();
	}

	public FeedbackDetails(String userid, String truename, String mobile, String photo, String agree, String value1) {
		super();
		this.userid = userid;
		this.truename = truename;
		this.mobile = mobile;
		this.photo = photo;
		this.agree = agree;
		this.value1 = value1;
	}

	public FeedbackDetails(String userid, String truename, String mobile, String photo, String agree, String value1,
			int tsp) {
		super();
		this.userid = userid;
		this.truename = truename;
		this.mobile = mobile;
		this.photo = photo;
		this.agree = agree;
		this.value1 = value1;
		this.tsp = tsp;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAgree() {
		return agree;
	}

	public void setAgree(String agree) {
		this.agree = agree;
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public int getTsp() {
		return tsp;
	}

	public void setTsp(int tsp) {
		this.tsp = tsp;
	}

	@Override
	public String toString() {
		return "FeedbackDetails [userid=" + userid + ", truename=" + truename + ", mobile=" + mobile + ", photo="
				+ photo + ", agree=" + agree + ", value1=" + value1 + ", tsp=" + tsp + "]";
	}

}
