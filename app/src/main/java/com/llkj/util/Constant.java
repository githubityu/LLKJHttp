package com.llkj.util;

import android.os.Environment;

import java.io.File;

/**
 * Created by yujunlong on 2016/12/3.
 */

public class Constant {
    public static final String IMG_MEDIATYPE = "image/png";
    public final static String VERSION_NAME = "0.2";
    public final static String DATA = "data";
    public static final String STATE_SAVE_IS_HIDDEN = "state_save_is_hidden";
    /**
     * 0=android,1=iOS
     */
    public final static String PHONE_TYPE = "0";

    public static  String  destFileDir = Environment.getExternalStorageDirectory().getAbsolutePath() + File
            .separator + "M_DEFAULT_DIR";
    /**
     * 目标文件存储的文件名
     */
    public static String destFileName = "shan_yao.apk";
}
