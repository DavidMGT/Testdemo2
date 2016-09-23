package com.work.student.bean;

import java.io.Serializable;

/**
 * 个人资料
 *@author 左丽姬
 */
public class PersonalBean implements Serializable {

	private int status;
	private String userId;
	private String userName;
	private String avatar;
	private String subjectId;
	private String subject;
	private String course_gradeId;
	private String course_grade;
	private String schoolCode;
	private String school;
	private String grade;
	private String zhuce;
	private String mobile;
	
	public PersonalBean() {
		super();
	}
	
	public PersonalBean(int status, String userId, String userName, String avatar, String subjectId, String subject,
			String course_gradeId, String course_grade, String schoolCode, String school, String grade, String zhuce,
			String mobile) {
		super();
		this.status = status;
		this.userId = userId;
		this.userName = userName;
		this.avatar = avatar;
		this.subjectId = subjectId;
		this.subject = subject;
		this.course_gradeId = course_gradeId;
		this.course_grade = course_grade;
		this.schoolCode = schoolCode;
		this.school = school;
		this.grade = grade;
		this.zhuce = zhuce;
		this.mobile = mobile;
	}



	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getCourse_gradeId() {
		return course_gradeId;
	}
	public void setCourse_gradeId(String course_gradeId) {
		this.course_gradeId = course_gradeId;
	}
	public String getCourse_grade() {
		return course_grade;
	}
	public void setCourse_grade(String course_grade) {
		this.course_grade = course_grade;
	}
	public String getSchoolCode() {
		return schoolCode;
	}
	public void setSchoolCode(String schoolCode) {
		this.schoolCode = schoolCode;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getZhuce() {
		return zhuce;
	}
	public void setZhuce(String zhuce) {
		this.zhuce = zhuce;
	}
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Override
	public String toString() {
		return "PersonalBean [status=" + status + ", userId=" + userId + ", userName=" + userName + ", avatar=" + avatar
				+ ", subjectId=" + subjectId + ", subject=" + subject + ", course_gradeId=" + course_gradeId
				+ ", course_grade=" + course_grade + ", schoolCode=" + schoolCode + ", school=" + school + ", grade="
				+ grade + ", zhuce=" + zhuce + ", mobile=" + mobile + "]";
	}
	
	
	
}
