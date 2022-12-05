package vuplph13134.fpoly.assignment.adapter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import vuplph13134.fpoly.assignment.DAO.KhoanThuDao;
import vuplph13134.fpoly.assignment.DAO.LoaiThuDao;
import vuplph13134.fpoly.assignment.DTO.KhoanThu;
import vuplph13134.fpoly.assignment.DTO.LoaiThu;
import vuplph13134.fpoly.assignment.MainActivity;
import vuplph13134.fpoly.assignment.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class KhoanThuAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<KhoanThu> list;
    private KhoanThuDao khoanThuDao;
    private LoaiThuDao loaiThuDao;
    private SpinnerKhoanThuAdapter adapter;

    public KhoanThuAdapter(Context context, ArrayList<KhoanThu> list) {
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
            view = View.inflate(parent.getContext(), R.layout.custom_listview_khoanthu, null);
        } else {
            view = convertView;
        }
        KhoanThu khoanThu = list.get(position);
        TextView ten = (TextView) view.findViewById(R.id.tv_ten_kt);
        TextView tien = (TextView) view.findViewById(R.id.tv_tien_kt);
        TextView date = (TextView) view.findViewById(R.id.tv_date_kt);
        ImageView update = (ImageView) view.findViewById(R.id.img_update_kt);
        ImageView delete = (ImageView) view.findViewById(R.id.img_delete_kt);
        ten.setText(khoanThu.getTen());
        Locale lc = new Locale("nv", "VN");
        NumberFormat nf = NumberFormat.getCurrencyInstance(lc);
        String tien_fm = nf.format(khoanThu.getTien());
        tien.setText(tien_fm);
        date.setText(khoanThu.getNgay());
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                khoanThuDao = new KhoanThuDao(context);
                khoanThuDao.open();
                int kq = khoanThuDao.Delete(khoanThu);
                if (kq > 0) {
                    list.clear();
                    list.addAll(khoanThuDao.GetAll());
                    notifyDataSetChanged();
                    Toast.makeText(context, "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
                khoanThuDao.close();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = 0;

                if (context.getClass().equals(MainActivity.class)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    View viewdl = ((MainActivity) context).getLayoutInflater().inflate(R.layout.dialog_khoan_thu, null);
                    builder.setView(viewdl);
                    Spinner spinner = (Spinner) viewdl.findViewById(R.id.spinner_kt);
                    EditText ed_ten = (EditText) viewdl.findViewById(R.id.ed_ten_kt);
                    EditText ed_tien = (EditText) viewdl.findViewById(R.id.ed_tien_kt);
                    EditText ed_date = (EditText) viewdl.findViewById(R.id.ed_date_kt);
                    ImageView pick_date = (ImageView) viewdl.findViewById(R.id.img_date_kt);
                    loaiThuDao = new LoaiThuDao(context);
                    loaiThuDao.open();
                    ArrayList<LoaiThu> arrayList = new ArrayList<>();
                    arrayList = loaiThuDao.GetAll();
                    adapter = new SpinnerKhoanThuAdapter(context, arrayList);
                    spinner.setAdapter(adapter);
                    index = khoanThu.getId_lt() - 1;
                    loaiThuDao.close();
                    spinner.setSelection(index);
                    ed_ten.setText(khoanThu.getTen());
                    ed_tien.setText(khoanThu.getTien() + "");
                    ed_date.setText((khoanThu.getNgay()));
                    builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (khoanThu.getTen().equals(ed_ten.getText()) && khoanThu.getTien() == Double.parseDouble(ed_tien.getText().toString()) && khoanThu.getNgay().equals(ed_date.getText().toString()) && khoanThu.getId_lt() == spinner.getSelectedItemPosition() - 1) {
                                Toast.makeText(context, "Không có thay đổi để cập nhật", Toast.LENGTH_SHORT).show();
                            } else {
                                khoanThuDao = new KhoanThuDao(context);
                                khoanThuDao.open();
                                loaiThuDao = new LoaiThuDao(context);
                                loaiThuDao.open();
                                int id_lc = loaiThuDao.GetAll().get(spinner.getSelectedItemPosition()).getId();
                                loaiThuDao.close();
                                khoanThu.setTen(ed_ten.getText().toString());
                                khoanThu.setTien(Double.parseDouble(ed_tien.getText().toString()));
                                khoanThu.setNgay(ed_date.getText().toString());
                                khoanThu.setId_lt(id_lc);
                                int kq = khoanThuDao.Update(khoanThu);
                                if (kq > 0) {
                                    list.clear();
                                    list.addAll(khoanThuDao.GetAll());
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Sửa Thành Công", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                                }
                                khoanThuDao.close();
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

    public void pickDate(ImageView pick_date, EditText editText) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        pick_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editText.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
                );
                dialog.show();
            }
        });
    }
}
