package vuplph13134.fpoly.assignment.ui.ThongKe;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import vuplph13134.fpoly.assignment.DAO.KhoanChiDao;
import vuplph13134.fpoly.assignment.DAO.KhoanThuDao;
import vuplph13134.fpoly.assignment.R;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThongKeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThongKeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ThongKeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThongKEFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThongKeFragment newInstance(String param1, String param2) {
        ThongKeFragment fragment = new ThongKeFragment();
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

    KhoanChiDao khoanChiDao;
    KhoanThuDao khoanThuDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thong_k_e, container, false);
        TextView tv_tgt = view.findViewById(R.id.tv_tgt);
        TextView tv_tkt = view.findViewById(R.id.tv_tkt);
        TextView tv_ttt = view.findViewById(R.id.tv_ttt);
        khoanChiDao = new KhoanChiDao(getActivity());
        khoanChiDao.open();
        khoanThuDao = new KhoanThuDao(getActivity());
        khoanThuDao.open();
        int j=0;
        j=khoanThuDao.SumTien();
        int i = 0;
        i = khoanChiDao.sumTien();
        Locale lcc = new Locale("nv","VN");
        NumberFormat nff=NumberFormat.getCurrencyInstance(lcc);
        String tien_fmm=nff.format(i);
        Locale lccc = new Locale("nv","VN");
        NumberFormat nfff=NumberFormat.getCurrencyInstance(lccc);
        String tien_fmmm=nff.format(j);
        int sum = i+j;
        Locale lc = new Locale("nv","VN");
        NumberFormat nf = NumberFormat.getCurrencyInstance(lc);
        String tien_fm =nf.format(sum);
        tv_tgt.setText("Tổng số tiền chi :"+tien_fmm);
        tv_tkt.setText("Tổng số tiền thu :"+tien_fmmm);
        tv_ttt.setText("Tổng Giá Trị Thu Chi : "+tien_fm);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        khoanChiDao.close();
        khoanThuDao.close();
    }
}