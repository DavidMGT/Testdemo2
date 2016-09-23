package com.work.teacher.fragment;

import com.work.teacher.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 课程Fragment
 *@author 左丽姬
 */
public class MessageClassFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.fragment_messageclass, container,false);
		
		return view;
	}
}
