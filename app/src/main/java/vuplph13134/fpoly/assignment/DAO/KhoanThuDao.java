package vuplph13134.fpoly.assignment.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import vuplph13134.fpoly.assignment.CreateDataBase.CreateDataBase;
import vuplph13134.fpoly.assignment.DTO.KhoanThu;

import java.util.ArrayList;

public class KhoanThuDao {
    CreateDataBase createDataBase;
    SQLiteDatabase database;

    public KhoanThuDao(Context context) {
        createDataBase = new CreateDataBase(context);
    }

    public void open() {
        database = createDataBase.getWritableDatabase();
    }

    public void close() {
        createDataBase.close();
    }

    public long Add(KhoanThu khoanThu) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KhoanThu.COL_TEN_THU, khoanThu.getTen());
        contentValues.put(KhoanThu.COL_TIEN_THU, khoanThu.getTien());
        contentValues.put(KhoanThu.COL_DATE_THU, khoanThu.getNgay());
        contentValues.put(KhoanThu.COL_FK_ID_LT, khoanThu.getId_lt());
        long res = database.insert(KhoanThu.TB_NAME, null, contentValues);
        return res;
    }

    public ArrayList<KhoanThu> GetAll() {
        ArrayList<KhoanThu> list = new ArrayList<>();
        String data[] = {
                KhoanThu.COL_ID_THU, KhoanThu.COL_TEN_THU, KhoanThu.COL_TIEN_THU, KhoanThu.COL_DATE_THU, KhoanThu.COL_ID_USE_THU, KhoanThu.COL_FK_ID_LT
        };
        Cursor cursor = database.query(KhoanThu.TB_NAME, data, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            KhoanThu khoanThu = new KhoanThu();
            int id = cursor.getInt(0);
            String ten = cursor.getString(1);
            double tien = cursor.getDouble(2);
            String ngay = cursor.getString(3);
            int id_user = cursor.getInt(4);
            int id_lc = cursor.getInt(5);
            khoanThu.setId(id);
            khoanThu.setTen(ten);
            khoanThu.setTien(tien);
            khoanThu.setNgay(ngay);
            khoanThu.setId_user(id_user);
            khoanThu.setId_lt(id_lc);
            list.add(khoanThu);
            cursor.moveToNext();
        }
        return list;
    }

    public int Update(KhoanThu khoanThu) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KhoanThu.COL_TEN_THU, khoanThu.getTen());
        contentValues.put(KhoanThu.COL_TIEN_THU, khoanThu.getTien());
        contentValues.put(KhoanThu.COL_DATE_THU, khoanThu.getNgay());
        contentValues.put(KhoanThu.COL_FK_ID_LT, khoanThu.getId_lt());
        int res = database.update(KhoanThu.TB_NAME, contentValues, "id =" + khoanThu.getId(), null);
        return res;
    }

    public int Delete(KhoanThu khoanThu) {
        return database.delete(KhoanThu.TB_NAME, "id=" + khoanThu.getId(), null);

    }

    public int SumTien() {
        String sum = "SELECT SUM(so_tien) FROM thu";
        Cursor cursor = database.rawQuery(sum, null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }
}
