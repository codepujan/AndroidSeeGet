package blue.arche.sample_1;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by pujanpaudel on 11/19/16.
 */

public class NearMe {
    private String storeName;
    private String brandRelated;
    private LatLng geoLocation;

    public NearMe(String storeName, String brandRelated, LatLng geoLocation) {
        this.storeName = storeName;
        this.brandRelated = brandRelated;
        this.geoLocation = geoLocation;
    }

    public String getStoreName() {

        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getBrandRelated() {
        return brandRelated;
    }

    public void setBrandRelated(String brandRelated) {
        this.brandRelated = brandRelated;
    }

    public LatLng getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(LatLng geoLocation) {
        this.geoLocation = geoLocation;
    }
}
