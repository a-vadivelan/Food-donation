package com.vadivelan.fooddonation;

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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class options extends AppCompatActivity {
	RecyclerView active_food;
	AlertDialog.Builder alert_builder;
	Button delete_ac;
	Intent intent;
	FirebaseAuth auth;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options);
		active_food = findViewById(R.id.active_food);
		delete_ac = findViewById(R.id.delete_ac);
		intent = new Intent(this,MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		auth = FirebaseAuth.getInstance();
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
				.setNegativeButton("No",(DialogInterface dialog, int which)->dialog.cancel());
		AlertDialog alertDialog = alert_builder.create();
		List<ModelClass> active_list = new ArrayList<>();
		active_list.add(new ModelClass("Puliyotharai",2,"Coimbatore","9876543210","01-11-2021"));
		active_list.add(new ModelClass("Idly",4,"Coimbatore","9876543210","01-11-2021"));
		active_list.add(new ModelClass("Rice",3,"Coimbatore","9876543210","01-11-2021"));
		active_list.add(new ModelClass("Kuzhambu",5,"Coimbatore","9876543210","01-11-2021"));
		active_list.add(new ModelClass("Dosai",5,"Coimbatore","9876543210","01-11-2021"));

		Adapter_two adapter_two = new Adapter_two(active_list);
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
		linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
		active_food.setLayoutManager(linearLayoutManager);
		active_food.setAdapter(adapter_two);

		delete_ac.setOnClickListener((View v)->{
			alertDialog.show();
		});

	}
}