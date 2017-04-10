package blue.arche.sample_1;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.ScaleGestureDetector;
import android.view.Surface;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.catchoom.test.PictureScreenShotActivity;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import blue.arche.sample_1.Interfaces.PreviewItemSelectedListener;

import static android.view.MotionEvent.INVALID_POINTER_ID;

/**  import co.tanvas.haptics.service.adapter.HapticServiceAdapter;
 import co.tanvas.haptics.service.app.HapticApplication;
 import co.tanvas.haptics.service.model.HapticMaterial;
 import co.tanvas.haptics.service.model.HapticSprite;
 import co.tanvas.haptics.service.model.HapticTexture;
 import co.tanvas.haptics.service.model.HapticView;

 **/



public class SurfaceViewActivity extends AppCompatActivity implements SurfaceHolder.Callback,PreviewItemSelectedListener,Camera.PictureCallback {
    TextView testView;


    public  static SurfaceViewActivity reference;

    private  int  baseWidth=150;
    private  int   baseHeight=150;

    Camera camera;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;

    PictureCallback rawCallback;
    ShutterCallback shutterCallback;
    PictureCallback jpegCallback;

    private float xCoOrdinate, yCoOrdinate;

    private int currentCameraID=Camera.CameraInfo.CAMERA_FACING_FRONT;
    private Bitmap bmp;

    /** private HapticView mHapticView;
     private HapticTexture mHapticTexture;
     private HapticMaterial mHapticMaterial;
     private HapticSprite mHapticSprite;
     **/

    private View augmentedControl;
    private ImageView augmentedImageView;
    LayoutInflater controlInflater = null;


    //For the Pinch Zoom and scale thing
    private Matrix matrix = new Matrix();


    private DiscreteSeekBar imageResizer;

    private Bitmap placeHolderBitmap;


    private RecyclerView sidePreviewLists;

    private SidePreviewAdapter sidePreviewAdapter;
    private StoreItem currentlySelectedItem;

    private float scaleFactor = 1;


    ScaleGestureDetector scaleGestureDetector;
    private float mLastTouchX;
    private float mLastTouchY;

    private int mActivePointerId = INVALID_POINTER_ID;
    private float mPosX;
    private float mPosY;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //   Log.i("PERSONAL CLOSET",String.valueOf(StaticHolder.getCurrentBrandItems().size()));


        reference=this;
        setContentView(R.layout.content_surface_view);

        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        surfaceHolder.addCallback(this);

        // deprecated setting, but required on Android versions prior to 3.0
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);


        /**
         * These  lines are only for emulation purposes
         * We need to work with Real time passed Static Data for Real demo app
         *
         * For Some Magic Number
         * We have implemented 0 as the Sorted Algorithm thing
         *
         */



        //placeHolderBitmap=ModelsData.getAllItems().get(1).productImage;
        //currentlySelectedItem=ModelsData.getAllItems().get(1);

        scaleGestureDetector =
                new ScaleGestureDetector(this,
                        new SurfaceViewActivity.MyOnScaleGestureListener());

        placeHolderBitmap=BitmapFactory.decodeResource(getResources(),StaticHolder.currentBrandItems.get(0).productImage);
        currentlySelectedItem=StaticHolder.currentBrandItems.get(0);

        jpegCallback = new PictureCallback() {
            public void onPictureTaken(byte[] data, Camera camera) {
                FileOutputStream outStream = null;
                try {
                    outStream = new FileOutputStream(String.format("/sdcard/%d.jpg", System.currentTimeMillis()));
                    outStream.write(data);
                    outStream.close();
                    Log.d("Log", "onPictureTaken - wrote bytes: " + data.length);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                }
                Toast.makeText(getApplicationContext(), "Picture Saved", Toast.LENGTH_LONG).show();
                refreshCamera();
            }
        };


        controlInflater = LayoutInflater.from(getBaseContext());
        View view = controlInflater.inflate(R.layout.control, null);
        ViewGroup.LayoutParams layoutParamsControl
                = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT);
        this.addContentView(view, layoutParamsControl);







        augmentedControl=(View) view.findViewById(R.id.imageView);
        augmentedImageView=(ImageView ) view.findViewById(R.id.imageView);
        augmentedImageView.setImageBitmap(placeHolderBitmap);


        sidePreviewLists=(RecyclerView) view.findViewById(R.id.side_preview);


        sidePreviewLists.setHasFixedSize(false);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        sidePreviewLists.setLayoutManager(llm);


        // sidePreviewAdapter=new SidePreviewAdapter(ModelsData.getAllItems());  // Need  to change this with the live

        sidePreviewAdapter=new SidePreviewAdapter(StaticHolder.currentBrandItems);

        sidePreviewLists.setAdapter(sidePreviewAdapter);


        //sidePRevieewLists End

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        augmentedImageView.setLayoutParams(params);

        augmentedImageView.setImageBitmap(Bitmap.createScaledBitmap(placeHolderBitmap,placeHolderBitmap.getWidth()/3,placeHolderBitmap.getHeight()/3,false));




        augmentedImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                scaleGestureDetector.onTouchEvent(motionEvent);
                final int action = MotionEventCompat.getActionMasked(motionEvent);

                switch (action) {
                    case MotionEvent.ACTION_DOWN: {
                        final int pointerIndex = MotionEventCompat.getActionIndex(motionEvent);
                        final float x = MotionEventCompat.getX(motionEvent, pointerIndex);
                        final float y = MotionEventCompat.getY(motionEvent, pointerIndex);

                        // Remember where we started (for dragging)
                        mLastTouchX = x;
                        mLastTouchY = y;
                        // Save the ID of this pointer (for dragging)
                        mActivePointerId = MotionEventCompat.getPointerId(motionEvent, 0);
                        break;
                    }

                    case MotionEvent.ACTION_MOVE: {
                        // Find the index of the active pointer and fetch its position
                        final int pointerIndex =
                                MotionEventCompat.findPointerIndex(motionEvent, mActivePointerId);

                        final float x = MotionEventCompat.getX(motionEvent, pointerIndex);
                        final float y = MotionEventCompat.getY(motionEvent, pointerIndex);

                        // Calculate the distance moved
                        final float dx = x - mLastTouchX;
                        final float dy = y - mLastTouchY;

                        mPosX += dx;
                        mPosY += dy;

                        augmentedImageView.setX(mPosX);
                        augmentedImageView.setY(mPosY);
                        augmentedImageView.invalidate();

                        // Remember this touch position for the next move event
                        mLastTouchX = x;
                        mLastTouchY = y;

                        break;
                    }

                    case MotionEvent.ACTION_UP: {
                        mActivePointerId = INVALID_POINTER_ID;
                        break;
                    }

                    case MotionEvent.ACTION_CANCEL: {
                        mActivePointerId = INVALID_POINTER_ID;
                        break;
                    }

                    case MotionEvent.ACTION_POINTER_UP: {

                        final int pointerIndex = MotionEventCompat.getActionIndex(motionEvent);
                        final int pointerId = MotionEventCompat.getPointerId(motionEvent, pointerIndex);

                        if (pointerId == mActivePointerId) {
                            // This was our active pointer going up. Choose a new
                            // active pointer and adjust accordingly.
                            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                            mLastTouchX = MotionEventCompat.getX(motionEvent, newPointerIndex);
                            mLastTouchY = MotionEventCompat.getY(motionEvent, newPointerIndex);
                            mActivePointerId = MotionEventCompat.getPointerId(motionEvent, newPointerIndex);
                        }
                        break;
                    }
                }
                return true;
            }
        });


        imageResizer=(DiscreteSeekBar)view.findViewById(R.id.tryout_seekbar);
        imageResizer.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {


                //   RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(150*value, 150*value);
                //0 applies for top of the parent
                /**  if(StaticHolder.getCurrentStore().metadataLocation==0)
                 params.addRule(RelativeLayout.ALIGN_PARENT_TOP,RelativeLayout.CENTER_HORIZONTAL);
                 else if(StaticHolder.getCurrentStore().metadataLocation==1)
                 params.addRule(RelativeLayout.CENTER_IN_PARENT);
                 else if(StaticHolder.getCurrentStore().metadataLocation==2)
                 params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

                 **/

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                params.addRule(RelativeLayout.CENTER_IN_PARENT);

                if(value==1)
                    seekBar.setIndicatorFormatter("XS");
                else if (value==2)
                    seekBar.setIndicatorFormatter("S");
                else if(value==3)
                    seekBar.setIndicatorFormatter("M");
                else if(value==4)
                    seekBar.setIndicatorFormatter("L");
                else if(value==5)
                    seekBar.setIndicatorFormatter("XXL");


                augmentedImageView.setLayoutParams(params);
                augmentedImageView.setImageBitmap(Bitmap.createScaledBitmap(placeHolderBitmap,placeHolderBitmap.getWidth()/3*value,placeHolderBitmap.getHeight()/3*value,false));



                /**  Bitmap bMapScaled = Bitmap.createScaledBitmap(StaticHolder.getCurrentStore().productImage, 150*value,100*value, true);
                 augmentedImageView.setImageBitmap(bMapScaled);
                 //Picasso.with(getApplicationContext()).load(R.drawable.nikeblack)

                 Picasso.with(getApplicationContext()).load(R.drawable.nikeblack).resize(50*value,50*value).centerInside().into(augmentedImageView);
                 **/
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

            }
        });



        augmentedImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        xCoOrdinate = view.getX() - event.getRawX();
                        yCoOrdinate = view.getY() - event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        view.animate().x(event.getRawX() + xCoOrdinate).y(event.getRawY() + yCoOrdinate).setDuration(0).start();
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });

        //   initHaptics();

        ImageButton changeCamera=(ImageButton)view.findViewById(R.id.change_camera);
        changeCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                camera.stopPreview();

                //NB: if you don't release the current camera before switching, you app will crash
                camera.release();

//swap the id of the camera to be used
                if(currentCameraID == Camera.CameraInfo.CAMERA_FACING_BACK){
                    currentCameraID = Camera.CameraInfo.CAMERA_FACING_FRONT;
                }
                else {
                    currentCameraID = Camera.CameraInfo.CAMERA_FACING_BACK;
                }

                camera = Camera.open(currentCameraID);
                setCameraDisplayOrientation(camera);


                try {
                    camera.setPreviewDisplay(surfaceHolder);
                    camera.startPreview();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });


        ImageButton shopButton=(ImageButton)view.findViewById(R.id.goto_shop);

        shopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start the Intent For the SHop Thing

                Intent shopIntent=new Intent(SurfaceViewActivity.this,NearMeActivity.class);
                startActivity(shopIntent);

            }
        });


        View capturescreenShot=(View)view.findViewById(R.id.capture_screenshot);

        capturescreenShot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //camera.takePicture(null,null,SurfaceViewActivity.reference);


                Bitmap bitmap;
                View v1 = surfaceView.getRootView();
                v1.setDrawingCacheEnabled(true);
                bitmap = Bitmap.createBitmap(v1.getDrawingCache());
                v1.setDrawingCacheEnabled(false);


                StaticHolder.setScreenShotBitmap(bitmap);


                Intent screenShotIntent=new Intent(SurfaceViewActivity.this, PictureScreenShotActivity.class);
                startActivity(screenShotIntent);

            }
        });
    }



    @Override
    public void onPictureTaken(byte[] bytes, Camera camera) {


        Bitmap bitmapPicture
                = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);


//        camera.startPreview();

        StaticHolder.setScreenShotBitmap(bitmapPicture);


        Intent screenShotIntent=new Intent(SurfaceViewActivity.this, PictureScreenShotActivity.class);
        startActivity(screenShotIntent);


    }



    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }



    public void refreshCamera() {
        if (surfaceHolder.getSurface() == null) {
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            camera.stopPreview();
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here
        // start preview with new settings
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (Exception e) {

        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // Now that the size is known, set up the camera parameters and begin
        // the preview.
       // if(camera!=null) {

        Log.i("Surface Calback ","Surface Changed  Callback Fired");
            setCameraDisplayOrientation(camera);
            refreshCamera();
     //   }
    }



    public void setCameraDisplayOrientation(android.hardware.Camera camera) {
        Camera.Parameters parameters = camera.getParameters();

        android.hardware.Camera.CameraInfo camInfo =
                new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(getBackFacingCameraId(), camInfo);


        Display display = ((WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int rotation = display.getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        int result;
        if (camInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (camInfo.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (camInfo.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }




    private int getBackFacingCameraId() {
        int cameraId = -1;
        // Search for the front facing camera
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {

                cameraId = i;
                break;
            }
        }
        return cameraId;
    }

    public void surfaceCreated(SurfaceHolder holder) {
        Log.i("Surface Calback ","Surface Created Callback Fired");

        try {
            // open the camera
            camera = Camera.open(currentCameraID);

            currentCameraID= Camera.CameraInfo.CAMERA_FACING_BACK;
            camera=Camera.open(currentCameraID);
           // camera=Camera.open()
        } catch (RuntimeException e) {
            // check for exceptions
            System.err.println(e);
            return;
        }
        Camera.Parameters param;
        param = camera.getParameters();

        // modify parameter
        param.setPreviewSize(352, 288);
        camera.setParameters(param);
        try {
            // The Surface has been created, now tell the camera where to draw
            // the preview.
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (Exception e) {
            // check for exceptions
            System.err.println(e);
            return;
        }

        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.noise_texture);



    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // stop preview and release camera
        camera.stopPreview();
        camera.release();
        camera = null;
    }

    @Override
    public void PreviewSelected(StoreItem sourceItem) {

        placeHolderBitmap=BitmapFactory.decodeResource(getResources(),sourceItem.productImage);

        augmentedImageView.setImageBitmap(Bitmap.createScaledBitmap(placeHolderBitmap,placeHolderBitmap.getWidth()/3,placeHolderBitmap.getHeight()/3,false));


        currentlySelectedItem=sourceItem;

    }


/**
 public void initHaptics() {
 try {
 // Get the service adapter
 HapticServiceAdapter serviceAdapter =
 HapticApplication.getHapticServiceAdapter();
 // Create a haptic view and activate it
 mHapticView = HapticView.create(serviceAdapter);
 mHapticView.activate();
 // Set the orientation of the haptic view
 Display display = ((WindowManager)
 getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
 int rotation = display.getRotation();
 HapticView.Orientation orientation =
 HapticView.getOrientationFromAndroidDisplayRotation(rotation);

 mHapticView.setOrientation(orientation);
 // Retrieve texture data from the bitmap
 Bitmap hapticBitmap = StaticHolder.getCurrentStore().tanvasImage;

 // Bitmap hapticBitmap = BitmapFactory.decodeResource(getResources(),
 //       R.drawable.noise_texture);

 byte[] textureData =
 HapticTexture.createTextureDataFromBitmap(hapticBitmap);
 // Create a haptic texture with the retrieved texture data
 mHapticTexture = HapticTexture.create(serviceAdapter);
 int textureDataWidth = hapticBitmap.getRowBytes() / 4; // 4 channels,
 // i.e., ARGB
 int textureDataHeight = hapticBitmap.getHeight();
 mHapticTexture.setSize(textureDataWidth, textureDataHeight);
 mHapticTexture.setData(textureData);
 // Create a haptic material with the created haptic texture
 mHapticMaterial = HapticMaterial.create(serviceAdapter);

 mHapticMaterial.setTexture(0, mHapticTexture);
 // Create a haptic sprite with the haptic material
 mHapticSprite = HapticSprite.create(serviceAdapter);
 mHapticSprite.setMaterial(mHapticMaterial);
 // Add the haptic sprite to the haptic view
 mHapticView.addSprite(mHapticSprite);
 } catch (Exception e) {
 Log.e(null, e.toString());

 }
 }
 **/

/**
 @Override
 public void onWindowFocusChanged(boolean hasFocus) {
 super.onWindowFocusChanged(hasFocus);
 // The activity is gaining focus
 Log.i("On Window Focus Changed","Window Focus Changed has been called");
 if (hasFocus) {
 try {
 // Set the size and position of the haptic sprite to correspond to
 //the view we created
 //View view = findViewById(R.id.view);
 int[] location = new int[2];
 augmentedControl.getLocationOnScreen(location);
 mHapticSprite.setSize(augmentedImageView.getWidth(), augmentedImageView.getHeight());
 mHapticSprite.setPosition(location[0], location[1]);
 } catch (Exception e) {
 Log.e(null, e.toString());
 }
 }
 }



 @Override
 public void onDestroy() {
 super.onDestroy();
 try {
 mHapticView.deactivate();
 } catch (Exception e) {
 Log.e(null, e.toString());
 }
 }

 **/



public class MyOnScaleGestureListener extends
        ScaleGestureDetector.SimpleOnScaleGestureListener {



    @Override
    public boolean onScale(ScaleGestureDetector detector) {

        // float scaleFactor = detector.getScaleFactor();

        scaleFactor *= detector.getScaleFactor();

        scaleFactor = (scaleFactor < 1 ? 1 : scaleFactor); // prevent our view from becoming too small //
        scaleFactor = ((float)((int)(scaleFactor * 100))) / 100; // Change precision to help with jitter when user just rests their fingers //


        Log.i("Scale Factor is ",String.valueOf(scaleFactor));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        augmentedImageView.setLayoutParams(params);

        augmentedImageView.setImageBitmap(Bitmap.createScaledBitmap(placeHolderBitmap,placeHolderBitmap.getWidth()/3*(int)scaleFactor,placeHolderBitmap.getHeight()/3*(int)scaleFactor,false));

        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }
}





}