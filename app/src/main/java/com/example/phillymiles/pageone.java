package com.example.phillymiles;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class pageone extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.pageone, container, false);
        TextView textView=view.findViewById(R.id.welc);
        TextView e=view.findViewById(R.id.slo);

        if(getActivity()!=null) {
            Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Pacifico.ttf");
            textView.setTypeface(tf);
            e.setTypeface(tf);
        }
        return view;
    }
}
