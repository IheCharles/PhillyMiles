package com.example.phillymiles;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.View;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.io.ByteArrayOutputStream;

public class home extends AppCompatActivity {
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        CardView cardView=findViewById(R.id.si);

        mViewPager = findViewById(R.id.container_activity);
        setupViewPager(mViewPager);
        final TabLayout tabLayout =  findViewById(R.id.tabs_useractivity);
        tabLayout.setupWithViewPager(mViewPager,true);
        ViewPagerSetUp();

        TextView textView=findViewById(R.id.home_create);
        TextView textView2=findViewById(R.id.home_join);


        Typeface tf = Typeface.createFromAsset(this.getAssets(), "fonts/Platform-Bold.ttf");
        textView.setTypeface(tf);
        textView2.setTypeface(tf);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        ImageButton imageButton_create=findViewById(R.id.imagebutton_group);
        ImageButton imageButton_join = findViewById(R.id.imagebutton_join);

        imageButton_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap= blur(getWindow().getDecorView().getRootView());
                ByteArrayOutputStream stream=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100,stream);
                byte[] bytes=stream.toByteArray();
                Intent intent=new Intent(home.this,create.class);
                intent.putExtra("bit",bytes);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });


        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap= blur(getWindow().getDecorView().getRootView());
                ByteArrayOutputStream stream=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100,stream);
                byte[] bytes=stream.toByteArray();
                Intent intent=new Intent(home.this,create.class);
                intent.putExtra("bit",bytes);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });





        CardView ferfef=findViewById(R.id.siup);
        ferfef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap= blur(getWindow().getDecorView().getRootView());
                ByteArrayOutputStream stream=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100,stream);
                byte[] bytes=stream.toByteArray();
                Intent intent=new Intent(home.this,join.class);
                intent.putExtra("bit",bytes);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });

        imageButton_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap= blur(getWindow().getDecorView().getRootView());
                ByteArrayOutputStream stream=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100,stream);
                byte[] bytes=stream.toByteArray();
                Intent intent=new Intent(home.this,join.class);
                intent.putExtra("bit",bytes);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });


    }
    private void setupViewPager(ViewPager viewPager){
        SectionPageAdapter3 adapter3 = new SectionPageAdapter3(getSupportFragmentManager());
        adapter3.addFragment(new pageone(), "P1");
        adapter3.addFragment(new pagetwo(), "P2");
        adapter3.addFragment(new pagethree(),"P3");
        viewPager.setAdapter(adapter3);
    }
    private void ViewPagerSetUp(){
        final TabLayout tabLayout =  findViewById(R.id.tabs_useractivity);
        if (tabLayout!=null) {

            int[] image = {
                    R.drawable.dot,
                    R.drawable.dot,
                    R.drawable.dot,
            };
            for (int i = 0; i < image.length; i++) {
                tabLayout.getTabAt(i).setIcon(image[i]);

            }
            View root = tabLayout.getChildAt(0);

            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    if (tabLayout.getSelectedTabPosition() == 1) {
                        tab.setIcon(R.drawable.dotselected);

                    }
                    if (tabLayout.getSelectedTabPosition() == 0) {
                        tab.setIcon(R.drawable.dotselected);

                    }
                    if (tabLayout.getSelectedTabPosition() == 2) {
                        tab.setIcon(R.drawable.dotselected);

                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                    if (tabLayout.getSelectedTabPosition() == 0) {
                        tab.setIcon(R.drawable.dot);

                    } else if (tabLayout.getSelectedTabPosition() == 0){
                        tab.setIcon(R.drawable.dot);

                    }else{
                        tab.setIcon(R.drawable.dot);
                    }
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                }
            });
        }
    }
    private static final float BITMAP_SCALE = 0.4f;
    private static final float BLUR_RADIUS = 7.5f;
    public static Bitmap blur(View v) {
        return blur(v.getContext(), getScreenshot(v));
    }
    public static Bitmap blur(Context ctx, Bitmap image) {
        int width = Math.round(image.getWidth() * BITMAP_SCALE);
        int height = Math.round(image.getHeight() * BITMAP_SCALE);
        Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);
        RenderScript rs = RenderScript.create(ctx);
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
        theIntrinsic.setRadius(BLUR_RADIUS);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);
        return outputBitmap;
    }

    private static Bitmap getScreenshot(View v) {
        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);
        return b;
    }

}
