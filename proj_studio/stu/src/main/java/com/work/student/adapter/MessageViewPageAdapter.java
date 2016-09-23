package com.work.student.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * 
 * 消息viewpage
 * 
 * @author 左丽姬
 */
public class MessageViewPageAdapter extends FragmentPagerAdapter {

	private List<Fragment> fragments;

	public MessageViewPageAdapter(FragmentManager fm) {
		super(fm);
	}

	public MessageViewPageAdapter(FragmentManager fm, List<Fragment> fragments) {
		super(fm);
		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int arg0) {
		return fragments.get(arg0);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

}
