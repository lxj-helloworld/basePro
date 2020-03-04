package com.example.xiaojin20135.mybaseapp.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.xiaojin20135.basemodule.activity.ToolBarActivity;
import com.example.xiaojin20135.basemodule.util.MethodsUtils;
import com.example.xiaojin20135.basemodule.view.edittext.ip.IPEditText;
import com.example.xiaojin20135.basemodule.view.edittext.ip.IPView;
import com.example.xiaojin20135.mybaseapp.R;

import butterknife.BindView;

@Route(path = "/router/ipActivity")
public class IpAndPortActivity extends ToolBarActivity {
    @BindView (R.id.ip_ET)
    IPView ip_ET;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
    }

    @Override
    protected int getLayoutId () {
        return R.layout.activity_ip_and_port;
    }

    @Override
    protected void initView () {

    }

    @Override
    protected void initEvents () {

    }

    @Override
    protected void loadData () {

    }

    public void onClick (View view) {
        if(view.getId () == R.id.read_btn){
            byte[] temp = ip_ET.getBytesWithIP ();
            Log.d (TAG," = " + MethodsUtils.METHODS_UTILS.byteToHexString (temp));
            Log.d (TAG," = " + ip_ET.getAbsEditText ().toString ());
        }else if(view.getId () == R.id.set_btn){
            ip_ET.setGatewayText (new String[]{"1","2","3","4"});
        }
    }
}
