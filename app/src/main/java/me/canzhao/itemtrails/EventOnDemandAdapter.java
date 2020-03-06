package me.canzhao.itemtrails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class EventOnDemandAdapter extends ArrayAdapter<EventOnDemand> {

    public EventOnDemandAdapter(Context context, ArrayList<EventOnDemand> events) {
        super(context, 0, events);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view.
        View listEvent = convertView;
        if (listEvent == null) {
            listEvent = LayoutInflater.from(getContext())
                    .inflate(R.layout.event_on_item_list, parent, false);
        }

        // Get the {@link EventOnDemand} object located at this position in the list.
        EventOnDemand currentEvent = getItem(position);

        // Set the trail images.
        ImageView trailImageView = listEvent.findViewById(R.id.image);
        trailImageView.setImageResource(currentEvent.getTrailImageId());

//        if (currentEvent.hasImage()) {
//            trailImageView.setImageResource(currentEvent.getTrailImageId());
//            trailImageView.setVisibility(View.VISIBLE);
//        } else {
//            trailImageView.setVisibility(View.GONE);
//        }

        // Find the TextView in the event_on_item_list.xml_list.xml layout with the ID miwok_text_view.
        TextView eventName = listEvent.findViewById(R.id.event_name);
        // Get the  translation from the currentWord object and set this text on the Miwok TextView.
        eventName.setText(currentEvent.getEventName());

        // Find the TextView in the event_on_item_list.xml_list.xml layout with the ID miwok_text_view.
        TextView eventDesSnippet = listEvent.findViewById(R.id.event_des_snippet);
        // Get the  translation from the currentWord object and set this text on the Miwok TextView.
        eventDesSnippet.setText(currentEvent.getEventDesSnippet());

        return listEvent;
    }
}
