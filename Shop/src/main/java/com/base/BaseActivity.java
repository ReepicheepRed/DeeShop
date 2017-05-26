package com.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.content.DialogInterface.OnCancelListener;
import android.widget.Toast;

import com.deeshop.R;
import com.deeshop.helper.ActivityResponsable;
import com.deeshop.helper.DialogHelper;

import org.xutils.x;

/**
 * Created by zhiPeng.S on 2017/3/15.
 */

public class BaseActivity extends AppCompatActivity implements ActivityResponsable,View.OnClickListener,DialogInterface.OnClickListener{
    private DialogHelper mDialogHelper;
    private long firstTime = 0L;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setDisplayStyle();
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        mDialogHelper = new DialogHelper(this);
        initData();
        initActionbar();
    }

    protected void initData(){

    }

    protected void initActionbar(){

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }

    private void setDisplayStyle(){
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setStatusBar();
    }

    private void setStatusBar(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION| View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    @Override
    public void finish() {
        if (isTaskRoot()) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 800L) {
                toast(getString(R.string.exit_tip), Toast.LENGTH_SHORT);
                firstTime = secondTime;
                return ;
            }
        }
        super.finish();
    }


    @Override
    public void alert(String title, String msg, String positive,
                      DialogInterface.OnClickListener positiveListener, String negative,
                      DialogInterface.OnClickListener negativeListener) {
        mDialogHelper.alert(title, msg, positive, positiveListener, negative, negativeListener);
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
     *            外部点是否可以取消对话框
     */
    @Override
    public void alert(String title, String msg, String positive,
                      DialogInterface.OnClickListener positiveListener, String negative,
                      DialogInterface.OnClickListener negativeListener,
                      Boolean isCanceledOnTouchOutside) {
        mDialogHelper.alert(title, msg, positive, positiveListener, negative, negativeListener,
                isCanceledOnTouchOutside);
    }

    /**
     * TOAST
     *
     * @param msg
     *            消息
     * @param period
     *            时长
     */
    @Override
    public void toast(String msg, int period) {
        mDialogHelper.toast(msg, period);
    }

    /**
     * 显示进度对话框
     *
     * @param msg
     *            消息
     */
    @Override
    public void showProgressDialog(String msg) {
        mDialogHelper.showProgressDialog(msg);
    }

    /**
     * 显示可取消的进度对话框
     *
     * @param msg
     *            消息
     */
    public void showProgressDialog(final String msg, final boolean cancelable,
                                   final OnCancelListener cancelListener) {
        mDialogHelper.showProgressDialog(msg, cancelable, cancelListener,true);
    }

    @Override
    public void dismissProgressDialog() {
        mDialogHelper.dismissProgressDialog();
    }
}
