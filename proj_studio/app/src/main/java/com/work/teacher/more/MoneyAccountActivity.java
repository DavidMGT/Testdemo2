package com.work.teacher.more;

import com.work.teacher.MoreActivity;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 充值，提现，收益统计，提现记录，我的账户
 * @author 左丽姬
 */
public class MoneyAccountActivity extends Activity implements OnClickListener {
	private ImageView isnet_image;
	private int money_index;
	
	//充值页面
	private EditText tv_alipay_money;
	private RelativeLayout rl_alipay, rl_weixin;
	private ImageView iv_select_alipay,iv_select_weixin;
	private Button btn_recharge_commit;
	private int pay=0;//选择支付方式（0,支付宝支付;1,微信支付）
	
	//提现页面
	private EditText deposit_alipay_money;
	private TextView can_deposit_money;
	private Button all_deposit_money, btn_deposit_commit;
	private float deposit_money=0;//保存账户总余额
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_money_account);
		initMoneyAccount();
	}

	public void initMoneyAccount() {
		money_index=getIntent().getIntExtra("money_index", 0);
		//网络判断
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
		Button top_btn = (Button) findViewById(R.id.top_btn);
		top_btn.setVisibility(View.GONE);
		
		
		//内容
		LinearLayout ll_recharge=(LinearLayout) findViewById(R.id.ll_recharge);
		LinearLayout ll_deposit=(LinearLayout) findViewById(R.id.ll_deposit);
		LinearLayout ll_income=(LinearLayout) findViewById(R.id.ll_income);
		LinearLayout ll_record=(LinearLayout) findViewById(R.id.ll_record);
		LinearLayout ll_account=(LinearLayout) findViewById(R.id.ll_account);
		switch (money_index) {
		case 0:
			top_text.setText("充值");
			ll_recharge.setVisibility(View.VISIBLE);
			tv_alipay_money = (EditText) findViewById(R.id.tv_alipay_money);//金额
			tv_alipay_money.setHintTextColor(getResources().getColor(R.color.weakgrey));
			rl_alipay = (RelativeLayout) findViewById(R.id.rl_alipay);//支付宝支付
			rl_weixin = (RelativeLayout) findViewById(R.id.rl_weixin);//微信支付
			iv_select_alipay = (ImageView) findViewById(R.id.iv_select_alipay);
			iv_select_weixin = (ImageView) findViewById(R.id.iv_select_weixin);
			btn_recharge_commit = (Button) findViewById(R.id.btn_recharge_commit);//确定支付
			rl_alipay.setOnClickListener(this);
			rl_weixin.setOnClickListener(this);
			btn_recharge_commit.setOnClickListener(this);
			break;
		case 1:
			top_text.setText("提现");
			ll_deposit.setVisibility(View.VISIBLE);
			deposit_alipay_money = (EditText) findViewById(R.id.deposit_alipay_money);//提现金额
			deposit_alipay_money.setHintTextColor(getResources().getColor(R.color.weakgrey));
			can_deposit_money = (TextView) findViewById(R.id.can_deposit_money);//账户余额（现币100,可换成80元）
			all_deposit_money = (Button) findViewById(R.id.all_deposit_money);//全部提现
			btn_deposit_commit = (Button) findViewById(R.id.btn_deposit_commit);//确定提现
			all_deposit_money.setOnClickListener(this);
			btn_deposit_commit.setOnClickListener(this);
			queryAccountMoney();//查询账户余额
			break;
		case 2:
			top_text.setText("收益统计");
			ll_income.setVisibility(View.VISIBLE);
			break;
		case 3:
			top_text.setText("提现记录");
			ll_record.setVisibility(View.VISIBLE);
			break;
		case 4:
			top_text.setText("我的账户");
			ll_account.setVisibility(View.VISIBLE);
			break;
		}
		
	}
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
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
		case R.id.rl_alipay:
			//充值--》》支付宝支付选择
			pay=0;
			iv_select_alipay.setImageResource(R.drawable.select_pay_yes);
			iv_select_weixin.setImageResource(R.drawable.select_pay_no);
			break;
		case R.id.rl_weixin:
			//充值--》》微信支付选择
			pay=1;
			iv_select_alipay.setImageResource(R.drawable.select_pay_no);
			iv_select_weixin.setImageResource(R.drawable.select_pay_yes);
			break;
		case R.id.btn_recharge_commit:
			//充值--》》确认充值
			rechargeCommit();
			break;
		case R.id.all_deposit_money:
			//提现--》》全部提现
			if(deposit_money>0)
				deposit_alipay_money.setText(deposit_money+"");
			break;
		case R.id.btn_deposit_commit:
			//提现--》》确定提现
			depositCommit();
			break;
		}
	}
	
	/**充值--》》确定充值*/
	public void rechargeCommit(){
		String money=tv_alipay_money.getText().toString();//充值金额
		if(!IBaes.isNet(this)){
			IBaes.toastShow(this, "网络不给力,请稍后...");
			return ;
		}
		if(Integer.parseInt(money)<=0){
			IBaes.toastShow(this, "金额不能小于1");
			return ;
		}
		
	}
	
	/**提现--》》查询账户总余额*/
	public void queryAccountMoney(){
		if(!IBaes.isNet(this)){
			IBaes.toastShow(this, "网络不给力,请稍后...");
			return ;
		}
		deposit_money=80;//网络获取账户余额
		can_deposit_money.setText("现币100,可换成80元");//计算现金赋值给显示按钮
		if(deposit_money==0){
			all_deposit_money.setText("");
		}
	}
	/**提现--》》确定提现*/
	public void depositCommit(){
		String money=deposit_alipay_money.getText().toString();//充值金额
		if(!IBaes.isNet(this)){
			IBaes.toastShow(this, "网络不给力,请稍后...");
			return ;
		}
		if(Integer.parseInt(money)<=0){
			IBaes.toastShow(this, "金额不能小于1");
			return ;
		}
		
	}
}
