package com.ripple.lendmoney.utils;

import android.content.Context;
import android.text.TextUtils;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.ripple.lendmoney.R;

/**
 * Created by admin on 2018/3/23.
 */

public class DialogUtil {

    /**
     * 加载对话框
     *
     * @param context
     * @return
     */
    public static QMUITipDialog showDialog(Context context,String tipWords) {
        QMUITipDialog tipDialog = new QMUITipDialog.Builder(context)
                .setTipWord(tipWords)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .create();
        tipDialog.show();
        tipDialog.setCancelable(false);
        return tipDialog;
    }


    public static QMUIDialog showCustomDialog(final Context context, String title, String content, final DialogCallBack dialogCallBack) {
        QMUIDialog qmuiDialog = new QMUIDialog.MessageDialogBuilder(context)
                .setTitle(TextUtils.isEmpty(title) ? null : title)
                .setMessage(content)
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction(0, "删除", QMUIDialogAction.ACTION_PROP_NEGATIVE, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                        dialogCallBack.onActionClick();
                    }
                })
                .create(R.style.DialogTheme2);
        qmuiDialog.show();
        return qmuiDialog;
    }


    public interface DialogCallBack {
        void onActionClick();
    }


}
