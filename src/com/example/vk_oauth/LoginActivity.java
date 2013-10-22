package com.example.vk_oauth;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class LoginActivity extends Activity {

	WebView webview;
	public static String tag = "VKLogin";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		webview = (WebView) findViewById(R.id.VKview);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.clearCache(true);
		
		//webview.setWebViewClient(new VKWebViewClient());
		
		CookieSyncManager.createInstance(this);
		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.removeAllCookie();
		
		 String url = Auth.getUrl(Constants.APP_ID);
		 webview.loadUrl(url);
	}
	
	class VKWebViewClient extends WebViewClient {
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			parseUrl(url);
		}
		public void parseUrl(String url) {
			try {
				if (url == null)
					return;
				Log.i(tag, "url = " + url);
				if (url.startsWith(Auth.redirect_url)) {
					if (!url.contains("error=")) {
						String[] auth = Auth.parseRedirectUrl(url);
						Intent intent = new Intent();
						intent.putExtra("token", auth[0]);
						intent.putExtra("user_Id", Long.parseLong(auth[1]));
						setResult(Activity.RESULT_OK, intent);
					}
					finish();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}
