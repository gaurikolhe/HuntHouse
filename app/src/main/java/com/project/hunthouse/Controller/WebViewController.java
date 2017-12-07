package com.project.hunthouse.Controller;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by gaurikolhe on 12/2/2017.
 */

public class WebViewController extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }


    public void onPageFinished(WebView view, String url){
        view.loadUrl("javascript:init('" + "gkolhe@syr.edu" + "')");
    }
}