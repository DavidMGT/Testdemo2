package com.work.teacher.bean;

import java.io.Serializable;

/**
 * 作业--》》侧滑菜单---》》学科列表
 * 
 * @author 左丽姬
 */
public class LeftSubject implements Serializable {
	private String id;
	private String cname;
	private String pid;
	private String cs;
	private String path;
	private boolean flag = false;

	public LeftSubject() {
		super();
	}

	public LeftSubject(String id, String cname, String pid, String cs, String path) {
		super();
		this.id = id;
		this.cname = cname;
		this.pid = pid;
		this.cs = cs;
		this.path = path;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getCs() {
		return cs;
	}

	public void setCs(String cs) {
		this.cs = cs;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	@Override
	public String toString() {
		return "LeftSubject [id=" + id + ", cname=" + cname + ", pid=" + pid + ", cs=" + cs + ", path=" + path
				+ ", flag=" + flag + "]";
	}

}
