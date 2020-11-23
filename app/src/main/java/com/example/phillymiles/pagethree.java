package com.example.phillymiles;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class pagethree extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.pagethree, container, false);
        TextView textView=view.findViewById(R.id.welc);
        TextView textView2=view.findViewById(R.id.welc2);


        if(getActivity()!=null) {
            Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Pacifico.ttf");
            textView.setTypeface(tf);
            //textView2.setTypeface(tf);
        }
        return view;
    }
}
