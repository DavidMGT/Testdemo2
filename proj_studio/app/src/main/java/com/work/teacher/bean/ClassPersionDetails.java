package com.work.teacher.bean;

import java.io.Serializable;

/**
 * 班级详情，管理
 * 
 * @author 左丽姬
 */
public class ClassPersionDetails implements Serializable {
	private String userid;
	private String truename;
	private String photo;
	private String value1;
	private String cid;

	public ClassPersionDetails() {
		super();
	}

	public ClassPersionDetails(String userid, String truename, String photo, String value1) {
		super();
		this.userid = userid;
		this.truename = truename;
		this.photo = photo;
		this.value1 = value1;
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

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	@Override
	public String toString() {
		return "ClassPersionDetails [userid=" + userid + ", truename=" + truename + ", photo=" + photo + ", value1="
				+ value1 + "]";
	}

}
