package me.canzhao.itemtrails;

import java.io.Serializable;
import java.util.ArrayList;

public class PoiItem implements Serializable {

    private String mItemName;
    private int mItemImageId;
    private String mItemImageCap;
    private ArrayList<ItemEvent> mItemEvent;

    public PoiItem(String itemName, int itemImageId, String itemImageCap, ArrayList<ItemEvent> itemEvent) {
        mItemName = itemName;
        mItemImageId = itemImageId;
        mItemImageCap = itemImageCap;
        mItemEvent = itemEvent;
    }

    public String getItemName() { return mItemName; };

    public int getItemImageId() { return mItemImageId; };

    public String getItemImageCap() { return mItemImageCap; };

    public ArrayList<ItemEvent> getItemEvent() { return mItemEvent; };

}
