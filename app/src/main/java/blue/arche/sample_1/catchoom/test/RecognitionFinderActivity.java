// com.craftar.craftarexamplesir is free software. You may use it under the MIT license, which is copied
// below and available at http://opensource.org/licenses/MIT
//
// Copyright (c) 2014 Catchoom Technologies S.L.
//
// Permission is hereby granted, free of charge, to any person obtaining a copy of
// this software and associated documentation files (the "Software"), to deal in
// the Software without restriction, including without limitation the rights to use,
// copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the
// Software, and to permit persons to whom the Software is furnished to do so, subject to
// the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
// INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
// PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
// FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
// OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
// DEALINGS IN THE SOFTWARE.

package blue.arche.sample_1.catchoom.test;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.craftar.CraftARActivity;
import com.craftar.CraftARError;
import com.craftar.CraftAROnDeviceIR;
import com.craftar.CraftARResult;
import com.craftar.CraftARSDK;
import com.craftar.CraftARSearchResponseHandler;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import blue.arche.sample_1.Adapters.AlternateMatchesAdapter;
import blue.arche.sample_1.Adapters.RecentlyScannedAdapter;
import blue.arche.sample_1.Model.AlternateMatch;
import blue.arche.sample_1.ModelsData;
import blue.arche.sample_1.NearMeActivity;
import blue.arche.sample_1.R;
import blue.arche.sample_1.StaticHolder;
import blue.arche.sample_1.StoreItem;
import blue.arche.sample_1.SurfaceViewActivity;

public class RecognitionFinderActivity extends CraftARActivity implements CraftARSearchResponseHandler {

    private final static String TAG = "RecognitionFinderActivity";

    private final static long FINDER_SESSION_TIME_MILLIS = 10000;

    private CraftAROnDeviceIR mOnDeviceIR;
    private CraftARSDK mCraftARSDK;
    long startFinderTimeMillis;
    boolean mIsActivityRunning = false;

    private List<StoreItem> activeToBeItems = new ArrayList<>();

    private View itemFoundView;

    private RecyclerView alternateList;
    private AlternateMatchesAdapter alternateMatchesAdapter;

    private RecyclerView recentlyScanned;
    private RecentlyScannedAdapter recentlyScannedAdapter;

    private Button tryOutButton;

    private Camera flashCamera;
    Camera.Parameters params;

    private boolean isFlashOn;
    private boolean hasFlash;
    private List<StoreItem> recentlyScannedList = new ArrayList<>();

    private ModelsData mModelsData;

    /**
     * Snippet to add when the user needs to add something to the recently Scanned entry
     * <p>
     * recentlyScannedAdapter=new RecentlyScannedAdapter(null);
     * <p>
     * recentlyScanned.setAdapter(recentlyScannedAdapter);
     *
     * @param savedInstanceState
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognition_finder);

        mModelsData = new ModelsData();
    }

    @Override
    public void onPostCreate() {
        //Obtain an instance of the CraftARSDK (which manages the camera interaction).
        //Note we already called CraftARSDK.init() in the Splash Screen, so we don't have to do it again
        mCraftARSDK = CraftARSDK.Instance();
        mCraftARSDK.startCapture(this);


        mCraftARSDK.getCamera().setAutoFocusOnTouch(true);

        //Get the instance to the OnDeviceIR singleton (it has already been initialized in the SplashScreenActivity, and the collectoins are already loaded).
        mOnDeviceIR = CraftAROnDeviceIR.Instance();

        //Tell the SDK that the OnDeviceIR who manage the calls to singleShotSearch() and startFinding().
        //In this case, as we are using on-device-image-recognition, we will tell the SDK that the OnDeviceIR singleton will manage this calls.
        mCraftARSDK.setSearchController(mOnDeviceIR.getSearchController());

        //Tell the SDK that we want to receive the search responses in this class.
        mOnDeviceIR.setCraftARSearchResponseHandler(this);

        startFinding();


        View utilityView = findViewById(R.id.home_page_surface_utilities);

        //Setting up the Recent Matches Recently Scanned


        recentlyScanned = (RecyclerView) utilityView.findViewById(R.id.recently_scanned);


        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(RecognitionFinderActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recentlyScanned.setLayoutManager(horizontalLayoutManagaer);


        ImageButton turnFlashLightson = (ImageButton) utilityView.findViewById(R.id.open_flash);
        ImageButton flipCamera = (ImageButton) utilityView.findViewById(R.id.switch_camera);


        //Flash light part

        hasFlash = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (!hasFlash) {

            AlertDialog alert = new AlertDialog.Builder(RecognitionFinderActivity.this).create();
            alert.setTitle("Error");
            alert.setMessage("Sorry, your device doesn't support flash light!");
            alert.setButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alert.show();
            return;
        }


        turnFlashLightson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        flipCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 *  Flipping the CAmera isn't probably required for now
                 */
            }
        });


        tryOutButton = (Button) utilityView.findViewById(R.id.try_out_button);

        tryOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // do everything you'd do in a onStop First
                mCraftARSDK.stopFinder();
                mCraftARSDK.getCamera().stopCapture();
                //Set the Intent Thing
                Intent tryItOutIntent = new Intent(RecognitionFinderActivity.this, SurfaceViewActivity.class);
                startActivity(tryItOutIntent);

            }
        });


        SetUpIdentifiedProductWindow();

    }


    private void turnOnFlash() {

        if (!isFlashOn) {
            if (flashCamera == null || params == null) {
                Log.i("Flash Alert", "Flash Problem");
                return;
            }

            params = flashCamera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            flashCamera.setParameters(params);
            flashCamera.startPreview();
            isFlashOn = true;
        }

    }

    private void turnOffFlash() {

        if (isFlashOn) {
            if (flashCamera == null || params == null) {
                return;
            }

            params = flashCamera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            flashCamera.setParameters(params);
            flashCamera.stopPreview();
            isFlashOn = false;
        }
    }


    @Override
    public void onCameraOpenFailed() {
        Toast.makeText(getApplicationContext(), "Camera error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPreviewStarted(int width, int height) {

    }

    @Override
    public void searchResults(ArrayList<CraftARResult> results,
                              long searchTimeMillis, int requestCode) {
        //Callback with the search results

        if (results.size() > 0) {
            //We found something! Show the results
            stopFinding();
            showResultDialog(results);
        } else {
            long ellapsedTime = System.currentTimeMillis() - startFinderTimeMillis;
            if (ellapsedTime > FINDER_SESSION_TIME_MILLIS) {
                stopFinding();
                //No object were found during this session
                showNoObjectsDialog();
            }
        }
    }

    private void showNoObjectsDialog() {
        if (!mIsActivityRunning) {
            return;
        }

        //Toast.makeText(this, "Scan Properly without shaking", Toast.LENGTH_SHORT).show();
    }

    private void showResultDialog(ArrayList<CraftARResult> results) {
        if (!mIsActivityRunning) {
            return;
        }

        String resultsText = "";
        for (CraftARResult result : results) {
            String itemName = result.getItem().getItemName();
            resultsText += itemName + "\n";
        }
        resultsText = resultsText.substring(0, resultsText.length() - 1); //Eliminate the last \n

        ShowIdentifiedProduct(mModelsData.getBrandMapping().get(resultsText));

        //activeToBeItems=ModelsData.getSortedItems(ModelsData.getBrandMapping().get(resultsText));


    }


    private void startFinding() {
        Log.i("CRAFTARSDK", "Finding Task Started");
        mCraftARSDK.startFinder();
        startFinderTimeMillis = System.currentTimeMillis();

    }

    private void stopFinding() {
        Log.i("CRAFTARSDK", "Finding Task Stopped");
        mCraftARSDK.stopFinder();
    }


    @Override
    public void searchFailed(CraftARError error, int requestCode) {
        //Log.e(TAG, "Search failed("+error.getErrorCode()+"):"+error.getErrorMessage());
    }

    @Override
    protected void onStop() {
        super.onStop();
        mIsActivityRunning = false;

        // on stop release the camera
        /**if (flashCamera != null) {
         flashCamera.release();
         flashCamera = null;
         }
         **/
    }

    @Override
    public void onPause() {
        super.onPause();
        stopFinding();
        //	turnOffFlash();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mIsActivityRunning = true;
        //	getCamera();

    }


    /**
     * The Utilities Method for Showing the Identifed Product
     */

    private void SetUpIdentifiedProductWindow() {


        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();  // deprecated
        int height = display.getHeight();  // deprecated

        LayoutInflater controlInflater = LayoutInflater.from(getBaseContext());
        View view = controlInflater.inflate(R.layout.empty_container_view, null);
        LinearLayout.LayoutParams layoutParamsControl
                = new LinearLayout.LayoutParams(width,
                height);
        layoutParamsControl.setMargins(0, 40, 0, 0);

        this.addContentView(view, layoutParamsControl);

        itemFoundView = view.findViewById(R.id.briefing_view);
        LinearLayout.LayoutParams innerParams = new LinearLayout.LayoutParams((int) (width / 1.5f), (int) (height / 1.5f));
        innerParams.gravity = Gravity.RIGHT;
        innerParams.setMargins(0, 40, 20, 0);
        itemFoundView.setLayoutParams(innerParams);


        itemFoundView.setVisibility(View.INVISIBLE);

        alternateList = (RecyclerView) itemFoundView.findViewById(R.id.alternate_matches);

        alternateList.setHasFixedSize(false);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        alternateList.setLayoutManager(llm);


        //For emulation purposes only
        //	SetUpRecentMatches();
    }

    private void ShowIdentifiedProduct(String brandName) {

        stopFinding();
        tryOutButton.setVisibility(View.VISIBLE);
        Log.i("Identified Product is", brandName);
        itemFoundView.setVisibility(View.VISIBLE);


        activeToBeItems = mModelsData.getSortedItems(brandName);
        //Set the items to make it ready for the Try It Thing
        StaticHolder.setCurrentBrandItems(activeToBeItems);

        ImageView productPreview = (ImageView) itemFoundView.findViewById(R.id.product_preview);
        TextView productName = (TextView) itemFoundView.findViewById(R.id.product_name);
        RatingBar productRating = (RatingBar) itemFoundView.findViewById(R.id.product_rating);
        TextView productPrice = (TextView) itemFoundView.findViewById(R.id.item_price);
        Button buyNowButton = (Button) itemFoundView.findViewById(R.id.product_buy_now);
        ImageView moreAlternateMatches = (ImageView) itemFoundView.findViewById(R.id.more_alternate_matches);


        ImageView cancelView = (ImageView) itemFoundView.findViewById(R.id.dialog_close);
        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HideIdentifiedProductWindow();
            }
        });

        //activeToBeItems=ModelsData.getSortedItems(ModelsData.getBrandMapping().get(brandName));


        // For emulation purposes nowww

        //The 1st Item Retrieved
        //By Some Algorithm or Best Match Stuff or SPonsored Stuff is the first to be shown
        // Run Any Sort of Algorithm  and get the 0th item anyhow

        Picasso.with(RecognitionFinderActivity.this).load(activeToBeItems.get(0).productImage).fit().centerInside().into(productPreview);
        productRating.setRating(5.0f);
        productPrice.setText(" $ " + String.valueOf(activeToBeItems.get(0).price));
        productName.setText(String.valueOf(activeToBeItems.get(0).name));


        //activeToBeItems.get(0) can be added to the review view


        //This thing
        if (!recentlyScannedList.contains(activeToBeItems.get(0)))
            recentlyScannedList.add(activeToBeItems.get(0));

        recentlyScannedAdapter = new RecentlyScannedAdapter(recentlyScannedList);

        recentlyScanned.setAdapter(recentlyScannedAdapter);

        //Probably need to create a Brand to Logo map also


        buyNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent nearMeIntent = new Intent(RecognitionFinderActivity.this, NearMeActivity.class);
                startActivity(nearMeIntent);
            }
        });


        // For emulating the alternate Matches
        setUpAlternateMatches();


    }


    private void HideIdentifiedProductWindow() {
        itemFoundView.setVisibility(View.INVISIBLE);
        startFinding();
        tryOutButton.setVisibility(View.VISIBLE);
    }

    private void setUpAlternateMatches() {


        //alternate_matches is the recycler View id here


        alternateMatchesAdapter = new AlternateMatchesAdapter(activeToBeItems);
        alternateList.setAdapter(alternateMatchesAdapter);

    }


    private List<AlternateMatch> getFakeAlternateMatchesList() {
        List<AlternateMatch> list = new ArrayList<AlternateMatch>();

        list.add(new AlternateMatch(BitmapFactory.decodeResource(getResources(), R.drawable.nikered), "Sweat Shirt", "$20", 5.0f));
        list.add(new AlternateMatch(BitmapFactory.decodeResource(getResources(), R.drawable.nikehat), "Nike Hat", "$15", 3.5f));

        return list;
    }
}
