package com.cranelabs.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		Handler handler = new Handler();
		handler.postDelayed(new Runnable(){
			
			@Override
			public void run(){
				startActivity(new Intent(SplashActivity.this, MainActivity.class));
				finish();
			}
			
		}, 4000);
	}
	
}