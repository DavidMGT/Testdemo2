package com.work.teacher.bean;

import java.io.Serializable;

import android.graphics.Bitmap;

/**
 * 发布通知--》》学生老师家长对象
 * 
 * @author 左丽姬
 */
public class TeaStuPat implements Serializable {
	private String id;
	private String name;
	private String avatar;
	private String subject;
	private boolean select = false;

	public TeaStuPat() {
		super();
	}

	public TeaStuPat(String id, String name, String avatar) {
		super();
		this.id = id;
		this.name = name;
		this.avatar = avatar;
	}

	public TeaStuPat(String id, String name, String avatar, String subject) {
		super();
		this.id = id;
		this.name = name;
		this.avatar = avatar;
		this.subject = subject;
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

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public boolean isSelect() {
		return select;
	}

	public void setSelect(boolean select) {
		this.select = select;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Override
	public String toString() {
		return "TeaStuPat [id=" + id + ", name=" + name + ", avatar=" + avatar + ", select=" + select + "]";
	}

}
