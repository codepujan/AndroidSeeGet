package blue.arche.sample_1;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by pujanpaudel on 11/13/16.
 */

public class StaticHolder {
    public static  StoreItem store;

    public  static Bitmap screenShotBitmap;

    public  static void setScreenShotBitmap(Bitmap source){
        screenShotBitmap=source;
    }


    public  static Bitmap getScreenShotBitmap(){
        return screenShotBitmap;
    }
    public static void setCurrentStore(StoreItem currentStore){
        store=currentStore;
    }

    public static List<StoreItem> currentBrandItems;


    public  static StoreItem getCurrentStore(){
        return store;
    }


    public static   void setCurrentBrandItems(List<StoreItem>list){
        currentBrandItems=list;
    }


    public static List<StoreItem> getCurrentBrandItems(){
        return currentBrandItems;
    }
}
