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

import vuplph13134.fpoly.assignment.DAO.LoaiThuDao;
import vuplph13134.fpoly.assignment.DTO.LoaiThu;
import vuplph13134.fpoly.assignment.MainActivity;
import vuplph13134.fpoly.assignment.R;

import java.util.ArrayList;

public class LoaithuAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<LoaiThu> list;
    private LoaiThuDao loaiThuDao;


    public LoaithuAdapter(Context context, ArrayList<LoaiThu> list) {
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
            view = View.inflate(parent.getContext(), R.layout.custom_listview_loaithu, null);

        } else {
            view = convertView;
        }
        LoaiThu loaiThu = list.get(position);
        TextView mTextView = (TextView) view.findViewById(R.id.tv_ten_lt);
        ImageView update = (ImageView) view.findViewById(R.id.img_update_lt);
        ImageView delete = (ImageView) view.findViewById(R.id.img_delete_lt);
        mTextView.setText(loaiThu.getTen());
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loaiThuDao = new LoaiThuDao(context);
                loaiThuDao.open();
                int kq = loaiThuDao.Delete(loaiThu);
                if (kq > 0) {
                    list.clear();
                    list.addAll(loaiThuDao.GetAll());
                    notifyDataSetChanged();
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
                loaiThuDao.close();

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context.getClass().equals(MainActivity.class)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    View viewdl = ((MainActivity) context).getLayoutInflater().inflate(R.layout.dialog_loai_thu, null);
                    builder.setView(viewdl);
                    EditText ed_ten = (EditText) viewdl.findViewById(R.id.ed_ten_lt);
                    ed_ten.setText(loaiThu.getTen());
                    builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (loaiThu.getTen().equals(ed_ten.getText().toString())) {
                                Toast.makeText(context, "Không có gì để thay đổi", Toast.LENGTH_SHORT).show();
                            } else {
                                loaiThuDao = new LoaiThuDao(context);
                                loaiThuDao.open();
                                loaiThu.setTen(ed_ten.getText().toString());
                                int kq = loaiThuDao.Update(loaiThu);
                                if (kq > 0) {
                                    list.clear();
                                    list.addAll(loaiThuDao.GetAll());
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                                }
                                loaiThuDao.close();

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
