package com.work.teacher.tool;

import com.work.teacher.RegisterActivity;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
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
    public static String USER_ID;
    /**
     * 2016-8-18左丽姬 http://mtea.hzyeah.com
     */
    public static String URL = "http://mtea.hzyeah.com";
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
     * 年级科目个人
     */
    public static String PERSONAL_SUB_CLASS = URL + "/Personalcenter/sub_class";
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
	/**作业-》发布-》》添加至作业*/
	public static String WORK_ADDQORK_SELECT=URL+"/Syslb/addtm";
	/**作业-》题库--》编辑题库保存*/
	public static  String WORK_QUESTION_EDITSAVE=URL+"/IbEdit/yc_updateIbInfo";
	/**作业-》发布作业--》编辑题目保存*/
	public static  String WORK_ADDWORK_EDTISAVE=URL+"/IbEdit/zy_updateIbInfo";
	/**作业-》发布作业--》》添加题目*/
	public static  String WORK_ADDWROK_ADDSAVE=URL+"/Addlb/Addnewlb";
    /**作业-》删除作业*/
    public static  String WORK_DELETE=URL+"/Ibcart/delzy";
    /**作业-》编辑作业中的题目*/
    public static String WORK_EDIT_QUEREION=URL+"/IbEdit/zy_updateIbInfo";
    /**作业-》原创题目添加*/
    public static String WORK_QUEREION_ADD=URL+"/Addlb/yc_add";
    /**作业-》题目添加，听力音频上传*/
    public static String WORK_RECORD_UPLOAD=URL+"/Addlb/upload";
    /**作业-》发布作业，修改题目分数*/
    public static String WORK_UPDATE_SCORE=URL+"/Ibcart/edittxfs";
    /**作业-》发布作业*/
    public static String WORK_RESELT_COMMIT=URL+"/Ibcart/sendzy";

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
     * 班级--》》删除班级成员--》》老师
     */
    public static String CLASS_DEL_TEACHER = URL + "/Class/delTeacher";
    /**
     * 班级--》》删除班级成员--》》学生
     */
    public static String CLASS_DEL_STUDENT = URL + "/Class/delStudent";
    /**
     * 班级--》》退出该班级
     */
    public static String CLASS_QUITE_EXIT = URL + "/Class/quiteClass";
    /**
     * 班级--》》班级管理--》》查询申请加入列表及本班信息
     */
    public static String CLASS_QUERY_MANAGE = URL + "/Class/classManager";
    /**
     * 班级--》》班级管理--》》拒绝他人加入班级
     */
    public static String CLASS_MANAGE_REFUSE = URL + "/Class/refuse";
    /**
     * 班级--》》班级管理--》》同意他人加入班级
     */
    public static String CLASS_MANAGE_AGREE = URL + "/Class/changeUserStatus";
    /**
     * 班级--》》班级管理--》》删除班级
     */
    public static final String POST_QUESTION = URL + "/Mobile/editWjdetail";
    public static String CLASS_DELETE_MANAGE = URL + "/Class/delClass";
	/**广播--》作业--》发布添加、编辑、修改至作业*/
	public static String ACTION_WORK_ADDWROK="com.work.teacher.work.WORK_AUD";
	/**广播-》作业-》》题库编辑*/
	public static String ACTION_QUESTION_UPDATE="com.work.teacher.work.QUESTION_UPDATE";

	/**2016-8-18 左丽姬*/
	/**动态设置listview的高度*/
	public static  void setGroupHeight(ListView mListView)  
    {  
        int mTotalHeight=0;  
        ListAdapter mAdapter=mListView.getAdapter();
        
        for(int i=0;i<mAdapter.getCount();i++)  
        {  
            View ItemView=mAdapter.getView(i, null, mListView);
            /*测量一下子控件的高度*/
            ItemView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            mTotalHeight+=ItemView.getMeasuredHeight();
        }  
        ViewGroup.LayoutParams mParams=mListView.getLayoutParams();  
        mParams.height=mTotalHeight +(mListView.getDividerHeight() * (mAdapter.getCount() - 1));
        mListView.setMinimumHeight(mTotalHeight);  
    }
	/**2016-8-18 左丽姬*/
	/**弹出框*/
	public static void toastShow(Activity activity,String content){
		Toast.makeText(activity, content, Toast.LENGTH_SHORT).show();
	}
	/**2016-8-18 左丽姬*/
	/**网络断开显示按钮*/
	public static RelativeLayout net_relative;
	//判断网络是否连接
	public static boolean isNet(Context context){
		//获取手机所有连接对象
		ConnectivityManager manager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		//获取NetworkInfo对象
		NetworkInfo[] info=manager.getAllNetworkInfo();
		 if (info != null && info.length > 0)
         {
             for (int i = 0; i < info.length; i++)
             {
                 // 判断当前网络状态是否为连接状态
                 if (info[i]!=null&&info[i].isAvailable())
                 {
                	 return true;
                 }else{
                	 return false;
                 }
             }
         }
		return false;
	}
    /**
     * 作业-》》菜单--》》查询学科列表
     */
    public static String WORK_SUBJECT_LISTS = URL + "/History/left_subject";
    /**
     * 作业-》》菜单--》》查询教材列表
     */
    public static String WORK_TEAHING_LISTS = URL + "/History/left_textbook";
    /**
     * 作业-》》菜单--》》查询章节列表--》》》第一层
     */
    public static String WORK_TREE_ONELISTS = URL + "/History/one_tree";
    /**
     * 作业-》》菜单--》》查询章节列表--》》》子层
     */
    public static String WORK_TREE_OTHERLISTS = URL + "/History/more_tree";
    /**
     * 作业-》》历史--》》》列表
     */
    public static String WORK_HISTORY = URL + "/History/zy_history";

    /**2016-9-5 左丽姬*/
    /**
     * 作业-》设置属性--》》第一步获取模式、类型
     */
    public static String WORK_SETTING_ARRT = URL + "/Ibcart/get_model";
    /**
     * 作业-》设置属性--》》第一步提交
     */
    public static String WORK_SETTING_ARRTCOMMITE = URL + "/Ibcart/save_property";
    /**
     * 作业-》编辑作业--》》第二步查询
     */
    public static String WORK_EDITTEXT_PREVIEW = URL + "/Ibcart/addtwo";
    /**2016-9-7 左丽姬*/
    /**
     * 作业-》题库--》》查询参数
     */
    public static String WORK_QUESTION_TYPE = URL + "/Ibcart/get_type";
    /**
     * 作业-》题库--》》系统题库
     */
    public static String WORK_QUESTION_SYSTEM = URL + "/Syslb/get_zy";
    /**
     * 作业-》题库--》》共享题库
     */
    public static String WORK_QUESTION_SHARE = URL + "/Syslb/zy_share";
    /**
     * 作业-》题库--》》收藏
     */
    public static String WORK_QUESTION_COLLECT = URL + "/Syslb/zy_collection";
    /**
     * 作业-》题库--》》原创
     */
    public static String WORK_QUESTION_OLD = URL + "/Syslb/zy_ibs";
    /**
     * 作业-》题库--》》痕迹
     */
    public static String WORK_QUESTION_MARK = URL + "/Syslb/zy_useib";
    /**
     * 作业-》题库--》》错题集
     */
    public static String WORK_QUESTION_ERROR = URL + "/Syslb/zy_errorlb";
    /**
     * 作业-》题库--》》添加收藏
     */
    public static String WORK_QUESTION_ADDCOLLECT = URL + "/Syslb/collection";
    /**
     * 作业-》题库--》》移除收藏
     */
    public static String WORK_QUESTION_REMOVECOLLECT = URL + "/Syslb/del_collection";
    /**2016-9-10*/
    /**
     * 作业-》根据章节ID查询章节详情
     */
    public static String WORK_SECTION_TIGAN = URL + "/IbEdit/get_tigan";
    /**
     * 作业-》知识点树结构0层
     */
    public static String WORK_KNOWLEDGE_ONE = URL + "/IbEdit/one_tree";
    /**
     * 作业-》知识点树结构多层
     */
    public static String WORK_KNOWLEDGE_MORE = URL + "/IbEdit/more_tree";
    /**
     * 作业-》编辑图片上传
     */
    public static String WORK_EDITOR_UPLOAD = URL + "/IbEdit/upload_photo";
    /**作业->删除作业中单个题目*/
    public static String WORK_DELETE_QUESTION=URL+"/Ibcart/del";
    /**作业->延迟交卷时间*/
    public static String WORK_DELAY_DATE=URL+"/History/yanshi";
    /**作业->共享作业*/
    public static String WORK_SHARE_WORK=URL+"/History/share";
    /**作业->收作业*/
    public static String WORK_ROLLING_WORK=URL+"/History/soujuan";


    /**2016-8-18 左丽姬*/
    /**
     * 广播--》班级--》》删除成员
     */
    public static String ACTION_CLASS_DELMEMBER = "com.work.teacher.DELMEMBER";
    /**
     * 广播--》班级--》》创建班级
     */
    public static String ACTION_CLASS_CREATE = "com.work.teacher.team.CREATE_CLASS";
    /**
     * 广播--》班级--》》删除班级
     */
    public static String ACTION_CLASS_DEL = "com.work.teacher.team.DEL_CLASS";
    /**
     * 广播--》》登陆
     */
    public static String ACTION_LOGIN = "com.work.teacher.LOGIN";
    /**
     * 广播--》更多--》》个人资料修改
     */
    public static String ACTION_UPDATE_PERSONAL = "com.work.teacher.UPDATE_PERSONAL";
    /**
     * 广播--》》网络发生改变
     */
    public static String ACTION_NET = "com.work.teacher.ISNET";
    /**
     * 广播--》》通知
     */
    public static String ACTION_NOTICE = "com.work.teacher.more.NOTICE";
    /**
     * 广播--》作业--》》章节选中
     */
    public static String ACTION_WORK_HISTORY_SUBJECT = "com.work.teacher.work.HISTORY_SUBJECT";

    public static String URL_GET_QUESTIONNARIE_LIST = URL + "/Mobile/getTeaWjList";//获取问卷列表的链接
    public static String URL_GET_QUESTIONNARIE_DETAIL = URL + "/Mobile/getTeaWjDetail";//获取问卷详情的链接
    /**2016-8-18 左丽姬*/
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
    public static int NOTICETWO_GET = 0x116;
    /**
     * 定时发--》》布发布通知第二步
     */
    public static int STATE_NOTICETWO = 0x205;
    /**
     * 作业发布作业--》》发布对象
     */
    public static int WORKPROPERT_NOTICEAMOUNT = 0x109;
    /**
     * 作业发布作业-》》定时发
     */
    public static int WORKPROPERT_STATE = 0x110;
    /**
     * 作业历史--》》树结构
     */
    public static int WORK_HISTORY_TREE = 0x111;
    /**
     * 作业设置属性--》》树结构
     */
    public static int WORK_ARRT_TREE = 0x112;
    /**
     * 作业编辑作业--》》树结构
     */
    public static int WORK_RELESE_TREE = 0x113;
    /**
     * 树结构--》》作业
     */
    public static int TREE_WORK = 0X207;
    /**
     * 发布作业选题--》》树结构
     */
    public static int WORK_QUESTION_TREE = 0x114;
    /**
     * 发布作业选题--》》知识点选择--》》树结构
     */
    public static int WORK_EDITORANSWER_TREE = 0x115;

    /**2016-8-18 左丽姬*/
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
    /**
     * listview列表，每页显示多少行
     */
    public static int PAGE = 10;


}
