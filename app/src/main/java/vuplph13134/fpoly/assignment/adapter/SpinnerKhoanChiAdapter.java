package vuplph13134.fpoly.assignment.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import vuplph13134.fpoly.assignment.DTO.LoaiChi;
import vuplph13134.fpoly.assignment.R;

import java.util.ArrayList;

public class SpinnerKhoanChiAdapter extends BaseAdapter {
private ArrayList<LoaiChi>list;
private Context context;

public SpinnerKhoanChiAdapter(ArrayList<LoaiChi>list,Context context){
    this.list=list;
    this.context=context;
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
        if(convertView==null){
            view=View.inflate(parent.getContext(), R.layout.spinner_khoan_chi,null);
        }else {
            view=convertView;
        }
LoaiChi loaiChi = list.get(position);
        TextView mTextView = (TextView)view.findViewById(R.id.tv_sp_lc);
        mTextView.setText(loaiChi.getTen());
        return view;
    }
}
