package br.com.anteros.social.instagram;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

/**
 * Created by edson on 05/04/16.
 */
public class AnterosOAuthActivity extends Activity {

    public static final String PARAM_URL_TO_LOAD = "OAuthActivity.PARAM_URL_TO_LOAD";
    public static final String PARAM_CALLBACK = "OAuthActivity.PARAM_CALLBACK";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout relativeLayout = new RelativeLayout(this);

        RelativeLayout.LayoutParams relativeLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        relativeLayout.setBackgroundColor(Color.parseColor("#f5f5f5"));
        setContentView(relativeLayout, relativeLayoutParams);

        final String paramUrlToLoad = getIntent().getStringExtra(PARAM_URL_TO_LOAD);
        final String paramCallback = getIntent().getStringExtra(PARAM_CALLBACK);

        if (TextUtils.isEmpty(paramUrlToLoad)) {
            throw new IllegalArgumentException("required PARAM_URL_TO_LOAD");
        }

        if (TextUtils.isEmpty(paramCallback)) {
            throw new IllegalArgumentException("required PARAM_CALLBACK");
        }
        WebView webView = new WebView(this);
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        webView.setLayoutParams(linearLayoutParams);
        relativeLayout.addView(webView);

        final LinearLayout progressContainer = new LinearLayout(this);
        LinearLayout.LayoutParams progressLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        progressContainer.setGravity(Gravity.CENTER);
        progressContainer.setBackgroundColor(Color.parseColor("#f5f5f5"));
        progressContainer.setLayoutParams(progressLayoutParams);
        relativeLayout.addView(progressContainer);

        ProgressBar progressBar = new ProgressBar(this);
        LinearLayout.LayoutParams progressBarLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        progressBar.setLayoutParams(progressBarLayoutParams);
        progressContainer.addView(progressBar);

        webView.setHorizontalScrollBarEnabled(false);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                progressContainer.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                progressContainer.setVisibility(View.GONE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.toLowerCase().startsWith(paramCallback.toLowerCase())) {
                    Intent intent = new Intent();
                    intent.setData(Uri.parse(url));
                    setResult(RESULT_OK, intent);
                    finish();
                    return true;
                }

                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);

                Intent intent = new Intent();
                intent.setAction(description);
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });
        webView.loadUrl(paramUrlToLoad);
    }
}