package me.canzhao.itemtrails;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        OnMapReadyCallback,
        GoogleMap.OnInfoWindowClickListener {

    private static final LatLng RUGBY_PARK = new LatLng(-37.781106, 175.270136);

    public GoogleMap mMap;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private GeofencingClient geofencingClient;

    private ArrayList<Geofence> geofenceList = new ArrayList<>();

    private PendingIntent geofencePendingIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_neighbourhood);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment map = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        map.getMapAsync(this);

        geofencingClient = LocationServices.getGeofencingClient(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Set up the map.
        mMap.setMinZoomPreference(15);
        //mMap.addMarker(new MarkerOptions().position(RUGBY_PARK)
                        //.title("Rugby Park, Hamilton")
                        //.snippet("Two items were at this place.")
                        //.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(RUGBY_PARK));

        // Set a listener for info window events.
        mMap.setOnInfoWindowClickListener(this);

        enableMyLocation();

        //startNewGeofence(new GeofenceInfo("aa",-37.781106,175.270136, 10));
        //GeofenceInfo gf = new GeofenceInfo("aa",-37.799315,175.328655, 100.0f);
        //startNewGeofence(gf);

        geofenceList.add(new Geofence.Builder()
                // Set the request ID of the geofence. This is a string to identify this
                // geofence.
                .setRequestId("0")

                .setCircularRegion(
                        -37.799315,
                        175.328655,
                        100
                )
                .setExpirationDuration(-1)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                        Geofence.GEOFENCE_TRANSITION_EXIT)
                .build());

        geofencingClient.addGeofences(getGeofencingRequest(), getGeofencePendingIntent())
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("kfkf","success");
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("kfkf","fail");
                    }
                });

        addAll();
    }

    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(geofenceList);
        return builder.build();
    }

    private PendingIntent getGeofencePendingIntent() {
        // Reuse the PendingIntent if we already have it.
        if (geofencePendingIntent != null) {
            return geofencePendingIntent;
        }
        Intent intent = new Intent(this, GeofenceIntentService.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when
        // calling addGeofences() and removeGeofences().
        geofencePendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.
                FLAG_UPDATE_CURRENT);
        return geofencePendingIntent;
    }


    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    mMap.setMyLocationEnabled(true);
                } else {
                    // permission denied.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        BottomSheetFragment itemSheetFragment = new BottomSheetFragment(pois.get((int)marker.getTag()).getPoiItem());
        itemSheetFragment.show(getSupportFragmentManager(), "Items");
    }

    public MarkerOptions addMC(LatLng center, float radiusMeters, int index){
        //draw the marker on the map
        MarkerOptions mo = new MarkerOptions().position(center);
        //draw the circle on the map
        mMap.addCircle(new CircleOptions()
                .center(center)
                .radius(radiusMeters)
                .strokeWidth(2)
                .strokeColor(R.color.colorPrimaryDark)
                .fillColor(R.color.colorPrimaryDark)
                .clickable(false));
        //create the geofence
        geofenceList.add(new Geofence.Builder()
                .setRequestId(index+"")
                .setCircularRegion(
                        center.latitude,
                        center.longitude,
                        radiusMeters
                )
                .setExpirationDuration(-1)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                .build());


        return mo;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String a = intent.getStringExtra("i");
        BottomSheetFragment itemSheetFragment = new BottomSheetFragment(pois.get( Integer.parseInt(a)).getPoiItem());
        itemSheetFragment.show(getSupportFragmentManager(), "Items");
    }



    public static ArrayList<Poi> loadPoiData() {

        /**
         * Courtesy constants needed for populating ItemEvent and PoiItem objects.
         */
        //final String COURTESY_WM = ", courtesy of the Waikato Museum Te Whare Taonga o Waikato.";
        final String COURTESY_HCL = ", courtesy of the Hamitlon City Libraries Te Ohomauri o Kirikiriroa.";

        /**
         * Other constants needed for populating PoiItem objects.
         */
        // Event name values
        final String EVENT_NAME_CREATION = "Birth";
        final String EVENT_NAME_PURCHASE = "As Commodity";
        final String EVENT_NAME_GIFT = "As Gift";
        final String EVENT_NAME_EXHIBITION = "As Exhibit";

        // Event description snippet values
        final String EVENT_DS_1 = "This is a short description of the event shown on the item screen.";
        final String EVENT_DS_2 = "This is a short description of the event shown on the item screen.";
        final String EVENT_DS_3 = "This is a short description of the event shown on the item screen.";
        final String EVENT_DS_4 = "This is a short description of the event shown on the item screen.";
        final String EVENT_DS_5 = "This is a short description of the event shown on the item screen.";
        final String EVENT_DS_6 = "This is a short description of the event shown on the item screen.";
        final String EVENT_DS_7 = "This is a short description of the event shown on the item screen.";
        final String EVENT_DS_8 = "This is a short description of the event shown on the item screen.";
        final String EVENT_DS_9 = "This is a short description of the event shown on the item screen.";
        final String EVENT_DS_10 = "This is a short description of the event shown on the item screen.";
        final String EVENT_DS_11 = "This is a short description of the event shown on the item screen.";
        final String EVENT_DS_12 = "This is a short description of the event shown on the item screen.";
        final String EVENT_DS_13 = "This is a short description of the event shown on the item screen.";
        final String EVENT_DS_14 = "This is a short description of the event shown on the item screen.";

        // Event image caption values
        final String EVENT_IC_1 = "Fans line up outside the Rugby Park in c. 1980 (Accession Number: HCL_M00647.26)";
        final String EVENT_IC_2 = "This is a caption of an event image (Accession Number: HCL_?)";
        final String EVENT_IC_3 = "This is a caption of an event image (Accession Number: HCL_?)";
        final String EVENT_IC_4 = "This is a caption of an event image (Accession Number: HCL_?)";
        final String EVENT_IC_5 = "This is a caption of an event image (Accession Number: HCL_?)";
        final String EVENT_IC_6 = "This is a caption of an event image (Accession Number: HCL_?)";
        final String EVENT_IC_7 = "This is a caption of an event image (Accession Number: HCL_?)";
        final String EVENT_IC_8 = "This is a caption of an event image (Accession Number: HCL_?)";
        final String EVENT_IC_9 = "This is a caption of an event image (Accession Number: HCL_?)";
        final String EVENT_IC_10 = "This is a caption of an event image (Accession Number: HCL_?)";
        final String EVENT_IC_11 = "This is a caption of an event image (Accession Number: HCL_?)";
        final String EVENT_IC_12 = "This is a caption of an event image (Accession Number: HCL_?)";
        final String EVENT_IC_13 = "This is a caption of an event image (Accession Number: HCL_?)";
        final String EVENT_IC_14 = "This is a caption of an event image (Accession Number: HCL_?)";

        // Event description values
        final String EVENT_DES_1 = "The ticket was created between 13 and 25 July 1981 at one of the ticket kiosks within the Rugby Park. A typo was made on the planned match date, i.e. 1980 instead of 1981.";
        final String EVENT_DES_2 = "This is a description of an event. 2";
        final String EVENT_DES_3 = "This is a description of an event. 3";
        final String EVENT_DES_4 = "This is a description of an event. 4";
        final String EVENT_DES_5 = "This is a description of an event. 5";
        final String EVENT_DES_6 = "This is a description of an event. 6";
        final String EVENT_DES_7 = "This is a description of an event. 7";
        final String EVENT_DES_8 = "This is a description of an event. 8";
        final String EVENT_DES_9 = "This is a description of an event. 9";
        final String EVENT_DES_10 = "This is a description of an event. 10";
        final String EVENT_DES_11 = "This is a description of an event. 11";
        final String EVENT_DES_12 = "This is a description of an event. 12";
        final String EVENT_DES_13 = "This is a description of an event. 13";
        final String EVENT_DES_14 = "This is a description of an event. 14";

        /**
         * Other constants needed for populating PoiItem objects.
         */
        // Item name values
        final String ITEM_NAME_1 = "Ticket, Waikato vrs South Africa";
        final String ITEM_NAME_2 = "Anti Springbok Tour Drawing";
        final String ITEM_NAME_3 = "Photograph, Springbok Tour Protest";
        final String ITEM_NAME_4 = "Vending Box for Waikato Times";

        // Item image caption values
        final String ITEM_IC_1 = "Ticket to South Africa vs. Waikato, Rugby Park, Hamilton (Accession Number: 1985/12/88)";
        final String ITEM_IC_2 = "This is a caption of an item image (Accession Number: ?)";
        final String ITEM_IC_3 = "This is a caption of an item image (Accession Number: ?)";
        final String ITEM_IC_4 = "This is a caption of an item image (Accession Number: ?)";

        /**
         * Constants needed for populating Poi objects.
         */
        final String POI_NAME_1 = "Rugby Park";
        final String POI_NAME_2 = "Garden Place";

        /**
         * Event initialisation
         */
        ItemEvent event1 = new ItemEvent(EVENT_NAME_CREATION, EVENT_DS_1, R.drawable.outside_rugby_park, EVENT_IC_1, EVENT_DES_1);
        ItemEvent event2 = new ItemEvent("", EVENT_DS_2, R.drawable.ladle, EVENT_IC_2, EVENT_DES_2);
        ItemEvent event3 = new ItemEvent("", EVENT_DS_3, R.drawable.ladle, EVENT_IC_3, EVENT_DES_3);
        ItemEvent event4 = new ItemEvent("", EVENT_DS_4, R.drawable.ladle, EVENT_IC_4, EVENT_DES_4);
        ItemEvent event5 = new ItemEvent("", EVENT_DS_5, R.drawable.ladle, EVENT_IC_5, EVENT_DES_5);
        ItemEvent event6 = new ItemEvent("", EVENT_DS_6, R.drawable.ladle, EVENT_IC_6, EVENT_DES_6);
        ItemEvent event7 = new ItemEvent("", EVENT_DS_7, R.drawable.ladle, EVENT_IC_7, EVENT_DES_7);
        ItemEvent event8 = new ItemEvent("", EVENT_DS_8, R.drawable.ladle, EVENT_IC_8, EVENT_DES_8);
        ItemEvent event9 = new ItemEvent("", EVENT_DS_9, R.drawable.ladle, EVENT_IC_9, EVENT_DES_9);
        ItemEvent event10 = new ItemEvent("", EVENT_DS_10, R.drawable.ladle, EVENT_IC_10, EVENT_DES_10);
        ItemEvent event11 = new ItemEvent("", EVENT_DS_11, R.drawable.ladle, EVENT_IC_11, EVENT_DES_11);
        ItemEvent event12 = new ItemEvent("", EVENT_DS_12, R.drawable.ladle, EVENT_IC_12, EVENT_DES_12);
        ItemEvent event13 = new ItemEvent("", EVENT_DS_13, R.drawable.ladle, EVENT_IC_13, EVENT_DES_13);
        ItemEvent event14 = new ItemEvent("", EVENT_DS_14, R.drawable.ladle, EVENT_IC_14, EVENT_DES_14);

        event1.setmRevelentEvent(event6);
        event6.setmRevelentEvent(event1);

        /**
         * Create event array list for Ticket, Waikato vrs South Africa
         */
        ArrayList<ItemEvent> ticketEvents = new ArrayList<>();
        ticketEvents.add(event1);
        ticketEvents.add(event2);
        ticketEvents.add(event3);
        ticketEvents.add(event4);
        ticketEvents.add(event5);

        /**
         * Create event array list for Anti Springbok Tour Drawing
         */
        ArrayList<ItemEvent> drawingEvents = new ArrayList<>();
        drawingEvents.add(event6);
        drawingEvents.add(event7);

        /**
         * Create event array list for Photograph, Springbok Tour Protest
         */
        ArrayList<ItemEvent> photoEvents = new ArrayList<>();
        photoEvents.add(event8);
        photoEvents.add(event9);
        photoEvents.add(event10);
        photoEvents.add(event11);

        /**
         * Create event array list for Vending Box for Waikato Times
         */
        ArrayList<ItemEvent> vendingBoxEvents = new ArrayList<>();
        vendingBoxEvents.add(event12);
        vendingBoxEvents.add(event13);
        vendingBoxEvents.add(event14);

        /**
         * PoiItem initialisation
         */
        PoiItem ticket = new PoiItem(ITEM_NAME_1, R.drawable.ticket, ITEM_IC_1, ticketEvents);
        PoiItem drawing = new PoiItem(ITEM_NAME_2, R.drawable.ladle, ITEM_IC_2, drawingEvents);
        PoiItem photo = new PoiItem(ITEM_NAME_3, R.drawable.ladle, ITEM_IC_3, photoEvents);
        PoiItem vendingBox = new PoiItem(ITEM_NAME_4, R.drawable.ladle, ITEM_IC_4, vendingBoxEvents);

        /**
         * Create item array list for Rugby Park
         */
        ArrayList<PoiItem> rugbyParkItems = new ArrayList<>();
        rugbyParkItems.add(ticket);

        /**
         * Create item array list for Garden Place
         */
        ArrayList<PoiItem> gardenPlaceItems = new ArrayList<>();
        gardenPlaceItems.add(drawing);
        gardenPlaceItems.add(photo);
        gardenPlaceItems.add(vendingBox);

        /**
         * LatLng initialisation
         */
        LatLng rugbyParkCoordinates = new LatLng(-37.781106, 175.270136);
        LatLng gardenPlaceCoordinates = new LatLng(-37.787872, 175.281901);

        /**
         * Poi initialisation
         */
        Poi rugbyPark = new Poi(rugbyParkCoordinates, POI_NAME_1, rugbyParkItems);
        Poi gardenPlace = new Poi(gardenPlaceCoordinates, POI_NAME_2, gardenPlaceItems);

        /**
         * Create poi array list
         */
        ArrayList<Poi> pois = new ArrayList<>();
        pois.add(rugbyPark);
        pois.add(gardenPlace);

        return pois;
    }

    public ArrayList<Poi> pois;
    public void addAll(){
        pois = loadPoiData();
        for(int i = 0;i<pois.size();i++){
            Poi p = pois.get(i);
            MarkerOptions mo = addMC(p.getPoiCoordinates(),100,i);
            mo.title(p.getPoiName());
            mo.snippet(new Integer(pois.get(i).getPoiItem().size()).toString()+" item(s) have been to this place.");
            mo.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
            mMap.addMarker(mo).setTag(i);
        }

    }


}



