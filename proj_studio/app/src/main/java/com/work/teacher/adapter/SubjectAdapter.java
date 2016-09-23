package com.work.teacher.adapter;

import java.util.HashMap;
import java.util.List;

import com.work.teacher.R;
import com.work.teacher.bean.SubAndGrade;
import com.work.teacher.more.SubjectActivity;
import com.work.teacher.tool.IBaes;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import net.tsz.afinal.FinalBitmap;

/**
 * 科目年级
 * @author 左丽姬
 */
public class SubjectAdapter extends BaseAdapter {

	private List<SubAndGrade> subGrades = null;

	private Context context;
	private Hodler hodler;
	private String type = "";

	public SubjectAdapter() {
		super();
	}

	public SubjectAdapter(Context context, List<SubAndGrade> subGrades, String type) {
		super();
		this.context = context;
		this.subGrades = subGrades;
		this.type = type;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return subGrades.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return subGrades.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		hodler = null;
		if (convertView == null) {
			if (type.equals("grade")) {
				convertView = LayoutInflater.from(context).inflate(R.layout.gradview_btn, null);
			} else {
				convertView = LayoutInflater.from(context).inflate(R.layout.grideview_sub_item, null);
			}

			hodler = new Hodler(convertView);
			convertView.setTag(hodler);
		} else {
			hodler = (Hodler) convertView.getTag();
		}
		SubAndGrade sub = subGrades.get(position);
		if (type.equals("grade")) {
			if (sub.isIscheck() == true) {
				hodler.gridview_item.setBackgroundResource(R.drawable.shape_feedback_select);
				hodler.gridview_item.setTextColor(context.getResources().getColor(R.color.green));
			} else {
				hodler.gridview_item.setBackgroundResource(R.drawable.shape_feedback_noselect);
				hodler.gridview_item.setTextColor(context.getResources().getColor(R.color.weakgrey));
			}
		} else {
			if (sub != null) {

				if (!"".equals(sub.getUrl())) {
					FinalBitmap.create(context).display(hodler.iv_sub_item, IBaes.URL+sub.getUrl());
				}else{
					hodler.iv_sub_item.setImageResource(R.drawable.geography);
				}
				if (sub.isIscheck() == true) {
					hodler.fl_item.setBackgroundResource(R.drawable.sub_bg);
					hodler.iv_sub_yes.setVisibility(View.VISIBLE);
				} else {
					hodler.fl_item.setBackgroundResource(R.drawable.class_project_yes);
					hodler.iv_sub_yes.setVisibility(View.GONE);
				}
			}
		}
		if(sub!=null){
			hodler.gridview_item.setText(sub.getName());
		}
		return convertView;
	}

	class Hodler {
		public TextView gridview_item;
		private FrameLayout fl_item;
		private ImageView iv_sub_item;
		private ImageView iv_sub_yes;

		public Hodler(View v) {
			if (type.equals("grade")) {
				gridview_item = (TextView) v.findViewById(R.id.gridview_item);
			} else {
				gridview_item = (TextView) v.findViewById(R.id.tv_sub_item);
				fl_item = (FrameLayout) v.findViewById(R.id.fl_item);
				iv_sub_item = (ImageView) v.findViewById(R.id.iv_sub_item);
				iv_sub_yes = (ImageView) v.findViewById(R.id.iv_sub_yes);
			}
		}
	}
}
