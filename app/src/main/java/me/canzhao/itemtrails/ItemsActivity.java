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

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ItemsActivity extends AppCompatActivity {
    
    private int itemId;//to be integrated with other data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

//        ImageView itemImageView = findViewById(R.id.item_image);
//        itemImageView.setImageResource(setImageResourceId());

        /**
         * Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
         * There should be a {@link ListView} with the view ID called list,
         * which is declared in the event_on_item_list.xml_list.xml file.
         */
        ListView listView = findViewById(R.id.list);

        //ImageView itemImageView = (ImageView)
        //getLayoutInflater().inflate(R.layout.header_list_eventonitem, listView, false);

        ImageView itemImageView = findViewById(R.id.item_image);

        itemImageView.setImageResource(setImageResourceId());
        //itemImageView.setImageResource(R.drawable.ticket);
        //listView.addHeaderView(itemImageView);

        /**
         * Create a list of item events
         */
        final ArrayList<EventOnItem> events = new ArrayList<>();

        events.add(new EventOnItem(R.drawable.ic_first_footprints, "Event 1 of Item 1",
                "This is a secondary text that takes up more than one line."));
        events.add(new EventOnItem(R.drawable.ic_footprints, "Event 2 of Item 1",
                "This is a secondary text that takes up more than one line."));
        events.add(new EventOnItem(R.drawable.ic_footprints, "Event 3 of Item 1",
                "This is a secondary text that takes up more than one line."));
        events.add(new EventOnItem(R.drawable.ic_footprints, "Event 4 of Item 1",
                "This is a secondary text that takes up more than one line."));
        events.add(new EventOnItem(R.drawable.ic_last_footprints, "Event 5 of Item 1",
                "This is a secondary text that takes up more than one line."));
//        events.add(new EventOnItem(R.drawable.number_one, R.string.one_on_two_title,
//                R.string.one_on_two_description));
//        events.add(new EventOnItem(R.drawable.number_two, R.string.two_on_two_title,
//                R.string.two_on_two_description));
//        events.add(new EventOnItem(R.drawable.number_two, R.string.three_on_two_title,
//                R.string.three_on_two_description));
//        events.add(new EventOnItem(R.drawable.number_two, R.string.four_on_two_title,
//                R.string.four_on_two_description));
//        events.add(new EventOnItem(R.drawable.number_two, R.string.five_on_two_title,
//                R.string.five_on_two_description));
//        events.add(new EventOnItem(R.drawable.number_one, R.string.one_on_three_title,
//                R.string.one_on_three_description));
//        events.add(new EventOnItem(R.drawable.number_two, R.string.two_on_three_title,
//                R.string.two_on_three_description));
//        events.add(new EventOnItem(R.drawable.number_two, R.string.three_on_three_title,
//                R.string.three_on_three_description));
//        events.add(new EventOnItem(R.drawable.number_two, R.string.four_on_three_title,
//                R.string.four_on_three_description));
//        events.add(new EventOnItem(R.drawable.number_two, R.string.five_on_three_title,
//                R.string.five_on_three_description));
//        events.add(new EventOnItem(R.drawable.number_one, R.string.one_on_four_title,
//                R.string.one_on_four_description));
//        events.add(new EventOnItem(R.drawable.number_two, R.string.two_on_four_title,
//                R.string.two_on_four_description));
//        events.add(new EventOnItem(R.drawable.number_two, R.string.three_on_four_title,
//                R.string.three_on_four_description));
//        events.add(new EventOnItem(R.drawable.number_two, R.string.four_on_four_title,
//                R.string.four_on_four_description));
//        events.add(new EventOnItem(R.drawable.number_two, R.string.five_on_four_title,
//                R.string.five_on_four_description));
//        events.add(new EventOnItem(R.drawable.number_one, R.string.one_on_five_title,
//                R.string.one_on_five_description));
//        events.add(new EventOnItem(R.drawable.number_two, R.string.two_on_five_title,
//                R.string.two_on_five_description));
//        events.add(new EventOnItem(R.drawable.number_two, R.string.three_on_five_title,
//                R.string.three_on_five_description));
//        events.add(new EventOnItem(R.drawable.number_two, R.string.four_on_five_title,
//                R.string.four_on_five_description));
//        events.add(new EventOnItem(R.drawable.number_two, R.string.five_on_five_title,
//                R.string.five_on_five_description));

        /**
         * Create an {@link ArrayAdapter}, whose data source is a list of Strings.
         * The adapter knows how to create layouts for each item in the list,
         * using the simple_list_item_1.xml layout resource defined in the Android framework.
         * This list item layout contains a single {@link TextView},
         * which the adapter will set to display a single word.
         */
        final EventOnItemAdapter adapter = new EventOnItemAdapter(this, events);

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
                startActivity(eventIntent);

            }
        });
    }

    
    private int setImageResourceId() {

        int imageResourceId = 0;

        for(itemId = 0; itemId < 5; itemId++) {

            if (itemId == 0) { imageResourceId = R.drawable.ladle; }
            else if (itemId == 1) { imageResourceId = R.drawable.ladle; }
            else if (itemId == 2) { imageResourceId = R.drawable.ladle; }
            else if (itemId == 3) { imageResourceId = R.drawable.ladle; }
            else { imageResourceId = R.drawable.ticket; }

        }
        
        return imageResourceId;
    }
}
