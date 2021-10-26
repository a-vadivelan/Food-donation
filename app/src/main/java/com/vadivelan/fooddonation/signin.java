package com.vadivelan.fooddonation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class signin extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signin);
		Button signin = findViewById(R.id.send_btn);
		signin.setOnClickListener((View v)->{
			Intent intent = new Intent(this,otp.class);
			startActivity(intent);
		});
	}
}