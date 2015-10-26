package info.dabu.jscallactivity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private ProgressBar Pbar;
    private TextView txtview;


    private String curPhoneNumber = "Hello workd!";

    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        txtview = (TextView) findViewById(R.id.tV1);
        Pbar = (ProgressBar) findViewById(R.id.pB1);

        mWebView = (WebView) findViewById(R.id.webview);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);


        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                // Activities and WebViews measure progress with different scales.
                // The progress meter will automatically disappear when we reach 100%


                if (progress < 100 && Pbar.getVisibility() == ProgressBar.GONE) {
                    Pbar.setVisibility(ProgressBar.VISIBLE);
                    txtview.setVisibility(View.VISIBLE);
                }
                Pbar.setProgress(progress);
                if (progress == 100) {
                    Pbar.setVisibility(ProgressBar.GONE);
                    txtview.setVisibility(View.GONE);
                }

                MainActivity.this.setProgress(progress * 1000);
            }
        });
        mWebView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(MainActivity.this, "出错了! " + description, Toast.LENGTH_SHORT).show();
            }
        });

        mWebView.addJavascriptInterface(new JavaScriptInterface(this), "Android");


//        将这里替换成自己存放js的网页地址
        String url = "http://45.78.3.206/down/test.html";
        mWebView.loadUrl(url);
//        mWebView.loadUrl("javascript:changeActivity();");


    }





    public class JavaScriptInterface {

        Context mContext;

        /**
         * Instantiate the interface and set the context
         */
        JavaScriptInterface(Context c) {
            mContext = c;
        }


        @JavascriptInterface
        public void openActivity() {

           mContext.startActivity(new Intent(mContext, OpenActivity.class));

        }
    }


}


