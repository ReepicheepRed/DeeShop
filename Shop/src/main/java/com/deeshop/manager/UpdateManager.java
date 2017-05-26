package com.deeshop.manager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ProgressBar;

import com.deeshop.R;

/*
 *@author Eric 
 *@2015-11-7上午8:03:31
 */
public class UpdateManager {

	private static UpdateManager manager = null;
	
	public static int version ,serverVersion;
	public static String versionName,serverVersionName;
	public receiveVersionHandler handler  = new receiveVersionHandler();
	private ProgressDialog proBar;
	private Context context;

	private UpdateManager(){}
	public static UpdateManager getInstance(){
		manager = new UpdateManager();
		return manager;
	}


	public void init(Context c){
		this.context = c;
		version = getVersion(context);
		versionName = getVersionName(context);
		proBar = new ProgressDialog(context);
	}

	/**
	 * 获取当前应用程序的包名
	 * @param context 上下文对象
	 * @return 返回包名
	 */
	public static String getAppProcessName(Context context) {
		//当前应用pid
		int pid = android.os.Process.myPid();
		//任务管理类
		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		//遍历所有应用
		List<ActivityManager.RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
		for (ActivityManager.RunningAppProcessInfo info : infos) {
			if (info.pid == pid)//得到当前应用
				return info.processName;//返回包名
		}
		return "";
	}

	//获取版本号
	public int getVersion(Context context){
		int version = 0;
		try {  
			version = context.getPackageManager().getPackageInfo(  
                    getAppProcessName(context), 0).versionCode;
        } catch (Exception e) {  
        	 System.out.println("获取版本号异常！");
        }  
		return version;
	}
	
	//获取版本名
	public String getVersionName(Context context){
		String versionName = null;
		try {
			versionName = context.getPackageManager().getPackageInfo(
					getAppProcessName(context), 0).versionName;
		} catch (Exception e) {
			 System.out.println("获取版本名异常！");
		}
		return versionName;
	}
	
	//获取服务器版本号
	public String getServerVersion(){
		String serverJson = null;
		byte[] buffer = new byte[128];
		
		try {
			URL serverURL = new URL("http://192.168.226.106/ver.aspx");
			HttpURLConnection connect = (HttpURLConnection) serverURL.openConnection();
			BufferedInputStream bis = new BufferedInputStream(connect.getInputStream());
			int n = 0;
			while((n = bis.read(buffer))!= -1){
				serverJson = new String(buffer);
			}
		} catch (Exception e) {
			System.out.println("获取服务器版本号异常！"+e);
		}
		
		return serverJson;
	}	
	
	//比较服务器版本与本地版本弹出对话框
	public boolean compareVersion(Context context){
		
		final Context contextTemp = context;
		
		new Thread(){
			public void run() {
				Looper.prepare();
				String serverJson = manager.getServerVersion();
				
				//解析Json数据
				try {
					JSONArray array = new JSONArray(serverJson);
					JSONObject object = array.getJSONObject(0);
					String getServerVersion = object.getString("version");
					String getServerVersionName = object.getString("versionName");
					
					UpdateManager.serverVersion = Integer.parseInt(getServerVersion);
					UpdateManager.serverVersionName = getServerVersionName;
					
					if(UpdateManager.version < UpdateManager.serverVersion){
						//弹出一个对话框
			            Builder builder  = new Builder(contextTemp);
			            builder.setTitle("版本更新" ) ;  
			            builder.setMessage("当前版本："+UpdateManager.versionName
			            		+"\n"+"服务器版本："+UpdateManager.serverVersionName ) ;  
			            builder.setPositiveButton("立即更新",new DialogInterface.OnClickListener() {  
			                   @Override  
			                   public void onClick(DialogInterface dialog, int arg1) { 
			                       //开启线程下载apk
			                	   new Thread(){
			                		   public void run() {
			                			   Looper.prepare();
			                			   downloadApkFile(contextTemp);
			                			   Looper.loop();
			                		   };
			                	   }.start();
			                   }  
			               });  
			            builder.setNegativeButton("下次再说", null);  
			            builder.show();
					}else{
			            Builder builder  = new Builder(contextTemp);
			            builder.setTitle("版本信息" ) ;  
			            builder.setMessage("当前已经是最新版本" ) ;  
			            builder.setPositiveButton("确定",null);  
			            builder.show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
					System.out.println("获取服务器版本线程异常！"+e);
				}
				
				Looper.loop();
			}
			
		}.start();
		return false;
	}
	
	
	//下载apk文件
	public void downloadApkFile(Context context){
		String savePath = Environment.getExternalStorageDirectory()+"/AndroidUpdateDemo.apk";
		String serverFilePath = "http://192.168.226.106/AndroidUpdateDemo.png";
		try {
			if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){  
				URL serverURL = new URL(serverFilePath);
				HttpURLConnection connect = (HttpURLConnection) serverURL.openConnection();
				BufferedInputStream bis = new BufferedInputStream(connect.getInputStream());
				File apkfile = new File(savePath);
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(apkfile));
				
				int fileLength = connect.getContentLength();
				int downLength = 0;
				int progress = 0;
				int n;
				byte[] buffer = new byte[1024];
				while((n=bis.read(buffer, 0, buffer.length))!=-1){
					bos.write(buffer, 0, n);
					downLength +=n;
					progress = (int) (((float) downLength / fileLength) * 100);
					Message msg = new Message();
					msg.arg1 = progress;
					UpdateManager.this.handler.sendMessage(msg);
					//System.out.println("发送"+progress);
				}
				bis.close();
				bos.close();
				connect.disconnect();
	        } 
			
		} catch (Exception e) {
			System.out.println("下载出错！"+e);
		}
		

		/*AlertDialog.Builder builder  = new Builder(context);  
        builder.setTitle("下载apk" ) ;  
        builder.setMessage("正在下载" ) ;  
        builder.setPositiveButton("确定",null);  
        builder.show();*/
		
		
		
	}

	@SuppressLint("HandlerLeak")
	public class receiveVersionHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {

			proBar.setProgress(msg.arg1);
			proBar.setMessage("下载进度："+msg.arg1);
			proBar.show();

			if(msg.arg1 == 100){
				Intent intent = new Intent(Intent.ACTION_VIEW);
				String path = Environment.getExternalStorageDirectory()+"/AndroidUpdateDemo.apk";
				intent.setDataAndType(Uri.fromFile(new File(path)),"application/vnd.android.package-archive");
				context.startActivity(intent);
			}
			proBar.dismiss();
		}
	}

	private void googlePlay(Context context){
		String serverFilePath = context.getString(R.string.app_url);
		Intent intent= new Intent();
		intent.setAction("android.intent.action.VIEW");
		Uri content_url = Uri.parse(serverFilePath);
		intent.setData(content_url);
		context.startActivity(intent);
	}
}
