package com.vadivelan.fooddonation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
FirebaseAuth mAuth;
ConnectionStatusReceiver receiver = new ConnectionStatusReceiver();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAuth = FirebaseAuth.getInstance();
		registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
	}
	@Override
	protected void onStart(){
		super.onStart();
		FirebaseUser current_user = mAuth.getCurrentUser();
		if(current_user==null)
			startActivity(new Intent(this,signin.class));
		else
			startActivity(new Intent(this,primary.class));
		unregisterReceiver(receiver);
		finish();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}