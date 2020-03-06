package me.canzhao.itemtrails;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ItemsActivity extends AppCompatActivity {


    private ArrayList<ItemEvent> itemEvents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        Intent intent = getIntent();
        PoiItem pi = (PoiItem) intent.getSerializableExtra("data");

        ImageView itemImage = findViewById(R.id.item_image);
        itemImage.setImageResource(pi.getItemImageId());

        TextView itemImageCap = findViewById(R.id.item_image_caption);
        itemImageCap.setText(pi.getItemImageCap());

        /**
         * Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
         * There should be a {@link ListView} with the view ID called list,
         * which is declared in the event_on_item_list.xml_list.xml file.
         */
        ListView listView = findViewById(R.id.list);

        /**
         * Create a list of item events
         */

        itemEvents = pi.getItemEvent();
        ArrayList<EventOnDemand> eod = new ArrayList<>();
        for(int i=0; i<itemEvents.size(); i++){
            if(i == 0){
                eod.add(new EventOnDemand(R.drawable.ic_first_footprints,itemEvents.get(i).getEventName(),itemEvents.get(i).getEventDesSnippet()));
            }else if(i == itemEvents.size()-1){
                eod.add(new EventOnDemand(R.drawable.ic_last_footprints,itemEvents.get(i).getEventName(),itemEvents.get(i).getEventDesSnippet()));
            }else{
                eod.add(new EventOnDemand(R.drawable.ic_footprints,itemEvents.get(i).getEventName(),itemEvents.get(i).getEventDesSnippet()));
            }
        }

        /**
         * Create an {@link ArrayAdapter}, whose data source is a list of Strings.
         * The adapter knows how to create layouts for each item in the list,
         * using the simple_list_item_1.xml layout resource defined in the Android framework.
         * This list item layout contains a single {@link TextView},
         * which the adapter will set to display a single word.
         */
        final EventOnDemandAdapter adapter = new EventOnDemandAdapter(this, eod);

        /**
         * Make the {@link ListView} use the {@link ArrayAdapter} we created above,
         * so that the {@link ListView} will display list items for each word in the list of words.
         * Do this by calling the setAdapter method on the {@link ListView} object and pass in one argument,
         * which is the {@link ArrayAdapter} with the variable name itemsAdapter.
         */
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //EventOnItem eventContainer = events.get(position);
                //Toast.makeText(adapter.getContext(), "selected name is " + eventContainer.toString(), Toast.LENGTH_SHORT).show();
                LinearLayout eventContainer = findViewById(R.id.event_container);

                Intent eventIntent = new Intent(eventContainer.getContext(), EventsActivity.class);
                eventIntent.putExtra("position", position);
                eventIntent.putExtra("events", itemEvents);
                startActivity(eventIntent);
            }
        });
    }



}
