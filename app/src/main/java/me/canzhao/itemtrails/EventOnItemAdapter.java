package me.canzhao.itemtrails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class EventOnItemAdapter extends ArrayAdapter<EventOnItem> {

    public EventOnItemAdapter(Context context, ArrayList<EventOnItem> events) {
        super(context, 0, events);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext())
                    .inflate(R.layout.event_on_item_list, parent, false);
        }

        // Get the {@link EventOnItem} object located at this position in the list.
        EventOnItem currentEvent = getItem(position);

        // Set
        ImageView trailImageView = listItemView.findViewById(R.id.image);

        if (currentEvent.hasImage()) {
            trailImageView.setImageResource(currentEvent.getTrailImageId());
            trailImageView.setVisibility(View.VISIBLE);
        } else {
            trailImageView.setVisibility(View.GONE);
        }

        // Find the TextView in the event_on_item_list.xml_list.xml layout with the ID miwok_text_view.
        TextView titleTextView = listItemView.findViewById(R.id.title_text_view);
        // Get the Miwok translation from the currentWord object and set this text on the Miwok TextView.
        titleTextView.setText(currentEvent.getEventTitle());

        // Find the TextView in the event_on_item_list.xml_list.xml layout with the ID miwok_text_view.
        TextView descriptionTextView = listItemView.findViewById(R.id.description_text_view);
        // Get the Miwok translation from the currentWord object and set this text on the Miwok TextView.
        descriptionTextView.setText(currentEvent.getEventDescription());

        return listItemView;
    }
}
