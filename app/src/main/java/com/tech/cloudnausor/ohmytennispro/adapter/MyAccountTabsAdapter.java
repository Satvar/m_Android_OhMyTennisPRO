package com.tech.cloudnausor.ohmytennispro.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.tech.cloudnausor.ohmytennispro.fragment.myaccount.MyAccFormOneFragment;
import com.tech.cloudnausor.ohmytennispro.fragment.myaccount.MyAccFormThreeFragment;
import com.tech.cloudnausor.ohmytennispro.fragment.myaccount.MyAccFormTwoFragment;
import com.tech.cloudnausor.ohmytennispro.utils.SingleTonProcess;


public class MyAccountTabsAdapter extends FragmentStatePagerAdapter {
    public int mNumOfTabs;
    MyAccFormOneFragment form_one;
    MyAccFormTwoFragment form_two;
    MyAccFormThreeFragment form_three;

    public MyAccountTabsAdapter(FragmentManager fm, int NoofTabs){
        super(fm);
        this.mNumOfTabs = NoofTabs;
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
    @Override
    public Fragment getItem(int position){
        switch (position){
            case 0:
                form_one = new MyAccFormOneFragment();
                return form_one;
            case 1:
                  form_two = new MyAccFormTwoFragment();
                return form_two;
            case 2:
                  form_three = new MyAccFormThreeFragment();
                 return form_three;
            default:
                return null;
        }
    }

    public  Fragment getFragement(int postion){
        switch (postion){
            case 0:
                return form_one;
            case 1:
                return form_two;
            case 2:
                return form_three;
            default:
                return null;
        }

    }
}