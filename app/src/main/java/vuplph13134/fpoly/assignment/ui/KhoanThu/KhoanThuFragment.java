package vuplph13134.fpoly.assignment.ui.KhoanThu;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import vuplph13134.fpoly.assignment.DAO.KhoanThuDao;
import vuplph13134.fpoly.assignment.DAO.LoaiThuDao;
import vuplph13134.fpoly.assignment.DTO.KhoanThu;
import vuplph13134.fpoly.assignment.DTO.LoaiThu;
import vuplph13134.fpoly.assignment.R;
import vuplph13134.fpoly.assignment.adapter.KhoanThuAdapter;
import vuplph13134.fpoly.assignment.adapter.SpinnerKhoanThuAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link KhoanThuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KhoanThuFragment extends Fragment {
    private ListView mListView;
    private FloatingActionButton fab;
    private KhoanThuDao khoanThuDao;
    private LoaiThuDao loaiThuDao;
    private ArrayList<KhoanThu> arrayList;
    private KhoanThuAdapter adapter;
    private ArrayList<LoaiThu> list;
    private SpinnerKhoanThuAdapter spinnerKhoanThuAdapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public KhoanThuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment KhoanThuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KhoanThuFragment newInstance(String param1, String param2) {
        KhoanThuFragment fragment = new KhoanThuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_khoan_thu, container, false);
        mListView = (ListView) view.findViewById(R.id.lv_kt);
        fab = (FloatingActionButton) view.findViewById(R.id.fab_kt);
        khoanThuDao = new KhoanThuDao(getActivity());
        khoanThuDao.open();

        loaiThuDao = new LoaiThuDao(getActivity());
        loaiThuDao.open();

        arrayList = new ArrayList<>();
        arrayList = khoanThuDao.GetAll();

        list = new ArrayList<>();
        list = loaiThuDao.GetAll();

        adapter = new KhoanThuAdapter(getActivity(), arrayList);
        mListView.setAdapter(adapter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog();
            }
        });
        return view;

    }

    public void Dialog() {
        list.clear();
        list.addAll(loaiThuDao.GetAll());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.dialog_khoan_thu, null);
        builder.setView(view);

        Spinner spinner = (Spinner) view.findViewById(R.id.spinner_kt);
        EditText ed_ten = (EditText) view.findViewById(R.id.ed_ten_kt);
        EditText ed_tien = (EditText) view.findViewById(R.id.ed_tien_kt);
        EditText ed_date = (EditText) view.findViewById(R.id.ed_date_kt);
        ImageView pick_date = (ImageView) view.findViewById(R.id.img_date_kt);

        spinnerKhoanThuAdapter = new SpinnerKhoanThuAdapter(getActivity(), list);
        spinner.setAdapter(spinnerKhoanThuAdapter);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        pick_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        ed_date.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!list.isEmpty()) {
                    if (checknull(ed_ten, "Bạn chưa nhập tên khoản thu") && checknull(ed_tien, "Bạn chưa nhập số tiền") && checkDate(ed_date) && !list.isEmpty()) {
                        int index = spinner.getSelectedItemPosition();
                        int id_lc = list.get(index).getId();
                        KhoanThu khoanThu = new KhoanThu();
                        khoanThu.setTen(ed_ten.getText().toString());
                        khoanThu.setTien(Double.parseDouble(ed_tien.getText().toString()));
                        khoanThu.setNgay(ed_date.getText().toString());
                        khoanThu.setId_lt(id_lc);
                        long kq = khoanThuDao.Add(khoanThu);

                        if (kq > 0) {
                            arrayList.clear();
                            arrayList.addAll(khoanThuDao.GetAll());
                            adapter.notifyDataSetChanged();
                            Toast.makeText(getActivity(), "Thêm mới thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Thêm mới thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
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

    public boolean checknull(EditText editText, String mess) {
        if (editText.length() == 0) {
            Toast.makeText(getActivity(), mess, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public boolean checkDate(EditText editText) {
        if (editText.length() == 0) {
            Toast.makeText(getActivity(), "bạn chưa nhập ngày", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Date date = null;
            String value = editText.getText().toString();
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
                date = sdf.parse(value);
                if (!value.equals(sdf.format(date))) {
                    date = null;
                    Toast.makeText(getActivity(), "sai định dạng ngày (d/M/yyyy)", Toast.LENGTH_SHORT).show();
                }
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            return date != null;

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        loaiThuDao.close();
        khoanThuDao.close();
    }
}