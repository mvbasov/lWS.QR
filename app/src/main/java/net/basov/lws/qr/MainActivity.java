package net.basov.lws.qr;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient;
import android.webkit.ConsoleMessage;
import android.util.Log;

public class MainActivity extends Activity {
    private WebView mainUI_WV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.webview_ui);
        mainUI_WV = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = mainUI_WV.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        /* Enable JavaScript */
        webSettings.setJavaScriptEnabled(true);
        //mainUI_WV.addJavascriptInterface(new WebViewJSCallback(this), "Android");
        /* Show external page in browser */
        mainUI_WV.setWebViewClient(new MyWebViewClient());
        /* Handle JavaScript console log */
        mainUI_WV.setWebChromeClient(new myWebChromeClient());

        /* Enable chome remote debuging for WebView (Ctrl-Shift-I) */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (0 != (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE)) {
                WebView.setWebContentsDebuggingEnabled(true);
            }
        }

        onNewIntent(getIntent());

        //ui.setWelcome("w_debug", "[onCreate()]");
        //ui.displayWelcomeScreen(mainUI_WV);
        mainUI_WV.loadUrl("file:///android_asset/" + getString(R.string.welcome_ui_file));


    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public static String escapeCharsForJSON(String src) {
        return src
                .replace("\n", "\\n")
                .replace("\t", "\\t")
                .replace("'", "&#39;")
                .replace("\"", "\\\"");
    }

    /**
     * Redirect external URLs to browser
     */
    public static class MyWebViewClient extends WebViewClient {

        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            final Uri uri = Uri.parse(url);
            return processUri(uri, view);
        }

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            final Uri uri = request.getUrl();
            return processUri(uri, view);
        }

        private boolean processUri(final Uri uri, WebView view) {
            if (uri.getHost().length() == 0) {
                return false;
            } else {
                final Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                view.getContext().startActivity(intent);
                return true;
            }
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                view.evaluateJavascript("javascript:jreplace('" + welcome_json.toString() +"')",null);
//            } else {
//                view.loadUrl("javascript:jreplace('" + welcome_json.toString() +"')",null);
//            }
            view.clearCache(true);
            view.clearHistory();
        }
    }
    
    /**
     * Handle JavaScript console log
     */
    private class myWebChromeClient extends WebChromeClient {

        public boolean onConsoleMessage(ConsoleMessage cm) {
            final String TAG = "lWS.QR";
            String formattedMessage =
                cm.message()
                + " -- From line: "
                + cm.lineNumber()
                + " of "
                + cm.sourceId();
            switch (cm.messageLevel()) {
                case DEBUG:
                    Log.d(TAG, formattedMessage);
                    break;
                case ERROR:
                    Log.e(TAG, formattedMessage);
                    break;
                case LOG:
                    Log.i(TAG, formattedMessage);
                    break;
                case TIP:
                    Log.i(TAG, formattedMessage);
                    break;
                case WARNING:
                    Log.v(TAG, formattedMessage);
                    break;
                default:
                    Log.w(TAG, formattedMessage);
                    break;
            }
            return true;
        }
    }
}
