package com.llkj.http;

import android.content.Context;
import android.widget.Toast;

import com.llkj.util.ToastUtil;
import com.orhanobut.logger.Logger;

import rx.Subscriber;

/**
 * Created by yujunlong on 2016/8/25.
 */

public class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {
    private SubscriberOnNextListener mSubscriberOnNextListener;
    private ProgressDialogHandler mProgressDialogHandler;

    private Context context;
    private int httpcode;

    public ProgressSubscriber(SubscriberOnNextListener mSubscriberOnNextListener, Context context,int httpcode) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.context = context;
        this.httpcode = httpcode;
        mProgressDialogHandler = new ProgressDialogHandler(context, this, true);
    }

    private void showProgressDialog(){
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog(){
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    @Override
    public void onStart() {
        showProgressDialog();
    }

    @Override
    public void onCompleted() {
        dismissProgressDialog();
      //  Toast.makeText(context, " Completed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(Throwable e) {
        dismissProgressDialog();
        Logger.e(e.getMessage());
        ApiException a= e instanceof  ApiException? ((ApiException) e) : null;
        if(a!=null)
        Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
      //  Toast.makeText(context, "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNext(T t) {
        mSubscriberOnNextListener.onNext(t,httpcode);
    }

    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }

}
