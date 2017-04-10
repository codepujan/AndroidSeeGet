package blue.arche.sample_1.Model;

import android.graphics.Bitmap;

/**
 * Created by pujanpaudel on 11/25/16.
 */

public class AlternateMatch {

    private Bitmap productthumbnail;
    private String productname;
    private String productprice;
    private float productRating;

    public Bitmap getProductthumbnail() {
        return productthumbnail;
    }

    public String getProductname() {
        return productname;
    }

    public String getProductprice() {
        return productprice;
    }

    public float getProductRating() {
        return productRating;
    }

    public AlternateMatch(Bitmap productthumbnail, String productname, String productprice, float productRating) {

        this.productthumbnail = productthumbnail;
        this.productname = productname;
        this.productprice = productprice;
        this.productRating = productRating;
    }
}
