package com.vadivelan.fooddonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class otp extends AppCompatActivity {
EditText otp;
Button verify,resend;
String OTP;
FirebaseAuth firebaseAuth;
DatabaseReference ref;
FirebaseDatabase database;
SharedPreferences preferences;
SharedPreferences.Editor editor;
AlertDialog.Builder processing_dialog;
AlertDialog alertDialog;
TextView otp_number;
boolean request_otp = false;
int second=60;
PhoneAuthProvider.OnVerificationStateChangedCallbacks callback;
PhoneAuthProvider.ForceResendingToken token;
ConnectionStatusReceiver receiver = new ConnectionStatusReceiver();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_otp);
		otp = findViewById(R.id.otp);
		verify = findViewById(R.id.verify_btn);
		resend = findViewById(R.id.resend_otp);
		otp_number = findViewById(R.id.otp_number);
		OTP  = getIntent().getStringExtra("auth");
		token = getIntent().getParcelableExtra("token");
		otp_number.setText(String.format("OTP has been send to +91%s",getIntent().getStringExtra("mobile")));
		registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
		processing_dialog = new AlertDialog.Builder(this);
		processing_dialog.setMessage("Processing..").setCancelable(false).create();
		preferences = getPreferences(MODE_PRIVATE);
		editor  = preferences.edit();
		verify.setOnClickListener((View v)->{
			if(otp.getText().toString().isEmpty())
				Toast.makeText(this, "Please enter OTP", Toast.LENGTH_SHORT).show();
			else{
				alertDialog = processing_dialog.show();
				PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(OTP,otp.getText().toString());
				firebaseAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener((@NonNull Task<AuthResult> task)->{
					if(task.isSuccessful()){
						if(getIntent().getStringExtra("do").equals("delete")){
							Log.w("2","Signin success");
							firebaseAuth = FirebaseAuth.getInstance();
							database = FirebaseDatabase.getInstance();
							ref = database.getReference().getRoot().child("post").child(firebaseAuth.getCurrentUser().getUid());
							preferences.edit().clear().commit();
							ref.addListenerForSingleValueEvent(new ValueEventListener() {
								@Override
								public void onDataChange(@NonNull DataSnapshot snapshot) {
									for (DataSnapshot snapshot1 : snapshot.getChildren())
										ref.child(String.valueOf(snapshot1.getKey())).removeValue();
									Log.w("3","Posts deleted");
									firebaseAuth.getCurrentUser().delete()
										.addOnSuccessListener((Void unused) -> {
											Toast.makeText(otp.this, "Account successfully deleted", Toast.LENGTH_SHORT).show();
											startActivity(new Intent(otp.this,MainActivity.class));
											finish();
										})
										.addOnFailureListener((Exception e) -> Toast.makeText(otp.this, e.getMessage(), Toast.LENGTH_LONG).show());
								}
								@Override
								public void onCancelled(@NonNull DatabaseError error) {
								}
							});
						} else {
							startActivity(new Intent(this, primary.class));
							finish();
						}
					} else{
						Toast.makeText(this, "Verification Failed", Toast.LENGTH_SHORT).show();
					}
					alertDialog.dismiss();
				});
			}
		});
		resend.setOnClickListener((View v)-> {
			PhoneAuthOptions authOptions = PhoneAuthOptions.newBuilder(firebaseAuth)
						.setPhoneNumber("+91"+getIntent().getStringExtra("mobile"))
						.setTimeout(60L, TimeUnit.SECONDS)
						.setActivity(this)
						.setCallbacks(callback)
						.setForceResendingToken(token)
						.build();
				PhoneAuthProvider.verifyPhoneNumber(authOptions);
				request_otp = false;
		});
		new Timer().scheduleAtFixedRate(new TimerTask(){
			public void run(){
				otp.this.resend_timer();
			}
		},0,1000);
		callback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
			@Override
			public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
				firebaseAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener((@NonNull Task<AuthResult> task)->{
					if(!task.isSuccessful())
						Toast.makeText(otp.this, Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_LONG).show();

				});
			}

			@Override
			public void onVerificationFailed(@NonNull FirebaseException e) {
				Toast.makeText(otp.this, e.getMessage(),Toast.LENGTH_LONG).show();
			}

			@Override
			public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
				super.onCodeSent(s, forceResendingToken);
				Toast.makeText(otp.this,"OTP has been send",Toast.LENGTH_SHORT).show();
				token = forceResendingToken;
			}
		};
	}

	public void resend_timer(){
		runOnUiThread(()->{
			if(!request_otp)
				if(second==0){
					resend.setClickable(true);
					resend.setText(getResources().getString(R.string.resend_otp));
					resend.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.custom_purple)));
					second=60;
					request_otp=true;
				} else{
					second--;
					resend.setClickable(false);
					resend.setText(String.format(Locale.ENGLISH,"%s(%ds)",getResources().getString(R.string.resend_otp),second));
					resend.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.custom_purple_trans)));
				}
		});
	}
	@Override
	protected void onStart() {
		super.onStart();
		firebaseAuth = FirebaseAuth.getInstance();
	}
	@Override
	protected void onDestroy(){
		super.onDestroy();
		unregisterReceiver(receiver);
	}
}