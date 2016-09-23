package com.work.teacher.more;

import com.work.teacher.R;
import com.work.teacher.RegisterActivity;
import com.work.teacher.tool.IBaes;
import com.work.teacher.tool.JsonData;
import com.work.teacher.tool.SPUtils;
import com.work.teacher.tool.ServecHttp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/**
 * 修改姓名
 * @author 左丽姬
 */
public class UpdateNameActivity extends Activity implements OnClickListener {

	private ImageView isnet_image;
	private EditText update_name;
	private ImageView update_cancel;
	private ServecHttp servecHttp;
	private JsonData jsonDate;
	private FinalHttp finalHttp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_username);
		initUpdateName();
	}
	
	public void initUpdateName(){
		
		servecHttp=new ServecHttp();
		jsonDate=new JsonData();
		finalHttp=new FinalHttp();
		
		//顶部设置
		ImageView top_back=(ImageView) findViewById(R.id.top_back);
		top_back.setOnClickListener(this);
		TextView top_text=(TextView) findViewById(R.id.top_text);
		top_text.setText("修改姓名");
		Button top_btn=(Button) findViewById(R.id.top_btn);
		top_btn.setText("确认");
		
		//网络判断
		IBaes.net_relative = (RelativeLayout) findViewById(R.id.net_relative);
		IBaes.net_relative.setVisibility(View.GONE);
		isnet_image = (ImageView) findViewById(R.id.isnet_image);
		if(!IBaes.isNet(this)){
			//网络不存在时显示
			IBaes.net_relative.setVisibility(View.VISIBLE);
		}
		isnet_image.setOnClickListener(this);
		IBaes.net_relative.setOnClickListener(this);
		
		update_name = (EditText) findViewById(R.id.update_name);
		update_name.setHintTextColor(getResources().getColor(R.color.whitegrey));
		update_cancel = (ImageView) findViewById(R.id.update_cancel);
		update_cancel.setOnClickListener(this);
		top_btn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.isnet_image:
			IBaes.net_relative.setVisibility(View.GONE);
			break;
		case R.id.net_relative:
			//打开设置界面
			 Intent mIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
             startActivity(mIntent);
			break;
		case R.id.top_back:
			//返回
			finish();
			break ;
		case R.id.update_cancel:
			update_name.setText("");
			break;
		case R.id.top_btn:
			//修改
			if(!IBaes.isNet(this)){
				IBaes.toastShow(UpdateNameActivity.this, "网络不给力,请稍后...");
				return ;
			}
			String name=update_name.getText().toString();
			if("".equals(name.trim())){
				IBaes.toastShow(this, "姓名不能为空,请填写...");
				return ;
			}
			String key = SPUtils.get(this, "key", "").toString();
			String userId = SPUtils.get(this, "userId", "").toString();
			AjaxParams params=servecHttp.updatePersonalName(key, userId, name);
			
			finalHttp.post(IBaes.PERSONAL_UPDATE_NAME,params, new AjaxCallBack<Object>() {
				@Override
				public void onSuccess(Object t) {
					// TODO Auto-generated method stub
					super.onSuccess(t);
					Log.i("test", t.toString());
					int status =jsonDate.jsonInt(t.toString());
					handler.sendEmptyMessage(status);
				}
			});
			break;
		}
	}
	
	Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				IBaes.toastShow(UpdateNameActivity.this, "修改失败");
				break;
			case 1:
				IBaes.toastShow(UpdateNameActivity.this, "修改成功");
				Intent intent=new Intent(IBaes.ACTION_UPDATE_PERSONAL);
				sendBroadcast(intent);
				finish();
				break;
			case 2:
				IBaes.toastShow(UpdateNameActivity.this, "用户不存在");
				break;
			}
		}
	};
}
