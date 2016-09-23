package com.work.teacher.bean;

import java.io.Serializable;

/**
 * 作业-》设置属性--》》作业类型and作业模式
 * 
 * @author 左丽姬
 */
public class WorkModel implements Serializable {
	private String id;
	private String cname;
	private String pid;
	private String path;
	private String cs;
	private boolean flag = false;

	public WorkModel() {
		super();
	}

	public WorkModel(String id, String cname, String pid, String path, String cs) {
		super();
		this.id = id;
		this.cname = cname;
		this.pid = pid;
		this.path = path;
		this.cs = cs;
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getCs() {
		return cs;
	}

	public void setCs(String cs) {
		this.cs = cs;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	@Override
	public String toString() {
		return "WorkModel [id=" + id + ", cname=" + cname + ", pid=" + pid + ", path=" + path + ", cs=" + cs + ", flag="
				+ flag + "]";
	}
}
