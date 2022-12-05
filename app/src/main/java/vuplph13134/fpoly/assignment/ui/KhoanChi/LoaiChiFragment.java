package vuplph13134.fpoly.assignment.ui.KhoanChi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import vuplph13134.fpoly.assignment.DAO.LoaiChiDao;
import vuplph13134.fpoly.assignment.DTO.LoaiChi;
import vuplph13134.fpoly.assignment.R;
import vuplph13134.fpoly.assignment.adapter.LoaiChiAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoaiChiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoaiChiFragment extends Fragment {
    private FloatingActionButton fab;
    private ListView mListView;
    private LoaiChiDao loaiChiDao;
    private LoaiChiAdapter adapter;
    private ArrayList<LoaiChi> arrayList;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoaiChiFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoaiChiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoaiChiFragment newInstance(String param1, String param2) {
        LoaiChiFragment fragment = new LoaiChiFragment();
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
        View view = inflater.inflate(R.layout.fragment_loai_chi, container, false);
        fab = (FloatingActionButton) view.findViewById(R.id.fab_lc);
        mListView = (ListView) view.findViewById(R.id.lv_lc);
        loaiChiDao = new LoaiChiDao(getActivity());
        loaiChiDao.open();
        arrayList = new ArrayList<>();
        arrayList = loaiChiDao.GetAll();
        adapter = new LoaiChiAdapter(getActivity(), arrayList);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.dialog_loai_chi, null);
        builder.setView(view);
        EditText ed_ten = (EditText) view.findViewById(R.id.ed_ten_lc);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (checkNull(ed_ten)) {
                    LoaiChi loaiChi = new LoaiChi();
                    loaiChi.setTen(ed_ten.getText().toString());
                    long kq = loaiChiDao.Add(loaiChi);
                    if (kq > 0) {
                        arrayList.clear();
                        arrayList.addAll(loaiChiDao.GetAll());
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(), "Thêm mới Thành Công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Thêm mới thất bại", Toast.LENGTH_SHORT).show();
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

    public boolean checkNull(EditText editText) {
        if (editText.length() == 0) {
            Toast.makeText(getActivity(), "Bạn chưa nhập loại chi", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        loaiChiDao.close();
    }
}