package com.work.student.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;

import com.work.student.tool.LogUtils;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.HttpHandler;

import java.io.File;

/**
 * 检查版本更新的service created by maguitao
 */
public class UpdateService extends Service {
    private Context mContext;
    private FinalHttp mFinalhttp;

    public UpdateService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        checkForUpdate();

        return super.onStartCommand(intent, flags, startId);
    }

    private void checkForUpdate() {
        //检查版本更新
        mFinalhttp = new FinalHttp();
        HttpHandler handler = mFinalhttp.download("", getSdCardPath() + "/1.apk", true, new AjaxCallBack<File>() {
            @Override
            public void onSuccess(File file) {
                LogUtils.debug("download-onSuccess-----");
                super.onSuccess(file);
            }

            @Override
            public void onStart() {
                LogUtils.debug("download--start--");
                super.onStart();
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
            }

            @Override
            public void onLoading(long count, long current) {
                LogUtils.debug("download--onLoadding " + "count=" + count + ": current=" + current);
                super.onLoading(count, current);
            }
        });

    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public String getSdCardPath() {
        String sdcardPath = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);//
        sdcardPath = Environment.getExternalStorageDirectory().toString();//获取跟目录
        return sdcardPath;
    }
}
