package com.llkj.util;

import android.content.Context;
import android.os.PowerManager;

import static android.content.Context.POWER_SERVICE;
import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by yujunlong on 2016/12/13.
 */

public class WakeLockUtil {
    public static  void setScreenOn(Context c, PowerManager.WakeLock wakeLock){
        if (wakeLock == null){
            wakeLock = getWakeLock(getPowerManager(c));
        }
        wakeLock.acquire();
    }
    public static PowerManager getPowerManager(Context c){
        return   (PowerManager) c.getSystemService(POWER_SERVICE);
    }
    public static PowerManager.WakeLock getWakeLock(PowerManager pm){
        return   pm.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, TAG);
    }

    public static void setScreenOff(PowerManager.WakeLock wakeLock){
        if (wakeLock != null){
            wakeLock.setReferenceCounted(false);
            wakeLock.release();
        }
    }
}
