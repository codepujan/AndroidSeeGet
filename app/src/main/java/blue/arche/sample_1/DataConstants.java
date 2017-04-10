package blue.arche.sample_1;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pujanpaudel on 11/19/16.
 */

public class DataConstants {

    private static  List<NearMe> dummyNearMeObjects=new ArrayList<NearMe>();

    private static List<NearMe>storeItems=new ArrayList<>();


    public  static List<NearMe>getDummyNearMeObjects(){

        dummyNearMeObjects.add(new NearMe("Highway Adidas Store","Adidas",new LatLng(31.322659, -89.373377)));
        dummyNearMeObjects.add(new NearMe("Mama-Bhanda Store","Adidas",new LatLng(31.324555, -89.329162)));
        dummyNearMeObjects.add(new NearMe("Hitaiko Jutta Pasal","Adidas",new LatLng(31.334546, -89.336199)));

        return  dummyNearMeObjects;
    }

    public  void setDummyStoreItems(){



    }
}
