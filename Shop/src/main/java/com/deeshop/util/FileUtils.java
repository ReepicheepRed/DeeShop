package com.deeshop.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.deeshop.manager.ThreadManager;

import org.xutils.x;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadPoolExecutor;

public class FileUtils {

    
    /**
     * 将Bitmap 图片保存到本地路径，并返回路径
     * @param c
     * @param fileName 文件名称
     * @param bitmap 图片
     * @return
     */
	public static String saveFile(Context c, String fileName, Bitmap bitmap) {
		return saveFile(c, "", fileName, bitmap);
	}
	
	public static String saveFile(Context c, String filePath, String fileName, Bitmap bitmap) {
		byte[] bytes = bitmapToBytes(bitmap);
		return saveFile(c, filePath, fileName, bytes);
	}
	public static int ratio = -1;

	public static byte[] bitmapToBytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		if(ratio != -1)
			bm.compress(CompressFormat.JPEG, ratio, baos);
		else
			bm.compress(CompressFormat.JPEG, 100, baos);
        ratio = -1;
		return baos.toByteArray();
	}
	
	public static String saveFile(Context c, String filePath, String fileName, byte[] bytes) {
		String fileFullName = "";
		FileOutputStream fos = null;
		String dateFolder = new SimpleDateFormat("yyyyMMdd", Locale.CHINA)
				.format(new Date());
		try {
			String suffix = "";
			if (filePath == null || filePath.trim().length() == 0) {
				filePath = Environment.getExternalStorageDirectory() + "/DeeShop/" + dateFolder + "/";
			}
			File file = new File(filePath);
			if (!file.exists()) {
				file.mkdirs();
			}
			File fullFile = new File(filePath, fileName + suffix);
			fileFullName = fullFile.getPath();
			fos = new FileOutputStream(new File(filePath, fileName + suffix));
			fos.write(bytes);
		} catch (Exception e) {
			fileFullName = "";
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					fileFullName = "";
				}
			}
		}
		return fileFullName;
	}

	public static void savePicture(List<String> urls){
        ThreadPoolExecutor executor = ThreadManager.getInstance().Executor();
        Handler handler = new Handler(){
            int count;
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                x.app().sendBroadcast(
                        new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                                Uri.fromFile(new File((String)msg.obj))));
                count++;
                if(count >= urls.size()){
                    count = 0;
                    Toast.makeText(x.app(),"Save picture complete",Toast.LENGTH_SHORT).show();
                }
            }
        };
        for (int i = 0; i < urls.size(); i++) {
            executor.execute(new SaveRunnable(urls.get(i),handler));
        }
    }

    private static class SaveRunnable implements Runnable {
        private String url;
        private Handler handler;

        SaveRunnable(String url, Handler handler) {
            this.url = url;
            this.handler = handler;
        }

        @Override
        public void run() {
            try {
                String filePath = convertToSdPath(x.app(), url);
                if (filePath == null) {
                    return;
                }
                Bitmap bm = getBitmapFromSdCard(x.app(), url);
                if (bm == null) {
                    Bitmap bitmap = Glide.with(x.app())
                            .load(url)
                            .asBitmap()
                            .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                            .get();
                    saveImageToSd(x.app(), url, bitmap);
                    Message msg = handler.obtainMessage();
                    msg.what = 0;
                    msg.obj = convertToSdPath(x.app(), url);
                    handler.sendMessage(msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }



    /**
     *
     * 将bm存储到SD卡中
     * @param url 图片在服务器上的相对路径
     * @param bm 从服务器获得的图片
     * @return 存储成功 true ，失败 false
     */
    public static boolean saveImageToSd(Context context,String url, Bitmap bm){
        String sdStatus = Environment.getExternalStorageState();
        if(!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            return false;
        }
        String sdPath = convertToSdPath(context, url);
        if(sdPath == null){
            return false;
        }

        File file = new File(sdPath);
        File dir = file.getParentFile();
        if(!dir.exists()){
            dir.mkdirs();
        }
        BufferedOutputStream bos;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file));
            bm.compress(Bitmap.CompressFormat.PNG, 100, bos);
            bos.flush();
            bos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }  catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static Bitmap getBitmapFromSdCard(Context context,String url){
        if(!isSavedToSd(context, url)){
            return null;
        }

        String filePath = convertToSdPath(context, url);
        if(filePath == null){
            return null;
        }

        Bitmap bm = BitmapFactory.decodeFile(filePath);
        return bm;
    }

    public static String getLiImageDir(Context context){
        File sd= context.getExternalCacheDir();
        if(sd == null){
            return null;
        }
        String path=sd.getPath();
        File file=new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    public static boolean isSavedToSd(Context context,String url){
        String filePath = convertToSdPath(context, url);
        if(filePath == null){
            return false;
        }
        File file = new File(filePath);
        return file.exists();
    }

    public static String convertToSdPath(Context context,String url){
        if(url == null || url.trim().equals("")){
            return null;
        }
        url = url.trim();
        String baseUrl = Constant.getBaseUrl();
        if(url.startsWith(baseUrl)){
            url = url.replace(baseUrl, "");
        }

        String filePath = getLiImageDir(context);
        if(!url.startsWith("/")){
            filePath= filePath + "/";
        }

        filePath = filePath + url;
        return filePath;
    }
}
