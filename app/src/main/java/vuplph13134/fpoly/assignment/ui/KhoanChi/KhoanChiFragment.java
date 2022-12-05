package vuplph13134.fpoly.assignment.ui.KhoanChi;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import vuplph13134.fpoly.assignment.DAO.KhoanChiDao;
import vuplph13134.fpoly.assignment.DAO.LoaiChiDao;
import vuplph13134.fpoly.assignment.DTO.KhoanChi;
import vuplph13134.fpoly.assignment.DTO.LoaiChi;
import vuplph13134.fpoly.assignment.R;
import vuplph13134.fpoly.assignment.adapter.KhoanChiAdapter;
import vuplph13134.fpoly.assignment.adapter.SpinnerKhoanChiAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link KhoanChiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KhoanChiFragment extends Fragment {
    private ListView mListView;
    private FloatingActionButton fab;
    private KhoanChiDao khoanChiDao;
    private LoaiChiDao loaiChiDao;
    private ArrayList<KhoanChi> arrayList;
    private KhoanChiAdapter adapter;
    private ArrayList<LoaiChi> list;
    private SpinnerKhoanChiAdapter spinnerKhoanChiAdapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public KhoanChiFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment KhoanChiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KhoanChiFragment newInstance(String param1, String param2) {
        KhoanChiFragment fragment = new KhoanChiFragment();
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
        View view = inflater.inflate(R.layout.fragment_khoan_chi, container, false);
        mListView = (ListView) view.findViewById(R.id.lv_kc);
        fab = (FloatingActionButton) view.findViewById(R.id.fab_kc);

        khoanChiDao = new KhoanChiDao(getActivity());
        khoanChiDao.open();

        loaiChiDao = new LoaiChiDao(getActivity());
        loaiChiDao.open();
        arrayList = new ArrayList<>();
        arrayList = khoanChiDao.GetAll();

        list = new ArrayList<>();
        list=loaiChiDao.GetAll();
        adapter = new KhoanChiAdapter(getActivity(), arrayList);
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
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        list.clear();
        list.addAll(loaiChiDao.GetAll());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.dialog_khoan_chi, null);
        builder.setView(view);

        Spinner spinner = (Spinner) view.findViewById(R.id.spinner_kc);
        EditText ed_ten = (EditText) view.findViewById(R.id.ed_ten_kc);
        EditText ed_tien = (EditText) view.findViewById(R.id.ed_tien_kc);
        EditText ed_date = (EditText) view.findViewById(R.id.ed_date_kc);
        ImageView pick_date = (ImageView) view.findViewById(R.id.img_date_kc);

        spinnerKhoanChiAdapter = new SpinnerKhoanChiAdapter(list, getActivity());
        spinner.setAdapter(spinnerKhoanChiAdapter);

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
                if (checknull(ed_ten, "Bạn chưa nhập tên") &&
                        checknull(ed_tien, "Bạn chưa nhập tiền") &&
                        chechdate(ed_date)) {
                    int index = spinner.getSelectedItemPosition();
                    int id_lc = list.get(index).getId();
                    KhoanChi khoanChi = new KhoanChi();
                    khoanChi.setTen(ed_ten.getText().toString());
                    khoanChi.setTien(Double.parseDouble(ed_tien.getText().toString()));
                    khoanChi.setNgay(ed_date.getText().toString());
                    khoanChi.setId_lc(id_lc);

                    long kq = khoanChiDao.Add(khoanChi);
                    if (kq > 0) {
                        arrayList.clear();
                        arrayList.addAll(khoanChiDao.GetAll());
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
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

    public boolean chechdate(EditText editText) {
        if (editText.length() == 0) {
            Toast.makeText(getActivity(), "bạn chưa nhập ngày ", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Date date = null;
            String value = editText.getText().toString();
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d/M/yyyy");
                date = simpleDateFormat.parse(value);
                if (!value.equals(simpleDateFormat.format(date))) {
                    date = null;
                    Toast.makeText(getActivity(), "Sai định dạng ngày ", Toast.LENGTH_SHORT).show();
                }
            } catch (ParseException exception) {
                exception.printStackTrace();
            }
            return date != null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        khoanChiDao.close();
        loaiChiDao.close();
    }
}