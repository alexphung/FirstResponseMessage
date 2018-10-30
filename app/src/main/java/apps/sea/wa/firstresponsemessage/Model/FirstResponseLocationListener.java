package apps.sea.wa.firstresponsemessage.Model;

import android.content.Intent;
import android.os.Bundle;
import android.location.Location;
import android.location.LocationListener;
import android.provider.Settings;

public class FirstResponseLocationListener implements LocationListener {
    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        //startActivity(intent);
    }
}
