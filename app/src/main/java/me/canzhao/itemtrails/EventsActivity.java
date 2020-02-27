package me.canzhao.itemtrails;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class EventsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        ImageView itemImageView = findViewById(R.id.context_image);
        itemImageView.setImageResource(R.drawable.outside_rugby_park);
    }





    public String createArrivalNotice(String arrivalNotice) {
        boolean hasArrived = true;
        if (hasArrived) {
            arrivalNotice = "You've arrived.";
        } else {
            arrivalNotice = "You're away from the place.";
        }
        return arrivalNotice;
    }
}
