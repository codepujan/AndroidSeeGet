package blue.arche.sample_1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelsData {

    private static final String BRAND_NIKE = "Nike";
    private static final String BRAND_ADDIDAS = "Addidas";
    private static final String BRAND_UNDER_ARMS = "Under Arms";
    private static final String BRAND_AMERICAN_EAGLES = "American Eagles";
    private static final String BRAND_COACH_AMERICA = "Coach America";
    private static final String BRAND_LEVIS = "Levis";
    private static final String BRAND_GAP = "Gap";
    private static final String BRAND_JORDAN = "Jordan";
    private static final String BRAND_MICHAEL_KORS = "Michael Kors";
    private static final String BRAND_THE_NORTH_FACE = "The North Face";

    private List<String> mPartners;
    private List<StoreItem> mAddidasItem;
    private List<StoreItem> mNikeItem;
    private List<StoreItem> mThenorthfaceItem;
    private List<StoreItem> mUnderarmourItem;
    private List<StoreItem> mNikeCaps;
    private List<StoreItem> mStoreItems = new ArrayList<>();
    private Map<String, String> mBrand_mapping = new HashMap<>();

    public Map<String, String> getBrandMapping() {
        return mBrand_mapping;
    }

    public static Map<String, Bitmap> getBrandLogoMapping() {
        return brand_logo_mapping;
    }

    public static Map<String, Bitmap> brand_logo_mapping = new HashMap<>();

    // These are just for Static Modeling of Data and nothing more
    public void setBrandMapping(Context ctx) {
        mBrand_mapping.put("seeget_nike", BRAND_NIKE);
        mBrand_mapping.put("seeget_michaelkors", BRAND_MICHAEL_KORS);
        mBrand_mapping.put("seeget_levis", BRAND_LEVIS);
        mBrand_mapping.put("seeget_coach", BRAND_COACH_AMERICA);
        mBrand_mapping.put("seeget_underarms", BRAND_UNDER_ARMS);
        mBrand_mapping.put("seeget_americaneagles", BRAND_AMERICAN_EAGLES);
        mBrand_mapping.put("seeget_adidas", BRAND_ADDIDAS);
        mBrand_mapping.put("seeget_jordan", BRAND_JORDAN);
        mBrand_mapping.put("seeget_gap", BRAND_GAP);
        mBrand_mapping.put("seeget_thenorthface", BRAND_THE_NORTH_FACE);


        //Set Brand Logo Mapping would be mandatory
        //setBrandLogoMapping(ctx);
    }

    public static void setBrandLogoMapping(Context context) {
        brand_logo_mapping.put(BRAND_NIKE, BitmapFactory.decodeResource(context.getResources(), R.drawable.brand_nike));
        brand_logo_mapping.put(BRAND_ADDIDAS, BitmapFactory.decodeResource(context.getResources(), R.drawable.brand_addidas));
        brand_logo_mapping.put(BRAND_THE_NORTH_FACE, BitmapFactory.decodeResource(context.getResources(), R.drawable.brand_north_face));
    }

    public List<StoreItem> getAdidasItem() {
        return mAddidasItem;
    }

    public List<StoreItem> getNikeItem() {
        return mNikeItem;
    }

    public List<StoreItem> getNorthFaceItems() {
        return mThenorthfaceItem;
    }


    public List<StoreItem> getUnderArmorItems() {
        return mUnderarmourItem;
    }

    public List<StoreItem> getNikeCaps() {
        return mNikeCaps;
    }

    public List<StoreItem> getAllItems() {
        return mStoreItems;
    }

    public void populateItemsData(Context context) {
        // TODO: Migrate this work to the backend
        // ADDIDAS FIRST
        // behold some moment for the hat thingy
        // mStoreItems.add(new StoreItem("Sun Protecting Hat",15.0f,"Best from the Nike Store",
        // R.drawable.nikehat,null,0,BRAND_NIKE));


        mStoreItems.add(new StoreItem("White Woolen Shirt", 28.50f, "White Shirts favoured by Sexy men above everything",
                R.drawable.addidas_2,
                null, 1, BRAND_ADDIDAS));

        mStoreItems.add(new StoreItem("Sherwaani Shirts", 28.50f, "White Shirts favoured by Sexy men above everything",
                R.drawable.addidas_3,
                null, 1, BRAND_ADDIDAS));

        mStoreItems.add(new StoreItem("Husky Musky", 28.50f, "White Shirts favoured by Sexy men above everything",
                R.drawable.addidas_3,
                null, 1, BRAND_ADDIDAS));
        mStoreItems.add(new StoreItem("Ado Fado", 28.50f, "White Shirts favoured by Sexy men above everything",
                R.drawable.addidas_4,
                null, 1, BRAND_ADDIDAS));
        mStoreItems.add(new StoreItem("Adidas Grand", 28.50f, "White Shirts favoured by Sexy men above everything",
                R.drawable.addidas_5,
                null, 1, BRAND_ADDIDAS));
        mStoreItems.add(new StoreItem("Summer Special ", 28.50f, "White Shirts favoured by Sexy men above everything",
                R.drawable.addidas_6,
                null, 1, BRAND_ADDIDAS));

        //NIKE THEN

        mStoreItems.add(new StoreItem("White Cotton Cuisine", 39.0f, "White Cuisines Used by The Royal Families",
                R.drawable.nike_1,
                null, 1, BRAND_NIKE));

        mStoreItems.add(new StoreItem("Scary Black Customee", 24.0f, "Scary as hell : Especially in Halloween",
                R.drawable.nike_2,
                null, 1, BRAND_LEVIS));

        mStoreItems.add(new StoreItem("Royal Grey Leather", 50.0f, "Grey Cuisine from the Royal Family",
                R.drawable.nike_4,
                null, 1, BRAND_NIKE));

        mStoreItems.add(new StoreItem("Red Riding Hood", 45.0f, "The Hood Little Red got away with",
                R.drawable.nike_6,
                null, 1, BRAND_NIKE));


        mStoreItems.add(new StoreItem("Navy Blue Mountain", 60.0f, "Suited for The Mountain Everest",
                R.drawable.north_face_3,
                null, 1, BRAND_THE_NORTH_FACE));

        mStoreItems.add(new StoreItem("Black Jacket", 80.0f, "Black Leather Warmth",
                R.drawable.north_face_4,
                null, 1, BRAND_THE_NORTH_FACE));


        //3 items for the underarmor stuff
        mStoreItems.add(new StoreItem("Red Sweatshirt", 40.0f, "Suited for The Hot Guys in Summer",
                R.drawable.under_arms_1, null, 1, BRAND_UNDER_ARMS));

        mStoreItems.add(new StoreItem("Black Sweatshirt", 35.0f, "Black thing which absorbs every sun Out There",
                R.drawable.under_arms_2, null, 1, BRAND_UNDER_ARMS));


        mStoreItems.add(new StoreItem("American Special ", 35.0f, "Black thing which absorbs every sun Out There",
                R.drawable.americaneagles_1, null, 1, BRAND_AMERICAN_EAGLES));

        mStoreItems.add(new StoreItem("Life Jacket ", 35.0f, "Black thing which absorbs every sun Out There",
                R.drawable.americaneagles_2, null, 1, BRAND_AMERICAN_EAGLES));

        mStoreItems.add(new StoreItem(" Sweat Remover", 35.0f, "Black thing which absorbs every sun Out There",
                R.drawable.americaneagles_3, null, 1, BRAND_AMERICAN_EAGLES));

        mStoreItems.add(new StoreItem("Levis Roadie ", 35.0f, "Black thing which absorbs every sun Out There",
                R.drawable.levis_1, null, 1, BRAND_LEVIS));

        mStoreItems.add(new StoreItem("Levis  Stoppie ", 35.0f, "Black thing which absorbs every sun Out There",
                R.drawable.levis_3, null, 1, BRAND_LEVIS));


        mStoreItems.add(new StoreItem("Sumemr Gumemr", 35.0f, "Black thing which absorbs every sun Out There",
                R.drawable.gap_1, null, 1, BRAND_GAP));

        mStoreItems.add(new StoreItem(" Special Shirt", 35.0f, "Black thing which absorbs every sun Out There",
                R.drawable.gap_2, null, 1, BRAND_GAP));

        mStoreItems.add(new StoreItem("Black Hoodie ", 35.0f, "Black thing which absorbs every sun Out There",
                R.drawable.levis_1, null, 1, BRAND_LEVIS));


        mStoreItems.add(new StoreItem("Dunk Shirt ", 35.0f, "Your asset for Basketball Season",
                R.drawable.jordan_1, null, 1, BRAND_JORDAN));

        mStoreItems.add(new StoreItem("3 Ranger  ", 35.0f, "Best Gear for Supporting your team ",
                R.drawable.jordan_2, null, 1, BRAND_JORDAN));
    }

    public List<StoreItem> getSortedItems(final String sortingBrand) {
        List<StoreItem> filteredList = new ArrayList<>();

        for (StoreItem item : mStoreItems) {
            if (item.brandAssociated.equals(sortingBrand)) {
                filteredList.add(item);
            }
        }

        return filteredList;
    }
}
