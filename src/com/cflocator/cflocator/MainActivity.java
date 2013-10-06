package com.cflocator.cflocator;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;

@SuppressLint("NewApi")
public class MainActivity extends Activity implements LocationListener {
	
	private static final long MIN_TIME = 20;
	private static final float MIN_DISTANCE = 0;
	private GoogleMap map;
	LocationManager locationManager;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Intent intent = new Intent(this, backgroundService.class);
        startService(intent);
		
		this.map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		   
		   this.locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		   this.locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, (LocationListener) this);
	
		   this.map.addMarker(new MarkerOptions()
	        .position(new LatLng(33.7757, -84.3966261))
	        .title("CF Buddy"));
		    
		    this.map.addMarker(new MarkerOptions()
	        .position(new LatLng(33.7757747, -84.3966263))
	        .title("Me"));
		    
		    this.map.addMarker(new MarkerOptions()
	        .position(new LatLng(33.775781111, -84.3966265))
	        .title("CF Buddy"));
		    
		    LatLng latLng = new LatLng(33.7757747, -84.3966263);
		    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 25);
		    
		    
		    this.map.animateCamera(cameraUpdate);
		    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
	    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 2);
	    
	    
	    this.map.animateCamera(cameraUpdate);
	    this.locationManager.removeUpdates(this);
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

}
