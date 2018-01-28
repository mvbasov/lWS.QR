package net.basov.lws.qr;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;

import java.util.Locale;


public class MainActivity extends Activity {
    private WebView mainUI_WV;
    private UI ui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ui = new UI();

        setContentView(R.layout.webview_ui);
        mainUI_WV = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = mainUI_WV.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        /* Enable JavaScript */
        webSettings.setJavaScriptEnabled(true);
        mainUI_WV.addJavascriptInterface(new WebViewJSCallback(this), "Android");
        /* Show external page in browser */
        mainUI_WV.setWebViewClient(new MyWebViewClient());
        /* Handle JavaScript prompt doalog */
        mainUI_WV.setWebChromeClient(new myWebChromeClient());

        /* Enable chome remote debuging for WebView (Ctrl-Shift-I) */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (0 != (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE))
            { WebView.setWebContentsDebuggingEnabled(true); }
        }
        
        onNewIntent(getIntent());

        ui.setWelcome("w_debug", "[onCreate()]");
        ui.displayWelcomeScreen(mainUI_WV);

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getAction().equals(MainActivity.this.getPackageName()+".LAUNCH_HELP")) {

            /* Handle BACK button */
            mainUI_WV.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        WebView webView = (WebView) v;
                        switch (keyCode) {
                            case KeyEvent.KEYCODE_BACK:
                                if (webView.canGoBack()) {
                                    //webView.goBack();
                                    ui.setWelcome("w_debug", "[onNewIntent() after launch help]");
                                    ui.displayWelcomeScreen(mainUI_WV);
                                    return true;
                                }
                                break;
                        }
                    }
                    return false;
                }
            });

            ui.setHelp("h_debug", "[From MainActivity]");

        } else {
            ui.setWelcome("w_debug", "[onNewIntent()]");
            ui.displayWelcomeScreen(mainUI_WV);
        }
    }

    /**
     * Handle JavaScript prompt() dialogue
     */
    private class myWebChromeClient extends WebChromeClient {
        @Override
        public boolean onJsPrompt(
            WebView view,
            String url,
            String message,
            String defaultValue,
            final JsPromptResult result) {

            final EditText input = new EditText(MainActivity.this);

            new AlertDialog.Builder(MainActivity.this)
                .setView(input)
                .setTitle("need to be defined")
                .setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String value = input.getText().toString();
                        result.confirm(value);
                    }
                })
                .setNegativeButton(android.R.string.cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        result.cancel();
                    }
                })
                .setOnCancelListener(
                new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        result.cancel();
                    }
                })
                .show();

            return true;
        }  
    }
}
