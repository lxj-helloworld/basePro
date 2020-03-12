package com.example.xiaojin20135.mybaseapp.view.widget;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.xiaojin20135.basemodule.activity.ToolBarActivity;
import com.example.xiaojin20135.basemodule.view.widget.empty.EmptyView;
import com.example.xiaojin20135.mybaseapp.R;

public class EmptyViewActivity extends ToolBarActivity {

    EmptyView mEmptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTwoLine();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_empty_view;
    }

    @Override
    protected void initView() {
        mEmptyView = findViewById(R.id.emptyView);
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void loadData() {

    }

    /*
    * @author lixiaojin
    * create on 2020-03-11 09:51
    * description: 仅显示两行文字
    */
    private void showTwoLine(){
        mEmptyView.show("加载失败","服务器故障，请稍后再试！");
    }

    /*
    * @author lixiaojin
    * create on 2020-03-11 09:52
    * description: 今夏是一行文字
    */
    private void showOneLineWords(){
        mEmptyView.show("您访问的内容不存在！","");
    }

    /*
    * @author lixiaojin
    * create on 2020-03-11 09:53
    * description: 显示等待框
    */
    private void showLoading(){
        mEmptyView.show(true);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-11 09:53
    * description: 显示一行文字和按钮
    */
    private void showOneLineWordsAndOpera(){
        mEmptyView.show(false, "加载失败", null, "点击重试", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EmptyViewActivity.this,"已重试！！",Toast.LENGTH_LONG).show();
            }
        });
    }

    /*
    * @author lixiaojin
    * create on 2020-03-11 09:53
    * description: 显示两行文字和按钮
    */
    private void showTwoLineWordsAndOpera(){
        mEmptyView.show(false, "加载失败", "这里填写可能的操作提示，比如检查网络", "点击重试", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EmptyViewActivity.this,"尝试重试中...",Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.empty_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.two_line_words:
                showTwoLine();
                break;
            case R.id.one_line_words:
                showOneLineWords();
                break;
            case R.id.loading:
                showLoading();
                break;
            case R.id.one_line_words_and_opera:
                showOneLineWordsAndOpera();
                break;
            case R.id.two_line_words_and_opera:
                showTwoLineWordsAndOpera();
                break;
        }


        return super.onOptionsItemSelected(item);
    }
}
