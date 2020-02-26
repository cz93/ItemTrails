package me.canzhao.itemtrails;

import androidx.appcompat.app.AppCompatActivity;

public class EventsActivity extends AppCompatActivity {

    private String createArrivalNotice(String arrivalNotice) {
        boolean hasArrived = true;
        if(hasArrived) {
            arrivalNotice = "You've arrived.";
        } else {
            arrivalNotice = "You're away from the place.";
        }
        return arrivalNotice;
    }

}
