package com.example.vk_oauth;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener {
	
	private final int REQUEST_LOGIN = 1;
    private String VK_API_URL = "https://api.vkontakte.ru/method/";
    private String VK_POST_TO_WALL_URL = VK_API_URL + "wall.post?";
	
	Button authorizeButton;
	Button logoutButton;
	Button postButton;
	EditText message;
	Account account = new Account();
	Api api;
	private void setupUI() {
		authorizeButton = (Button) findViewById(R.id.authorize);
		postButton = (Button) findViewById(R.id.post);
		message = (EditText) findViewById(R.id.message);
	
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setupUI();
		account.restore(this);
	}
	

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.authorize:
			startLoginActivity();
			break;
		case R.id.post:
			postToWall();
			break;
		case R.id.logout:
			break;
		}
		
	}
	private void startLoginActivity() {
		Intent intent = new Intent();
		intent.setClass(this, LoginActivity.class);
		startActivityForResult(intent, REQUEST_LOGIN);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_LOGIN) {
			if (resultCode == RESULT_OK) {
				account.access_token = data.getStringExtra("token");
				account.user_id = data.getLongExtra("user_id", 0);
				account.save(MainActivity.this);
				api = new Api(account.access_token, Constants.APP_ID);
			}
		}
	}
	private void postToWall() {
		String text = message.getText().toString();
		VK_POST_TO_WALL_URL += "owner_id"
				+account.user_id + "&message" + 
				Uri.encode(text) + "&access_token"
				+ account.access_token;
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(VK_POST_TO_WALL_URL);
		try {
			HttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			String responseText = EntityUtils.toString(entity);
		}
		catch (IOException t) {
			t.printStackTrace();
		}
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


}
