package com.work.student.tool;

import com.work.student.bean.JobDetail;

import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by maguitao on 2016/9/23.
 * 说明
 */
public class HomeWorkTools {
    public static boolean isExitUndo(List<JobDetail> mlist) {
        for (JobDetail jobDetail : mlist) {
            switch (jobDetail.getQuestype()) {
                case JobDetail.TYPE_DANXUAN:
                    if (jobDetail.getSelectPosition() == -1) {
                        LogUtils.debug("存在没有完成的单选题");
                        return true;
                    }
                    break;
                case JobDetail.TYPE_DUOXUAN:
                    if (jobDetail.getSelectionPositions() == null || jobDetail.getSelectionPositions().size() == 0) {
                        LogUtils.debug("存在没有完成的多选题");
                        return true;
                    }
                    break;
                case JobDetail.TYPE_TIANKONG:
                default:
                    if (jobDetail.getCommitanswer() == null || jobDetail.getCommitanswer() == "") {
                        LogUtils.debug("存在没有完成的填空题或者其他");
                        return true;
                    }
            }
        }
        LogUtils.debug("所有题目已经完成");
        return false;
    }
}
