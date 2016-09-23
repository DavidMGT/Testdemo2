package com.work.teacher.receiver;

import com.work.teacher.tool.IBaes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * 监测网络状态是否发生变化
 * @author 左丽姬
 */
public class NetBroadCastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
//		Log.i("test", "监测网络是否发生变化");
		if (IBaes.isNet(context)) {
			IBaes.net_relative.setVisibility(View.GONE);
			//通知数据发生改变
			Intent intentCast=new Intent(IBaes.ACTION_NET);
			context.sendBroadcast(intentCast);
		}else{
			IBaes.net_relative.setVisibility(View.VISIBLE);
		}
	}

}
