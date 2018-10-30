package apps.sea.wa.firstresponsemessage;


import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

public class FirstResponseMessageInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        NotificationMessage.currentToken = refreshedToken;
        //FirebaseMessaging.getInstance().subscribeToTopic("EmulatorTestTopic");
        FirebaseMessaging.getInstance().subscribeToTopic("MyGroupTopic");
        //Log.d(TAG, "Refreshed token: " + refreshedToken);
    }

}
