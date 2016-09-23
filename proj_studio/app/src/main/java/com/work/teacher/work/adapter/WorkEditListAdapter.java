package com.work.teacher.work.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.work.teacher.R;
import com.work.teacher.bean.WorkRelseContent;
import com.work.teacher.bean.WorkRelseQuestion;
import com.work.teacher.tool.IBaes;
import com.work.teacher.view.LoadingDialog;

import net.tsz.afinal.FinalBitmap;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * 作业-》发布作业作业预览
 * Created by 左丽姬 on 2016/9/14.
 */
public class WorkEditListAdapter extends BaseAdapter implements LoadingDialog.OnLoadingDialogResultListener {
    private Context context;
    private List<WorkRelseContent> lists;
    private WorkeditListHodle hodle;
    private int indext;
    private String[] letter = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T"};

    public WorkEditListAdapter(Context context, List<WorkRelseContent> lists, int indext) {
        this.context = context;
        this.lists = lists;
        this.indext = indext;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        hodle = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_workedit_lists, null);
            hodle = new WorkeditListHodle(convertView);
            convertView.setTag(hodle);
        } else {
            hodle = (WorkeditListHodle) convertView.getTag();
        }
        WorkRelseContent question = lists.get(position);
        if (question != null) {
            int cum = position + 1;
            String ss = cum + "、";
            if (question.answer.voiceurl != null && !"".equals(question.answer.voiceurl) && !"null".equals(question.answer.voiceurl)) {
                //判断是否是听力题
                ss="";
                question.mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                hodle.item_seekBar.setMax((int)question.answer.voicetime);
                hodle.item_seekBar.setProgress(0);
                question.player = new MediaPlayer();
                question.player.reset();
                try {
//                    Log.i("test",IBaes.URL+question.answer.voiceurl);
                    question.player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    question.player.setDataSource(IBaes.URL + question.answer.voiceurl);
                    question.player.setLooping(false);
                    question.player.prepareAsync();
                    final View finalConvertView = convertView;
                    question.player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
//                            Log.i("test", "总时间:" + mp.getDuration());
                            if (linstener != null) {
                                linstener.onImageClick(finalConvertView, position, "缓存");
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
                hodle.item_ll_hearing.setVisibility(View.VISIBLE);
                hodle.item_hearing_start.setText(cum + "、00:00");
                GregorianCalendar gc = new GregorianCalendar();
                gc.setTimeInMillis(question.answer.voicetime*1000);
                SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
                hodle.item_hearing_end.setText(sdf.format(gc.getTime()));
                hodle.item_play_start.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (linstener != null) {
                            linstener.onImageClick((View) v.getParent(), position, "开始");
                        }
                    }
                });
                hodle.item_play_end.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (linstener != null) {
                            linstener.onImageClick((View) v.getParent(), position, "结束");
                        }
                    }
                });

            }
            Html.ImageGetter imageGette_body = new Html.ImageGetter() {
                public Drawable getDrawable(String source) {
                    FinalBitmap.create(context).display(hodle.item_body_image, IBaes.URL + source);
                    Drawable drawable = hodle.item_body_image.getDrawable();
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    return hodle.item_body_image.getDrawable();
                }
            };
            if (!"null".equals(question.answer.body)) {
                String body = question.answer.body;
                if (body.indexOf("<div>") != -1) {
                    body = body.substring(0, body.indexOf("<div>") + 5) + ss + body.substring(body.indexOf("<div>") + 5, body.length());
                } else {
                    body = ss + body;
                }
                Spanned name = Html.fromHtml(body.replace("<br />", "").trim(), imageGette_body, null);
                hodle.item_edit_body.setText(name);
            } else {
                hodle.item_edit_body.setText("");
            }
            Html.ImageGetter imageGette_xx = new Html.ImageGetter() {
                public Drawable getDrawable(String source) {
                    FinalBitmap.create(context).display(hodle.item_option_image, IBaes.URL + source);
                    Drawable drawable = hodle.item_option_image.getDrawable();
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    return drawable;
                }
            };
            Spanned xuanxiang = Html.fromHtml(question.answer.xuanxiang, imageGette_xx, null);
            if (!"null".equals(question.answer.xuanxiang) && !"[]".equals(question.answer.xuanxiang)) {
                hodle.item_edit_option.setText(xuanxiang);
            } else {
                hodle.item_edit_option.setVisibility(View.GONE);
            }
            if (indext == 1) {
                if (!"null".equals(question.answer.getCorrectanswer())) {
                    if (question.answer.answertype == 1 || question.answer.getCorrectanswer().indexOf("<div>") != -1) {
                        Html.ImageGetter imageGette_anser = new Html.ImageGetter() {
                            public Drawable getDrawable(String source) {
                                FinalBitmap.create(context).display(hodle.item_answer_image, IBaes.URL + source);
                                Drawable drawable = hodle.item_answer_image.getDrawable();
                                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                                return drawable;
                            }
                        };
                        String as = question.answer.getCorrectanswer();
                        if (!"null".equals(as) && as.indexOf("<div>") != -1) {
                            as = as.substring(0, as.indexOf("<div>") + 5) + "答案：" + as.substring(as.indexOf("<div>") + 5, as.length());
                        } else {
                            as = "答案：" + as;
                        }
                        Spanned correctanswer = Html.fromHtml(as.trim().replace("<br />", ""), imageGette_anser, null);
                        hodle.item_edit_answer.setText(correctanswer);
                    } else {
                        String[] strs = question.answer.getCorrectanswer().split(",");
                        String result = "";
                        for (int s = 0; s < strs.length; s++) {
                            if (!"".equals(strs[s])) {
                                //判断strs[s]是否为数字
                                if (Character.isDigit(strs[s].charAt(0))) {
                                    result += letter[Integer.parseInt(strs[s])] + ",";
                                }
                            }
                        }
                        if (!"".equals(result)) {
                            result = result.substring(0, result.length() - 1);
                        } else {
                            result = "未设定";
                        }
                        hodle.item_edit_answer.setText("答案：" + result);
                    }
                    hodle.item_edit_answer.setVisibility(View.VISIBLE);
                } else {
                    hodle.item_edit_answer.setVisibility(View.GONE);
                }
                Html.ImageGetter imageGette_exapin = new Html.ImageGetter() {
                    public Drawable getDrawable(String source) {
                        FinalBitmap.create(context).display(hodle.item_exapin_image, IBaes.URL + source);
                        Drawable drawable = hodle.item_exapin_image.getDrawable();
                        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                        return drawable;
                    }
                };
                String ep = question.answer.getExplain();
                if (!"null".equals(ep) && ep.indexOf("<div>") != -1) {
                    ep = ep.substring(0, ep.indexOf("<div>") + 5) + "解析：" + ep.substring(ep.indexOf("<div>") + 5, ep.length());
                } else {
                    ep = "解析：" + ep;
                }
                Spanned exapin = Html.fromHtml(ep.trim().replace("<br />", ""), imageGette_exapin, null);
                if (!"null".equals(question.answer.getExplain())) {
                    hodle.item_edit_exapin.setText(exapin);
                    hodle.item_edit_exapin.setVisibility(View.VISIBLE);
                } else {
                    hodle.item_edit_exapin.setVisibility(View.GONE);
                }

            }
            hodle.item_btn_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (linstener != null) {
                        linstener.onImageClick((View) v.getParent(), position, "删除");
                    }
                }
            });
        }
        return convertView;
    }

    @Override
    public void dialogResult(int tag, int state) {
        if (state == LoadingDialog.SUCCESS) {
            ((Activity) context).setResult(100);
            ((Activity) context).finish();
        }
    }

    class WorkeditListHodle {
        TextView item_hearing_start, item_hearing_end, item_edit_body, item_edit_option, item_edit_answer, item_edit_exapin;
        LinearLayout item_edit_content, item_ll_hearing, item_ll_body;
        SeekBar item_seekBar;
        ImageView item_play_start, item_play_end, item_btn_del, item_body_image, item_option_image, item_answer_image, item_exapin_image;

        public WorkeditListHodle(View v) {
            item_edit_content = (LinearLayout) v.findViewById(R.id.item_edit_content);
            item_ll_hearing = (LinearLayout) v.findViewById(R.id.item_ll_hearing);
            item_ll_body = (LinearLayout) v.findViewById(R.id.item_ll_body);
            item_hearing_start = (TextView) v.findViewById(R.id.item_hearing_start);
            item_hearing_end = (TextView) v.findViewById(R.id.item_hearing_end);
            item_seekBar = (SeekBar) v.findViewById(R.id.item_seekBar);
            item_play_start = (ImageView) v.findViewById(R.id.item_play_start);
            item_play_end = (ImageView) v.findViewById(R.id.item_play_end);
            item_btn_del = (ImageView) v.findViewById(R.id.item_btn_del);
            item_body_image = (ImageView) v.findViewById(R.id.item_body_image);
            item_option_image = (ImageView) v.findViewById(R.id.item_option_image);
            item_edit_body = (TextView) v.findViewById(R.id.item_edit_body);
            item_edit_option = (TextView) v.findViewById(R.id.item_edit_option);

            item_edit_answer = (TextView) v.findViewById(R.id.item_edit_answer);
            item_edit_exapin = (TextView) v.findViewById(R.id.item_edit_exapin);
            item_answer_image = (ImageView) v.findViewById(R.id.item_answer_image);
            item_exapin_image = (ImageView) v.findViewById(R.id.item_exapin_image);
        }
    }

    private onDelClickLinstener linstener = null;

    public interface onDelClickLinstener {
        void onImageClick(View v, int poistion, String name);
    }

    public void setonDelClickLinstener(onDelClickLinstener linstener) {
        this.linstener = linstener;
    }


}
