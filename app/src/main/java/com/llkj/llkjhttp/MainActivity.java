package com.llkj.llkjhttp;

import android.Manifest;
import android.support.v7.widget.AppCompatEditText;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxCompoundButton;
import com.llkj.app.AppConfig;
import com.llkj.http.HttpMethods;
import com.llkj.llkjhttp.base.BaseActivity;

import com.llkj.llkjhttp.test.DbTestActivity;
import com.llkj.util.AppUpdate;
import com.llkj.util.GlideUtil;
import com.llkj.util.PlayerManager;
import com.llkj.util.StringUtils;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

public class MainActivity extends BaseActivity {
    @BindView(R.id.iv_one)
    ImageView mIvOne;
    @BindView(R.id.cb_do)
    CheckBox cb_do;
    @BindView(R.id.btn_do)
    Button btn_do;
    @BindView(R.id.tv_do)
    TextView tv_do;
    @BindView(R.id.acet_content)
    AppCompatEditText acet_content;
    private int code;
    private PlayerManager playerManager;
    private static String PATH = "android.resource://";
    private boolean isPlaying,isOut = true;
    @Override
    protected void initView() {
        super.initView();
        setToolBar("标题",true,"",R.mipmap.btn_back,true,"右边文字",R.mipmap.btn_back);
        GlideUtil.getInstance().loadCircleImage(mIvOne, AppConfig.TEST_PIC_1);
        HttpMethods.getInstance().getCode(this,getCompositeSubscription(), this);
        RxView.clicks(findViewById(R.id.btn_do))
                .compose(new RxPermissions(this).ensure(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE))
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if(aBoolean){
                            StringUtils.getThumbnail(MainActivity.this);
                        }else{
                            Logger.e("拒绝");
                        }
                    }
                });
        RxCompoundButton.checkedChanges(cb_do)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        btn_do.setText("aBoolean="+aBoolean);
                    }
                });
        playerManager = PlayerManager.getManager();
        PATH = PATH + getPackageName() + "/" + R.raw.jueshi;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initListener() {
        super.initListener();
        RxView.clicks(findViewById(R.id.btn_changge))
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (isOut) {
                            playerManager.changeToEarpieceMode();
                        }else{
                            playerManager.changeToSpeakerMode();
                        }
                        isOut = !isOut;
                    }
                });

    }

    @Override
    protected void rightDo() {
        super.rightDo();
//        if (isPlaying) {
//            playerManager.stop();
//        }else {
//            playerManager.play(PATH, callback);
//        }
//        isPlaying = !isPlaying;
       // AppUpdate.showNoticeDialog(this,"1.0");
        //DbTestActivity.startActivity(this);
        Main4Activity.startActivity(this);

    }

    @Override
    public void onNext(Object o, int httpcode) {
        Logger.e(o.toString());
        switch (httpcode) {
            case 100:

                break;
        }
    }
    private PlayerManager.PlayCallback callback = new PlayerManager.PlayCallback() {
        @Override
        public void onPrepared() {
            Logger.e("音乐准备完毕,开始播放");
        }
        @Override
        public void onComplete() {
            Logger.e("音乐播放完毕");
        }
        @Override
        public void onStop() {
            Logger.e("音乐停止播放");
        }
    };
}
