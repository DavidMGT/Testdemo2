package com.work.student.bean;

import java.io.Serializable;

/**
 * 通知
 *@author 左丽姬
 */
public class Notice implements Serializable {

	private String fid;// 通知ID
	private long ftime;// 通知时间
	private String fcontent;// 通知内容
	private String ftitle; // 通知标题
	private int state;// 发布状态
	private String agree;// 是否同意
	private String disargee;
	private int total;// 发布总条数
	private String count; // 判断是否回复
	private int agree_num = 0;// 同意--多少条
	private int disargee_num = 0;// 不同意--多少条
	private int noselect_num = 0;// 不选择--多少条
	private int num = 0;// 总共选择了多少条

	public Notice() {
		super();
	}

	public Notice(String fid, long ftime, String fcontent, String ftitle, int state, String agree, String disargee,
			int total, String count) {
		super();
		this.fid = fid;
		this.ftime = ftime;
		this.fcontent = fcontent;
		this.ftitle = ftitle;
		this.state = state;
		this.agree = agree;
		this.disargee = disargee;
		this.total = total;
		this.count = count;
	}

	public Notice(String fid, long ftime, String fcontent, String ftitle, int state, String agree, String disargee,
			int total, String count, int agree_num, int disargee_num, int noselect_num, int num) {
		super();
		this.fid = fid;
		this.ftime = ftime;
		this.fcontent = fcontent;
		this.ftitle = ftitle;
		this.state = state;
		this.agree = agree;
		this.disargee = disargee;
		this.total = total;
		this.count = count;
		this.agree_num = agree_num;
		this.disargee_num = disargee_num;
		this.noselect_num = noselect_num;
		this.num = num;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public long getFtime() {
		return ftime;
	}

	public void setFtime(long ftime) {
		this.ftime = ftime;
	}

	public String getFcontent() {
		return fcontent;
	}

	public void setFcontent(String fcontent) {
		this.fcontent = fcontent;
	}

	public String getFtitle() {
		return ftitle;
	}

	public void setFtitle(String ftitle) {
		this.ftitle = ftitle;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getAgree() {
		return agree;
	}

	public void setAgree(String agree) {
		this.agree = agree;
	}

	public String getDisargee() {
		return disargee;
	}

	public void setDisargee(String disargee) {
		this.disargee = disargee;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public int getAgree_num() {
		return agree_num;
	}

	public void setAgree_num(int agree_num) {
		this.agree_num = agree_num;
	}

	public int getDisargee_num() {
		return disargee_num;
	}

	public void setDisargee_num(int disargee_num) {
		this.disargee_num = disargee_num;
	}

	public int getNoselect_num() {
		return noselect_num;
	}

	public void setNoselect_num(int noselect_num) {
		this.noselect_num = noselect_num;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	@Override
	public String toString() {
		return "Notice [fid=" + fid + ", ftime=" + ftime + ", fcontent=" + fcontent + ", ftitle=" + ftitle + ", state="
				+ state + ", agree=" + agree + ", disargee=" + disargee + ", total=" + total + ", count=" + count
				+ ", agree_num=" + agree_num + ", disargee_num=" + disargee_num + ", noselect_num=" + noselect_num
				+ ", num=" + num + "]";
	}

}
