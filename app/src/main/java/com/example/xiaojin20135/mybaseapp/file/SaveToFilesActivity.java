package com.example.xiaojin20135.mybaseapp.file;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.xiaojin20135.basemodule.activity.ToolBarActivity;
import com.example.xiaojin20135.basemodule.files.AppExternalFileWriter;
import com.example.xiaojin20135.basemodule.files.FileSaveHelp;
import com.example.xiaojin20135.mybaseapp.R;

import java.io.File;

import butterknife.BindView;

public class SaveToFilesActivity extends ToolBarActivity {

    @BindView(R.id.file_content_ET)
    EditText file_content_ET;
    @BindView(R.id.save_file_Btn)
    Button save_file_Btn;
    @BindView(R.id.zip_file_Btn)
    Button zip_file_Btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_save_to_files;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initEvents() {
        save_file_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = file_content_ET.getText().toString();
                if(TextUtils.isEmpty(content)){
                    file_content_ET.setError("不能为空");
                    return;
                }

                saveToFiels(content);
            }
        });

        zip_file_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zipToFile("/storage/emulated/0/thisismineaudio035805166.amr");
            }
        });
    }

    @Override
    protected void loadData() {

    }


    /*
    * @author lixiaojin
    * create on 2019/3/22 16:58
    * description: 保存到文件
    */
    private void saveToFiels(String content){
        Log.d(TAG,"content = " + content);
        FileSaveHelp.FILE_SAVE_HELP.saveToTxtFile(this,content,"hello","txt");
    }


    /**
     * 压缩文件，传入被压缩文件路径
     * @param filePath
     */
    private void zipToFile(String filePath){
        File file = new File(filePath);
        String zipPath = new AppExternalFileWriter(this).makeFileToZip(file);
        Log.d(TAG,"zipPath = " + zipPath);
        showToast(this,"保存至：" + zipPath);
    }
}
