package com.vadivelan.fooddonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

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
	StringBuilder detail;
	String[] string_detail;
	List<ModelClass> available_list = new ArrayList<>();
	LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
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
		linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
		active_food.setLayoutManager(linearLayoutManager);
		auth = FirebaseAuth.getInstance();
		database = FirebaseDatabase.getInstance();
		ref = database.getReference().getRoot().child(String.valueOf(auth.getCurrentUser().getPhoneNumber()));
		alert_builder = new AlertDialog.Builder(this);
		alert_builder.setTitle(R.string.app_name)
				.setCancelable(false)
				.setMessage("Are you sure delete your account?")
				.setPositiveButton("Yes",(DialogInterface dialog, int which)->{
					auth.signOut();
					Toast.makeText(this, "Account successfully deleted", Toast.LENGTH_SHORT).show();
					((ResultReceiver)getIntent().getParcelableExtra("finisher")).send(1,new Bundle());
					startActivity(intent);
					finish();
					})
				.setNegativeButton("No",(DialogInterface dialog, int which)->dialog.cancel()).create();
		user_id.setText(String.valueOf(auth.getCurrentUser().getPhoneNumber()));
		try{
			ref.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(@NonNull DataSnapshot snapshot) {
					try {
						for (DataSnapshot snapshot1 : snapshot.getChildren()) { //Getting post of users
							detail = new StringBuilder();
							for (DataSnapshot snapshot2 : snapshot1.getChildren())
								detail.append(",").append(snapshot2.getValue());
							string_detail = (detail.toString()).split(",");
							//String address, String available, String city, String district, String food, String mobile, String name, String postId, String time, String unit
							available_list.add(new ModelClass(string_detail[1], string_detail[2], string_detail[3], string_detail[4], string_detail[5], string_detail[6], string_detail[7], string_detail[8], string_detail[9], string_detail[10]));
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
		} catch (Exception exception) {
			Toast.makeText(options.this,exception.getMessage(), Toast.LENGTH_LONG).show();
		}
		delete_ac.setOnClickListener((View v)->alertDialog = alert_builder.show());
		refresh.setOnClickListener((View v)->{
			Intent re = new Intent(this,options.class);
			startActivity(re);
			finish();
			overridePendingTransition(0,0);
		});
	}
}