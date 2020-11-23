package com.example.phillymiles;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class pagetwo extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.pagetwo, container, false);

        TextView e=view.findViewById(R.id.slo);
        TextView a=view.findViewById(R.id.wel);

        if(getActivity()!=null) {
            Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Pacifico.ttf");
            //a.setTypeface(tf);
            e.setTypeface(tf);
        }
        return view;
    }
}
