package com.work.teacher.bean;

import java.io.Serializable;

/**
 * 历史班级
 * 
 * @author 左丽姬
 */
public class HistoryClass implements Serializable {

	private String classname;
	private String cid;
	private long startime;// 创建时间
	private long endtime;// 结束时间
	private String userid;
	private long jointime;// 加入时间
	private long quitetime;// 退出时间

	public HistoryClass() {
		super();
	}

	public HistoryClass(String classname, String cid, long startime, long endtime, String userid, long jointime,
			long quitetime) {
		super();
		this.classname = classname;
		this.cid = cid;
		this.startime = startime;
		this.endtime = endtime;
		this.userid = userid;
		this.jointime = jointime;
		this.quitetime = quitetime;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public long getStartime() {
		return startime;
	}

	public void setStartime(long startime) {
		this.startime = startime;
	}

	public long getEndtime() {
		return endtime;
	}

	public void setEndtime(long endtime) {
		this.endtime = endtime;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public long getJointime() {
		return jointime;
	}

	public void setJointime(long jointime) {
		this.jointime = jointime;
	}

	public long getQuitetime() {
		return quitetime;
	}

	public void setQuitetime(long quitetime) {
		this.quitetime = quitetime;
	}

	@Override
	public String toString() {
		return "HistoryClass [classname=" + classname + ", cid=" + cid + ", startime=" + startime + ", endtime="
				+ endtime + ", userid=" + userid + ", jointime=" + jointime + ", quitetime=" + quitetime + "]";
	}

}
