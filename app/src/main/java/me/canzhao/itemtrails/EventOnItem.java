package me.canzhao.itemtrails;

/**
 * {@link EventOnItem} represents a heritage item with its known event information
 * that the user wants to know more about.
 * Each of its events contains a title and a short description.
 */

public class EventOnItem {

    private static final int NO_IMAGE = -1;
    private int mTrailImageId;
    private String mEventTitle;
    private String mEventDescription;

    public EventOnItem(int TrailImageId, String eventTitle, String eventDescription) {

        mTrailImageId = TrailImageId;
        mEventTitle = eventTitle;
        mEventDescription = eventDescription;

    }

    /**
     * Get the ID of the image resource.
     * @return
     */
    public int getTrailImageId() { return mTrailImageId; }

    /**
     * Get the Miwok translation of the word.
     * @return
     */
    public String getEventTitle() {
        return mEventTitle;
    }

    /**
     * Get the default translation of the word.
     * @return
     */
    public String getEventDescription() {
        return mEventDescription;
    }

    /**
     *
     */
    public boolean hasImage() {
        return mTrailImageId != NO_IMAGE;
    }

}
