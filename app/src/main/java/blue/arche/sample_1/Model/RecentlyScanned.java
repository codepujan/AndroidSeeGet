package blue.arche.sample_1.Model;

import android.graphics.Bitmap;

/**
 * Created by pujanpaudel on 11/25/16.
 */

public class RecentlyScanned {
    private Bitmap recentlyScannedBitmap;

    public RecentlyScanned(Bitmap recentlyScannedBitmap, String recentlyScannedProductTitle) {
        this.recentlyScannedBitmap = recentlyScannedBitmap;
        this.recentlyScannedProductTitle = recentlyScannedProductTitle;
    }

    private String recentlyScannedProductTitle;

    public String getRecentlyScannedProductTitle() {
        return recentlyScannedProductTitle;
    }

    public Bitmap getRecentlyScannedBitmap() {

        return recentlyScannedBitmap;
    }
}
