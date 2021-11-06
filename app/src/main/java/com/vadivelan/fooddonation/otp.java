package com.vadivelan.fooddonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class otp extends AppCompatActivity {
EditText otp;
Button verify;
String OTP;
FirebaseAuth firebaseAuth;
AlertDialog.Builder processing_dialog;
AlertDialog alertDialog;
TextView otp_number;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_otp);
		otp = findViewById(R.id.otp);
		verify = findViewById(R.id.verify_btn);
		otp_number = findViewById(R.id.otp_number);
		OTP  = getIntent().getStringExtra("auth");
		otp_number.setText(String.format("OTP has been send to +91%s",getIntent().getStringExtra("mobile")));
		processing_dialog = new AlertDialog.Builder(this);
		processing_dialog.setMessage("Processing..").setCancelable(false).create();
		verify.setOnClickListener((View v)->{
			if(otp.getText().toString().isEmpty())
				Toast.makeText(this, "Please enter OTP", Toast.LENGTH_SHORT).show();
			else{
				alertDialog = processing_dialog.show();
				PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(OTP,otp.getText().toString());
				firebaseAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener((@NonNull Task<AuthResult> task)->{
					if(task.isSuccessful()){
						startActivity(new Intent(this,primary.class));
						finish();
					} else{
						Toast.makeText(this, "Verification Failed", Toast.LENGTH_SHORT).show();
					}
					alertDialog.dismiss();
				});
			}
		});
	}

	@Override
	protected void onStart() {
		super.onStart();
		firebaseAuth = FirebaseAuth.getInstance();
	}
}