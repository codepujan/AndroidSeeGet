package blue.arche.sample_1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;


public class PinchExampleActivity extends AppCompatActivity {

    ScaleGestureDetector scaleGestureDetector;

    public ImageView imageView;
    public  Bitmap placeHolderBitmap;
    public  float scaleFactor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinch_example);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

         placeHolderBitmap= BitmapFactory.decodeResource(getResources(),R.drawable.nikered);

        scaleGestureDetector =
                new ScaleGestureDetector(this,
                        new MyOnScaleGestureListener());


         imageView=(ImageView)findViewById(R.id.scale_image_view);



        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        imageView.setLayoutParams(params);

        imageView.setImageBitmap(Bitmap.createScaledBitmap(placeHolderBitmap,placeHolderBitmap.getWidth()/3,placeHolderBitmap.getHeight()/3,false));




        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                scaleGestureDetector.onTouchEvent(motionEvent);

                return true;
            }
        });

    }




    public class MyOnScaleGestureListener extends
            ScaleGestureDetector.SimpleOnScaleGestureListener {



        @Override
        public boolean onScale(ScaleGestureDetector detector) {

           // float scaleFactor = detector.getScaleFactor();

            scaleFactor *= detector.getScaleFactor();

            scaleFactor = (scaleFactor < 1 ? 1 : scaleFactor); // prevent our view from becoming too small //
            scaleFactor = ((float)((int)(scaleFactor * 100))) / 100; // Change precision to help with jitter when user just rests their fingers //



            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            imageView.setLayoutParams(params);

            imageView.setImageBitmap(Bitmap.createScaledBitmap(placeHolderBitmap,placeHolderBitmap.getWidth()/3*(int)scaleFactor,placeHolderBitmap.getHeight()/3*(int)scaleFactor,false));

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
