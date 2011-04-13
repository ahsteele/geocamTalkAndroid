package gov.nasa.arc.geocam.talk.service;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

public class GeoLocationListener implements LocationListener {

	private Location curLocation;
	
	@Override
	public void onLocationChanged(Location location) {
		curLocation = location;
		Log.d("location", "location listened");
	}

	@Override
	public void onProviderDisabled(String provider) { }

	@Override
	public void onProviderEnabled(String provider) { }

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) { }

	public Location getLocation() {
		return curLocation;
	}

}
