package com.vadivelan.fooddonation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.Toast;

public class ConnectionStatusReceiver extends BroadcastReceiver {
	boolean isConnected,firstTime = true;
	@Override
	public void onReceive(Context context, Intent intent) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if(cm != null){
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
				NetworkCapabilities networkCapabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
				if(networkCapabilities != null)
					if(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
						isConnected = true;
			} else {
				NetworkInfo networkInfo = cm.getActiveNetworkInfo();
				if(networkInfo != null && networkInfo.isConnected())
					isConnected = true;
			}
			if(isConnected && !firstTime)
				Toast.makeText(context,"Your connection was restored", Toast.LENGTH_SHORT).show();
			else
				if(!isConnected)
					Toast.makeText(context,"No internet connection", Toast.LENGTH_SHORT).show();
			isConnected = false;
			firstTime = false;
		} else
			Toast.makeText(context,"No internet connection", Toast.LENGTH_SHORT).show();
	}
}
