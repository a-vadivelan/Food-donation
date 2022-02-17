package com.vadivelan.fooddonation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
FirebaseAuth mAuth;
ConnectionStatusReceiver receiver = new ConnectionStatusReceiver();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mAuth = FirebaseAuth.getInstance();
		registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
	}
	@Override
	protected void onStart(){
		super.onStart();
		FirebaseUser current_user = mAuth.getCurrentUser();
		new Handler().postDelayed(()->{
			if(current_user==null){
				startActivity(new Intent(this,signin.class));
			} else{
				startActivity(new Intent(this,primary.class));
			}
			finish();
		},2000);
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
	}
}