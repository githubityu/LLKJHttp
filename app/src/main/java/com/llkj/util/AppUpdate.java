package com.llkj.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.llkj.download.DownLoadService;

/**
 * Created by yujunlong on 2016/12/3.
 */

public class AppUpdate {
    /**
     * 显示更新对话框
     *
     * @param version_info
     */
    public static  void showNoticeDialog(final Context c, String version_info) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("更新提示");
        builder.setMessage(version_info);

        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                c.startService(new Intent(c, DownLoadService.class));
            }
        });

        builder.setNegativeButton("以后更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Dialog noticeDialog = builder.create();
        noticeDialog.show();
    }
}
