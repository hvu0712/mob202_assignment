package vuplph13134.fpoly.assignment.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import vuplph13134.fpoly.assignment.DTO.LoaiThu;
import vuplph13134.fpoly.assignment.R;

import java.util.ArrayList;

public class SpinnerKhoanThuAdapter extends BaseAdapter {
    private ArrayList<LoaiThu> list;
    private Context context;

    public SpinnerKhoanThuAdapter(Context context, ArrayList<LoaiThu> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = View.inflate(parent.getContext(), R.layout.spinner_khoan_chi, null);
        } else {
            view = convertView;
        }
        LoaiThu loaiThu = list.get(position);
        TextView mTextView = (TextView) view.findViewById(R.id.tv_sp_lc);
        mTextView.setText(loaiThu.getTen());
        return view;
    }
}
