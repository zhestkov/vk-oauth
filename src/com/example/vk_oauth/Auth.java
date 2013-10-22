package com.example.vk_oauth;

import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

public class Auth {
	public static String tag = "VKLogin";
	public static String redirect_url = "https://oauth.vk.com/blank.html";
	
	public static String getUrl(String api_id) {
		String url = "https://oauth.vk.com/authorize?client_id="
	+api_id+"&display=mobile&scope="
				+getSettings()+"&redirect_uri="
	+URLEncoder.encode(redirect_url)+"&response_type=token";
		return url;
	}
	
	public static String getSettings() {
		String settings[] = {"wall", "photos", "friends", "video", "messages"};
		return settings[0];
		}
	
	public static String[] parseRedirectUrl(String url) throws Exception {
		String access_token = extractPattern(url, "access_token=(.*?)&");
		Log.i(tag, "access_token = " + access_token);
		String user_id = extractPattern(url, "user_id=(\\d*)");
		Log.i(tag, "user_id = " + user_id);
		if (user_id == null || user_id.length() == 0 ||
				access_token == null || access_token.length() == 0)
			throw new Exception("Failed to parse redirect URL");
		return new String[] {access_token, user_id};
	}
	
	public static String extractPattern(String string, String pattern) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(string);
		if (!m.find())
			return null;
		return m.toMatchResult().group(1);
	}
}
	
