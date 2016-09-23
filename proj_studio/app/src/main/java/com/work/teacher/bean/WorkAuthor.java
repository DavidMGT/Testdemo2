package com.work.teacher.bean;

import java.io.Serializable;

/***
 * 作业创建者信息
 * @author 左丽姬
 */
public class WorkAuthor implements Serializable{
	private String id;
	private String name;
	private String authorid;
	private String desc;
	private double zhongfen;
	private long createtime;
	private long lastupdatetime;
	private long endtime;
	private String subjectid;
	private String gradeid;
	private String chapterdid;
	
	public WorkAuthor() {
		super();
	}
	public WorkAuthor(String id, String name, String authorid, String desc, double zhongfen, long createtime,
			long lastupdatetime) {
		super();
		this.id = id;
		this.name = name;
		this.authorid = authorid;
		this.desc = desc;
		this.zhongfen = zhongfen;
		this.createtime = createtime;
		this.lastupdatetime = lastupdatetime;
	}

	public WorkAuthor(String id, String name, String authorid, String desc, double zhongfen, long createtime, long lastupdatetime,long endtime, String subjectid, String gradeid, String chapterdid) {
		this.id = id;
		this.name = name;
		this.authorid = authorid;
		this.desc = desc;
		this.zhongfen = zhongfen;
		this.createtime = createtime;
		this.lastupdatetime = lastupdatetime;
		this.endtime=endtime;
		this.subjectid = subjectid;
		this.gradeid = gradeid;
		this.chapterdid = chapterdid;
	}

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
	public String getAuthorid() {
		return authorid;
	}
	public void setAuthorid(String authorid) {
		this.authorid = authorid;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public double getZhongfen() {
		return zhongfen;
	}
	public void setZhongfen(double zhongfen) {
		this.zhongfen = zhongfen;
	}
	public long getCreatetime() {
		return createtime;
	}
	public void setCreatetime(long createtime) {
		this.createtime = createtime;
	}
	public long getLastupdatetime() {
		return lastupdatetime;
	}
	public void setLastupdatetime(long lastupdatetime) {
		this.lastupdatetime = lastupdatetime;
	}

	public String getSubjectid() {
		return subjectid;
	}

	public void setSubjectid(String subjectid) {
		this.subjectid = subjectid;
	}

	public String getGradeid() {
		return gradeid;
	}

	public void setGradeid(String gradeid) {
		this.gradeid = gradeid;
	}

	public String getChapterdid() {
		return chapterdid;
	}

	public void setChapterdid(String chapterdid) {
		this.chapterdid = chapterdid;
	}

	public long getEndtime() {
		return endtime;
	}

	public void setEndtime(long endtime) {
		this.endtime = endtime;
	}

	@Override
	public String toString() {
		return "WorkAuthor [id=" + id + ", name=" + name + ", authorid=" + authorid + ", desc=" + desc + ", zhongfen="
				+ zhongfen + ", createtime=" + createtime + ", lastupdatetime=" + lastupdatetime + "]";
	}
}
