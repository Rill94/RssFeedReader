package com.example.valerie.rssfeedreader;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;

public class RSSContentActivity extends ActionBarActivity {
    WebView webView;
    URL imgurl = null;
    String s_imgurl = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsscontent);
        webView = (WebView) findViewById(R.id.myWebView);
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.setWebViewClient(new MyWebViewClient());
        Post post = (Post) getIntent().getExtras().getSerializable("rss");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(post.getCategory().get(0).getName());
        try {
            webView.loadDataWithBaseURL(null, parseToHtml(post),"text/html", "UTF-8", null);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    private String parseToHtml(Post post) throws MalformedURLException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        imgurl = new URL(post.getEnclosures().get(0).getUrl());
        s_imgurl = imgurl.toString();
        String page = "<html>" +
                "<head>" +
                "<title>" + post.getTitle() + "</title>" +
                "</head>" +
                "<body>" +
                "<center><h3>" + post.getTitle() + "</h3></center>" +
                "<center><p>" + dateFormat.format(post.getDate()) +"</p></center>" +
                "<img src='" + s_imgurl + "' width='100%'/>" +
                "<p>" + post.getDescr() +"</p>" +
                "</body>" +
                "</html>";
        return page;

    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}