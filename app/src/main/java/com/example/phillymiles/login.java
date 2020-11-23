package com.example.phillymiles;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class login extends AppCompatActivity {
      boolean t;
      boolean s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        final ImageButton imageButton = findViewById(R.id.service);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageButton.setImageResource(R.drawable.ic_radio_button_checked_black_24dp);
                s=true;
            }
        });

        final  EditText e1=findViewById(R.id.editText);
        final EditText e2=findViewById(R.id.editText2);
        final EditText e3=findViewById(R.id.editText3);
        final EditText e4=findViewById(R.id.editText4);

        final TextView m1=findViewById(R.id.m1);
        final  TextView m2=findViewById(R.id.m2);
        final  TextView m3=findViewById(R.id.m3);
        final  TextView m4=findViewById(R.id.m4);
        m1.setVisibility(View.INVISIBLE);
        m2.setVisibility(View.INVISIBLE);
        m3.setVisibility(View.INVISIBLE);
        m4.setVisibility(View.INVISIBLE);

        CardView cardView=findViewById(R.id.bla);

        t=true;
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(e1.getText().toString())){
                    t=true;
                    m1.setVisibility(View.INVISIBLE);
                }else{
                    m1.setVisibility(View.VISIBLE);
                    t=false;
                }


                if (!TextUtils.isEmpty(e2.getText().toString())) {
                    m2.setVisibility(View.INVISIBLE);
                    t=true;
                }else{
                    m2.setVisibility(View.VISIBLE);
                    t=false;
                }


                if (!TextUtils.isEmpty(e3.getText().toString())) {
                    m3.setVisibility(View.INVISIBLE);
                    t=true;
                }else{
                    m3.setVisibility(View.VISIBLE);
                    t=false;
                }

                if (!TextUtils.isEmpty(e4.getText().toString())) {
                    m4.setVisibility(View.INVISIBLE);
                    t=true;
                }else{
                    m4.setVisibility(View.VISIBLE);
                    t=false;
                }
                if (t && s){
                    Toast.makeText(login.this,"Invalid access code. We're sorry, but this is a private travel social network.You need an access code to enter.",Toast.LENGTH_LONG).show();
                }else if (!s){
                    Toast.makeText(login.this,"Agree to terms of service to continue",Toast.LENGTH_SHORT).show();
                }

            }
        });

        TextView textView=findViewById(R.id.reg);
        TextView clickhere=findViewById(R.id.clickhere);
        clickhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(login.this,newlogin.class);
                startActivity(intent);
            }
        });

        Typeface tf = Typeface.createFromAsset(this.getAssets(), "fonts/Platform-Bold.ttf");
        final CardView card1=findViewById(R.id.card1);
        final CardView card2=findViewById(R.id.card2);
        final CardView card3=findViewById(R.id.card3);
        final CardView card4=findViewById(R.id.card4);

        e1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {

                    card1.setElevation(15.0f);
                } else {
                    card1.setElevation(3);
                }
            }
        });

        e2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {

                    card2.setElevation(15.0f);
                } else {
                    card2.setElevation(3);
                }
            }
        });
        e3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {

                    card3.setElevation(15.0f);
                } else {
                    card3.setElevation(3);
                }
            }
        });
        e4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {

                    card4.setElevation(15.0f);
                } else {
                    card4.setElevation(3);
                }
            }
        });


    }
    }
