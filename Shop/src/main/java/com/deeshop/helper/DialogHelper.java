package com.deeshop.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.widget.Toast;

import org.xutils.x;


public class DialogHelper {
    private Activity mActivity;
    private AlertDialog mAlertDialog;
    private static Toast mToast = null;

    public DialogHelper(Activity activity) {
        mActivity = activity;
    }

    /**
     * 弹对话框
     *
     * @param title
     *            标题
     * @param msg
     *            消息
     * @param positive
     *            确定
     * @param positiveListener
     *            确定回调
     * @param negative
     *            否定
     * @param negativeListener
     *            否定回调
     */
    public void alert(final String title, final String msg, final String positive,
                      final DialogInterface.OnClickListener positiveListener,
                      final String negative, final DialogInterface.OnClickListener negativeListener) {
        alert(title, msg, positive, positiveListener, negative, negativeListener, false);
    }

    /**
     * 弹对话框
     *
     * @param title
     *            标题
     * @param msg
     *            消息
     * @param positive
     *            确定
     * @param positiveListener
     *            确定回调
     * @param negative
     *            否定
     * @param negativeListener
     *            否定回调
     * @param isCanceledOnTouchOutside
     *            是否可以点击外围框
     */
    public void alert(final String title, final String msg, final String positive,
                      final DialogInterface.OnClickListener positiveListener,
                      final String negative,
                      final DialogInterface.OnClickListener negativeListener,
                      final Boolean isCanceledOnTouchOutside) {
        dismissProgressDialog();

        mActivity.runOnUiThread(() -> {
            if (mActivity == null || mActivity.isFinishing()) {
                return;
            }
                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
            if (title != null) {
                builder.setTitle(title);
            }
            if (msg != null) {
                builder.setMessage(msg);
            }
            if (positive != null) {
                builder.setPositiveButton(positive, positiveListener);
            }
            if (negative != null) {
                builder.setNegativeButton(negative, negativeListener);
            }
            mAlertDialog = builder.show();
            mAlertDialog.setCanceledOnTouchOutside(isCanceledOnTouchOutside);
            mAlertDialog.setCancelable(false);
        });
    }

    /**
     * TOAST
     *
     * @param msg
     *            消息
     * @param period
     *            时长
     */

    public void toast(final String msg, final int period) {
        mActivity.runOnUiThread(() -> {
            if (mToast == null) {
                mToast = Toast.makeText(mActivity, msg, period);
            } else {
                mToast.setText(msg);
            }
            mToast.show();
        });
    }

    public static void toast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(x.app(), msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }

    /**
     * 显示对话框
     *
     * @param showProgressBar
     *            是否显示圈圈
     * @param msg
     *            对话框信息
     */
    public void showProgressDialog(boolean showProgressBar, String msg) {
        showProgressDialog(msg, true, null, showProgressBar);
    }

    /**
     * 显示进度对话框
     *
     * @param msg
     *            消息
     */
    public void showProgressDialog(final String msg) {
        showProgressDialog(msg, true, null, true);
    }

    /**
     * 显示可取消的进度对话框
     *
     * @param msg
     *            消息
     */
    public void showProgressDialog(final String msg, final boolean cancelable,
                                   final OnCancelListener cancelListener,
                                   final boolean showProgressBar) {
        dismissProgressDialog();

        mActivity.runOnUiThread(() -> {
            if (mActivity == null || mActivity.isFinishing()) {
                return;
            }

            mAlertDialog = new ProgressDialog(mActivity);
            mAlertDialog.setMessage(msg);
            mAlertDialog.setCancelable(cancelable);
            mAlertDialog.setOnCancelListener(cancelListener);
            mAlertDialog.show();

            mAlertDialog.setCanceledOnTouchOutside(true);
        });
    }

    public void dismissProgressDialog() {
        mActivity.runOnUiThread(() -> {
            if (mAlertDialog != null && mAlertDialog.isShowing() && !mActivity.isFinishing()) {
                mAlertDialog.dismiss();
                mAlertDialog = null;
            }
        });
    }


}
