package com.work.teacher.work.adapter;

import java.util.List;

import com.work.teacher.R;
import com.work.teacher.bean.LeftSubject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/***
 * 作业-》设置属性--》》模式、类别布局适配器
 * @author 左丽姬
 */
public class ModeTypeAdapter extends BaseAdapter {

	private Context context;
	private List<LeftSubject> lists;
	
	public ModeTypeAdapter(Context context, List<LeftSubject> lists) {
		super();
		this.context = context;
		this.lists = lists;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lists.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return lists.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ModeHodler hodler=null;
		if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(R.layout.gradview_btn, null);
				hodler=new ModeHodler(convertView);
				convertView.setTag(hodler);
		}else{
			hodler=(ModeHodler) convertView.getTag();
		}
		
		LeftSubject mode_type=lists.get(position);
		if(mode_type!=null){
			hodler.gridview_item.setText(mode_type.getCname());
			if(mode_type.isFlag()){
				hodler.gridview_item.setBackgroundResource(R.drawable.shape_feedback_select);
				hodler.gridview_item.setTextColor(context.getResources().getColor(R.color.green));
			} else {
				hodler.gridview_item.setBackgroundResource(R.drawable.shape_feedback_noselect);
				hodler.gridview_item.setTextColor(context.getResources().getColor(R.color.weakgrey));
			}
		}else{
			hodler.gridview_item.setBackgroundResource(R.drawable.image_btn);
			hodler.gridview_item.setText("");
		}
		return convertView;
	}
	
	class ModeHodler {
		TextView gridview_item;
		public ModeHodler(View v){
			gridview_item = (TextView) v.findViewById(R.id.gridview_item);
		}
	}
}
