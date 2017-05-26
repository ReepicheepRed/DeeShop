package com.deeshop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.base.BaseActivity;
import com.deeshop.databinding.ActivityWebBinding;
import com.deeshop.manager.WebManager;
import com.deeshop.util.Constant;

/**
 * Created by zhiPeng.S on 2017/3/17.
 */

public class WebActivity extends BaseActivity {
    private ActivityWebBinding binding;

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    @Override
    protected void initData() {
        super.initData();
        binding = DataBindingUtil.setContentView(this,R.layout.activity_web);
        WebSettings webSettings = binding.contentWv.getSettings();
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(false);

        WebChromeClient wcc = new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                binding.include.titleTv.setText(title);
            }

        };
        WebViewClient wvc = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                binding.contentWv.loadUrl(url);
                return true;
            }
        };
        binding.contentWv.setWebChromeClient(wcc);
        binding.contentWv.setWebViewClient(wvc);

        String content_url  = getIntent().getStringExtra(WebManager.WEB_H5);
        binding.contentWv.loadUrl(content_url);
    }

    @Override
    protected void initActionbar() {
        super.initActionbar();
        binding.include.backIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.back_iv:
                finish();
                break;
        }
    }
}
