package com.llkj.http;

/**
 * Created by yujunlong on 2016/8/24.
 */

public class HttpResult<T> {
    public boolean success;
    public int code;
    public String message;
    public T data;
}
