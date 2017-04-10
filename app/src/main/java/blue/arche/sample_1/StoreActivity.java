package blue.arche.sample_1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import java.util.List;

public class StoreActivity extends FragmentActivity {

    private int NUM_PAGES;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private List<StoreItem> activeArrayList;
    private ModelsData mModelsData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_store);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        mModelsData = new ModelsData();
        mModelsData.populateItemsData(this);
        // setActionBar(toolbar);
        // getSupportActionBar().setTitle(getIntent().getExtras().getString("BRAND_NAME"));

        if (getIntent().getExtras().getString("BRAND_NAME").equals("Adidas")) {
            activeArrayList = mModelsData.getAdidasItem();
        } else if (getIntent().getExtras().getString("BRAND_NAME").equals("Nike")) {
            activeArrayList = mModelsData.getNikeItem();
        } else if (getIntent().getExtras().getString("BRAND_NAME").equals("North Face Jacket")) {
            activeArrayList = mModelsData.getNorthFaceItems();
        } else if (getIntent().getExtras().getString("BRAND_NAME").equals("UnderArmor")) {
            activeArrayList = mModelsData.getUnderArmorItems();
        } else if (getIntent().getExtras().getString("BRAND_NAME").equals("NikeCap")) {
            activeArrayList = mModelsData.getNikeCaps();
        }

        NUM_PAGES = activeArrayList.size();
        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);


    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }


    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new StoreProductFragment(activeArrayList.get(position));
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }


}
