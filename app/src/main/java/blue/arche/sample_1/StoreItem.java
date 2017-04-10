package blue.arche.sample_1;

import android.graphics.Bitmap;

/**
 * Created by pujanpaudel on 11/13/16.
 */

public class StoreItem {

    public String name;
    public int productImage;
    public Bitmap tanvasImage;

    public int metadataLocation; // 0 for Top : 1 for middle : 2 for Bottom

    public String brandAssociated;

    public StoreItem(String name, float price, String description,int productImage,Bitmap tanvasImage,int locatiion,String branded) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.productImage=productImage;
        this.tanvasImage=tanvasImage;
        this.metadataLocation=locatiion;
        this.brandAssociated=branded;
    }

    public float price;
    public String description;
    //Camera Button
}
