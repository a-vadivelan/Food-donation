package com.vadivelan.fooddonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.Manifest;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class signin extends AppCompatActivity {
Button send_btn;
EditText mobile_number;
FirebaseAuth auth;
AlertDialog.Builder processing_dialog;
AlertDialog alertDialog;
String otp_number;
PhoneAuthProvider.OnVerificationStateChangedCallbacks callback;
ConnectionStatusReceiver receiver = new ConnectionStatusReceiver();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signin);
		send_btn = findViewById(R.id.send_btn);
		mobile_number = findViewById(R.id.mobile_number);
		registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
		auth =FirebaseAuth.getInstance();
		processing_dialog = new AlertDialog.Builder(this);
		if(ActivityCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
			ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},1);
		send_btn.setOnClickListener((View v)->{
			otp_number=mobile_number.getText().toString();
			if(otp_number.isEmpty())
				Toast.makeText(this, "Please enter your mobile number", Toast.LENGTH_SHORT).show();
			else {
				processing_dialog.setMessage("Processing..")
						.setCancelable(false)
						.create();
				alertDialog = processing_dialog.show();
				PhoneAuthOptions authOptions = PhoneAuthOptions.newBuilder(auth)
						.setPhoneNumber("+91"+otp_number)
						.setTimeout(60L, TimeUnit.SECONDS)
						.setActivity(this)
						.setCallbacks(callback)
						.build();
				PhoneAuthProvider.verifyPhoneNumber(authOptions);
			}
		});
		callback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
			@Override
			public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
				auth.signInWithCredential(phoneAuthCredential).addOnCompleteListener((@NonNull Task<AuthResult> task)->{
					if(task.isSuccessful()){
						Intent intent2 = new Intent(signin.this,primary.class);
						startActivity(intent2);
						finish();
					} else{
						Toast.makeText(signin.this, Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_LONG).show();
					}
				});
			}

			@Override
			public void onVerificationFailed(@NonNull FirebaseException e) {
				Toast.makeText(signin.this, e.getMessage(),Toast.LENGTH_LONG).show();
			}

			@Override
			public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
				super.onCodeSent(s, forceResendingToken);
				Intent intent = new Intent(signin.this,otp.class);
				intent.putExtra("auth",s);
				intent.putExtra("mobile",otp_number);
				intent.putExtra("token",forceResendingToken);
				intent.putExtra("do","signin");
				alertDialog.dismiss();
				startActivity(intent);
				finish();
			}

		};
	}
	@Override
	protected void onDestroy(){
		super.onDestroy();
		unregisterReceiver(receiver);
	}

}