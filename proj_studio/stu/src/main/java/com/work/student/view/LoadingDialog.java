package com.work.student.view;

import java.util.Timer;
import java.util.TimerTask;

import com.work.student.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class LoadingDialog extends Dialog implements OnDismissListener{

	public static final int SUCCESS =1;
	public static final int FAILURE =2;
	public static final int WARN =3;
	private Activity activity;
    private TextView msg_txt;
    private ProgressBar probar;
    private ImageView tag_img;
    private int width;
    private String message = null;
    private Drawable success_da,fail_da,warn_da;
    private LinearLayout.LayoutParams params;
    private OnLoadingDialogResultListener listener;
    private int post_time =0;
    private int tag =0;
    private int state;
	private CountDownTimerLoad load;
    public interface OnLoadingDialogResultListener{
    	void dialogResult(int tag,int state);
    }
    
    public LoadingDialog(Activity context) {
        super(context,R.style.LoadDialog);
        this.activity =context;
        message = "正在加载...";
        width =(int)(ViewUtil.getScreenWidth(context) *2 /5.0);
        success_da =context.getResources().getDrawable(R.drawable.common_dialog_success_icon);
        fail_da =context.getResources().getDrawable(R.drawable.common_dialog_fail_icon);
        warn_da =context.getResources().getDrawable(R.drawable.common_dialog_warn_icon);
        params =new LinearLayout.LayoutParams(-2, -2);
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        View view =this.getLayoutInflater().inflate(R.layout.common_loading_layout, null);
        
        msg_txt = (TextView) view.findViewById(R.id.common_loading_msg);
        probar =(ProgressBar) view.findViewById(R.id.common_loading_probar);
        tag_img =(ImageView) view.findViewById(R.id.common_loading_finish_img);
        msg_txt.setText(this.message);
        tag_img.setVisibility(View.GONE);
//        int width =(int)(ViewUtil.getScreenWidth(activity) /8.0);
        ViewUtil.measureView(msg_txt);
        int width =msg_txt.getMeasuredHeight();
        LinearLayout.LayoutParams param =new LinearLayout.LayoutParams(width, width);
        param.rightMargin =10;
        probar.setLayoutParams(param);
        tag_img.setLayoutParams(param);
        this.setContentView(view, params);
        this.setOnDismissListener(this);
    }

    public void setText(String message) {
        this.message = message;
        msg_txt.setText(this.message);
    }

    public void setText(int resId) {
        setText(getContext().getResources().getString(resId));
    }
    
    public void setWidth(int width){
    	this.width =width;
    }
    
    public void setOnLoadingDialogResultListener(OnLoadingDialogResultListener listener){
    	this.listener =listener;
    }
    
    public void setTag(int tag){
    	this.tag =tag;
    }

    @Override
	public void show() {
		// TODO Auto-generated method stub
		super.show();
		probar.setVisibility(View.VISIBLE);
    	tag_img.setVisibility(View.GONE);
    	msg_txt.setText(message);
    	if(load!=null)
    		load.cancel();
    	load = new CountDownTimerLoad(20000, 1000);
        load.start();
        LoadingDialog.this.setCancelable(false);
	}
    
    public void setFinishSuccess(String msg,int time){
    	
    	post_time =time;
    	state =SUCCESS;
    	probar.setVisibility(View.GONE);
    	tag_img.setVisibility(View.VISIBLE);
    	tag_img.setBackgroundDrawable(success_da);
    	msg_txt.setText(msg);
    	closeDialog();
    }

	public void setFinishSuccess(String msg){
		
		post_time =2000;
		state =SUCCESS;
    	probar.setVisibility(View.GONE);
    	tag_img.setVisibility(View.VISIBLE);
    	tag_img.setVisibility(View.GONE);
    	msg_txt.setText(msg);
    	closeDialog();
    }
    
    public void setFinishFailure(String msg){
    	
    	post_time =2000;
    	state =FAILURE;
    	probar.setVisibility(View.GONE);
    	tag_img.setVisibility(View.VISIBLE);
    	tag_img.setBackgroundDrawable(fail_da);
    	tag_img.setVisibility(View.GONE);
    	msg_txt.setText(msg);
    	closeDialog();
    }
    
    public void setFinishFailure(String msg,int time){
    	
    	post_time =time;
    	state =FAILURE;
    	probar.setVisibility(View.GONE);
    	tag_img.setVisibility(View.VISIBLE);
    	tag_img.setBackgroundDrawable(fail_da);
    	msg_txt.setText(msg);
    	closeDialog();
    } 
    
    public void setWarn(String msg){
    	
    	post_time =2000;
    	state =FAILURE;
    	probar.setVisibility(View.GONE);
    	tag_img.setVisibility(View.VISIBLE);
    	tag_img.setBackgroundDrawable(warn_da);
    	msg_txt.setText(msg);
    	closeDialog();
    }
    
    private void closeDialog(){
    	if(load!=null)
    		load.cancel();
    	dismiss();
    }

	@Override
	public void onDismiss(DialogInterface dialog) {
		// TODO Auto-generated method stub
		if (listener!=null){
			listener.dialogResult(tag, state);
		}
		state =0;
	}
	
	class CountDownTimerLoad extends CountDownTimer{

		public CountDownTimerLoad(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			LoadingDialog.this.dismiss();
		}

		@Override
		public void onTick(long millisUntilFinished) {
		}
		
	}
}
