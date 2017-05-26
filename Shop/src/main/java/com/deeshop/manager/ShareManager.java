package com.deeshop.manager;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.Toast;

import com.deeshop.R;
import com.deeshop.bean.Pop;
import com.deeshop.helper.DialogHelper;

import org.xutils.x;

import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by zhiPeng.S on 2017/1/4.
 */

public class ShareManager {

    public static void showShare(String platform,Pop pop) {
        OnekeyShare oks = new OnekeyShare();
        if (platform != null) {
            oks.setPlatform(platform);
        }
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");


        // text是分享文本，所有平台都需要这个字段
        oks.setText(pop.string1.get());
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        if(pop.string2.get().contains("http"))
            oks.setImageUrl(pop.string2.get());
        else
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
            oks.setImagePath(pop.string2.get());//确保SDcard下面存在此张图片

        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(x.app());
    }

    public static void copy(ClipData clipData){
        ClipboardManager clipboardManager = (ClipboardManager) x.app().getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.addPrimaryClipChangedListener(() -> DialogHelper.toast(x.app().getString(R.string.text_copied)));
        clipboardManager.setPrimaryClip(clipData);
    }

    public static void copy(CharSequence sequence){
        copy(ClipData.newPlainText(null,sequence));
    }

}
