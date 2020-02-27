package me.canzhao.itemtrails;

import androidx.appcompat.app.AppCompatActivity;

public class EventsActivity extends AppCompatActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_events);
//    }

    public String createArrivalNotice(String arrivalNotice) {
        boolean hasArrived = true;
        if(hasArrived) {
            arrivalNotice = "You've arrived.";
        } else {
            arrivalNotice = "You're away from the place.";
        }
        return arrivalNotice;
    }

}
