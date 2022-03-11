package com.vadivelan.fooddonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnSuccessListener;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class options extends AppCompatActivity {
	RecyclerView active_food;
	AlertDialog.Builder alert_builder,alert_builder_two;
	AlertDialog alertDialog;
	TextView user_id;
	ImageButton refresh;
	Button delete_ac;
	Intent intent;
	Adapter_two adapter;
	FirebaseAuth auth;
	DatabaseReference ref;
	FirebaseDatabase database;
	PhoneAuthProvider.OnVerificationStateChangedCallbacks callback;
	PhoneAuthProvider.ForceResendingToken token;
	StringBuilder detail;
	String[] string_detail;
	String send_otp,mobile_number;
	List<ModelClass> available_list = new ArrayList<>();
	LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
	SharedPreferences preferences;
	SharedPreferences.Editor editor;
	View view;
	EditText otp;
	boolean dontShowAgain;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options);
		alert_builder_two = new AlertDialog.Builder(this);
		alert_builder_two.setMessage("Loading...").setCancelable(false).create();
		alertDialog = alert_builder_two.show();
		active_food = findViewById(R.id.active_food);
		delete_ac = findViewById(R.id.delete_ac);
		user_id = findViewById(R.id.user_id);
		refresh = findViewById(R.id.refresh);
		intent = new Intent(this,MainActivity.class);
		preferences = getPreferences(MODE_PRIVATE);
		editor  = preferences.edit();
		dontShowAgain = preferences.getBoolean("ShowAgainDonor",false);
		linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
		active_food.setLayoutManager(linearLayoutManager);
		view = getLayoutInflater().inflate(R.layout.delete_account_layout,(ViewGroup) findViewById(R.id.delete_ac_layout));
		otp = view.findViewById(R.id.reauth_otp);
		auth = FirebaseAuth.getInstance();
		database = FirebaseDatabase.getInstance();
		ref = database.getReference().getRoot().child("post").child(auth.getCurrentUser().getUid());
		mobile_number = auth.getCurrentUser().getPhoneNumber();
		alert_builder = new AlertDialog.Builder(this);
		user_id.setText(String.valueOf(mobile_number));
		try{
			ref.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(@NonNull DataSnapshot snapshot) {
					try {
						for (DataSnapshot snapshot1 : snapshot.getChildren()) { //Getting post of users
							detail = new StringBuilder();
							if ((System.currentTimeMillis() - Long.parseLong(String.valueOf(snapshot1.child("timestamp").getValue()))) < 86400000) {
								for (DataSnapshot snapshot2 : snapshot1.getChildren())
									detail.append("=").append(snapshot2.getValue());
								string_detail = (detail.toString()).split("=");
								//String address, String available, String city, String district, String food, String mobile, String name, String postId, String time, String unit
								available_list.add(new ModelClass(string_detail[1], string_detail[2], string_detail[3], string_detail[4], string_detail[5], string_detail[6], string_detail[7], string_detail[8], string_detail[9], string_detail[10],string_detail[11]));
							}
						}
					} catch(Exception e){
						Toast.makeText(options.this,e.getMessage(),Toast.LENGTH_LONG).show();
					}
					adapter = new Adapter_two(available_list);
					active_food.setAdapter(adapter);
					alertDialog.dismiss();
				}

				@Override
				public void onCancelled(@NonNull DatabaseError error) {

				}
			});
			if(!dontShowAgain)
				alert_builder.setMessage("Long press over the post, to edit and delete").setCancelable(true)
						.setPositiveButton("Ok", (dialog, which) -> dialog.dismiss())
						.setNegativeButton("Don't show again", (dialog, which) -> {
							editor.putBoolean("ShowAgainDonor",true);
							editor.commit();
						}).create().show();
		} catch (Exception exception) {
			Toast.makeText(options.this,exception.getMessage(), Toast.LENGTH_LONG).show();
		}
		delete_ac.setOnClickListener((View v)->{
			alert_builder_two.setMessage("Sending OTP...").setCancelable(false).create();
			alertDialog = alert_builder_two.show();
			alert_builder.setTitle(R.string.app_name)
				.setCancelable(false)
				.setMessage("We sent an OTP to "+mobile_number+"\nPlease enter the OTP to delete account")
				.setView(view)
				.setPositiveButton("Confirm",(DialogInterface dialog, int which)->{
					if (otp.getText().toString().isEmpty()) {
						Toast.makeText(this, "Please enter OTP", Toast.LENGTH_LONG).show();
						alertDialog = alert_builder.show();
					} else {
						Toast.makeText(options.this,"Checking...",Toast.LENGTH_LONG).show();
						try{
							PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(send_otp, otp.getText().toString());
							auth.signOut();
							Log.w("1","Ready to signin");
							auth.signInWithCredential(phoneAuthCredential).addOnCompleteListener((@NonNull Task<AuthResult> task)-> {
								if (task.isSuccessful()) {
									Log.w("2","Signin success");
									preferences.edit().clear().commit();
									ref.addListenerForSingleValueEvent(new ValueEventListener() {
										@Override
										public void onDataChange(@NonNull DataSnapshot snapshot) {
											for (DataSnapshot snapshot1 : snapshot.getChildren())
												ref.child(String.valueOf(snapshot1.getKey())).removeValue();
											Log.w("3","Posts deleted");
											auth.getCurrentUser().delete()
												.addOnSuccessListener((Void unused) -> {
													Toast.makeText(options.this, "Account successfully deleted", Toast.LENGTH_SHORT).show();
													((ResultReceiver) getIntent().getParcelableExtra("finisher")).send(1, new Bundle());
													startActivity(intent);
													finish();
												})
												.addOnFailureListener((Exception e) -> Toast.makeText(options.this, e.getMessage(), Toast.LENGTH_LONG).show());
										}
										@Override
										public void onCancelled(@NonNull DatabaseError error) {
										}
									});
								} else {
									Toast.makeText(this, "Verification Failed", Toast.LENGTH_SHORT).show();
									Intent goToDelete = new Intent(options.this,otp.class);
									goToDelete.putExtra("do","delete");
									goToDelete.putExtra("auth",send_otp);
									goToDelete.putExtra("token",token);
									goToDelete.putExtra("mobile",mobile_number);
									((ResultReceiver) getIntent().getParcelableExtra("finisher")).send(1, new Bundle());
									startActivity(goToDelete);
									finish();
								}
							});
						} catch(Exception error){
							Toast.makeText(options.this, error.getMessage(), Toast.LENGTH_SHORT).show();
						}
					}
				})
				.setNegativeButton("Cancel",(DialogInterface dialog, int which)->dialog.cancel()).create();
			callback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
				@Override
				public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {}
				@Override
				public void onVerificationFailed(@NonNull FirebaseException e) {
					Toast.makeText(options.this,"OTP mismatch",Toast.LENGTH_LONG).show();
				}
				@Override
				public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
					super.onCodeSent(s, forceResendingToken);
					alertDialog.dismiss();
					alertDialog = alert_builder.show();
					send_otp = s;
					token = forceResendingToken;
				}
			};
			PhoneAuthOptions authOptions = PhoneAuthOptions.newBuilder(auth)
						.setPhoneNumber(mobile_number)
						.setTimeout(60L, TimeUnit.SECONDS)
						.setActivity(this)
						.setCallbacks(callback)
						.build();
			PhoneAuthProvider.verifyPhoneNumber(authOptions);
		});
		refresh.setOnClickListener((View v)->{
			Intent re = new Intent(this,options.class);
			startActivity(re);
			finish();
			overridePendingTransition(0,0);
		});
	}
}