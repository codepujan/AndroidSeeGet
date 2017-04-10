package blue.arche.sample_1;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;
import java.util.List;

public class NearMeActivity extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private List<Marker> markers;
    private ClusterManager<MyItem> mClusterManager;
    private int highestZIndexMarker=2;

    private View markerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_me);
        markers=new ArrayList<Marker>();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        markerView = ((LayoutInflater)this
                .getSystemService(
                        this.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.custom_marker, null);
        mapFragment.getMapAsync(this);



    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;




        mMap.setOnMarkerClickListener(this);



        setupMarkerLocations();
        setBounds();

        //setUpClusterer();



    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        Log.i("CLick alert","Marker Clicked Brahhh!!!");
marker.setZIndex(highestZIndexMarker++);
        return true;
    }


    private void setupMarkerLocations(){

    List<NearMe>locationDatas=DataConstants.getDummyNearMeObjects();


        for(int i=0;i<locationDatas.size();i++){

            LatLng location=locationDatas.get(i).getGeoLocation();
            String storeName=locationDatas.get(i).getStoreName();
            String brandRelated=locationDatas.get(i).getBrandRelated();

            // Instantiate an ImageView and extract   textviews out of it
            View markerView = ((LayoutInflater)this
                    .getSystemService(
                            this.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.custom_marker, null);
            TextView name=(TextView)markerView.findViewById(R.id.item_name);
            name.setText(storeName);

            markers.add(mMap.addMarker((new MarkerOptions()
                    .position(location)
                    .icon(BitmapDescriptorFactory
                            .fromBitmap(createDrawableFromView(
                                    this,
                                    markerView))).zIndex(1))));




        }

    }

    public static Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay()
                .getMetrics(displayMetrics);
        view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels,
                displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(),
                view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }




    public class MyItem implements ClusterItem {
        private final LatLng mPosition;

        public MyItem(double lat, double lng) {
            mPosition = new LatLng(lat, lng);
        }

        @Override
        public LatLng getPosition() {
            return mPosition;
        }
    }


    private void setBounds(){
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : markers) {
            builder.include(marker.getPosition());
        }
        LatLngBounds bounds = builder.build();

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(secondLocation));

        mMap.setMinZoomPreference(5);
        mMap.setMaxZoomPreference(40);



        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.12); // offset from edges of the map 12% of screen
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds,width,height,padding));
    }
    private void setUpClusterer() {
        // Declare a variable for the cluster manager.

        // Position the map.
        setBounds();





        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = new ClusterManager<MyItem>(this,mMap);

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);

        // Add cluster items (markers) to the cluster manager.
        addItems();
    }

    private void addItems() {
        for(Marker marker:markers){
            MyItem markerItem=new MyItem(marker.getPosition().latitude,marker.getPosition().longitude);
            mClusterManager.addItem(markerItem);
        }
    }
}
