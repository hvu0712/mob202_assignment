package vuplph13134.fpoly.assignment.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import vuplph13134.fpoly.assignment.DAO.LoaiChiDao;
import vuplph13134.fpoly.assignment.DTO.LoaiChi;
import vuplph13134.fpoly.assignment.MainActivity;
import vuplph13134.fpoly.assignment.R;

import java.util.ArrayList;

public class LoaiChiAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<LoaiChi> list;
    private LoaiChiDao loaiChiDao;

    public LoaiChiAdapter(Context context, ArrayList<LoaiChi> list) {
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
            view = View.inflate(parent.getContext(), R.layout.custom_listview_loaichi, null);
        } else {
            view = convertView;
        }
        LoaiChi loaiChi = list.get(position);
        TextView mTextView = (TextView) view.findViewById(R.id.tv_ten_lc);
        ImageView update = (ImageView) view.findViewById(R.id.img_update_lc);
        ImageView delete = (ImageView) view.findViewById(R.id.img_delete_lc);
        mTextView.setText(loaiChi.getTen());
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loaiChiDao = new LoaiChiDao(context);
                loaiChiDao.open();
                int kq = loaiChiDao.Delete(loaiChi);
                if (kq > 0) {
                    list.clear();
                    list.addAll(loaiChiDao.GetAll());
                    notifyDataSetChanged();
                    Toast.makeText(context, "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
                loaiChiDao.close();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context.getClass().equals(MainActivity.class)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    View viewdl = ((MainActivity) context).getLayoutInflater().inflate(R.layout.dialog_loai_chi, null);
                    builder.setView(viewdl);
                    EditText ed_ten = (EditText) viewdl.findViewById(R.id.ed_ten_lc);
                    ed_ten.setText(loaiChi.getTen());
                    builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (loaiChi.getTen().equals(ed_ten.getText().toString())) {
                                Toast.makeText(context, "Không có gì đẻ update", Toast.LENGTH_SHORT).show();
                            } else {
                                loaiChiDao = new LoaiChiDao(context);
                                loaiChiDao.open();
                                loaiChi.setTen(ed_ten.getText().toString());
                                int kq = loaiChiDao.Update(loaiChi);
                                if (kq > 0) {
                                    list.clear();
                                    list.addAll(loaiChiDao.GetAll());
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Update Thành Công", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Update Thất bại", Toast.LENGTH_SHORT).show();
                                }
                                loaiChiDao.close();
                            }

                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }
            }
        });
        return view;
    }
}
