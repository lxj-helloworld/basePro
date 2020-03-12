package com.example.xiaojin20135.mybaseapp.alert;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.xiaojin20135.basemodule.activity.ToolBarActivity;
import com.example.xiaojin20135.basemodule.view.widget.dialog.Dialog;
import com.example.xiaojin20135.basemodule.view.widget.dialog.DialogAction;
import com.example.xiaojin20135.mybaseapp.R;

public class SelfAlertActivity extends ToolBarActivity {
    private Button normal_btn,diff_color_btn,long_info_btn,checkbox_btn,single_sel_btn,single_sel_with_mark_btn,multiply_sel_btn;


    private int mCurrentDialogStyle = R.style.Dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_self_alert;
    }

    @Override
    protected void initView() {
        normal_btn = findViewById(R.id.normal_btn);
        diff_color_btn = findViewById(R.id.diff_color_btn);
        long_info_btn = findViewById(R.id.long_info_btn);
        checkbox_btn = findViewById(R.id.checkbox_btn);
        single_sel_btn = findViewById(R.id.single_sel_btn);
        single_sel_with_mark_btn = findViewById(R.id.single_sel_with_mark_btn);
        multiply_sel_btn = findViewById(R.id.multiply_sel_btn);
    }

    @Override
    protected void initEvents() {
        //显示普通按钮 同颜色
        normal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNormalBtn();
            }
        });
        //显示普通按钮，不同颜色
        diff_color_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNormalColorDiffColor();
            }
        });
        //长提示
        long_info_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLongInfo();
            }
        });
        //带单选框
        checkbox_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                withCheckBox();
            }
        });

        //单选对话框
        single_sel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singleSel();
            }
        });
        
        //带勾选类单选
        single_sel_with_mark_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singleSelWithMark(); 
            }
        });

        multiply_sel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiSel();
            }
        });

    }

    @Override
    protected void loadData() {

    }

    private void showNormalBtn(){
        new Dialog.MessageDialogBuilder(SelfAlertActivity.this)
                .setTitle("标题")
                .setMessage("确定要提交吗？")
                .addAction("取消", new DialogAction.ActionListener() {
                    @Override
                    public void onClick(Dialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction(0, "确定", DialogAction.ACTION_PROP_POSITIVE, new DialogAction.ActionListener() {
                    @Override
                    public void onClick(Dialog dialog, int index) {
                        dialog.dismiss();
                        Toast.makeText(SelfAlertActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                    }
                })
                .create(mCurrentDialogStyle)
                .show();
    }

    private void showNormalColorDiffColor(){
        new Dialog.MessageDialogBuilder(SelfAlertActivity.this)
                .setTitle("标题")
                .setMessage("确定要删除吗？")
                .addAction("取消", new DialogAction.ActionListener() {
                    @Override
                    public void onClick(Dialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction(0, "删除", DialogAction.ACTION_PROP_NEGATIVE, new DialogAction.ActionListener() {
                    @Override
                    public void onClick(Dialog dialog, int index) {
                        Toast.makeText(SelfAlertActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .create(mCurrentDialogStyle).show();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-10 10:06
    * description: 常提示型弹框
    */
    private void showLongInfo(){
        new Dialog.MessageDialogBuilder(SelfAlertActivity.this)
                .setTitle("标题")
                .setMessage("这是一段很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长" +
                        "很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很" +
                        "很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很" +
                        "很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很" +
                        "长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长" +
                        "很长很长很长很长很很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长" +
                        "很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长" +
                        "很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长" +
                        "很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长" +
                        "很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长" +
                        "很长很长很长很长很长很长很长很长很长很长很长很长很长很长长很长的文案")
                .addAction("取消", new DialogAction.ActionListener() {
                    @Override
                    public void onClick(Dialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .create(mCurrentDialogStyle).show();
    }
    
    
    
    /*
    * @author lixiaojin
    * create on 2020-03-10 10:16
    * description:  带单选框
    */
    private void withCheckBox(){
        new Dialog.CheckBoxMessageDialogBuilder(SelfAlertActivity.this)
                .setTitle("这里是简单的提示或警告信息")
                .setMessage("下次不再提示")
                .setChecked(true)
                .addAction("取消", new DialogAction.ActionListener() {
                    @Override
                    public void onClick(Dialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction("确认", new DialogAction.ActionListener() {
                    @Override
                    public void onClick(Dialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .create(mCurrentDialogStyle).show();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-10 10:43
    * description: 单选对话框
    */
    private void singleSel(){
        final String[] items = new String[]{"选项1", "选项2", "选项3"};
        new Dialog.MenuDialogBuilder(SelfAlertActivity.this)
                .addItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(SelfAlertActivity.this, "你选择了 " + items[which], Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .create(mCurrentDialogStyle).show();
    }
    
    
    /*
    * @author lixiaojin
    * create on 2020-03-10 11:12
    * description: 带mark类
    */
    private void singleSelWithMark(){
        final String[] items = new String[]{"选项1", "选项2", "选项3"};
        final int checkedIndex = 1;
        new Dialog.CheckableDialogBuilder(SelfAlertActivity.this)
                .setCheckedIndex(checkedIndex)
                .addItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(SelfAlertActivity.this, "你选择了 " + items[which], Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .create(mCurrentDialogStyle).show();
    }
    
    /*
    * @author lixiaojin
    * create on 2020-03-10 10:55
    * description: 多选对话框
    */
    private void multiSel(){
        final String[] items = new String[]{"选项1", "选项2", "选项3", "选项4", "选项5", "选项6"};
        final Dialog.MultiCheckableDialogBuilder builder = new Dialog.MultiCheckableDialogBuilder(SelfAlertActivity.this)
                .setCheckedItems(new int[]{1, 3})
                .addItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.addAction("取消", new DialogAction.ActionListener() {
            @Override
            public void onClick(Dialog dialog, int index) {
                dialog.dismiss();
            }
        });
        builder.addAction("提交", new DialogAction.ActionListener() {
            @Override
            public void onClick(Dialog dialog, int index) {
                String result = "你选择了 ";
                for (int i = 0; i < builder.getCheckedItemIndexes().length; i++) {
                    result += "" + builder.getCheckedItemIndexes()[i] + "; ";
                }
                Toast.makeText(SelfAlertActivity.this, result, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        builder.create(mCurrentDialogStyle).show();
    }
    
}
