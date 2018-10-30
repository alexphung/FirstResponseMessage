package apps.sea.wa.firstresponsemessage.Remote;

import apps.sea.wa.firstresponsemessage.Model.MyResponse;
import apps.sea.wa.firstresponsemessage.Model.Sender;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAAEWltG3M:APA91bHB1uGxS8zDD-tcMPT_47rta7jPyO2_AyozyW8tAyajitQbfHZNulvDA2YTB0Ga5lE3zzliGdhsmX85KxUnDqT8FIXgblhqGqnaMxNCAssL7uyXb7Q8PMhyPXgUq-_xkSunYSHH"
    })
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
