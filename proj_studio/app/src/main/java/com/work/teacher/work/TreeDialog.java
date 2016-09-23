package com.work.teacher.work;

import java.util.ArrayList;
import java.util.List;

import com.work.teacher.R;
import com.work.teacher.bean.LeftSubject;
import com.work.teacher.bean.LeftTree;
import com.work.teacher.tool.IBaes;
import com.work.teacher.tool.SPUtils;
import com.work.teacher.tool.ServecHttp;
import com.work.teacher.tool.WorkJson;
import com.work.teacher.work.adapter.GradeMenuAdapter;
import com.work.teacher.work.adapter.SpinnerMenuAdpater;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/**
 * 自定义树形对话框
 * 
 * @author 左丽姬
 */
public class TreeDialog extends Activity implements OnClickListener, OnItemClickListener {

	private Spinner teaching_spinner_work;
	private TextView left_subject_work;
	private ListView grade_left_work;
	private SpinnerMenuAdpater teachingAdapter;
	private GradeMenuAdapter gradeMenuAdapter;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_left_work);

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

		initTreeDialog();
	}

	public void initTreeDialog() {
		LinearLayout ll_close_left_work = (LinearLayout) findViewById(R.id.ll_close_left_work);
		ll_close_left_work.setOnClickListener(this);
		teaching_spinner_work = (Spinner) findViewById(R.id.teaching_spinner_work);
		left_subject_work = (TextView) findViewById(R.id.left_subject_work);
		grade_left_work = (ListView) findViewById(R.id.grade_left_work);
		teachingAdapter = new SpinnerMenuAdpater(this, teachings);
		teaching_spinner_work.setAdapter(teachingAdapter);
		gradeMenuAdapter = new GradeMenuAdapter(this, trees, alltrees);
		grade_left_work.setAdapter(gradeMenuAdapter);
		grade_left_work.setOnItemClickListener(this);
		trees.clear();
		alltrees.clear();
		if (sub == null)
			getLeftSubject();
		else {
			left_subject_work.setText(sub.getCname());
			getLeftTeahing();
		}
		teaching_spinner_work.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				getLeftDetails();
				trees.clear();
				alltrees.clear();
				name = ((LeftSubject) teaching_spinner_work.getSelectedItem()).getCname();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ll_close_left_work:
			exitDialog();
			break;
		}
	}

	/** 查询学科 */
	public void getLeftSubject() {
		if (!IBaes.isNet(this)) {
			IBaes.toastShow(this, "网络不给力,请稍后...");
			return;
		}
		AjaxParams params = servecHttp.keyAndId(key, userId);
		finalHttp.post(IBaes.WORK_SUBJECT_LISTS, params, new AjaxCallBack<Object>() {
			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
				// Log.i("test", t.toString());
				LeftSubject lists = jsonDate.jsonWorkSubjec(t.toString());
				Message msg = new Message();
				msg.obj = lists;
				msg.what = 0;
				handler.sendMessage(msg);
			}
		});
	}

	/** 查询教材 */
	public void getLeftTeahing() {
		if (!IBaes.isNet(this)) {
			IBaes.toastShow(this, "网络不给力,请稍后...");
			return;
		}
		// 得到学科选中的值
		AjaxParams params = new AjaxParams();
		params.put("bookid", sub.getId());
		finalHttp.post(IBaes.WORK_TEAHING_LISTS, params, new AjaxCallBack<Object>() {
			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
				// Log.i("test", t.toString());
				List<LeftSubject> subjects = jsonDate.jsonWorkJiao(t.toString());
				Message msg = new Message();
				msg.obj = subjects;
				msg.what = 1;
				handler.sendMessage(msg);
			}
		});
	}

	/** 查询章节 */
	public void getLeftDetails() {
		if (!IBaes.isNet(this)) {
			IBaes.toastShow(this, "网络不给力,请稍后...");
			return;
		}
		LeftSubject subject = (LeftSubject) teaching_spinner_work.getSelectedItem();
		AjaxParams params = new AjaxParams();
		params.put("fid", subject.getCs());
		finalHttp.post(IBaes.WORK_TREE_ONELISTS, params, new AjaxCallBack<Object>() {
			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
				// Log.i("test", t.toString());
				List<LeftTree> lists = jsonDate.jsonTree(t.toString());
				Message msg = new Message();
				msg.obj = lists;
				msg.what = 2;
				handler.sendMessage(msg);
			}
		});
	}

	/** 查询章节子层 */
	public void getLeftTreeChild(String fid, String id) {
		if (!IBaes.isNet(this)) {
			IBaes.toastShow(this, "网络不给力,请稍后...");
			return;
		}
		AjaxParams params = new AjaxParams();
		params.put("fid", fid);
		params.put("id", id);
		finalHttp.post(IBaes.WORK_TREE_OTHERLISTS, params, new AjaxCallBack<Object>() {
			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
				// Log.i("test", t.toString());
				List<LeftTree> list = jsonDate.jsonTreeOther(t.toString());
				Message msg = new Message();
				msg.obj = list;
				msg.what = 3;
				handler.sendMessage(msg);
			}
		});
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				// 侧滑学科查询
				sub = (LeftSubject) msg.obj;
				left_subject_work.setText(sub.getCname());
				getLeftTeahing();
				break;
			case 1:
				// 获取教材目录
				teachings.clear();
				List<LeftSubject> teas = (List<LeftSubject>) msg.obj;
				teachings.addAll(teas);
				teachingAdapter.notifyDataSetChanged();
				if ("".equals(jid))
					teaching_spinner_work.setSelection(0);
				else {
					for (int i = 0; i < teachings.size(); i++) {
						if (jid.equals(teachings.get(i).getId())) {
							teaching_spinner_work.setSelection(i);
							break;
						}
					}
				}
				break;
			case 2:
				// 侧滑章节
				List<LeftTree> leftTrees = (List<LeftTree>) msg.obj;
				if (leftTrees != null) {
					trees.clear();
					trees.addAll(leftTrees);
					alltrees.addAll(leftTrees);
					gradeMenuAdapter.notifyDataSetChanged();
					IBaes.setGroupHeight(grade_left_work);
				}
				break;
			case 3:
				// 章节子层
				List<LeftTree> lts = (List<LeftTree>) msg.obj;
				LeftTree leftTree = trees.get(indexItem);
				if (lts != null) {
					for (LeftTree l : lts) {
						l.setLevel(leftTree.getLevel() + 1);
					}
					alltrees.addAll(lts);
					clickShow();

				} else {
					dialog.dismiss();
					dialog.cancel();
					SPUtils.put(TreeDialog.this, "jid", leftTree.getId());
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
				getLeftTreeChild(tree.getFid(), tree.getId());
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
		LeftTree treeNode = (LeftTree) gradeMenuAdapter.getItem(indexItem);
		// 树中顶层的元素
		ArrayList<LeftTree> topNodes = gradeMenuAdapter.getTopNodes();
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
			gradeMenuAdapter.notifyDataSetChanged();
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
			gradeMenuAdapter.notifyDataSetChanged();
			IBaes.setGroupHeight(grade_left_work);
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
		intent.putExtra("sectionId", sectionId);
		intent.putExtra("sname", name);
		intent.putExtra("sectName", sectName);
		if (!"".equals(sectionId.trim())) {
			intent.putExtra("subjectCs", sub.getCs());
			intent.putExtra("jiaocaiCs", ((LeftSubject) teaching_spinner_work.getSelectedItem()).getCs());
		}
		setResult(IBaes.TREE_WORK, intent);
		finish();
		overridePendingTransition(0, R.anim.dialog_exit_anim);
	}
}
