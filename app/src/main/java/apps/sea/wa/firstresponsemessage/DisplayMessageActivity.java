package apps.sea.wa.firstresponsemessage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String messageBody = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        String messageTitle = intent.getStringExtra(MainActivity.EXTRA_TITLE);
        String gpsLocation = intent.getStringExtra(MainActivity.EXTRA_GPS_LOCATION);

        // Capture the layout's TextView and set the string as its text
        TextView mstViewTitle = findViewById(R.id.txtViewTitle);
        TextView msgViewBody = findViewById(R.id.txtViewBody);
        mstViewTitle.setText(messageTitle);
        msgViewBody.setText(messageBody);

        TextView gpsView = findViewById(R.id.gpsLocation);
        gpsView.append("\n" + gpsLocation);
    }
}
