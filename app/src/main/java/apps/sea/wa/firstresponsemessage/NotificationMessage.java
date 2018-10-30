package apps.sea.wa.firstresponsemessage;

import apps.sea.wa.firstresponsemessage.Remote.APIService;
import apps.sea.wa.firstresponsemessage.Remote.RetrofitClient;

public class NotificationMessage {
    public static String currentToken = "";

    private static String baseURL = "https://fcm.googleapis.com";

    public static APIService getFCMClient(){
        return RetrofitClient.getClient(baseURL).create(APIService.class);
    }
}
