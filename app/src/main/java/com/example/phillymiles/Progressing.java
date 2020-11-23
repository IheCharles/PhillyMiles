package com.example.phillymiles;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.Objects;

/**
 * Created by Dell on 2017/12/28.
 */

public class Progressing extends Dialog implements android.view.View.OnClickListener {
    public Activity c;
    String Check;
    public TextView textView1;
    TextView textView2;
    TextView textView3;

    public Progressing(@NonNull Context context) {
        super(context);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(false);
        setContentView(R.layout.progressing);
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.dimAmount = 0.75f;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(layoutParams);

        textView1=findViewById(R.id.progressing_1);
        textView2=findViewById(R.id.progressing_2);
        textView3=findViewById(R.id.progressing_3);
    }

    @Override
    public void onClick(View view) {

    }
}
