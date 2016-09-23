package com.work.teacher.work.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.work.teacher.R;
import com.work.teacher.bean.LeftTree;

import java.util.ArrayList;
import java.util.List;

/**
 * 作业-》侧滑菜单--》》学科年级选择
 * 
 * @author 左丽姬
 */
public class KnowHowAdapter extends BaseAdapter {
	private Context context;
	private List<LeftTree> trees;
	private List<LeftTree> treesChild;
	private GradeMenuHold hold;
	private int index = 0;
	/** item的行首缩进基数 */
	private int indentionBase;

	public KnowHowAdapter(Context context, List<LeftTree> trees, List<LeftTree> treesChild) {
		super();
		this.context = context;
		this.trees = trees;
		this.trees = trees;
		indentionBase = 10;
	}

	public ArrayList<LeftTree> getTopNodes() {
		return (ArrayList<LeftTree>) trees;
	}

	public ArrayList<LeftTree> getAllNodes() {
		return (ArrayList<LeftTree>) treesChild;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return trees.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return trees.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		hold = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_leftmenu_list, null);
			hold = new GradeMenuHold(convertView);
			convertView.setTag(hold);
		} else {
			hold = (GradeMenuHold) convertView.getTag();
		}
		LeftTree tree = trees.get(position);
		int level = tree.getLevel();
		if (level == 0)
			hold.ll_leftmenu.setPadding(0, hold.ll_leftmenu.getPaddingTop(), hold.ll_leftmenu.getPaddingRight(),
					hold.ll_leftmenu.getPaddingBottom());
		else
			hold.ll_leftmenu.setPadding(indentionBase * (level + 2), hold.ll_leftmenu.getPaddingTop(),
					hold.ll_leftmenu.getPaddingRight(), hold.ll_leftmenu.getPaddingBottom());

		if (tree.isFlag()) {
			if (level == 0) {
				hold.ll_leftmenu.setBackgroundResource(R.color.oldgreen);
				hold.ll_childleftmenu.setBackgroundResource(R.color.oldgreen);
			} else
				hold.ll_childleftmenu.setBackgroundResource(R.drawable.worksub_bg);
			if ("0".equals(tree.getPid())) {

				hold.select_leftmenu.setBackgroundResource(R.drawable.select_lefttree);
				hold.select_leftmenu.setVisibility(View.VISIBLE);
				hold.child_leftmenu.setVisibility(View.GONE);
				if (tree.getPanduan() == 0) {
					hold.select_leftmenu.setVisibility(View.GONE);
				}
			} else {
				hold.child_leftmenu.setImageResource(R.drawable.spinner_donw);
				hold.select_leftmenu.setVisibility(View.GONE);
				hold.child_leftmenu.setVisibility(View.VISIBLE);
				if (tree.getPanduan() == 0) {
					hold.child_leftmenu.setVisibility(View.GONE);
				}
			}

		} else {
			hold.ll_leftmenu.setBackgroundResource(R.color.whitegreen);
			hold.ll_childleftmenu.setBackgroundResource(R.color.whitegreen);
			if ("0".equals(tree.getPid())) {
				hold.select_leftmenu.setBackgroundResource(R.color.whitegreen);
				hold.select_leftmenu.setVisibility(View.VISIBLE);
				hold.child_leftmenu.setVisibility(View.GONE);
				if (tree.getPanduan() == 0) {
					hold.select_leftmenu.setVisibility(View.GONE);
				}
			} else {
				hold.child_leftmenu.setImageResource(R.drawable.open);
				hold.select_leftmenu.setVisibility(View.GONE);
				hold.child_leftmenu.setVisibility(View.VISIBLE);
				if (tree.getPanduan() == 0) {
					hold.child_leftmenu.setVisibility(View.GONE);
				}
			}

		}

		hold.name_lefttree.setText(tree.getName());
		return convertView;
	}

	class GradeMenuHold {
		private LinearLayout ll_leftmenu, ll_childleftmenu;
		private TextView select_leftmenu, name_lefttree;
		private ImageView child_leftmenu;

		public GradeMenuHold(View v) {
			ll_leftmenu = (LinearLayout) v.findViewById(R.id.ll_leftmenu);
			ll_childleftmenu = (LinearLayout) v.findViewById(R.id.ll_childleftmenu);
			select_leftmenu = (TextView) v.findViewById(R.id.select_leftmenu);
			child_leftmenu = (ImageView) v.findViewById(R.id.child_leftmenu);
			name_lefttree = (TextView) v.findViewById(R.id.name_lefttree);
		}
	}

}
