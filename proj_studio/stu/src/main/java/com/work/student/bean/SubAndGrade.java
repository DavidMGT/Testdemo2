package com.work.student.bean;

import java.io.Serializable;

/**
 *年级科目 ,班级列表
 *@author 左丽姬
 */
public class SubAndGrade implements Serializable {

	public String id;
	public String name;
	public boolean ischeck=false;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isIscheck() {
		return ischeck;
	}
	public void setIscheck(boolean ischeck) {
		this.ischeck = ischeck;
	}
	@Override
	public String toString() {
		return "SubAndGrade [id=" + id + ", name=" + name + ", ischeck=" + ischeck + "]";
	}
	
	
}
