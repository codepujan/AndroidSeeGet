package blue.arche.sample_1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.craftar.CLog;
import com.craftar.CraftARError;
import com.craftar.CraftAROnDeviceCollection;
import com.craftar.CraftAROnDeviceCollectionManager;
import com.craftar.CraftAROnDeviceIR;
import com.craftar.CraftARSDK;
import com.craftar.ImageRecognition;

import blue.arche.sample_1.catchoom.test.LaunchersActivity;
import blue.arche.sample_1.catchoom.test.SplashScreenActivity;

/**
 * Created by pujanpaudel on 11/26/16.
 */

public class Application extends android.app.Application implements ImageRecognition.SetOnDeviceCollectionListener,
        CraftAROnDeviceCollectionManager.AddCollectionListener, CraftAROnDeviceCollectionManager.SyncCollectionListener {




    private final static String TAG = "SplashScreenActivity";

    //Collection token of the collection you want to load.
    //Note that you can load several collections at once, but every search
    //request is performed only on ONE collection (the one that you have set through CraftAROnDeviceIR.setCollection()).
    public final static String COLLECTION_TOKEN="70dd0e0d3846471d";

    CraftAROnDeviceIR mCraftAROnDeviceIR;
    CraftAROnDeviceCollectionManager mCollectionManager;

    ProgressDialog addCollectionDialog;
    ProgressDialog setCollectionDialog;
    ProgressDialog syncCollectionDialog;




    //We created this class to initialize all the Application Related Activities
    //For now , we generated  the Craft AR thing
    // We need to setup a loading thing first if the data isn't loaded

    // So , emulate the onCreate





    @Override
    public void onCreate() {
        super.onCreate();

        CraftARSDK.Instance().init(getApplicationContext());

        ModelsData.setBrandMapping(getApplicationContext());


        ModelsData.populateItemsData(getApplicationContext());
        //Initialize the Collection Manager
        mCollectionManager = CraftAROnDeviceCollectionManager.Instance();

        //Initialize the Offline IR Module
        mCraftAROnDeviceIR = CraftAROnDeviceIR.Instance();


        //Obtain the collection with your token.
        //This will lookup for the collection in the internal storage, and return the collection if it's available.
        CraftAROnDeviceCollection col =  mCollectionManager.get(COLLECTION_TOKEN);
        if(col == null){
            //showAddCollectionWithTokenDialog();
            //Collection is not available. Add it from the CraftAR service using the collection token.
            mCollectionManager.addCollectionWithToken(COLLECTION_TOKEN,this);

            // Alternatively it can be added from assets using the collection bundle.
            //mCollectionManager.addCollection("com.catchoom.test.zip",(AddCollectionListener)this);

        }else{
            //Collection is already available in the device.
            //showSyncDialog();
            col.sync((CraftAROnDeviceCollectionManager.SyncCollectionListener)this);
        }
    }

    @Override
    public void collectionReady() {
        Toast.makeText(getApplicationContext(), "Collection ready!", Toast.LENGTH_SHORT).show();
        if(setCollectionDialog!=null){
            if(setCollectionDialog.isShowing()){
                CLog.d("Dissmissing setCollectionDialog...");
                setCollectionDialog.dismiss();
            }
        }
        //Collection is ready for recognition.
    }

    @Override
    public void setCollectionFailed(CraftARError error) {
        Toast.makeText(getApplicationContext(), "setCollection failed! ("+error.getErrorCode()+"):"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
        if(setCollectionDialog!=null){
            if(setCollectionDialog.isShowing()){
                setCollectionDialog.dismiss();
            }
        }
        //Error loading the collection into memory. No recognition can be performed unless a collection has been set.
        Log.e(TAG, "SetCollectionFailed (" + error.getErrorCode() + "):" + error.getErrorMessage());
        Toast.makeText(getApplicationContext(), "Error loading", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setCollectionProgress(double progress) {
        if(setCollectionDialog!=null){
            if(setCollectionDialog.isShowing()){
                setCollectionDialog.setProgress((int) (100 * progress));
            }
        }
        //The images from the collection are loading into memory. You will have to load the collections into memory every time you open the app.
        Log.d(TAG, "SetCollectionProgress:" + progress);
    }


    @Override
    public void collectionAdded(CraftAROnDeviceCollection collection) {
        //Collection bundle has been added. Set this collection as current collection.
        Toast.makeText(getApplicationContext(), "Collection "+collection.getName()+ " added!", Toast.LENGTH_SHORT).show();
        if(addCollectionDialog!=null){
            if(addCollectionDialog.isShowing()){
                addCollectionDialog.dismiss();
            }
        }
        loadCollection(collection);
    }

    @Override
    public void addCollectionFailed(CraftARError error) {
        //Error adding the bundle to the device internal storage.
        Log.e(TAG, "AddCollectionFailed(" + error.getErrorCode() + "):" + error.getErrorMessage());
        Toast.makeText(getApplicationContext(), "Error adding collection", Toast.LENGTH_SHORT).show();
        if(addCollectionDialog.isShowing()){
            addCollectionDialog.dismiss();
        }
        switch(error.getErrorCode()){
            case COLLECTION_BUNDLE_SDK_VERSION_IS_OLD:
                //You are trying to add a bundle which version is newer than the SDK version.
                //You should either update the SDK, or download and add a bundle compatible with this SDK version.
                break;
            case COLLECTION_BUNDLE_VERSION_IS_OLD:
                //You are trying to add a bundle which is outdated, since the SDK version is newer than the bundleSDK
                //You should download a bundle compatible with the newer SDK version.
                break;
            default:
                break;
        }
    }

    @Override
    public void addCollectionProgress(float progress) {
        //Progress adding the collection to internal storage (de-compressing bundle and storing into the device storage).
        //Note that this might only happen once per app installation, or when the bundle is updated.
        Log.d(TAG, "AddCollectionProgress:" + progress);
        if(addCollectionDialog!=null){
            if(addCollectionDialog.isShowing()){
                addCollectionDialog.setProgress((int)(100*progress));
            }
        }
    }

    private void loadCollection(CraftAROnDeviceCollection collection){
       // showSetCollectionDialog();
        mCraftAROnDeviceIR.setCollection(collection, (ImageRecognition.SetCollectionListener) this);
    }



    @Override
    public void syncSuccessful(CraftAROnDeviceCollection collection) {
        String text = "Sync succesful for collection "+collection.getName();
        Toast.makeText(getApplicationContext(),text, Toast.LENGTH_SHORT).show();
        Log.d(TAG, text);
        if(syncCollectionDialog!=null){
            if(syncCollectionDialog.isShowing()){
                syncCollectionDialog.dismiss();
            }
        }

        loadCollection(collection);
    }

    @Override
    public void syncFinishedWithErrors(CraftAROnDeviceCollection collection, int itemDownloads, int itemErrors) {
        String text = "Sync Finished but  " + itemErrors + " of the " + itemDownloads + " items could not be synchronized";
        Toast.makeText(getApplicationContext(), text , Toast.LENGTH_SHORT).show();
        Log.e(TAG, text);
    }

    @Override
    public void syncProgress(CraftAROnDeviceCollection collection, float progress) {
        Log.e(TAG, "Sync progress for collection "+collection.getName() + ":"+progress);
        if(syncCollectionDialog!=null){
            if(syncCollectionDialog.isShowing()){
                syncCollectionDialog.setProgress((int)(100*progress));
            }
        }
    }


    @Override
    public void syncFailed(CraftAROnDeviceCollection collection, CraftARError error) {
        String text = "Sync failed for collection "+collection.getName();
        Toast.makeText(getApplicationContext(), text , Toast.LENGTH_SHORT).show();
        Log.e(TAG, text + ":"+error.getErrorMessage());
        if(syncCollectionDialog!=null){
            if(syncCollectionDialog.isShowing()){
                syncCollectionDialog.dismiss();
            }
        }
        loadCollection(collection);
    }


    public void showAddCollectionWithTokenDialog(){
        addCollectionDialog = new ProgressDialog(Application.this);
        addCollectionDialog.setTitle("Downloading bundle...");
        addCollectionDialog.setMessage("Add collection in progress ...");
        addCollectionDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        addCollectionDialog.setProgress(0);
        addCollectionDialog.setMax(100);
        addCollectionDialog.show();
    }

    public void showSyncDialog(){
        syncCollectionDialog = new ProgressDialog(Application.this);
        syncCollectionDialog.setTitle("Syncing");
        syncCollectionDialog.setMessage("Sync in progress ...");
        syncCollectionDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        syncCollectionDialog.setProgress(0);
        syncCollectionDialog.setMax(100);
        syncCollectionDialog.show();
    }

    public void showSetCollectionDialog(){
        setCollectionDialog = new ProgressDialog(Application.this);
        setCollectionDialog.setTitle("Loading collection...");
        setCollectionDialog.setMessage("Loading collection in progress ...");
        setCollectionDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        setCollectionDialog.setProgress(0);
        setCollectionDialog.setMax(100);
        setCollectionDialog.show();
    }





}
