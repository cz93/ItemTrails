package me.canzhao.itemtrails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link ItemEvent} represents a heritage item with its known event information
 * that user wants to know more about.
 */
public class ItemEvent implements Serializable {

    private String mEventName;
    private String mEventDesSnippet;
    private int mEventImageId;
    private String mEventImageCap;
    private String mEventDes;

    private ItemEvent mRevelentEvent;

    private static final int NO_IMAGE = -1;

    public ItemEvent(String eventName, String eventDesSnippet, int eventImageId,
                     String eventImageCap, String eventDes) {

        mEventName = eventName;
        mEventDesSnippet = eventDesSnippet;
        mEventImageId = eventImageId;
        mEventImageCap = eventImageCap;
        mEventDes = eventDes;

    }

    public String getEventName() { return mEventName; };

    public String getEventDesSnippet() { return mEventDesSnippet; };

    public int getEventImageId() { return mEventImageId; };

    public String getEventImageCap() { return mEventImageCap; };

    public String getEventDes() { return mEventDes; };

    public ItemEvent getmRevelentEvent() {
        return mRevelentEvent;
    }

    public void setmRevelentEvent(ItemEvent mRevelentEvent) {
        this.mRevelentEvent = mRevelentEvent;
    }

}
