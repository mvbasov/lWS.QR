package net.basov.lws.qr;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.webkit.WebView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import net.basov.util.TextTools;

/**
 * Created by mvb on 8/1/17.
 */

public class UI {
    private final static String VISIBILITY = "visibility";

    private JSONObject help_json;
    private JSONObject welcome_json;

    private static final HashMap<String, String> visibilityMap;
    static {
        visibilityMap = new HashMap<String, String>();
        visibilityMap.put("h_debug", "vh_debug");
        visibilityMap.put("w_debug", "vw_debug");
        visibilityMap.put("w_msg", "vw_msg");
    }

    /**
     * Only create object.
     * Fields doesn't automatically filed.
     */
    public UI() {
        this.dataClean();
    }

    public void dataClean () {
        help_json = new JSONObject();
        welcome_json = new JSONObject();
    }

    public void setWelcome(String field, String message) {
        try {
            welcome_json.put(field, TextTools.escapeCharsForJSON(message));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (visibilityMap.containsKey(field))
            setWelcomeVisibility(visibilityMap.get(field));
    }

    public void setHelp(String field, String message) {
        try {
            help_json.put(field, TextTools.escapeCharsForJSON(message));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (visibilityMap.containsKey(field))
            setHelpVisibility(visibilityMap.get(field));
    }

    private void setHelpVisibility(String name) {
        try {
            if (help_json.has(VISIBILITY)) {
                if (! help_json.getJSONObject(VISIBILITY).has(name)) {
                    help_json.getJSONObject(VISIBILITY).put(name,"");
                }
            } else {
                JSONObject vv = new JSONObject();
                help_json.put(VISIBILITY, vv);
                help_json.getJSONObject(VISIBILITY).put(name,"");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setWelcomeVisibility(String name) {
        try {
            if (welcome_json.has(VISIBILITY)) {
                if (! welcome_json.getJSONObject(VISIBILITY).has(name)) {
                    welcome_json.getJSONObject(VISIBILITY).put(name,"");
                }
            } else {
                JSONObject vv = new JSONObject();
                welcome_json.put(VISIBILITY, vv);
                welcome_json.getJSONObject(VISIBILITY).put(name,"");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void displayWelcomeScreen(final WebView wv) {
        SharedPreferences defSharedPref = PreferenceManager.getDefaultSharedPreferences(wv.getContext());

        wv.setWebViewClient(new MyWebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(wv, url);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    view.evaluateJavascript("javascript:jreplace('" + welcome_json.toString() +"')",null);
                } else {
                    view.loadUrl("javascript:jreplace('" + welcome_json.toString() +"')",null);
                }
                wv.clearCache(true);
                wv.clearHistory();
            }
        });
        Context c = wv.getContext();
        wv.loadUrl("file:///android_asset/" + c.getString(R.string.welcome_ui_file));
    }

}
