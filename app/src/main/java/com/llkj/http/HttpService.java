package com.llkj.http;

import android.util.ArrayMap;
import android.util.SparseArray;

import com.llkj.llkjhttp.Model.Subject;
import com.llkj.llkjhttp.Model.UploadBean;

import java.util.Map;
import java.util.Objects;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

import static com.llkj.http.HttpApi.HTTP_DOWN;
import static com.llkj.http.HttpApi.HTTP_GETCODE;
import static com.llkj.http.HttpApi.HTTP_LOGIN;
import static com.llkj.http.HttpApi.HTTP_TESTPOST;
import static com.llkj.http.HttpApi.HTTP_UPLOADFILE;

/**
 * Created by yujunlong on 2016/11/11.
 */

public interface HttpService {
    @GET(HTTP_LOGIN)
    Observable<HttpResult<Subject>> login(@Query("account") String account, @Query("password") String password);
    @FormUrlEncoded
    @POST(HTTP_GETCODE)
    Observable<HttpResult<Subject>> getCode(@Path("versionName") String versionName, @Field("phone") String phone, @Field("type") int type);
    @FormUrlEncoded
    @POST(HTTP_TESTPOST)
    Observable<HttpResult> textPost(@FieldMap Map<String,Object> params);
    @Multipart
    @POST(HTTP_UPLOADFILE)
    Observable<HttpResult<UploadBean>> uploadfile(@PartMap Map<String,Object> params, @PartMap ArrayMap<String, RequestBody> files);
    @GET(HTTP_DOWN)
    Call<ResponseBody> loadFile();
}
