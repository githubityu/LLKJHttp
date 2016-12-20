package com.llkj.llkjhttp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.llkj.download.FileCallback;
import com.llkj.http.HttpMethods;
import com.llkj.http.HttpService;
import com.llkj.llkjhttp.base.BaseActivity;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

import static com.llkj.http.HttpMethods.initOkHttpClient;
import static com.llkj.util.Constant.destFileDir;
import static com.llkj.util.Constant.destFileName;

public class DownloadActivity extends BaseActivity {


    @BindView(R.id.textView2)
    TextView mTextView2;
    @BindView(R.id.button8)
    Button mButton8;
    @BindView(R.id.activity_main)
    PercentRelativeLayout mActivityMain;

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_download;
    }


    @Override
    public void onNext(Object o, int httpcode) {

    }


    public static void startActivity(Context c) {
        Intent i = new Intent(c, DownloadActivity.class);
        c.startActivity(i);
    }

    @OnClick(R.id.button8)
    public void onClick() {
        downFile();
    }

    public void downFile(){

        HttpMethods.getInstance().getRetrofit(initOkHttpClient(),"https://codeload.github.com/")
                .create(HttpService.class)
                .loadFile()
                .enqueue(new FileCallback(getCompositeSubscription(),destFileDir, destFileName) {

                    @Override
                    public void onSuccess(File file) {
                        Log.e("zs", "请求成功");
                        // 安装软件
                    }

                    @Override
                    public void onLoading(long progress, long total) {
                        Log.e("zs", progress + "----" + total);
                        mTextView2.setText(progress * 100 / total+"");
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("zs", "请求失败");
                    }
                });
    }


}
