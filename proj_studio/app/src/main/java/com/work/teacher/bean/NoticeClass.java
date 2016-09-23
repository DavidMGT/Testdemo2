package com.work.teacher.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 通知发布-->>班级实体类
 * 
 * @author 左丽姬
 */
public class NoticeClass implements Serializable {
	private String cid;
	private String classname;
	private String t_data;// 班级老师的个数
	private String x_data;// 班级学生的个数
	private String p_data;// 班级学生家长的个数
	private boolean select = false;// 选择班级
	private String t_select = "0";// 选择了多少个老师
	private String x_select = "0";// 选择了多少个学生
	private String p_select = "0";// 选择了多少个家长
	public ArrayList<String> teas = null;
	public ArrayList<String> stus = null;
	public ArrayList<String> pats = null;

	public NoticeClass() {
		super();
	}

	public NoticeClass(String cid, String classname, String t_data, String x_data, String p_data, boolean select) {
		super();
		this.cid = cid;
		this.classname = classname;
		this.t_data = t_data;
		this.x_data = x_data;
		this.p_data = p_data;
		this.select = select;
	}

	public NoticeClass(String cid, String classname, String t_data, String x_data, String p_data, boolean select,
			String t_select, String x_select, String p_select) {
		super();
		this.cid = cid;
		this.classname = classname;
		this.t_data = t_data;
		this.x_data = x_data;
		this.p_data = p_data;
		this.select = select;
		this.t_select = t_select;
		this.x_select = x_select;
		this.p_select = p_select;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getT_data() {
		return t_data;
	}

	public void setT_data(String t_data) {
		this.t_data = t_data;
	}

	public String getX_data() {
		return x_data;
	}

	public void setX_data(String x_data) {
		this.x_data = x_data;
	}

	public String getP_data() {
		return p_data;
	}

	public void setP_data(String p_data) {
		this.p_data = p_data;
	}

	public boolean isSelect() {
		return select;
	}

	public void setSelect(boolean select) {
		this.select = select;
	}

	public String getT_select() {
		return t_select;
	}

	public void setT_select(String t_select) {
		this.t_select = t_select;
	}

	public String getX_select() {
		return x_select;
	}

	public void setX_select(String x_select) {
		this.x_select = x_select;
	}

	public String getP_select() {
		return p_select;
	}

	public void setP_select(String p_select) {
		this.p_select = p_select;
	}

	@Override
	public String toString() {
		return "NoticeClass [cid=" + cid + ", classname=" + classname + ", t_data=" + t_data + ", x_data=" + x_data
				+ ", p_data=" + p_data + ", select=" + select + ", t_select=" + t_select + ", x_select=" + x_select
				+ ", p_select=" + p_select + "]";
	}

}
