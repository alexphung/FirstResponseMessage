package apps.sea.wa.firstresponsemessage;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import apps.sea.wa.firstresponsemessage.Model.MyResponse;
import apps.sea.wa.firstresponsemessage.Model.Notification;
import apps.sea.wa.firstresponsemessage.Model.Sender;
import apps.sea.wa.firstresponsemessage.Model.UserData;
import apps.sea.wa.firstresponsemessage.Remote.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private LocationManager locationManager;
    private LocationListener locationListener;
    public static final String EXTRA_TITLE = "apps.sea.wa.firstresponsemessage.TITLE";
    public static final String EXTRA_MESSAGE = "apps.sea.wa.firstresponsemessage.MESSAGE";
    public static final String EXTRA_GPS_LOCATION = "apps.sea.wa.device.GPS_LOCATION";
    APIService mService;
    private UserData userData = new UserData();
    private Location deviceCurrentLocation = null;

    //Beginning of Google Map Service Properties
    //=============================================================================================
    private static final String TAG = "MainActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    //End of Google Map Service Properties
    //=============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getGPSDeviceLocation();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                requestPermissions(new String[]{
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
                }, 10);
                return;
            }
            configurationButton();
        } else {
            configurationButton();
        }

        // NOTIFICATION MESSAGE HANDLING.
        NotificationMessage.currentToken = FirebaseInstanceId.getInstance().getToken();

        //For Multiple User
        FirebaseMessaging.getInstance().subscribeToTopic("MyGroupTopic");
        mService = NotificationMessage.getFCMClient();

        Log.d("MY_TOKEN", "Token --> " + NotificationMessage.currentToken);

        // Google Play Service Check
        if (isServicesOK()) {
            init();
        }

        showNotificationDetail();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    // Beginning of Google Map Service Codes
    // ===========================================================================================
    private void init() {
        Button btnMap = findViewById(R.id.btnMap);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MapsActivity.class);

                String gps_location = getIntent().getStringExtra("gps_location");
                Bundle bund = getIntent().getExtras();
                if (gps_location == null
                        && bund != null
                        && bund.getString("deviceLocation") != null) {
                    gps_location = bund.getString("deviceLocation");
                }
                if(gps_location != null){
                    intent.putExtra(EXTRA_GPS_LOCATION, gps_location);
                }else if(deviceCurrentLocation != null){
                    intent.putExtra(EXTRA_GPS_LOCATION, "" + deviceCurrentLocation.getLatitude() +
                                                        ":" + deviceCurrentLocation.getLongitude());
                }else {
                    intent.putExtra(EXTRA_GPS_LOCATION, "");
                }


                startActivity(intent);
            }
        });
    }

    public boolean isServicesOK() {
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if (available == ConnectionResult.SUCCESS) {
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //an error occurred but we can resolve it
            Log.d(TAG, "isServicesOK: an error occurred but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }


    // End of Google Map Service Codes
    // ===========================================================================================

    private void showNotificationDetail() {
        TextView txtView = findViewById(R.id.txtDisplayArea);
        String fcm_notification = getIntent().getStringExtra("fcm_notification");
        txtView.setText(fcm_notification);

        Bundle bund = getIntent().getExtras();
        if (fcm_notification == null && bund != null) {
            userData = (UserData) bund.get("data");
            if (userData != null) {
                fcm_notification = userData.toString();
                txtView.setText(fcm_notification);
            }
            if (bund.getString("title") != null){
                String title, body, data;
                title = bund.getString("title");
                body = bund.getString("body");
                data = bund.getString("deviceModel") + " : " + bund.getString("deviceLocation");
                txtView.setText(title + "\n" + body + "\n" + data);
            }
            if(deviceCurrentLocation != null) {
                txtView.append("My current GPS: " + deviceCurrentLocation.getLatitude() + ":" + deviceCurrentLocation.getLongitude());
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showNotificationDetail();

//        Intent intent_o = getIntent();
//        String fcm_notification = intent_o.getStringExtra("fcm_notification") ;
//        if(fcm_notification != null) {
//            TextView txtView = findViewById(R.id.txtDisplayArea);
//            txtView.setText("\n" + fcm_notification);
//        }
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editMsgTitle = (EditText) findViewById(R.id.editMsgTitle);
        EditText editMsgBody = (EditText) findViewById(R.id.editMsgBody);

        String messageTitle = editMsgTitle.getText().toString();
        String messageBody = editMsgBody.getText().toString();
        intent.putExtra(EXTRA_TITLE, messageTitle);
        intent.putExtra(EXTRA_MESSAGE, messageBody);

        String location = "";
        if(userData != null) {
            intent.putExtra(EXTRA_GPS_LOCATION, userData.getDeviceLocation());
            location = userData.getDeviceLocation();
        }else if(deviceCurrentLocation != null){
            intent.putExtra(EXTRA_GPS_LOCATION,
                    ("" + deviceCurrentLocation.getLatitude() + ":" + deviceCurrentLocation.getLongitude()));

            location =  ("" + deviceCurrentLocation.getLatitude() + ":" + deviceCurrentLocation.getLongitude());
        }else{
            intent.putExtra(EXTRA_GPS_LOCATION, "");
        }

        //Send Notification to Self or to App.
        //SendNotificationToApp(message);

        //Send Notification to subscribed MyGroupTopic device.
        sendNotificationToTopic(messageTitle, messageBody, location);

        // Lastly we start the activity by calling the following method below.
        startActivity(intent);
    }

    private void getGPSDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting the devices current location");
        // GET THE GPS LOCATION HERE.
        // Access the Device GPS Information
        final StringBuilder gpsLocation = new StringBuilder();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {

            // Get Call when the location get changed.
            @Override
            public void onLocationChanged(Location location) {
                deviceCurrentLocation = location;
                userData = new UserData();
                gpsLocation.append(location.getLatitude() + ":" + location.getLongitude());
                if(userData != null){
                    userData.setDeviceLocation(gpsLocation.toString());
                }
                Log.d(TAG,"onLocationChanged: event fired and userData instantiated: GPS: " + gpsLocation.toString());
                locationManager.removeUpdates(this);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            // Get Call when check if the GPS is enable.
            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
    }

    private void sendNotificationToTopic(String messageTitle, String messageBody, String location) {
        //Here to construct our message to be send out.

        // Added new Data Packet.
        // S4 Device API Key ID
        String messageId = "eqiMvOCdFfE:APA91bFyQdgChSB4lKpy8U6pk3NOexk9KereQionB23aZkimSDGit9-5znXBVx47Y_5_eYVaNNfBt33bTflbd9iD5T9G1JD3Nt0e6RUHqaf3T0jmQKA2LoXYyYs3NgLLLH7caqXBjFko";
        // S5 Device API Key ID
        // String messageId = "f1sdNgRolqs:APA91bF-kq9JC3LK6uJN9l6_Lzy--mx9i0iGkZoY4QK1FFCg73mgP82p6WsJl8a2NzPzCDogyw9RRRahXF1br0ZX86Zz421quKXbZ7ZR9_pGK_tDylg8DywfP5fr_jvrnRq-2tX8Ag1H";
        UserData dataPacket = new UserData(Build.USER, Build.DEVICE, Build.MODEL, Build.VERSION.RELEASE, location);

        //Construct the Sender Request
        Notification notification = new Notification(messageTitle, messageBody);
        Sender sender = new Sender(messageId, notification, dataPacket); //Send to topic
        //Sender sender = new Sender("/topics/MyGroupTopic", notification, dataPacket); //Send to topic

        mService.sendNotification(sender)
                .enqueue(new Callback<MyResponse>() {
                    @Override
                    public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
//                        if(response.body().success == 1)
//                            Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
//                        else
//                            Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<MyResponse> call, Throwable t) {
//                        Log.e("ERROR", t.getMessage());
                    }
                });
    }

    // Send Notification to Self or to App.
    private void SendNotificationToApp(String message) {
        NotificationMessage.currentToken = FirebaseInstanceId.getInstance().getToken();
        //[S5]
        //"dKAf2Mos6nY:APA91bEWIDLNKufgbLhvyxvmrszu5rUKFbU98a3DgZPDBfOZcq1_vy5KCpvAsmcv9erUW1xTVBWhZQjCHTKr4OHXVJqnvpQXH3uHIz6JKToOSFEicJW1EFC2J6F_h3HjzMlodNmjLbV9";
        // [S4]
        // "cEOMrw0y7eo:APA91bEllKSfQ8VNRsH5SiZTfH1CYh4fakl1RMGNJHafrY1otxu1BkgU_4y_JtV7rFEO6NHL-WNh726jkwN13fJheuJ0hVXh3j35VX0QTJwNikrC-T85jOtlO-kOfO4TmhMROTMaYvGV";

        //Here to construct our message to be send out.
        //We will use a default Title "[SOS] HELP!!!" as the message for now.
        String messageTitle = "[SOS] HELP!!!";

        //Construct the Sender Request
        Notification notification = new Notification(messageTitle, message);
        Sender sender = new Sender(NotificationMessage.currentToken, notification); //Send to itself

        mService.sendNotification(sender)
                .enqueue(new Callback<MyResponse>() {
                    @Override
                    public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                        if (response.body().success == 1)
                            Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<MyResponse> call, Throwable t) {
                        Log.e("ERROR", t.getMessage());
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    configurationButton();
                }
        }
    }

    private void configurationButton() {
        // Request the Location Updates
        // How about getting the GPS Location only once.
        // only get call ever 5 second and when the location is changed at least 5 meters.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }
}
