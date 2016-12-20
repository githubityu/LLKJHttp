package com.llkj.http;

import android.content.Context;
import android.util.ArrayMap;

import com.llkj.download.FileResponseBody;
import com.llkj.llkjhttp.Model.Subject;
import com.llkj.util.Constant;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.llkj.http.HttpApi.BASE_URL;

/**
 * Created by yujunlong on 2016/8/24.
 */

public class HttpMethods {
    private static final int DEFAULT_TIMEOUT = 5;
    private static final int HTTP_SUCCESS = 1;
    private Retrofit retrofit;
    private HttpService httpService;
    //构造方法私有
    private HttpMethods() {
        //手动创建一个OkHttpClient并设置超时时间
        retrofit = getRetrofit(getOkHttpClient(),BASE_URL);
        httpService = retrofit.create(HttpService.class);
    }

    public OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        if(true){
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClientBuilder.addInterceptor(logging);
        }
        return httpClientBuilder.build();
    }


    public Retrofit getRetrofit(OkHttpClient client,String baseUrl) {
       return new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())//GsonConverterFactory CustomGsonConverterFactory
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();

    }
    /**
     *  下载文件  初始化OkHttpClient
     *
     * @return
     */
    public static  OkHttpClient initOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(100000, TimeUnit.SECONDS);
        builder.networkInterceptors().add(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                okhttp3.Response originalResponse = chain.proceed(chain.request());
                return originalResponse
                        .newBuilder()
                        .body(new FileResponseBody(originalResponse))
                        .build();
            }
        });
        return builder.build();
    }


    //在访问HttpMethods时创建单例
    private static class SingletonHolder{
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance(){
        return SingletonHolder.INSTANCE;
    }


    //添加线程管理并订阅
    private void toSubscribe(Observable o, Subscriber s){
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }
    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    private class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {

        @Override
        public T call(HttpResult<T> httpResult) {
            if (httpResult.code != HTTP_SUCCESS) {
                throw new ApiException(httpResult.code,httpResult.message);
            }
            return httpResult.data;

        }
    }

    private class HttpResultFunc1<T> implements Func1<HttpResult<T>, Observable<T>> {

        @Override
        public Observable<T> call(HttpResult<T> httpResult) {
            return flatResponse(httpResult);
        }
    }
    private class HttpResultFunc2<T> implements Func1<HttpResult<T>, Observable<T>> {

        @Override
        public Observable<T> call(HttpResult<T> httpResult) {
            //相当于for循环取出集合中的每一个T然后交给Subscriber处理
            return Observable.from(new ArrayList<T>());
        }
    }

    public <T> Observable<T> flatResponse(final HttpResult<T> response) {
        return Observable.create(new Observable.OnSubscribe<T>() {

            @Override
            public void call(Subscriber<? super T> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    if(response.code==HTTP_SUCCESS){
                        subscriber.onNext(response.data);
                        subscriber.onCompleted();
                    }else{
                        subscriber.onError(new ApiException(response.code,response.message));
                    }
                    subscriber.onCompleted();
                }
            }
        });
    }
    public Subscriber login(Context c, CompositeSubscription compositeSubscription,SubscriberOnNextListener subscriberOnNextListener){
        //修改之后的样子
        Observable observable = httpService.login("18637492999","123456")
                .map(new HttpResultFunc<Subject>());
        Subscriber subscriber =  new ProgressSubscriber(subscriberOnNextListener, c,100);
        toSubscribe(observable, subscriber);
        compositeSubscription.add(subscriber);
        return subscriber;
    }
    public Subscriber getCode(Context c, CompositeSubscription compositeSubscription,SubscriberOnNextListener subscriberOnNextListener){
        //修改之后的样子
        Observable observable = httpService.getCode(Constant.VERSION_NAME,"",0)
                .map(new HttpResultFunc<Subject>());
        Subscriber subscriber =  new ProgressSubscriber(subscriberOnNextListener, c,100);
        toSubscribe(observable, subscriber);
        compositeSubscription.add(subscriber);
        return subscriber;
    }
    public void uploadFile(Subscriber<Object> subscriber, ArrayMap<String,Object> par, List<File> files) {
        ArrayMap<String, RequestBody> bodyMap = new ArrayMap<>();
        for (int i = 0; i < files.size(); i++) {
            bodyMap.put("pic"+"["+i+"]\"; filename=\""+files.get(i).getName(),RequestBody.create(MediaType.parse(Constant.IMG_MEDIATYPE),files.get(i)));
        }
        Logger.e(par.toString());
        Observable observable = httpService.uploadfile(par,bodyMap);
        toSubscribe(observable,subscriber);
    }
}
