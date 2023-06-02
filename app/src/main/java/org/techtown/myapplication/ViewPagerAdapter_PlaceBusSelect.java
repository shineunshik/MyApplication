package org.techtown.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter_PlaceBusSelect extends FragmentPagerAdapter {
    int Tab;
    String text;
    public ViewPagerAdapter_PlaceBusSelect(@NonNull FragmentManager fm, int Tab) {
        super(fm);
        this.Tab=Tab;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return PlaceBusSelect_1.newInstance(text);
            default:
                return null;
        }

    }


    @Override
    public int getCount() {
        return Tab;
    }

    public void getText(String text){
        this.text = text;
    }
}
