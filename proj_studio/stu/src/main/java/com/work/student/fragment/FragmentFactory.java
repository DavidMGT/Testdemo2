package com.work.student.fragment;


import android.app.ActivityManager;
import android.support.v4.app.Fragment;

import com.work.student.bean.HomeWorkDetailBean;
import com.work.student.bean.JobDetail;
import com.work.student.homework.ErrorsFragment;
import com.work.student.homework.HomeworkFragment;
import com.work.student.homework.TianKongFragment;
import com.work.student.homework.XueanZeFragment;

/**
 * Created by maguitao on 2016/9/18.
 * 说明
 */
public class FragmentFactory {
    public static final int TYPE_HOMEWORK = 0;
    public static final int TYPE_ERRORS = 1;
    public static final int TYPE_DANXUAN = 2;
    public static final int TYPE_TIANKONG = 3;
    private static HomeworkFragment homeworkFragment;
    private static ErrorsFragment errorsFragment;

    public static Fragment createFragemntByType(int type) {
        switch (type) {
            case TYPE_ERRORS:
                //错题集的fragment
                if (errorsFragment == null) {
                    return new ErrorsFragment();
                } else {
                    return errorsFragment;
                }
            case TYPE_HOMEWORK:
                if (homeworkFragment == null) {
                    return new HomeworkFragment();
                } else {
                    return homeworkFragment;
                }
        }
        return null;

    }

    public static Fragment createHomeWorkFragment(int type, JobDetail bean, int position) {
        switch (type) {
            case TYPE_DANXUAN:
                return XueanZeFragment.newInstance(bean, position);
            case TYPE_TIANKONG:
                return TianKongFragment.newInstance(bean,position);
        }
        return null;

    }
}
