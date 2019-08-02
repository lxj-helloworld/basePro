package com.example.xiaojin20135.mybaseapp.download;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.DownloadListener;
import android.widget.TextView;

import com.example.xiaojin20135.basemodule.activity.ToolBarActivity;
import com.example.xiaojin20135.basemodule.download.listener.MyDownloadListener;
import com.example.xiaojin20135.basemodule.download.util.DownloadUtils;
import com.example.xiaojin20135.basemodule.files.OpenFiles;
import com.example.xiaojin20135.basemodule.retrofit.bean.ResponseBean;
import com.example.xiaojin20135.mybaseapp.R;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class DownloadActivity extends ToolBarActivity {
    private TextView load_progress_TV;

    private String filePath = "/storage/emulated/0/Documents/lianyuan_20181106.apk";
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
    }

    @Override
    protected int getLayoutId () {
        return R.layout.activity_download;
    }

    @Override
    protected void initView () {
        load_progress_TV = (TextView)findViewById (R.id.load_progress_TV);
    }

    @Override
    protected void initEvents () {

    }

    @Override
    protected void loadData () {

    }

    public void onClick (View view) {
        switch (view.getId ()){
            case R.id.start_doc_btn:
                download("http://172.20.3.53:8924/er/cbo/cboAttachment_download.action?attachmentId=19080100000012");
                break;
            case R.id.start_pdf_btn:
                download("http://lineloss.topscomm.net:5101/app_download/app/zsk/sample.pdf");
                break;
            case R.id.start_image_btn:
                download("https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3160149763,2396430636&fm=173&app=25&f=JPEG?w=640&h=405&s=6F82BC0B68A36E8045FD7CC60300C0B2");
                break;
            case R.id.start_apk_btn:
                download("http://lineloss.topscomm.net:5101/app_download/package/nmscharge/lianyuan_20181106.apk");
                break;
            case R.id.openfile_btn:
                OpenFiles.openFile (DownloadActivity.this,filePath);
                break;
            case R.id.upload_btn:
                Map<String, RequestBody> paraMap = new HashMap<> ();
                paraMap.put("sourceId", RequestBody.create(MediaType.parse("text/plain"),"18090100000003"));
                paraMap.put("sourceType", RequestBody.create(MediaType.parse("text/plain"), "RtsDebugCode"));
                paraMap.put("attachmentTypeId", RequestBody.create(MediaType.parse("text/plain"), "17031000000001"));
                File file = new File ("/storage/emulated/0/topscomm/041143.png");
                paraMap.put("displayName", RequestBody.create(MediaType.parse("text/plain"), file.getName()));
                MultipartBody.Part[] filePart = new MultipartBody.Part[1];
                filePart[0] = MultipartBody.Part.createFormData("attachFile",file.getName(),RequestBody.create(MediaType.parse("image/png"),file));
                uploadFileWithMethod ("mobile/cboAttachmentMobileAction_upload",paraMap,filePart);
                break;
        }
    }

    public void cboAttachmentMobileAction_upload(ResponseBean responseBean){
        Log.d (TAG,"responseBean  = " + responseBean.getActionResult ().toString ());
    }

    private void download(String url){
        DownloadUtils downloadUtils = new DownloadUtils ("http://172.20.3.53:8924/er/");
        downloadUtils.downloadFile(url, new MyDownloadListener () {
            @Override
            public void onStart() {
                Log.e(TAG, "onStart: 开始下载");
                load_progress_TV.setText("开始下载");
            }
            @Override
            public void onProgress(final int currentLength) {
                Log.e(TAG, "onLoading: " + currentLength);
                load_progress_TV.setText("下载进度：" + currentLength);
            }
            @Override
            public void onFinish(String localPath) {
                Log.e(TAG, "onFinish: " + localPath);
                String result = "下载完成\r\n";
                result = result + "文件路径：" + localPath;
                load_progress_TV.setText(result);
                filePath = localPath;
            }
            @Override
            public void onFailure(final String errorInfo) {
                Log.e(TAG, "onFailure: " + errorInfo);
                load_progress_TV.setText("下载失败");
            }
        });
    }
}
