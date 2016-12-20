package com.llkj.http;

/**
 * Created by yujunlong on 2016/8/25.
 */

public interface SubscriberOnNextListener<T> {
    void onNext(T t, int httpcode);
}
