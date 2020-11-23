package com.example.phillymiles;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.ui.database.FirebaseRecyclerAdapter;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

import static androidx.core.graphics.ColorUtils.blendARGB;

public class main extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private final float[] rotationMatrix = new float[9];
    public static float[] orientationAngles = new float[3];
    private Sensor mSensor;
    public static float InitialStepNumber = -1;
    public static float prevSteps=0;
    private boolean isSensorPresent = false;
    private final float[] accelerometerReading = new float[3];
    TextView maintitle;
    private final float[] magnetometerReading = new float[3];
    private static final int requestPermissionID = 101;
    static Boolean pause=false;
    SharedPreferences pref;
    long DURATION = 500;
    ViewPager mViewPager;
    private boolean on_attach = true;
    public static boolean change=false;
    public int color=0;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final  DatabaseReference myRef = database.getReference("group");
    CollapsingToolbarLayout collapsingToolbarLayout;
    String title;
    float centreX;
    float centreY;
    public Boolean invert=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mViewPager =  findViewById(R.id.container);
        setupViewPager(mViewPager);

      //  final TextView rank = findViewById(R.id.textview_rank);
        final View line=findViewById(R.id.line);
       // final View line2=findViewById(R.id.line2);

        Typeface tf = Typeface.createFromAsset(this.getAssets(), "fonts/Platform-Bold.ttf");

        collapsingToolbarLayout=findViewById(R.id.callaping_vp);


      //  rank.setTypeface(tf);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ActivityCompat.requestPermissions((Activity) main.this, new String[]{Manifest.permission.ACTIVITY_RECOGNITION},
                    requestPermissionID);
        }
        final Window w = getWindow(); // in Activity's onCreate() for instance
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


         pref = this.getSharedPreferences("ID", Context.MODE_PRIVATE);
        final AppBarLayout appBarLayout=findViewById(R.id.appbar);
        final ImageView imgview = findViewById(R.id.overlay);

        final ImageView imgviewi = findViewById(R.id.overlay_doublecloud);
        final ImageView imgview2 = findViewById(R.id.overlay_doublecloud2);
        final ImageView imgview3 = findViewById(R.id.overlay_singlecloud);
        final ImageView imageView_balloon=findViewById(R.id.overlay_balloon);
        final ImageView imgview_sun = findViewById(R.id.overlay_sun);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (Math.abs(verticalOffset)-appBarLayout.getTotalScrollRange() == 0) {

                    if (invert){
                        imgview.setBackground(null);
                        imgview.setBackgroundColor(blendARGB(color,Color.WHITE,0.8f));


                    }else{
                        imgview.setBackground(null);
                        w.setStatusBarColor(blendARGB(color,Color.BLACK,0.8f));
                        imgview.setBackgroundColor(blendARGB(color,Color.BLACK,0.8f));

                    }



                }
                else {

                    if (invert){
                        imgview.setBackground(ContextCompat.getDrawable(main.this, R.drawable.skyline2black));
                        imgviewi.setBackground(ContextCompat.getDrawable(main.this, R.drawable.doublecloudblack));
                        imgview2.setBackground(ContextCompat.getDrawable(main.this, R.drawable.doublecloudblack));
                        imgview3.setBackground(ContextCompat.getDrawable(main.this, R.drawable.singlecloudblack));
                        imageView_balloon.setBackground(ContextCompat.getDrawable(main.this, R.drawable.balloonblack));
                        imgview_sun.setBackground(ContextCompat.getDrawable(main.this, R.drawable.imageblack));
                    }else{
                        imgview.setBackground(ContextCompat.getDrawable(main.this, R.drawable.skylinewhite2));
                        imgviewi.setBackground(ContextCompat.getDrawable(main.this, R.drawable.doublecloud));
                        imgview2.setBackground(ContextCompat.getDrawable(main.this, R.drawable.doublecloud));
                        imgview3.setBackground(ContextCompat.getDrawable(main.this, R.drawable.singlecloud));
                        imageView_balloon.setBackground(ContextCompat.getDrawable(main.this, R.drawable.balloon));
                        imgview_sun.setBackground(ContextCompat.getDrawable(main.this, R.drawable.image));
                    }


                   // window.setStatusBarColor(blendARGB(color,Color.BLACK,0.7f));

                }
            }
        });




        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {

            isSensorPresent = true;
        } else {
            isSensorPresent = false;
        }

        final int[] count = {0};
        final Boolean[] flip = {false};
        final Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                final ImageView imageView_balloon=findViewById(R.id.overlay_balloon);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageView_balloon.getLayoutParams();
                if (count[0] < 100 && !flip[0]){
                    count[0] = count[0] +2;
                    layoutParams.topMargin = count[0];
                    imageView_balloon.setLayoutParams(layoutParams);
                    //Log.e("positive",String.valueOf(count[0]));
                }
                if (count[0]>-100 && flip[0]){
                    count[0] = count[0] -2;
                    layoutParams.topMargin = count[0];
                    imageView_balloon.setLayoutParams(layoutParams);
                    //Log.e("negative",String.valueOf(count[0]));
                }
                if (count[0]==100){
                    flip[0] =true;
                }else if (count[0]==-100){
                    flip[0]=false;
                }
                handler.postDelayed(this, 100);
            }
        };
        handler.postDelayed(r, 100);


        myRef.child(pref.getString("GroupID",null)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                title = snapshot.child("groupname").getValue(String.class);

                 color = snapshot.child("members").child(pref.getString("UserID",null)).child("color").getValue(Integer.class);

                 if (pref.getFloat("Steps", 0)==0){
                     SharedPreferences.Editor editor = pref.edit();
                     editor.putFloat("Steps", (snapshot.child("members").child(pref.getString("UserID",null)).child("steps").getValue(Integer.class)*-1));
                     editor.apply();
                 }

                 change=true;

                 if (getContrastColor(color)==Color.BLACK){

                     invert=true;
                     imgview.setBackground(ContextCompat.getDrawable(main.this, R.drawable.skyline2black));
                     imgviewi.setBackground(ContextCompat.getDrawable(main.this, R.drawable.doublecloudblack));
                     imgview2.setBackground(ContextCompat.getDrawable(main.this, R.drawable.doublecloudblack));
                     imgview3.setBackground(ContextCompat.getDrawable(main.this, R.drawable.singlecloudblack));
                     imageView_balloon.setBackground(ContextCompat.getDrawable(main.this, R.drawable.balloonblack));
                     imgview_sun.setBackground(ContextCompat.getDrawable(main.this, R.drawable.imageblack));

                 }else{
                     invert=false;
                     imgview.setBackground(ContextCompat.getDrawable(main.this, R.drawable.skylinewhite2));
                     imgviewi.setBackground(ContextCompat.getDrawable(main.this, R.drawable.doublecloud));
                     imgview2.setBackground(ContextCompat.getDrawable(main.this, R.drawable.doublecloud));
                     imgview3.setBackground(ContextCompat.getDrawable(main.this, R.drawable.singlecloud));
                     imageView_balloon.setBackground(ContextCompat.getDrawable(main.this, R.drawable.balloon));
                     imgview_sun.setBackground(ContextCompat.getDrawable(main.this, R.drawable.image));

                 }


                appBarLayout.setBackgroundColor(color);
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(blendARGB(color,Color.BLACK,0.6f));

                collapsingToolbarLayout.setContentScrimColor(blendARGB(color,Color.BLACK,0.8f));
                collapsingToolbarLayout.setStatusBarScrimColor(blendARGB(color,Color.BLACK,0.8f));


                //line.setBackgroundColor(blendARGB(color,Color.BLACK,0.7f));
                //line2.setBackgroundColor(blendARGB(color,Color.BLACK,0.7f));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        super.onWindowFocusChanged(hasFocus);

        if(hasFocus) {
            final ImageView imgview = findViewById(R.id.overlay_doublecloud);
            final ImageView imgview2 = findViewById(R.id.overlay_doublecloud2);
            final ImageView imgview3 = findViewById(R.id.overlay_singlecloud);
            final RelativeLayout imageView_balloon=findViewById(R.id.overlay_balloon_relative);
            int width= Resources.getSystem().getDisplayMetrics().widthPixels;



            final ObjectAnimator animation = ObjectAnimator.ofFloat(imgview, "translationX", width);
            animation.setDuration(40000);
            animation.setRepeatCount(-1);
            animation.start();

            final ObjectAnimator animation2 = ObjectAnimator.ofFloat(imgview2, "translationX", width);
            animation2.setDuration(60000);
            animation2.setRepeatCount(-1);
            animation2.start();

            final ObjectAnimator animation3 = ObjectAnimator.ofFloat(imgview3, "translationX", width);
            animation3.setDuration(100000);
            animation3.setRepeatCount(-1);
            animation3.start();


            final ObjectAnimator animation4 = ObjectAnimator.ofFloat(imageView_balloon, "translationX", -width);
            animation4.setDuration(50000);
            animation4.setRepeatCount(-1);
            animation4.start();

            animation.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationRepeat(Animator animatio) {
                    super.onAnimationRepeat(animatio);
                    animation.reverse();

                }
            });

            animation2.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationRepeat(Animator animatio) {
                    super.onAnimationRepeat(animatio);
                    animation2.reverse();

                }
            });

            animation3.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationRepeat(Animator animatio) {
                    super.onAnimationRepeat(animatio);
                    animation3.reverse();

                }
            });

            animation4.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationRepeat(Animator animatio) {
                    super.onAnimationRepeat(animatio);
                    animation4.reverse();

                }
            });


            final ImageView imgview_sun = findViewById(R.id.overlay_sun);
            centreX=imgview_sun.getPivotX();
            centreY=imgview_sun.getPivotY();
            Animation an = new RotateAnimation(0.0f, 360.0f, centreX, centreY);
            // Set the animation's parameters
            an.setDuration(90000);               // duration in ms
            an.setRepeatCount(-1);
            an.setInterpolator(new LinearInterpolator());
            an.setFillAfter(true);               // keep rotation after animation

            // Aply animation to image view
            imgview_sun.setAnimation(an);



        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isSensorPresent) {
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);

        }



    }



    @Override
    protected void onPause() {
        super.onPause();
        if (isSensorPresent) {
            mSensorManager.unregisterListener(this);
        }

    }




    @Override
    public void onSensorChanged(SensorEvent event) {
        if (change){
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                System.arraycopy(event.values, 0, accelerometerReading,
                        0, accelerometerReading.length);
            }

            if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
                if (InitialStepNumber == -1) {
                    InitialStepNumber = event.values[0];
                    if ( pref.getFloat("InitSteps",0)>0){
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putFloat("Steps", pref.getFloat("Steps", 0) +(InitialStepNumber - pref.getFloat("InitSteps",0)));
                        editor.apply();
                        Toast.makeText(this,String.valueOf(pref.getFloat("Steps", 0)),Toast.LENGTH_SHORT).show();
                        myRef.child(pref.getString("GroupID", null)).child("members").child(pref.getString("UserID", null)).child("steps").setValue((pref.getFloat("Steps",0)*-1));

                    }

                }


                final float steps = (event.values[0] - InitialStepNumber);

                if (prevSteps != steps) {
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putFloat("Steps", pref.getFloat("Steps", 0) + (steps-prevSteps));
                    editor.apply();
                    Toast.makeText(this,String.valueOf("zero2"),Toast.LENGTH_SHORT).show();
                    myRef.child(pref.getString("GroupID", null)).child("members").child(pref.getString("UserID", null)).child("steps").setValue((pref.getFloat("Steps",0)*-1));

                }


                prevSteps=steps;

                SharedPreferences.Editor editor = pref.edit();
                editor.putFloat("InitSteps",event.values[0] );
                editor.apply();




                //  DrawingView drawingView=new DrawingView(this,null);
                //Toast.makeText(this,String.valueOf("sensor"),Toast.LENGTH_SHORT).show();

            }
        }

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }





    private void setupViewPager(ViewPager viewPager){

        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());
        Fragment mainpager =new mainpager();

        adapter.addFragment(mainpager, "mainpager");
        viewPager.setAdapter(adapter);
    }

    @ColorInt
    public static int getContrastColor(@ColorInt int color) {
        // Counting the perceptive luminance - human eye favors green color...
        double a = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
        return a < 0.5 ? Color.BLACK : Color.WHITE;
    }


}
