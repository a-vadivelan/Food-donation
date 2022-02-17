package com.vadivelan.fooddonation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.Manifest;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
List<ModelClass> food_list;
	public Adapter(List<ModelClass> food_list) {
		super();
		this.food_list = food_list;
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.donated_layout,parent,false);
		return new ViewHolder(v);
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		holder.setData(food_list.get(position).getAddress(),food_list.get(position).getAvailable(),food_list.get(position).getCity(),food_list.get(position).getDistrict(),food_list.get(position).getFood(),food_list.get(position).getMobile(),food_list.get(position).getName(),food_list.get(position).getPostId(),food_list.get(position).getTime(),food_list.get(position).getUnit(),food_list.get(position).getUserId());
	}

	@Override
	public int getItemCount() {
		return food_list.size();
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
		TextView name,food,quantity,address,mobile,date_time;
		FirebaseAuth auth = FirebaseAuth.getInstance();
		FirebaseDatabase database = FirebaseDatabase.getInstance();
		DatabaseReference ref = database.getReference().getRoot().child("report");
		DatabaseReference userRef,reportRef;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a",Locale.US);
		View view;
		primary primary = new primary();
		ViewHolder(View v){
			super(v);
			name = v.findViewById(R.id.donor_name);
			food = v.findViewById(R.id.food_name);
			quantity = v.findViewById(R.id.available);
			address = v.findViewById(R.id.location);
			mobile = v.findViewById(R.id.cell);
			date_time = v.findViewById(R.id.date);
			this.view = v;
		}
		public void setData(String donar_address,String available,String city,String district,String food_name,String donar_mobile,String donar_name,String postId,String time,String unit,String userId){
			name.setText(donar_name);
			food.setText(food_name);
			quantity.setText(String.format(Locale.ENGLISH,"%s %s",available,unit));
			address.setText(String.format(Locale.ENGLISH,"%s, %s, %s",donar_address,city,district));
			mobile.setText(donar_mobile);
			date_time.setText(simpleDateFormat.format(new Date(Long.parseLong(time))));
			view.setOnLongClickListener((View v) -> {
			PopupMenu popup = new PopupMenu(v.getContext(),v);
			popup.getMenuInflater().inflate(R.menu.post_options,popup.getMenu());
			popup.setOnMenuItemClickListener(item -> {
				setPopup(v,(String) item.getTitle(),donar_mobile,userId,postId);
				return false;
			});
			popup.show();
			return false;
		});
		}
		public void setPopup(View vi,String item, String mobile,String userId,String postId){
			if(item.equals("Call")){
				Intent intent = new Intent(Intent.ACTION_CALL);
				intent.setData(Uri.parse("tel:"+mobile));
				if(ContextCompat.checkSelfPermission(vi.getContext(),Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
					vi.getContext().startActivity(intent);
				else {
					if(ActivityCompat.shouldShowRequestPermissionRationale((Activity)vi.getContext(),Manifest.permission.CALL_PHONE)){
						new AlertDialog.Builder(vi.getContext())
								.setTitle("Permission")
								.setMessage("You need to give Phone call permission to call the donor from app.")
								.setCancelable(true)
								.setPositiveButton("OK", (dialog, which) -> ActivityCompat.requestPermissions((Activity) vi.getContext(), new String[]{Manifest.permission.CALL_PHONE}, 1))
								.setNegativeButton("Cancel",(DialogInterface dialog, int which) ->dialog.dismiss()).create().show();
					} else {
						Toast.makeText(vi.getContext(),"Please give Phone call permission to make a call", Toast.LENGTH_LONG).show();
						Intent openSetting = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
						openSetting.setData(Uri.parse("package:"+vi.getContext().getPackageName()));
						vi.getContext().startActivity(openSetting);
					}

				}
				if(ContextCompat.checkSelfPermission(vi.getContext(),Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
					vi.getContext().startActivity(intent);
			} else {
				PopupMenu popup1 = new PopupMenu(vi.getContext(),vi);
				popup1.getMenuInflater().inflate(R.menu.report_menu, popup1.getMenu());
				popup1.setOnMenuItemClickListener(item1 -> {
					userRef = ref.child(userId);
					reportRef = userRef.push();
					reportRef.setValue(new Report(postId,(String) item1.getTitle(),auth.getCurrentUser().getUid()));
					Toast.makeText(vi.getContext(),"Thanks for Report!",Toast.LENGTH_SHORT).show();
					return false;
				});
				popup1.show();
			}
		}
	}
}
