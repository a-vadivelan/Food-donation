package com.vadivelan.fooddonation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class otp extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_otp);
		Button otp = findViewById(R.id.verify_btn);
		otp.setOnClickListener((View v)->{
			Intent intent = new Intent(this,primary.class);
			startActivity(intent);
		});
	}
}