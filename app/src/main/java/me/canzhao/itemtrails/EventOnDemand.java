package me.canzhao.itemtrails;

public class EventOnDemand {
    private int mTrailImageId;
    private String mEventName;
    private String mEventDesSnippet;

    public EventOnDemand(int TrailImageId, String eventName, String eventDesSnippet) {

        mTrailImageId = TrailImageId;
        mEventName = eventName;
        mEventDesSnippet = eventDesSnippet;

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
    public String getEventName() {
        return mEventName;
    }

    /**
     * Get the default translation of the word.
     * @return
     */
    public String getEventDesSnippet() {
        return mEventDesSnippet;
    }

}
