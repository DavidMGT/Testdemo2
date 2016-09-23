package com.work.student.tool;


import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * 工具类
 *
 * @author 左丽姬
 */
public class IBaes {

    public static String URL_STU = "http://mstu.hzyeah.com";
    public static String URL = "http://mtea.hzyeah.com";
    public static String BASEURL = "http://student.hzyeah.com";
    /**
     * 登陆接口
     */
    public static String LOGIN = URL + "/login/submit_app";
    /**
     * 验证用户是否登录
     */
    public static String IS_LOGIN = URL + "/Inform/online";
    /**
     * 注册
     */
    public static String REGISTER = URL + "/Register/submit_stu";
    /**
     * 手机找回密码
     */
    public static String MOBILE_FORGET = URL + "/Register/find_pass/userName";
    /**
     * 退出登陆
     */
    public static String EXIT_LOGIN = URL + "/Personalcenter/secede";
    /**
     * 用户名验证
     */
    public static String REGISTER_NAME = URL + "/Register/username_match";
    /**
     * 手机号码验证
     */
    public static String REGISTER_MOBILE = URL + "/Register/mobile_match";
    /**
     * 获取验证码
     */
    public static String REGISTER_VERFIY = URL + "/Register/check/";
    /**
     * 个人资料
     */
    public static String PERSONAL_SETTING = URL + "/Personalcenter/personalcenter/";
    /**
     * 修改用户姓名
     */
    public static String PERSONAL_UPDATE_NAME = URL + "/Personalcenter/modified_nickname";
    /**
     * 年级科目all
     */
    public static String PERSONAL_SUBJECT_GRIDE = URL + "/Personalcenter/subject_calss";
    /**
     * 修改年级、科目
     */
    public static String UPDATE_SUBJECT_GRADE = URL + "/Personalcenter/subject";
    /**
     * 修改用户头像
     */
    public static String UPDATE_AVATAR = URL + "/Personalcenter/upload_photo";
    /**
     * 查询学校信息
     */
    public static String QUEY_SCHOOL = URL + "/Personalcenter/myschool";
    /**
     * 绑定学校
     */
    public static String UPDATE_ADD_SCHOOL = URL + "/Personalcenter/bindschool";
    /**
     * 修改密码
     */
    public static String UPDATE_PAESSWORD = URL + "/Personalcenter/acount_Modifypassword";
    /**
     * 修改手机号码
     */
    public static String UPDATE_MOBILE = URL + "/Personalcenter/bindtel";

    /**
     * 通知列表
     */
    public static String NOTICE_LISTS = URL + "/Inform/inform_intro";
    /**
     * 通知详情
     */
    public static String NOTICE_DETAILS = URL + "/Inform/inform_info";
    /**
     * 上传语音
     */
    public static String NOTICE_UPLOAD = URL + "/Inform/upload";
    /**
     * 发布通知第一步
     */
    public static String NOTICE_ONE = URL + "/Inform/inform_save";
    /**
     * 发布通知第二步
     */
    public static String NOTICE_TWO = URL + "/Inform/inform_send";
    /**
     * 发布通知--》》班级列表
     */
    public static String NOTICE_CLASSLISTS = URL + "/Inform/teacher_cla";
    /**
     * 发布通知--》》发布对象
     */
    public static String NOTICE_AMOUNT = URL + "/Inform/inform_object/userId";
    /**
     * 根据当前时间和通知状态查询通知
     */
    public static String NOTICE_DATE_STATE = URL + "/Inform/query_inform";
    /**
     * 修改查询到的数据
     */
    public static String NOTICE_UPDATE_STATE = URL + "/Inform/set_state";

    /**
     * 班级列表
     */
    public static String CLASS_LISTS = URL + "/Class/class_index";
    /**
     * 所加班班级及历史班级
     */
    public static String CLASS_HISTORY = URL + "/Class/historyClass";
    /**
     * 创建班级--》》验证班级名称是否存在与老师创建班级的个数
     */
    public static String CLASS_NAME_VERFIY = URL + "/Class/checkClass";
    /**
     * 创建班级
     */
    public static String CLASS_CREATE = URL + "/Class/class_creat";
    /**
     * 加入班级--》》查询班级基本信息
     */
    public static String CLASS_ADD_READ = URL + "/Class/readClass";
    /**
     * 加入班级
     */
    public static String CLASS_ADD = URL + "/Class/add_class";
    /**
     * 班级--》》班级详情
     */
    public static String CLASS_DETAILS = URL + "/Class/class_inform";
    /**
     * 班级--》》退出该班级
     */
    public static String CLASS_QUITE_EXIT = URL + "/Class/quiteClass";

    /**
     * 获取消息接口(通知,问卷)
     */
    public static final String ACTION_MESSAGE = "http://student.hzyeah.com/mobile/getLastUnreadMessage";
    /**
     * 通知/问卷列表获取
     */
    public static final String ACTION_MESSAGE_SUB_LIST = "http://student.hzyeah.com/mobile/getNoticeList";

    /**
     * 修改通知和问卷读取状态
     */
    public static final String UPDATE_INFO_STATE = "http://student.hzyeah.com/mobile/setReadStatus?";
    /**
     * 广播--》班级--》》删除成员
     */
    public static String ACTION_CLASS_DELMEMBER = "com.work.student.DELMEMBER";
    /**
     * 广播--》班级--》》创建班级
     */
    public static String ACTION_CLASS_CREATE = "com.work.student.team.CREATE_CLASS";
    /**
     * 广播--》》登陆
     */
    public static String ACTION_LOGIN = "com.work.student.LOGIN";
    /**
     * 广播--》更多--》》个人资料修改
     */
    public static String ACTION_UPDATE_PERSONAL = "com.work.student.UPDATE_PERSONAL";
    /**
     * 广播--》》网络发生改变
     */
    public static String ACTION_NET = "com.work.student.ISNET";
    /**
     * 广播--》》通知
     */
    public static String ACTION_NOTICE = "com.work.student.more.NOTICE";
    /**
     * 通知详情
     */
    public static String GET_NOTICE_DETAIL = BASEURL + "/mobile/getNoticeDetail";
    /**
     * 通知的决绝参加或者参与
     */
    public static String GET_NOTICE_STATE = BASEURL + "/mobile/setAgreeNotice";
    /**
     * 问卷详情的url
     */
    public static String GET_QUSTIONNAIRE = BASEURL + "/mobile/getWjStatictis";
    /**
     * 提交问卷的url
     */
    public static String GET_QUSTIONNAIRE_COMMIT = BASEURL + "/mobile/sendWjOption";

    public static String GET_HOME_WORK_LIST = "http://mstu.hzyeah.com/Task/index";
    //获取作业或者考试的详情
    public static String GET_HOME_WORK_DETAIL = "http://mstu.hzyeah.com/Task/getZyDetail";
    //提交作业
    public static String POST_HOME_WORK_COMMITE = URL_STU + "/Task/saveMyanswer";
    /**
     * 拍照
     */
    public static int REQUESTCODE_TAKE = 0x101;
    /**
     * 从相册选择
     */
    public static int REQUESTCODE_PICK = 0x102;
    /**
     * 系统图片裁剪
     */
    public static int SYS_CROPPHOTO = 0x103;
    /**
     * 注册--》忘记密码
     */
    public static int REG_FORGET = 0x104;
    public static int FORGET_REG = 0x201;//返回
    /**
     * 账号安全--》忘记密码
     */
    public static int SAFETY_UPDATEPWD = 0x105;
    /**
     * 忘记密码--》账号安全
     */
    public static int UPDATEPWD_SAFETY_PWD = 0x202;
    public static int UPDATEPWD_SAFETY_MOBILE = 0x203;

    /**
     * 发布通知第一步--》》发布通知第二步
     */
    public static int NOTICEONE_NOTICETWO = 0x108;
    /**
     * 发布通知第二步--》》发布通知第一步
     */
    public static int NOTICETWO_NOTICEONE = 0x206;
    /**
     * 发布通知第二步--》》发布对象
     */
    public static int NOTICETWO_NOTICEAMOUNT = 0x106;
    /**
     * 发布对象--》》发布通知第二步
     */
    public static int NOTICEAMOUNT_NOTICETWO = 0x204;
    /**
     * 发布通知第二步--》》定时发布
     */
    public static int NOTICETWO_STATE = 0x107;
    /**
     * 定时发--》》布发布通知第二步
     */
    public static int STATE_NOTICETWO = 0x205;

    /**
     * 充值
     */
    public static int MONEY_RECHARGE = 0;
    /**
     * 提现
     */
    public static int MONEY_DEPOSIT = 1;
    /**
     * 收益统计
     */
    public static int MONEY_INCOME = 2;
    /**
     * 提现记录
     */
    public static int MONEY_RECORD = 3;
    /**
     * 我的账户
     */
    public static int MONEY_ACCOUNT = 4;
    public static String userid;
    public static String TYPE_NOTICE = "1";
    public static String TYPE_QUESTIONARIE = "2";

    /**
     * 动态设置listview的高度
     */
    public static void setGroupHeight(ListView mListView) {
        int mTotalHeight = 0;
        ListAdapter mAdapter = mListView.getAdapter();
        for (int i = 0; i < mAdapter.getCount(); i++) {
            View ItemView = mAdapter.getView(i, null, mListView);
            ItemView.measure(0, 0);
            mTotalHeight += ItemView.getMeasuredHeight();
        }
        ViewGroup.LayoutParams mParams = mListView.getLayoutParams();
        mParams.height = mTotalHeight;
        mListView.setMinimumHeight(mTotalHeight);
    }

    /**
     * 弹出框
     */
    public static void toastShow(Activity activity, String content) {
        Toast.makeText(activity, content, 0).show();
    }

    public static RelativeLayout net_relative;

    //判断网络是否连接
    public static boolean isNet(Context context) {
        //获取手机所有连接对象
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取NetworkInfo对象
        NetworkInfo[] info = manager.getAllNetworkInfo();
        if (info != null && info.length > 0) {
            for (int i = 0; i < info.length; i++) {
//                 // 判断当前网络状态是否为连接状态
//                 if (info[i].getState() == NetworkInfo.State.CONNECTED)
//                 {
//                	 return true;
//                 }else{
//                	 return false;
//                 }

                // 判断当前网络状态是否为连接状态
                if (info[i] != null && info[i].isAvailable()) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }
}
