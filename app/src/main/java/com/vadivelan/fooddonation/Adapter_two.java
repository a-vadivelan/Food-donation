package com.vadivelan.fooddonation;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class Adapter_two extends RecyclerView.Adapter<Adapter_two.ViewHolder>{
static List<ModelClass> active_food;
Adapter_two(List<ModelClass> active_food){
	Adapter_two.active_food =active_food;
}
	@NonNull
	@Override
	public Adapter_two.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.active_layout,parent,false);
		return new ViewHolder(v);
	}

	@Override
	public void onBindViewHolder(@NonNull Adapter_two.ViewHolder holder, int position) {
	holder.setData(active_food.get(position).getAddress(),active_food.get(position).getAvailable(),active_food.get(position).getCity(),active_food.get(position).getDistrict(),active_food.get(position).getFood(),active_food.get(position).getMobile(),active_food.get(position).getName(),active_food.get(position).getPostId(),active_food.get(position).getTime(),active_food.get(position).getUnit(),active_food.get(position).getUserId(),position);
	}

	@Override
	public int getItemCount() {
		return active_food.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {
	TextView donor_name,food_name,quantity,location,cell,date_and_time;
	FirebaseAuth auth = FirebaseAuth.getInstance();
	FirebaseDatabase database = FirebaseDatabase.getInstance();
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a",Locale.US);
	View view;
	DatabaseReference ref = database.getReference().getRoot().child("post").child(auth.getCurrentUser().getUid());
		ViewHolder(View v){
			super(v);
			donor_name = v.findViewById(R.id.donor_name);
			food_name = v.findViewById(R.id.food_name);
			quantity = v.findViewById(R.id.available);
			location = v.findViewById(R.id.location);
			cell = v.findViewById(R.id.cell);
			date_and_time = v.findViewById(R.id.date);
			this.view = v;
		}

		public void setData(String address, String available, String city, String district, String food, String mobile, String name, String postId, String time, String unit,String userId,int position) {
			donor_name.setText(name);
			food_name.setText(food);
			quantity.setText(String.format(Locale.ENGLISH,"%s %s ",available,unit));
			location.setText(String.format(Locale.ENGLISH,"%s, %s, %s",address,city,district));
			cell.setText(mobile);
			date_and_time.setText(simpleDateFormat.format(new Date(Long.parseLong(time))));
			view.setOnLongClickListener((View v)->{
				PopupMenu popup = new PopupMenu(v.getContext(),v);
				popup.getMenuInflater().inflate(R.menu.donar_options,popup.getMenu());
				popup.setOnMenuItemClickListener(item -> {
					if((item.getTitle()).equals("Edit")){
						Intent edit = new Intent(v.getContext(),EditDonationActivity.class);
						edit.putExtra("name",name);    //Gets current info of post for autofill while edit
						edit.putExtra("food",food);
						edit.putExtra("address",address);
						edit.putExtra("available",available);
						edit.putExtra("id",postId);
						edit.putExtra("mobile",mobile);
						edit.putExtra("userId",userId);
						v.getContext().startActivity(edit);
					} else {
						active_food.remove(position);
						ref.child(postId).removeValue();
						notifyItemRemoved(position);
						notifyItemRangeChanged(position,active_food.size());
						Toast.makeText(v.getContext(),"Your post has been deleted",Toast.LENGTH_LONG).show();
					}
					return false;
				});
				popup.show();
				return false;
			});
		}
	}
}
