package com.vadivelan.fooddonation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Locale;

public class Adapter_two extends RecyclerView.Adapter<Adapter_two.ViewHolder>{
List<ModelClass> active_food;
Adapter_two(List<ModelClass> active_food){
	this.active_food=active_food;
}
	@NonNull
	@Override
	public Adapter_two.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.active_layout,parent,false);
		return new ViewHolder(v);
	}

	@Override
	public void onBindViewHolder(@NonNull Adapter_two.ViewHolder holder, int position) {
	holder.setData(active_food.get(position).getAddress(),active_food.get(position).getAvailable(),active_food.get(position).getCity(),active_food.get(position).getDistrict(),active_food.get(position).getFood(),active_food.get(position).getMobile(),active_food.get(position).getName(),active_food.get(position).getPostId(),active_food.get(position).getTime(),active_food.get(position).getUnit());
	}

	@Override
	public int getItemCount() {
		return active_food.size();
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
	TextView donar_name,food_name,quantity,location,cell,date_and_time;
	ImageButton edit,remove;
	FirebaseAuth auth = FirebaseAuth.getInstance();
	FirebaseDatabase database = FirebaseDatabase.getInstance();
	DatabaseReference ref = database.getReference().getRoot().child(String.valueOf(auth.getCurrentUser().getPhoneNumber()));
		ViewHolder(View v){
			super(v);
			donar_name = v.findViewById(R.id.donor_name);
			food_name = v.findViewById(R.id.food_name);
			quantity = v.findViewById(R.id.available);
			location = v.findViewById(R.id.location);
			cell = v.findViewById(R.id.cell);
			date_and_time = v.findViewById(R.id.date);
			edit = v.findViewById(R.id.edit);
			remove = v.findViewById(R.id.remove);
		}

		public void setData(String address, String available, String city, String district, String food, String mobile, String name, String postId, String time, String unit) {
			donar_name.setText(name);
			food_name.setText(food);
			quantity.setText(String.format(Locale.ENGLISH,"%s %s ",available,unit));
			location.setText(String.format(Locale.ENGLISH,"%s, %s, %s",address,city,district));
			cell.setText(mobile);
			//tdate.setText(date);
			edit.setContentDescription(postId);
			remove.setContentDescription(postId);
			edit.setOnClickListener((View v)->{

			});
			remove.setOnClickListener((View v)->{
				ref.child(String.valueOf(v.getContentDescription())).removeValue();
				((ViewGroup) v.getParent().getParent()).setVisibility(View.GONE);
				Toast.makeText(v.getContext(),"Your post has been deleted",Toast.LENGTH_LONG).show();
			});
		}
	}
}
