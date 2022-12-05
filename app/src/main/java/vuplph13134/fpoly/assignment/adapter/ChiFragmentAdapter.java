package vuplph13134.fpoly.assignment.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import vuplph13134.fpoly.assignment.ui.KhoanChi.KhoanChiFragment;
import vuplph13134.fpoly.assignment.ui.KhoanChi.LoaiChiFragment;

public class ChiFragmentAdapter extends FragmentStatePagerAdapter {
    String title[]={"Khoản Chi","Loại Chi"};

    public ChiFragmentAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment= new KhoanChiFragment();
        switch (position){
            case 0:
                break;
            case 1:
                fragment= new LoaiChiFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
