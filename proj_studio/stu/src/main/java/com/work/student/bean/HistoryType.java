package com.work.student.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 历史列表分类
 * 
 * @author 左丽姬
 */
public class HistoryType implements Serializable {

	private String name;
	private List<HistoryClass> list;
	private int status;// 0.当前自定义班级,1.当前加入班级，2.用户历史创建班级，3.用户历史加入班级

	public HistoryType() {
		super();
	}

	public HistoryType(String name, List<HistoryClass> list, int status) {
		super();
		this.name = name;
		this.list = list;
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<HistoryClass> getList() {
		return list;
	}

	public void setList(List<HistoryClass> list) {
		this.list = list;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "HistoryType [name=" + name + ", list=" + list + ", status=" + status + "]";
	}

}
