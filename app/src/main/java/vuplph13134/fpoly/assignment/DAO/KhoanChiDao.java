package vuplph13134.fpoly.assignment.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import vuplph13134.fpoly.assignment.CreateDataBase.CreateDataBase;
import vuplph13134.fpoly.assignment.DTO.KhoanChi;

import java.util.ArrayList;
import java.util.Date;

public class KhoanChiDao {
    SQLiteDatabase database;
    CreateDataBase createDataBase;

    public KhoanChiDao(Context context) {
        createDataBase = new CreateDataBase(context);
    }

    public void open() {
        database = createDataBase.getWritableDatabase();
    }

    public void close() {
        createDataBase.close();
    }

    public long Add(KhoanChi khoanChi) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KhoanChi.COL_TEN_CHI, khoanChi.getTen());
        contentValues.put(KhoanChi.COL_TIEN_CHI, khoanChi.getTien());
        contentValues.put(KhoanChi.COL_DATE_CHI, khoanChi.getNgay());
        contentValues.put(KhoanChi.COL_FK_IDLC, khoanChi.getId_lc());
        long res = database.insert(KhoanChi.TB_NAME, null, contentValues);
        return res;
    }

    public ArrayList<KhoanChi> GetAll() {
        ArrayList<KhoanChi> list = new ArrayList<>();
        String data[] = {
                KhoanChi.COL_ID_CHI, KhoanChi.COL_TEN_CHI, KhoanChi.COL_TIEN_CHI, KhoanChi.COL_DATE_CHI, KhoanChi.COL_ID_USE_CHI, KhoanChi.COL_FK_IDLC
        };
        Cursor cursor = database.query(KhoanChi.TB_NAME, data, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            KhoanChi khoanChi = new KhoanChi();
            int id = cursor.getInt(0);
            String ten = cursor.getString(1);
            double tien = cursor.getDouble(2);
            String ngay = cursor.getString(3);
            int id_user = cursor.getInt(4);
            int id_lc = cursor.getInt(5);
            khoanChi.setId(id);
            khoanChi.setTen(ten);
            khoanChi.setTien(tien);
            khoanChi.setNgay(ngay);
            khoanChi.setId_user(id_user);
            khoanChi.setId_lc(id_lc);
            list.add(khoanChi);
            cursor.moveToNext();
        }
        return list;
    }

    public int Update(KhoanChi khoanChi) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(KhoanChi.COL_TEN_CHI, khoanChi.getTen());
        contentValues.put(KhoanChi.COL_TIEN_CHI, khoanChi.getTien());
        contentValues.put(KhoanChi.COL_DATE_CHI, khoanChi.getNgay());
        contentValues.put(KhoanChi.COL_FK_IDLC, khoanChi.getId_lc());
        int res = database.update(KhoanChi.TB_NAME, contentValues, "id =" + khoanChi.getId(), null);
        return res;

    }

    public int Delete(KhoanChi khoanChi) {
        return database.delete(KhoanChi.TB_NAME, "id=" + khoanChi.getId(), null);
    }

    public int sumTien() {
        String sum = "SELECT SUM(so_tien) FROM chi";
        Cursor cursor = database.rawQuery(sum, null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }
}
