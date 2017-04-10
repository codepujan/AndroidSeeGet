package blue.arche.sample_1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * Created by pujanpaudel on 11/13/16.
 */

public  class ModelsData {

    private static List<String>partners;

    private static List<StoreItem>addidasItem;
    private static List<StoreItem>nikeItem;
    private   static List<StoreItem>thenorthfaceItem;
    private static List<StoreItem>underarmourItem;
    private static List<StoreItem>nikeCaps;



    private static List<StoreItem>storeItems=new ArrayList<StoreItem>();


    private static Map<String,String> brand_mapping=new HashMap<String,String>();



    public static Map<String, String> getBrandMapping(){
        return brand_mapping;
    }

    public static Map<String,Bitmap>getBrandLogoMapping(){return brand_logo_mapping;}

    public  static Map<String,Bitmap>brand_logo_mapping=new HashMap<String,Bitmap>();
    public static  void setBrandMapping(Context ctx){
        // These are just for Static Modeling of Data and nothing more

        brand_mapping.put("seeget_nike",Constants.BRAND_NIKE);
        brand_mapping.put("seeget_michaelkors",Constants.BRAND_MICHAEL_KORS);
        brand_mapping.put("seeget_levis",Constants.BRAND_LEVIS);
        brand_mapping.put("seeget_coach",Constants.BRAND_COACH_AMERICA);
        brand_mapping.put("seeget_underarms",Constants.BRAND_UNDER_ARMS);
        brand_mapping.put("seeget_americaneagles",Constants.BRAND_AMERICAN_EAGLES);
        brand_mapping.put("seeget_adidas",Constants.BRAND_ADDIDAS);
        brand_mapping.put("seeget_jordan",Constants.BRAND_JORDAN);
        brand_mapping.put("seeget_gap",Constants.BRAND_GAP);
        brand_mapping.put("seeget_thenorthface",Constants.BRAND_THE_NORTH_FACE);


        //Set Brand Logo Mapping would be mandatory
        //setBrandLogoMapping(ctx);
    }




    public  static void setBrandLogoMapping(Context ctx){
        brand_logo_mapping.put(Constants.BRAND_NIKE,BitmapFactory.decodeResource(ctx.getResources(),R.drawable.brand_nike));
        brand_logo_mapping.put(Constants.BRAND_ADDIDAS,BitmapFactory.decodeResource(ctx.getResources(),R.drawable.brand_addidas));
        brand_logo_mapping.put(Constants.BRAND_THE_NORTH_FACE,BitmapFactory.decodeResource(ctx.getResources(),R.drawable.brand_north_face));


    }

    public  static List<StoreItem>getAdidasItem(){

        return addidasItem;

    }


    public static List<StoreItem>getNikeItem(){
        return nikeItem;
    }

    public  static List<StoreItem>getNorthFaceItems(){
        return thenorthfaceItem;
    }


    public static List<StoreItem>getUnderArmorItems(){
        return underarmourItem;
    }

    public static List<StoreItem>getNikeCaps(){return nikeCaps;}


    public  static List<StoreItem>getAllItems(){
        return storeItems;
    }

    public  static void populateItemsData(Context ctx){

        //let's fill in the items one by one

        //ADDIDAS FIRST

        //behold some moment for the hat thingy
      //  storeItems.add(new StoreItem("Sun Protecting Hat",15.0f,"Best from the Nike Store",
             //   R.drawable.nikehat,null,0,Constants.BRAND_NIKE));


        storeItems.add(new StoreItem("White Woolen Shirt",28.50f,"White Shirts favoured by Sexy men above everything",
                R.drawable.addidas_2,
                null,1,Constants.BRAND_ADDIDAS));

        storeItems.add(new StoreItem("Sherwaani Shirts",28.50f,"White Shirts favoured by Sexy men above everything",
                R.drawable.addidas_3,
                null,1,Constants.BRAND_ADDIDAS));

        storeItems.add(new StoreItem("Husky Musky",28.50f,"White Shirts favoured by Sexy men above everything",
                R.drawable.addidas_3,
                null,1,Constants.BRAND_ADDIDAS));
        storeItems.add(new StoreItem("Ado Fado",28.50f,"White Shirts favoured by Sexy men above everything",
                R.drawable.addidas_4,
                null,1,Constants.BRAND_ADDIDAS));
        storeItems.add(new StoreItem("Adidas Grand",28.50f,"White Shirts favoured by Sexy men above everything",
                R.drawable.addidas_5,
                null,1,Constants.BRAND_ADDIDAS));
        storeItems.add(new StoreItem("Summer Special ",28.50f,"White Shirts favoured by Sexy men above everything",
                R.drawable.addidas_6,
                null,1,Constants.BRAND_ADDIDAS));

            //NIKE THEN

        storeItems.add(new StoreItem("White Cotton Cuisine",39.0f,"White Cuisines Used by The Royal Families",
                R.drawable.nike_1,
                null,1,Constants.BRAND_NIKE));

        storeItems.add(new StoreItem("Scary Black Customee",24.0f,"Scary as hell : Especially in Halloween",
                R.drawable.nike_2,
                null,1,Constants.BRAND_LEVIS));

        storeItems.add(new StoreItem("Royal Grey Leather",50.0f,"Grey Cuisine from the Royal Family",
               R.drawable.nike_4,
            null,1,Constants.BRAND_NIKE));

        storeItems.add(new StoreItem("Red Riding Hood",45.0f,"The Hood Little Red got away with",
               R.drawable.nike_6,
               null,1,Constants.BRAND_NIKE));


        storeItems.add(new StoreItem("Navy Blue Mountain",60.0f,"Suited for The Mountain Everest",
                R.drawable.north_face_3,
                null,1,Constants.BRAND_THE_NORTH_FACE));

        storeItems.add(new StoreItem("Black Jacket",80.0f,"Black Leather Warmth",
               R.drawable.north_face_4,
                null,1,Constants.BRAND_THE_NORTH_FACE));


        //3 items for the underarmor stuff
        storeItems.add(new StoreItem("Red Sweatshirt",40.0f,"Suited for The Hot Guys in Summer",
               R.drawable.under_arms_1,null,1,Constants.BRAND_UNDER_ARMS));

        storeItems.add(new StoreItem("Black Sweatshirt",35.0f,"Black thing which absorbs every sun Out There",
               R.drawable.under_arms_2,null,1,Constants.BRAND_UNDER_ARMS));


        storeItems.add(new StoreItem("American Special ",35.0f,"Black thing which absorbs every sun Out There",
                R.drawable.americaneagles_1,null,1,Constants.BRAND_AMERICAN_EAGLES));

        storeItems.add(new StoreItem("Life Jacket ",35.0f,"Black thing which absorbs every sun Out There",
                R.drawable.americaneagles_2,null,1,Constants.BRAND_AMERICAN_EAGLES));

        storeItems.add(new StoreItem(" Sweat Remover",35.0f,"Black thing which absorbs every sun Out There",
                R.drawable.americaneagles_3,null,1,Constants.BRAND_AMERICAN_EAGLES));

        storeItems.add(new StoreItem("Levis Roadie ",35.0f,"Black thing which absorbs every sun Out There",
                R.drawable.levis_1,null,1,Constants.BRAND_LEVIS));

        storeItems.add(new StoreItem("Levis  Stoppie ",35.0f,"Black thing which absorbs every sun Out There",
                R.drawable.levis_3,null,1,Constants.BRAND_LEVIS));



        storeItems.add(new StoreItem("Sumemr Gumemr",35.0f,"Black thing which absorbs every sun Out There",
                R.drawable.gap_1,null,1,Constants.BRAND_GAP));

        storeItems.add(new StoreItem(" Special Shirt",35.0f,"Black thing which absorbs every sun Out There",
                R.drawable.gap_2,null,1,Constants.BRAND_GAP));

        storeItems.add(new StoreItem("Black Hoodie ",35.0f,"Black thing which absorbs every sun Out There",
                R.drawable.levis_1,null,1,Constants.BRAND_LEVIS));


        storeItems.add(new StoreItem("Dunk Shirt ",35.0f,"Your asset for Basketball Season",
                R.drawable.jordan_1,null,1,Constants.BRAND_JORDAN));

        storeItems.add(new StoreItem("3 Ranger  ",35.0f,"Best Gear for Supporting your team ",
                R.drawable.jordan_2,null,1,Constants.BRAND_JORDAN));
    }



    public static  List<StoreItem> getSortedItems(final String sortingBrand) {


        List<StoreItem>filteredList=new ArrayList<StoreItem>();


        for(ListIterator<StoreItem> iter=storeItems.listIterator();iter.hasNext();){

            StoreItem next=iter.next();
            if(next.brandAssociated.equals(sortingBrand)){
                filteredList.add(next);

            }
        }

        return filteredList;
    }





}
