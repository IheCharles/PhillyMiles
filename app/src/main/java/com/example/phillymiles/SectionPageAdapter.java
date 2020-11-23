package com.example.phillymiles;



import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 2017/06/21.
 */

public class SectionPageAdapter extends FragmentPagerAdapter {
    private final List<Fragment> fragmentslist= new ArrayList<>();
    private final List<String> stringslist= new ArrayList<>();

    public  void addFragment(Fragment fragment, String title){
        fragmentslist.add(fragment);
        stringslist.add(title);
    }
    public SectionPageAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentslist.get(position);
    }


    @Override
    public int getCount() {
        return fragmentslist.size();
    }

}

