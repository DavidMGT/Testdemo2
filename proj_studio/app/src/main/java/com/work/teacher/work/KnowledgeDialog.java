package com.work.teacher.work;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.work.teacher.R;
import com.work.teacher.bean.LeftSubject;
import com.work.teacher.bean.LeftTree;
import com.work.teacher.bean.SubAndGrade;
import com.work.teacher.tool.IBaes;
import com.work.teacher.tool.JsonData;
import com.work.teacher.tool.SPUtils;
import com.work.teacher.tool.ServecHttp;
import com.work.teacher.tool.WorkJson;
import com.work.teacher.work.adapter.GradeMenuAdapter;
import com.work.teacher.work.adapter.KnowHowAdapter;
import com.work.teacher.work.adapter.SpinnerMenuAdpater;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 自定义树形对话框
 * 
 * @author 左丽姬
 */
public class KnowledgeDialog extends Activity implements OnClickListener, OnItemClickListener {

	private ListView grade_know_how;
	private SpinnerMenuAdpater teachingAdapter;
	private KnowHowAdapter knowHowAdapter;
	private String jid = "";
	private int indexItem = 0;// 保存章节列表点击的下标
	private LeftSubject sub = null;
	private List<LeftSubject> teachings = new ArrayList<LeftSubject>();
	private List<LeftTree> trees = new ArrayList<LeftTree>();
	private List<LeftTree> alltrees = new ArrayList<LeftTree>();
	public Dialog dialog;
	private ServecHttp servecHttp;
	private WorkJson jsonDate;
	private FinalHttp finalHttp;
	private String key;
	private String userId;
	private String sectionId = "";// 保存章节ID
	private String name = "";
	private String sectName = "";// 保存章节的名称
	private SubAndGrade andGrade;
	private SubAndGrade andSub;
	private String knowid;
	private ArrayList<String> knows;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_know_how);

		getWindow().getAttributes().windowAnimations = R.style.dialogWindowAnim;
		WindowManager m = getWindowManager();
		Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
		LayoutParams p = getWindow().getAttributes(); // 获取对话框当前的参数值
		p.height = (int) (d.getHeight() * 1.0); // 高度设置为屏幕的1.0
		p.width = (int) (d.getWidth() * 1.0); // 宽度设置为屏幕的0.9
		getWindow().setAttributes(p); // 设置生效
		getWindow().setGravity(Gravity.LEFT); // 设置靠右对齐

		servecHttp = new ServecHttp();
		jsonDate = new WorkJson();
		finalHttp = new FinalHttp();
		key = SPUtils.get(this, "key", "").toString();
		userId = SPUtils.get(this, "userId", "").toString();
		knowid = getIntent().getStringExtra("knowid");
		knows = getIntent().getStringArrayListExtra("knows");

		initTreeDialog();
	}

	public void initTreeDialog() {
		LinearLayout ll_close_know_how = (LinearLayout) findViewById(R.id.ll_close_know_how);
		ll_close_know_how.setOnClickListener(this);
		grade_know_how = (ListView) findViewById(R.id.grade_know_how);
		knowHowAdapter = new KnowHowAdapter(this, trees, alltrees);
		grade_know_how.setAdapter(knowHowAdapter);
		grade_know_how.setOnItemClickListener(this);
		trees.clear();
		alltrees.clear();
		getSubject();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ll_close_know_how:
			exitDialog();
			break;
		}
	}

	/**获取章节和年级id*/
	public void getSubject(){
		if (!IBaes.isNet(this)) {
			IBaes.toastShow(this, "网络不给力,请稍后...");
			return;
		}
		AjaxParams params=servecHttp.keyAndId(key,userId);
		finalHttp.post(IBaes.PERSONAL_SUB_CLASS, params, new AjaxCallBack<Object>() {
			@Override
			public void onSuccess(Object o) {
				super.onSuccess(o);
//				Log.i("test",o.toString());
				Map<String,Object> map=new JsonData().jsonGradeAndSubOne(o.toString());
				Message msg=new Message();
				msg.obj=map;
				msg.what=0;
				handler.sendMessage(msg);
			}
		});

	}
	/** 查询知识点第一层树结构 */
	public void getLeftKnowledge(String gid,String sid) {
		if (!IBaes.isNet(this)) {
			IBaes.toastShow(this, "网络不给力,请稍后...");
			return;
		}
		AjaxParams params = new AjaxParams();
		params.put("subjectid", sid);
		params.put("gradeid", gid);
		finalHttp.post(IBaes.WORK_KNOWLEDGE_ONE, params, new AjaxCallBack<Object>() {
			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
//				 Log.i("test", t.toString());
				List<LeftTree> lists = jsonDate.jsonKownlege(t.toString());
				Message msg = new Message();
				msg.obj = lists;
				msg.what = 1;
				handler.sendMessage(msg);
			}
		});
	}

	/** 查询知识点子层 */
	public void getLeftKnowChild(String id) {
		if (!IBaes.isNet(this)) {
			IBaes.toastShow(this, "网络不给力,请稍后...");
			return;
		}
		AjaxParams params = new AjaxParams();
		params.put("subjectid",andSub.getId());
		params.put("gradeid", andGrade.getId());
		params.put("id", id);
		finalHttp.post(IBaes.WORK_KNOWLEDGE_MORE, params, new AjaxCallBack<Object>() {
			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
//				 Log.i("test", t.toString());
				List<LeftTree> list = jsonDate.jsonTreeOther(t.toString());
				Message msg = new Message();
				msg.obj = list;
				msg.what = 2;
				handler.sendMessage(msg);
			}
		});
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				// 侧滑年级科目查询
				Map<String,Object> map= (Map<String, Object>) msg.obj;
				int status= (int) map.get("status");
				if(status==1){
					andGrade = (SubAndGrade) map.get("grades");
					andSub = (SubAndGrade) map.get("subs");
					getLeftKnowledge(andGrade.getId(),andSub.getId());
				}
				break;
			case 1:
				// 侧滑知识点
				List<LeftTree> leftTrees = (List<LeftTree>) msg.obj;
				if (leftTrees != null) {
					trees.clear();
					trees.addAll(leftTrees);
					alltrees.addAll(leftTrees);
					knowHowAdapter.notifyDataSetChanged();
					IBaes.setGroupHeight(grade_know_how);
				}
				break;
			case 2:
				// 知识点子层
				List<LeftTree> lts = (List<LeftTree>) msg.obj;
				LeftTree leftTree = trees.get(indexItem);
				if (lts != null) {
					for (LeftTree l : lts) {
						l.setLevel(leftTree.getLevel() + 1);
					}
					alltrees.addAll(lts);
					clickShow();
				}
				break;
			}
		}
	};

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		// 点击事件
		indexItem = position;
		LeftTree tree = trees.get(position);
		sectName = tree.getName();
		sectionId = tree.getId();
		if (1 == tree.getPanduan())
			if (!tree.isFlag()) {
				getLeftKnowChild(tree.getId());
			} else {
				clickShow();
			}
		else {
			clickShow();
		}
	}

	// 节点点击展示数据
	public void clickShow() {
		// 点击的item代表的元素
		LeftTree treeNode = (LeftTree) knowHowAdapter.getItem(indexItem);
		// 树中顶层的元素
		ArrayList<LeftTree> topNodes = knowHowAdapter.getTopNodes();
		// 元素的数据源
		// 点击没有子项的item直接返回
		// if (treeNode.getPanduan() == 0) {
		// return;
		// }

		ArrayList<LeftTree> elements = new ArrayList<LeftTree>();
		elements.clear();
		for (int i = 0; i < topNodes.size(); i++) {
			// 查询其他选中的数据
			LeftTree leftTree = topNodes.get(i);
			// 判断列表中是否有选中的，选中的是否和点击的是同一个，点击的是否是选中的下一级(不是下一级则移除选中状态)
			if (leftTree.isFlag() && !treeNode.getId().equals(leftTree.getId())
					&& treeNode.getLevel() <= leftTree.getLevel()) {
				leftTree.setFlag(false);
				for (LeftTree lt : topNodes) {
					if (leftTree.getId().equals(lt.getPid()) && leftTree.getLevel() < lt.getLevel()) {
						elements.add(lt);
					}
				}
			}
		}
		topNodes.removeAll(elements);
		alltrees.removeAll(elements);
		for (int i = 0; i < topNodes.size(); i++) {
			if (treeNode.getId().equals(topNodes.get(i).getId())) {
				indexItem = i;
			}
		}
		if (treeNode.isFlag()) {
			treeNode.setFlag(false);
			// 删除节点内部对应子节点数据，包括子节点的子节点...
			ArrayList<LeftTree> elementsToDel = new ArrayList<LeftTree>();
			for (int i = indexItem + 1; i < topNodes.size(); i++) {
				if (treeNode.getLevel() >= topNodes.get(i).getLevel())
					break;
				elementsToDel.add(topNodes.get(i));
			}
			topNodes.removeAll(elementsToDel);
			alltrees.removeAll(elementsToDel);
			knowHowAdapter.notifyDataSetChanged();
		} else {
			treeNode.setFlag(true);
			// 从数据源中提取子节点数据添加进树，注意这里只是添加了下一级子节点，为了简化逻辑
			int i = 1;// 注意这里的计数器放在for外面才能保证计数有效
			for (LeftTree e : alltrees) {
				if (e.getPid().equals(treeNode.getId())) {
					e.setFlag(false);
					topNodes.add(indexItem + i, e);
					i++;
				}
			}
			knowHowAdapter.notifyDataSetChanged();
			IBaes.setGroupHeight(grade_know_how);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitDialog();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	/** 退出 */
	public void exitDialog() {
		Intent intent = new Intent();
		intent.putExtra("KnowId", sectionId);
		intent.putExtra("Kname", name);
		intent.putExtra("KnowName", sectName);
		setResult(IBaes.TREE_WORK, intent);
		finish();
		overridePendingTransition(0, R.anim.dialog_exit_anim);
	}
}
