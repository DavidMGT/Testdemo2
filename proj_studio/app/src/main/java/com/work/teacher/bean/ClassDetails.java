package com.work.teacher.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 班级详情
 * 
 * @author 左丽姬
 */
public class ClassDetails implements Serializable {

	private String name;
	private int status;// 0.老师，1.学生，2.家长
	private List<ClassPersionDetails> details;
	private int tea_count;
	private int student_count;
	private int parent_count;
	private String cid;

	public ClassDetails() {
		super();
	}

	public ClassDetails(String name, int status, List<ClassPersionDetails> details, int tea_count, int student_count,
			int parent_count) {
		super();
		this.name = name;
		this.status = status;
		this.details = details;
		this.tea_count = tea_count;
		this.student_count = student_count;
		this.parent_count = parent_count;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<ClassPersionDetails> getDetails() {
		return details;
	}

	public void setDetails(List<ClassPersionDetails> details) {
		this.details = details;
	}

	public int getTea_count() {
		return tea_count;
	}

	public void setTea_count(int tea_count) {
		this.tea_count = tea_count;
	}

	public int getStudent_count() {
		return student_count;
	}

	public void setStudent_count(int student_count) {
		this.student_count = student_count;
	}

	public int getParent_count() {
		return parent_count;
	}

	public void setParent_count(int parent_count) {
		this.parent_count = parent_count;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	@Override
	public String toString() {
		return "ClassDetails [name=" + name + ", status=" + status + ", details=" + details + ", tea_count=" + tea_count
				+ ", student_count=" + student_count + ", parent_count=" + parent_count + "]";
	}

}
