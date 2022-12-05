package vuplph13134.fpoly.assignment.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import vuplph13134.fpoly.assignment.ui.KhoanThu.KhoanThuFragment;
import vuplph13134.fpoly.assignment.ui.KhoanThu.LoaiThuFragment;

public class ThuFragMentAdapter extends FragmentStatePagerAdapter {
    String title[]={"Khoản Thu","Loại thu"};
    public ThuFragMentAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new KhoanThuFragment();
        switch (position){
            case 0:
                break;
            case 1:
                fragment=new LoaiThuFragment();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

    @Override
    public int getCount() {
        return 2;
    }
}
