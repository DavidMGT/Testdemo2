package com.work.teacher.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 作业--》》侧滑菜单章节类
 * 作业--》》知识点
 * @author 左丽姬
 */
public class LeftTree implements Serializable {

	private String id;
	private String name;// 显示文字
	private String fid;//在知识点类中保存了年级ID
	private String pid;//
	private String layer;//知识点类中保存了科目ID
	private String orderidx;
	private String visible;
	private int panduan;// （0.没有下一级，1有下一级）
	private int Level = 0;// 判断级别
	/** 是否展开 */
	private boolean flag = false;

	public LeftTree() {
		super();
	}

	public LeftTree(String id, String name, String fid, String pid, int panduan) {
		super();
		this.id = id;
		this.name = name;
		this.fid = fid;
		this.pid = pid;
		this.panduan = panduan;
	}

	public LeftTree(String id, String name, String fid, String pid, String layer, String orderidx, String visible,
			int panduan) {
		super();
		this.id = id;
		this.name = name;
		this.fid = fid;
		this.pid = pid;
		this.layer = layer;
		this.orderidx = orderidx;
		this.visible = visible;
		this.panduan = panduan;
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

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getLayer() {
		return layer;
	}

	public void setLayer(String layer) {
		this.layer = layer;
	}

	public String getOrderidx() {
		return orderidx;
	}

	public void setOrderidx(String orderidx) {
		this.orderidx = orderidx;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public int getPanduan() {
		return panduan;
	}

	public void setPanduan(int panduan) {
		this.panduan = panduan;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public int getLevel() {
		return Level;
	}

	public void setLevel(int level) {
		Level = level;
	}

	@Override
	public String toString() {
		return "LeftTree [id=" + id + ", name=" + name + ", fid=" + fid + ", pid=" + pid + ", layer=" + layer
				+ ", orderidx=" + orderidx + ", visible=" + visible + ", panduan=" + panduan + ", Level=" + Level
				+ ", flag=" + flag + "]";
	}

}
