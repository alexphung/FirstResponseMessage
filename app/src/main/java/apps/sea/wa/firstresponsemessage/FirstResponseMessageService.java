package apps.sea.wa.firstresponsemessage;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.BundleCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import apps.sea.wa.firstresponsemessage.Model.Notification;
import apps.sea.wa.firstresponsemessage.Model.Sender;
import apps.sea.wa.firstresponsemessage.Model.UserData;

public class FirstResponseMessageService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...
        super.onMessageReceived(remoteMessage);

        //showReceiveNotification(remoteMessage.getNotification());

        showReceiveNotificationFromFcm(remoteMessage);

        //showReceiveNotificationFromFcm(remoteMessage);
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

//            if (/* Check if data needs to be processed by long running job */ true) {
//                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
//                scheduleJob();
//            } else {
//                // Handle message within 10 seconds
//                handleNow();
//            }
            //showReceiveNotificationFromFcm(remoteMessage);
        }


        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    private void showReceiveNotificationFromFcm(RemoteMessage remoteMessage) {
        // Setup the intent to start the activity for.
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Construct the message to be display on the MainActivity Screen.
        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();
        String details = remoteMessage.getData().toString(); // remoteMessage.getData().get("lastName");

        Map<String, String> map = remoteMessage.getData();
        for(Map.Entry<String, String> entry : map.entrySet()){
            Log.d("MAP_PACKET", "Key: " + entry.getKey() + "\tValue: " + entry.getValue() + "\n");
        }

        // This pass the message to the MainActivity screen.
        intent.putExtra("fcm_notification", title + "\n" + body + "\n" + details);
        intent.putExtra("gps_location", map.get("deviceLocation"));

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//            String CHANNEL_ID = "my_channel_01";
//            CharSequence name = "my_channel";
//            String Description = "This is my channel";
//            int importance = NotificationManager.IMPORTANCE_HIGH;
//            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setContentTitle(remoteMessage.getNotification().getTitle());
        notificationBuilder.setContentText(remoteMessage.getNotification().getBody());
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSmallIcon(R.mipmap.omega_launcher);
        notificationBuilder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }

    private void scheduleJob() {
        //@TODO
    }

    private void handleNow(){
        //@TODO
    }

    private void sendNotification(String messageBody){
        //@TODO
    }

    private void showReceiveNotification(RemoteMessage.Notification notification) {
        // Setup the intent to start the activity for.
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Construct the message to be display on the MainActivity Screen.
        String title =notification.getTitle();
        String body = notification.getBody();
        // This pass the message to the MainActivity screen.
        intent.putExtra("fcm_notification", title + "\n" + body);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setSmallIcon(R.mipmap.omega_launcher);
        notificationBuilder.setContentTitle(notification.getTitle());
        notificationBuilder.setContentText(notification.getBody());
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }
}

