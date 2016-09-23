package com.work.teacher.more;

import com.work.teacher.R;
import com.work.teacher.tool.IBaes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 反馈
 *@author 左丽姬
 */

public class FeedBackActivity extends Activity implements OnClickListener {
	
	private ImageView top_back, isnet_image;
	private Button error_feedback, problem_feedback, idea_feedback, feedback;
	private EditText depict_feedback;
	private int type=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
		initFeedback();
	}
	
	public void initFeedback(){
		//顶部设置
		top_back = (ImageView) findViewById(R.id.top_back);
		top_back.setOnClickListener(this);
		TextView top_text=(TextView) findViewById(R.id.top_text);
		top_text.setText("反馈");
		Button top_btn=(Button) findViewById(R.id.top_btn);
		top_btn.setVisibility(View.GONE);
		
		// 网络判断
		IBaes.net_relative = (RelativeLayout) findViewById(R.id.net_relative);
		IBaes.net_relative.setVisibility(View.GONE);
		isnet_image = (ImageView) findViewById(R.id.isnet_image);
		if(!IBaes.isNet(this)){
			//网络不存在时显示
			IBaes.net_relative.setVisibility(View.VISIBLE);
		}
		isnet_image.setOnClickListener(this);
		IBaes.net_relative.setOnClickListener(this);
		
		//页面内容
		
		error_feedback = (Button) findViewById(R.id.error_feedback);
		problem_feedback = (Button) findViewById(R.id.problem_feedback);
		idea_feedback = (Button) findViewById(R.id.idea_feedback);
		feedback = (Button) findViewById(R.id.feedback);
		depict_feedback = (EditText) findViewById(R.id.depict_feedback);
		depict_feedback.setHintTextColor(getResources().getColor(R.color.weakgrey));
		
		error_feedback.setOnClickListener(this);
		problem_feedback.setOnClickListener(this);
		idea_feedback.setOnClickListener(this);
		feedback.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent;		
		switch (v.getId()) {
		case R.id.isnet_image:
			IBaes.net_relative.setVisibility(View.GONE);
			break;
		case R.id.net_relative:
			//打开设置界面
			intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
             startActivity(intent);
			break;
		case R.id.top_back:
			finish();// 返回
			break;
		case R.id.error_feedback:
			//题目报错
			type=0;
			selectFeedback();
			error_feedback.setBackgroundResource(R.drawable.shape_feedback_select);
			error_feedback.setTextColor(getResources().getColor(R.color.green));
			break;
		case R.id.problem_feedback:
			//问题
			type=1;
			selectFeedback();
			problem_feedback.setBackgroundResource(R.drawable.shape_feedback_select);
			problem_feedback.setTextColor(getResources().getColor(R.color.green));
			break;
		case R.id.idea_feedback:
			//意见
			type=2;
			selectFeedback();
			idea_feedback.setBackgroundResource(R.drawable.shape_feedback_select);
			idea_feedback.setTextColor(getResources().getColor(R.color.green));
			break;
		case R.id.feedback:
			//提交
			break;
		}
	}
	
	/**
	 * 选择反馈类型
	 */
	public void selectFeedback(){
		error_feedback.setBackgroundResource(R.drawable.shape_feedback_noselect);
		problem_feedback.setBackgroundResource(R.drawable.shape_feedback_noselect);
		idea_feedback.setBackgroundResource(R.drawable.shape_feedback_noselect);
		
		error_feedback.setTextColor(getResources().getColor(R.color.weakgrey));
		problem_feedback.setTextColor(getResources().getColor(R.color.weakgrey));
		idea_feedback.setTextColor(getResources().getColor(R.color.weakgrey));
	}

}
