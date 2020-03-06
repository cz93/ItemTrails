package me.canzhao.itemtrails;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class Poi {

    private LatLng mPoiCoordinates;
    private String mPoiName;
    private ArrayList<PoiItem> mPoiItem;

    public Poi(LatLng poiCoordinates, String poiName, ArrayList<PoiItem> poiItem) {

        mPoiCoordinates = poiCoordinates;
        mPoiName = poiName;
        mPoiItem = poiItem;

    }

    public LatLng getPoiCoordinates() { return mPoiCoordinates; };

    public String getPoiName() { return mPoiName; };

    public ArrayList<PoiItem> getPoiItem() { return mPoiItem; };

}
