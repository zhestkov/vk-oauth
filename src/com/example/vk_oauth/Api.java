package com.example.vk_oauth;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;

import org.json.JSONException;
import org.json.JSONObject;

public class Api {
	
	public static String access_token;
	public static String app_id;
	
	
	public Api(String access_token, String app_id) {
		this.access_token = access_token;
		this.app_id = app_id;
	}
	
    public long createWallPost(long owner_id, String text, Collection<String> attachments, String export, boolean only_friends, boolean from_group, boolean signed, String lat, String lon, Long publish_date, String captcha_key, String captcha_sid) throws MalformedURLException, IOException, JSONException{
        Params params = new Params("wall.post");
        params.put("owner_id", owner_id);
        //params.put("attachments", arrayToString(attachments));
        params.put("lat", lat);
        params.put("message", text);
        params.put("long", lon);
        if(export!=null && export.length()!=0)
            params.put("services",export);
        if (from_group)
            params.put("from_group","1");
        if (only_friends)
            params.put("friends_only","1");
        if (signed)
            params.put("signed","1");
        params.put("publish_date", publish_date);
        //addCaptchaParams(captcha_key, captcha_sid, params);
        //JSONObject root = sendRequest(params, true);
        //JSONObject response = root.getJSONObject("response");
        //long post_id = response.optLong("post_id");
        //return post_id;
        return 1;
    }
    
    
	

}
