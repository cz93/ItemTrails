package me.canzhao.itemtrails;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class EventsActivity extends AppCompatActivity {

    private int position;
    private ArrayList<ItemEvent> events;
    private ItemEvent currentEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        Intent intent = getIntent();
        position = (int) intent.getSerializableExtra("position");
        events = (ArrayList<ItemEvent>) intent.getSerializableExtra("events");
        currentEvent = events.get(position);

        final ImageButton prevEventButton = findViewById(R.id.prev_event_button);
        if(position == 0) {
            prevEventButton.setVisibility(View.INVISIBLE);
        } else {
            prevEventButton.setVisibility(View.VISIBLE);
        }
        prevEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent prevEventButtonIntent = new Intent(prevEventButton.getContext(), EventsActivity.class);
                prevEventButtonIntent.putExtra("position", position-1);
                prevEventButtonIntent.putExtra("events", events);
                startActivity(prevEventButtonIntent);
            }
        });


        final ImageButton nextEventButton = findViewById(R.id.next_event_button);
        if(position == events.size()-1) {
            nextEventButton.setVisibility(View.INVISIBLE);
        } else {
            nextEventButton.setVisibility(View.VISIBLE);
        }
        nextEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextEventButtonIntent = new Intent(nextEventButton.getContext(), EventsActivity.class);
                nextEventButtonIntent.putExtra("position", position+1);
                nextEventButtonIntent.putExtra("events", events);
                startActivity(nextEventButtonIntent);
            }
        });

        TextView eventName = findViewById(R.id.event_name);
        eventName.setText(currentEvent.getEventName());

        TextView locationMessage = findViewById(R.id.location_message);
        locationMessage.setText("in");

        ImageView eventImage = findViewById(R.id.event_image);
        eventImage.setImageResource(currentEvent.getEventImageId());

        TextView eventImageCap = findViewById(R.id.event_image_caption);
        eventImageCap.setText(currentEvent.getEventImageCap());

        TextView eventDes = findViewById(R.id.event_des);
        eventDes.setText(currentEvent.getEventDes());



        final FloatingActionButton relevantEventButton = findViewById(R.id.relevant_event_fab);
        final ArrayList<Poi> pois = MainActivity.loadPoiData();


        if(currentEvent.getmRevelentEvent() == null) {
            relevantEventButton.setVisibility(View.INVISIBLE);
        }


        relevantEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<ItemEvent> revents = null;
                int rposition = -1;
                for(int i = 0; i < pois.size(); i++){
                    ArrayList<PoiItem> pi = pois.get(i).getPoiItem();
                    for (int a = 0; a < pi.size(); a++) {
                        ArrayList<ItemEvent> ies = pi.get(a).getItemEvent();
                        for(int b=0; b<ies.size(); b++){
                            if(ies.get(b).getEventDes().equals(currentEvent.getmRevelentEvent().getEventDes())){
                                revents = ies;
                                rposition = b;
                            }
                        }
                    }
                }

                Intent relevantEventIntent = new Intent(relevantEventButton.getContext(), EventsActivity.class);
                relevantEventIntent.putExtra("position", rposition);
                relevantEventIntent.putExtra("events", revents);
                startActivity(relevantEventIntent);
            }
        });

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
