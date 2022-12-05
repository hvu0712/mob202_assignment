package vuplph13134.fpoly.assignment.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import vuplph13134.fpoly.assignment.DAO.KhoanChiDao;
import vuplph13134.fpoly.assignment.DAO.LoaiChiDao;
import vuplph13134.fpoly.assignment.DTO.KhoanChi;
import vuplph13134.fpoly.assignment.DTO.LoaiChi;
import vuplph13134.fpoly.assignment.MainActivity;
import vuplph13134.fpoly.assignment.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class KhoanChiAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<KhoanChi> list;
    private KhoanChiDao khoanChiDao;
    private LoaiChiDao loaiChiDao;
    private SpinnerKhoanChiAdapter adapter;

    public KhoanChiAdapter(Context context, ArrayList<KhoanChi> list) {
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
            view = View.inflate(parent.getContext(), R.layout.custom_listview_khoanchi, null);
        } else {
            view = convertView;
        }
        KhoanChi khoanChi = list.get(position);
        TextView ten = (TextView) view.findViewById(R.id.tv_ten_kc);
        TextView tien = (TextView) view.findViewById(R.id.tv_tien_kc);
        TextView date = (TextView) view.findViewById(R.id.tv_date_kc);
        ImageView update = (ImageView) view.findViewById(R.id.img_update_kc);
        ImageView delete = (ImageView) view.findViewById(R.id.img_delete_kc);

        ten.setText(khoanChi.getTen());
        Locale lc = new Locale("nv", "VN");
        NumberFormat nf = NumberFormat.getCurrencyInstance(lc);
        String tien_fm = nf.format(khoanChi.getTien());
        tien.setText(tien_fm);
        date.setText(khoanChi.getNgay());

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                khoanChiDao = new KhoanChiDao(context);
                khoanChiDao.open();
                int kq = khoanChiDao.Delete(khoanChi);
                if (kq > 0) {
                    list.clear();
                    list.addAll(khoanChiDao.GetAll());
                    notifyDataSetChanged();
                    Toast.makeText(context, "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
                khoanChiDao.close();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = 0;
                if (context.getClass().equals(MainActivity.class)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    View viewdl = ((MainActivity) context).getLayoutInflater().inflate(R.layout.dialog_khoan_chi, null);
                    builder.setView(viewdl);
                    Spinner spinner = (Spinner) viewdl.findViewById(R.id.spinner_kc);
                    EditText ed_ten = (EditText) viewdl.findViewById(R.id.ed_ten_kc);
                    EditText ed_tien = (EditText) viewdl.findViewById(R.id.ed_tien_kc);
                    EditText ed_date = (EditText) viewdl.findViewById(R.id.ed_date_kc);
                    ImageView pick_date = (ImageView) viewdl.findViewById(R.id.img_date_kc);

                    loaiChiDao = new LoaiChiDao(context);
                    loaiChiDao.open();
                    ArrayList<LoaiChi> loaiChiArrayList = new ArrayList<>();
                    loaiChiArrayList = loaiChiDao.GetAll();
                    adapter = new SpinnerKhoanChiAdapter(loaiChiArrayList, context);
                    spinner.setAdapter(adapter);
                    index = khoanChi.getId_lc() - 1;
                    loaiChiDao.close();
                    spinner.setSelection(index);
                    ed_ten.setText(khoanChi.getTen());
                    ed_date.setText(khoanChi.getNgay());
                    ed_tien.setText(khoanChi.getTien() + "");

                    builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (khoanChi.getTen().equals(ed_ten.getText().toString()) && khoanChi.getTien() == Double.parseDouble(ed_tien.getText().toString()) && khoanChi.getNgay().equals(ed_date.getText().toString()) && khoanChi.getId_lc() == spinner.getSelectedItemPosition() + 1) {
                                Toast.makeText(context, "không có gì thay đổi để update", Toast.LENGTH_SHORT).show();

                            } else {
                                khoanChiDao = new KhoanChiDao(context);
                                khoanChiDao.open();
                                loaiChiDao = new LoaiChiDao(context);
                                loaiChiDao.open();
                                int i = spinner.getSelectedItemPosition();
                                int id_lc = loaiChiDao.GetAll().get(i).getId();
                                loaiChiDao.close();
                                khoanChi.setTen(ed_ten.getText().toString());

                                khoanChi.setTien(Double.parseDouble(ed_tien.getText().toString()));
                                khoanChi.setNgay(ed_date.getText().toString());
                                khoanChi.setId_lc(id_lc);
                                int kq = khoanChiDao.Update(khoanChi);
                                if (kq > 0) {
                                    list.clear();
                                    list.addAll(khoanChiDao.GetAll());
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                                }
                                khoanChiDao.close();
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
