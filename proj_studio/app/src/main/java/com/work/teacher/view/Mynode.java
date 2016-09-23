package com.work.teacher.view;

import java.util.ArrayList;
import java.util.List;


import android.util.Log;

public class Mynode {
	private int id;
	/**
	 * 鏍硅妭鐐筽Id涓�0
	 */
	private int pId = 0;

	private String name;

	/**
	 * 褰撳墠鐨勭骇鍒�
	 */
	private int level;

	/**
	 * 鏄惁灞曞紑
	 */
	private boolean isExpand = false;

	private int icon;

	/**
	 * 涓嬩竴绾х殑瀛怤ode
	 */
	private List<Mynode> children = new ArrayList<Mynode>();

	/**
	 * 鐖禢ode
	 */
	private Mynode parent;

	public Mynode()
	{
	}

	public Mynode(int id, int pId, String name)
	{
		super();
		this.id = id;
		this.pId = pId;
		this.name = name;
	}

	public int getIcon()
	{
		return icon;
	}

	public void setIcon(int icon)
	{
		this.icon = icon;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getpId()
	{
		return pId;
	}

	public void setpId(int pId)
	{
		this.pId = pId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setLevel(int level)
	{
		this.level = level;
	}

	public boolean isExpand()
	{
		return isExpand;
	}

	public void setExpand(boolean isExpand)
	{
		this.isExpand = isExpand;
		if (!isExpand)
		{

			Log.e("TAG", name + " , " + "shousuo ");
			for (Mynode node : children)
			{
				node.setExpand(isExpand);
			}
		}
	}

	public List<Mynode> getChildren()
	{
		return children;
	}

	public void setChildren(List<Mynode> children)
	{
		this.children = children;
	}

	public Mynode getParent()
	{
		return parent;
	}

	public void setParent(Mynode parent)
	{
		this.parent = parent;
	}

	/**
	 * 鏄惁涓鸿窡鑺傜偣
	 * 
	 * @return
	 */
	public boolean isRoot()
	{
		return parent == null;
	}

	/**
	 * 鍒ゆ柇鐖惰妭鐐规槸鍚﹀睍寮�
	 * 
	 * @return
	 */
	public boolean isParentExpand()
	{
		if (parent == null)
			return false;
		return parent.isExpand();
	}

	/**
	 * 鏄惁鏄彾瀛愮晫鐐�
	 * 
	 * @return
	 */
	public boolean isLeaf()
	{
		return children.size() == 0;
	}

	/**
	 * 鑾峰彇level
	 */
	public int getLevel()
	{
		return parent == null ? 0 : parent.getLevel() + 1;
	}

	@Override
	public String toString()
	{
		return "Node [name=" + name + ", isExpand=" + isExpand + ", children="
				+ children.size() + ", parent="
				+ (parent == null ? "" : parent.name) + ", icon=" + icon+ "]";
	}

}
