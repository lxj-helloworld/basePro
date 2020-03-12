package com.example.xiaojin20135.mybaseapp.view.widget;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiaojin20135.basemodule.activity.ToolBarActivity;
import com.example.xiaojin20135.basemodule.util.ui.DisplayHelper;
import com.example.xiaojin20135.basemodule.view.widget.Popus;
import com.example.xiaojin20135.basemodule.view.widget.popup.Popup;
import com.example.xiaojin20135.basemodule.view.widget.popup.QuickAction;
import com.example.xiaojin20135.mybaseapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyPopupActivity extends ToolBarActivity {
    private Button normal_btn,normal_popup_with_dim_btn,normal_popup_with_list_btn,normal_popup_with_action_btn;

    private Popup mPopup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_popup;
    }

    @Override
    protected void initView() {
        normal_btn = findViewById(R.id.normal_btn);
        normal_popup_with_dim_btn = findViewById(R.id.normal_popup_with_dim_btn);
        normal_popup_with_list_btn = findViewById(R.id.normal_popup_with_list_btn);
        normal_popup_with_action_btn = findViewById(R.id.normal_popup_with_action_btn);
    }

    @Override
    protected void initEvents() {
        normal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = new TextView(MyPopupActivity.this);
                textView.setLineSpacing(DisplayHelper.dp2px(MyPopupActivity.this, 4), 1.0f);
                int padding = DisplayHelper.dp2px(MyPopupActivity.this, 20);
                textView.setPadding(padding, padding, padding, padding);
                textView.setText("这是一个普通的浮层消息！");
                textView.setTextColor(Color.BLACK);

                mPopup = Popus.popup(MyPopupActivity.this, DisplayHelper.dp2px(MyPopupActivity.this,250))
                        .preferredDirection(Popup.DIRECTION_BOTTOM)
                        .view(textView)
                        .edgeProtection(DisplayHelper.dp2px(MyPopupActivity.this, 20))
                        .offsetX(DisplayHelper.dp2px(MyPopupActivity.this, 20))
                        .offsetYIfBottom(DisplayHelper.dp2px(MyPopupActivity.this, 5))
                        .shadow(true)
                        .arrow(true)
                        .animStyle(Popup.ANIM_GROW_FROM_CENTER)
                        .onDismiss(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                Toast.makeText(MyPopupActivity.this, "onDismiss", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show(v);

            }
        });

        //带遮罩的浮层消息
        normal_popup_with_dim_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = new TextView(MyPopupActivity.this);
                textView.setText("这是一个带遮罩的普通浮层消息！");
                textView.setTextColor(Color.BLACK);

                mPopup = Popus.popup(MyPopupActivity.this, DisplayHelper.dp2px(MyPopupActivity.this,250))
                        .preferredDirection(Popup.DIRECTION_BOTTOM)
                        .view(textView)
                        .edgeProtection(DisplayHelper.dp2px(MyPopupActivity.this, 20))
                        .offsetX(DisplayHelper.dp2px(MyPopupActivity.this, 20))
                        .offsetYIfBottom(DisplayHelper.dp2px(MyPopupActivity.this, 5))
                        .shadow(true)
                        .arrow(true)
                        .dimAmount(0.6f)
                        .animStyle(Popup.ANIM_GROW_FROM_CENTER)
                        .onDismiss(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                Toast.makeText(MyPopupActivity.this, "onDismiss", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show(v);
            }
        });


        //带列表的浮层
        normal_popup_with_list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] items = new String[]{
                        "Item 1",
                        "Item 2",
                        "Item 3",
                        "Item 4",
                        "Item 5"
                };
                List<String> data = new ArrayList<>();
                Collections.addAll(data,items);

                ArrayAdapter adapter = new ArrayAdapter<>(MyPopupActivity.this, R.layout.single_list_item, data);
                AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(MyPopupActivity.this, "Item " + (i + 1), Toast.LENGTH_SHORT).show();
                        if (mPopup != null) {
                            mPopup.dismiss();
                        }
                    }
                };
                mPopup = Popus.listPopup(MyPopupActivity.this,
                        DisplayHelper.dp2px(MyPopupActivity.this, 250),
                        DisplayHelper.dp2px(MyPopupActivity.this, 300),
                        adapter,
                        onItemClickListener)
                        .animStyle(Popup.ANIM_GROW_FROM_CENTER)
                        .preferredDirection(Popup.DIRECTION_BOTTOM)
                        .shadow(true)
                        .dimAmount(0.6f)
                        .offsetYIfTop(DisplayHelper.dp2px(MyPopupActivity.this, 5))
                        .onDismiss(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                Toast.makeText(MyPopupActivity.this, "onDismiss", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show(v);

            }
        });


        //快捷菜单
        normal_popup_with_action_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Popus.quickAction(MyPopupActivity.this,
                        DisplayHelper.dp2px(MyPopupActivity.this, 88),
                        DisplayHelper.dp2px(MyPopupActivity.this, 88))
                        .shadow(true)
                        .edgeProtection(DisplayHelper.dp2px(MyPopupActivity.this, 20))
                        .addAction(new QuickAction.Action().icon(R.drawable.permission_ic_location).text("定位").onClick(
                                new QuickAction.OnClickListener() {
                                    @Override
                                    public void onClick(QuickAction quickAction, QuickAction.Action action, int position) {
                                        quickAction.dismiss();
                                        Toast.makeText(MyPopupActivity.this, "定位", Toast.LENGTH_SHORT).show();
                                    }
                                }
                        ))
                        .addAction(new QuickAction.Action().icon(R.drawable.permission_ic_contacts).text("人员").onClick(
                                new QuickAction.OnClickListener() {
                                    @Override
                                    public void onClick(QuickAction quickAction, QuickAction.Action action, int position) {
                                        quickAction.dismiss();
                                        Toast.makeText(MyPopupActivity.this, "操作成功", Toast.LENGTH_SHORT).show();
                                    }
                                }
                        ))
                        .addAction(new QuickAction.Action().icon(R.drawable.permission_ic_phone).text("拨号").onClick(
                                new QuickAction.OnClickListener() {
                                    @Override
                                    public void onClick(QuickAction quickAction, QuickAction.Action action, int position) {
                                        quickAction.dismiss();
                                        Toast.makeText(MyPopupActivity.this, "拨号成功", Toast.LENGTH_SHORT).show();
                                    }
                                }
                        ))
                        .show(v);
            }
        });


    }

    @Override
    protected void loadData() {

    }
}
