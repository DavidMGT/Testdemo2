package com.work.teacher.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 通知详情
 * 
 * @author 左丽姬
 */
public class NoticeDetails implements Serializable {

	private String fcontent;
	private String ftitle;
	private long ftime;
	private long sendtime;
	private List<FeedbackDetails> detailsl;
	private int status;
	private String zhuce;

	private String agree;
	private String disargee;
	private String tapeurl;
	private String user_name;

	public NoticeDetails() {
		super();
	}

	public NoticeDetails(int status, String zhuce, String user_name) {
		super();
		this.status = status;
		this.zhuce = zhuce;
		this.user_name = user_name;
	}

	public NoticeDetails(String fcontent, String ftitle, long ftime, long sendtime, int status, String zhuce,
			String agree, String disargee, String tapeurl, String user_name) {
		super();
		this.fcontent = fcontent;
		this.ftitle = ftitle;
		this.ftime = ftime;
		this.sendtime = sendtime;
		this.status = status;
		this.zhuce = zhuce;
		this.agree = agree;
		this.disargee = disargee;
		this.tapeurl = tapeurl;
		this.user_name = user_name;
	}

	public NoticeDetails(String fcontent, String ftitle, long ftime, long sendtime, List<FeedbackDetails> detailsl,
			int status, String zhuce, String agree, String disargee, String tapeurl, String user_name) {
		super();
		this.fcontent = fcontent;
		this.ftitle = ftitle;
		this.ftime = ftime;
		this.sendtime = sendtime;
		this.detailsl = detailsl;
		this.status = status;
		this.zhuce = zhuce;
		this.agree = agree;
		this.disargee = disargee;
		this.tapeurl = tapeurl;
		this.user_name = user_name;
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

	public long getFtime() {
		return ftime;
	}

	public void setFtime(long ftime) {
		this.ftime = ftime;
	}

	public long getSendtime() {
		return sendtime;
	}

	public void setSendtime(long sendtime) {
		this.sendtime = sendtime;
	}

	public List<FeedbackDetails> getDetailsl() {
		return detailsl;
	}

	public void setDetailsl(List<FeedbackDetails> detailsl) {
		this.detailsl = detailsl;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getZhuce() {
		return zhuce;
	}

	public void setZhuce(String zhuce) {
		this.zhuce = zhuce;
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

	public String getTapeurl() {
		return tapeurl;
	}

	public void setTapeurl(String tapeurl) {
		this.tapeurl = tapeurl;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	@Override
	public String toString() {
		return "NoticeDetails [fcontent=" + fcontent + ", ftitle=" + ftitle + ", ftime=" + ftime + ", sendtime="
				+ sendtime + ", detailsl=" + detailsl + ", status=" + status + ", zhuce=" + zhuce + ", agree=" + agree
				+ ", disargee=" + disargee + ", tapeurl=" + tapeurl + ", user_name=" + user_name + "]";
	}

}
