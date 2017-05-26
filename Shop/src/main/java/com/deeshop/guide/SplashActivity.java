package com.deeshop.guide;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.deeshop.MainActivity;
import com.deeshop.R;
import com.base.BaseActivity;
import com.deeshop.login.HomeActivity;
import com.deeshop.manager.LoginManager;
import com.deeshop.util.Constant;

public class SplashActivity extends BaseActivity {
	
	private static final int GO_HOME = 1000;
	private static final int GO_GUIDE = 1001;
	// 延迟3秒
	private static final long SPLASH_DELAY_MILLIS = 2000;

	/**
	 * Handler:跳转到不同界面
	 */
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GO_HOME:
				LoginManager.getInstance().goHome();
				break;
			case GO_GUIDE:
				LoginManager.getInstance().goGuide();
				break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	protected void initData() {
		setContentView(R.layout.guide_splash);
		TextView version = (TextView)findViewById(R.id.version);
		PackageManager manager = getPackageManager();
		try {
			PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
			if(info != null && info.versionName != null){
				version.setText("V" + " " +info.versionName);
			}
		} catch (NameNotFoundException e1) {
			e1.printStackTrace();
		}

		// 使用SharedPreferences来记录程序的使用次数
		boolean isFirst = LoginManager.isFirst(this);
		// 判断程序与第几次运行，如果是第一次运行则跳转到引导界面，否则跳转到主界面
		if (isFirst) {
			mHandler.sendEmptyMessageDelayed(GO_GUIDE, SPLASH_DELAY_MILLIS);
		} else {
			mHandler.sendEmptyMessageDelayed(GO_HOME, SPLASH_DELAY_MILLIS);
		}

	}

}
