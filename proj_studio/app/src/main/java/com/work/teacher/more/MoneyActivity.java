package com.work.teacher.more;

import com.work.teacher.R;
import com.work.teacher.tool.IBaes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 我的钱包
 *@author 左丽姬
 */
public class MoneyActivity extends Activity implements OnClickListener {
	private ImageView isnet_image;
	private TextView tv_consimption_money;
	private TextView tv_deposit_money;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_money);
		initMoney();
	}

	public void initMoney() {
		// 网络判断
		IBaes.net_relative = (RelativeLayout) findViewById(R.id.net_relative);
		IBaes.net_relative.setVisibility(View.GONE);
		isnet_image = (ImageView) findViewById(R.id.isnet_image);
		if (!IBaes.isNet(this)) {
			// 网络不存在时显示
			IBaes.net_relative.setVisibility(View.VISIBLE);
		}
		isnet_image.setOnClickListener(this);
		IBaes.net_relative.setOnClickListener(this);

		// 头部设置
		ImageView top_back = (ImageView) findViewById(R.id.top_back);
		top_back.setOnClickListener(this);
		TextView top_text = (TextView) findViewById(R.id.top_text);
		top_text.setText("我的钱包");
		Button top_btn = (Button) findViewById(R.id.top_btn);
		top_btn.setVisibility(View.GONE);
		
		//页面内容
		
		tv_consimption_money = (TextView) findViewById(R.id.tv_consimption_money);
		tv_deposit_money = (TextView) findViewById(R.id.tv_deposit_money);
		
		
		RelativeLayout recharge_money_list=(RelativeLayout) findViewById(R.id.recharge_money_list);
		RelativeLayout deposit_money_list=(RelativeLayout) findViewById(R.id.deposit_money_list);
		RelativeLayout income_money_list=(RelativeLayout) findViewById(R.id.income_money_list);
		RelativeLayout depositrecord_money_list=(RelativeLayout) findViewById(R.id.depositrecord_money_list);
		RelativeLayout account_money_list=(RelativeLayout) findViewById(R.id.account_money_list);
		
		recharge_money_list.setOnClickListener(this);
		deposit_money_list.setOnClickListener(this);
		income_money_list.setOnClickListener(this);
		depositrecord_money_list.setOnClickListener(this);
		account_money_list.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent=null; 
		switch (v.getId()) {
		case R.id.isnet_image:
			IBaes.net_relative.setVisibility(View.GONE);
			break;
		case R.id.net_relative:
			// 打开设置界面
			Intent mIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
			startActivity(mIntent);
			break;
		case R.id.top_back:
			finish();
			break;
		case R.id.recharge_money_list:
			//充值
			intent=new Intent(this, MoneyAccountActivity.class);
			intent.putExtra("money_index", IBaes.MONEY_RECHARGE);
			startActivity(intent);
			break;
		case R.id.deposit_money_list:
			//提现
			intent=new Intent(this, MoneyAccountActivity.class);
			intent.putExtra("money_index", IBaes.MONEY_DEPOSIT);
			startActivity(intent);
			break;
		case R.id.income_money_list:
			//收益统计
			intent=new Intent(this, MoneyAccountActivity.class);
			intent.putExtra("money_index", IBaes.MONEY_INCOME);
			startActivity(intent);
			break;
		case R.id.depositrecord_money_list:
			//提现记录
			intent=new Intent(this, MoneyAccountActivity.class);
			intent.putExtra("money_index", IBaes.MONEY_RECORD);
			startActivity(intent);
			break;
		case R.id.account_money_list:
			//我的账户
			intent=new Intent(this, MoneyAccountActivity.class);
			intent.putExtra("money_index", IBaes.MONEY_ACCOUNT);
			startActivity(intent);
			break;
		}
	}
}
