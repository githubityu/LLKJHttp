package com.llkj.util;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * 作者：Administrator on 2016/3/24 17:51
 * 邮箱：
 */
public class ResourceUtil {
	public static String getContent(Context c, int rid){
		return c.getResources().getString(rid);
	}
	public static int getColor(Context c, int rid){
		return c.getResources().getColor(rid);
	}
	public static float getDimension(Context c, int rid){
		return c.getResources().getDimension(rid);
	}

	public static Drawable getDrawable(Context c, int rid){
		return c.getResources().getDrawable(rid);
	}
}
