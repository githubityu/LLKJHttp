package com.llkj.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.llkj.llkjhttp.R;
import com.llkj.llkjhttp.base.LlkjApplication;

/**
 * Created by yujunlong on 2016/11/11.
 */

public class ToastUtil {
    private static long lastToastTime;
    private static String lastToast = "";
    public static void showToast(Context c, String message) {
        showToast(c,message, Toast.LENGTH_LONG,R.mipmap.btn_back, Gravity.BOTTOM);
    }
    public static void showToast(Context c, int message) {
        showToast(c,ResourceUtil.getContent(c,message), Toast.LENGTH_LONG,R.mipmap.btn_back, Gravity.BOTTOM);
    }
    private static void showToast(Context c,String message, int duration, int icon,
                                 int gravity) {
        if (message != null && !message.equalsIgnoreCase("")) {
            long time = System.currentTimeMillis();
            if (!message.equalsIgnoreCase(lastToast)
                    || Math.abs(time - lastToastTime) > 2000) {
                View view = LayoutInflater.from(LlkjApplication.context()).inflate(
                        R.layout.view_toast, null);
                ((TextView) view.findViewById(R.id.title_tv)).setText(message);
                if (icon != 0) {
                    ((ImageView) view.findViewById(R.id.icon_iv))
                            .setImageResource(icon);
                    ((ImageView) view.findViewById(R.id.icon_iv))
                            .setVisibility(View.VISIBLE);
                }
                Toast toast = new Toast(c);
                toast.setView(view);
                if (gravity == Gravity.CENTER) {
                    toast.setGravity(gravity, 0, 0);
                } else {
                    toast.setGravity(gravity, 0, 35);
                }

                toast.setDuration(duration);
                toast.show();
                lastToast = message;
                lastToastTime = System.currentTimeMillis();
            }
        }
    }
}
