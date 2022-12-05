package vuplph13134.fpoly.assignment.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import vuplph13134.fpoly.assignment.CreateDataBase.CreateDataBase;
import vuplph13134.fpoly.assignment.DTO.LoaiChi;

import java.util.ArrayList;

public class LoaiChiDao {
    SQLiteDatabase database;
    CreateDataBase createDataBase;

    public LoaiChiDao(Context context) {
        createDataBase = new CreateDataBase(context);
    }

    public void open() {
        database = createDataBase.getWritableDatabase();
    }

    public void close() {
        createDataBase.close();
    }

    public long Add(LoaiChi loaiChi) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(LoaiChi.TEN_LC, loaiChi.getTen());
        long kq = database.insert(LoaiChi.TB_NAME, null, contentValues);
        return kq;
    }

    public ArrayList<LoaiChi> GetAll() {
        ArrayList<LoaiChi> list = new ArrayList<>();
        String data[] = {LoaiChi.ID_LC, LoaiChi.TEN_LC};
        Cursor cursor = database.query(LoaiChi.TB_NAME, data, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String ten = cursor.getString(1);
            LoaiChi loaiChi = new LoaiChi();
            loaiChi.setId(id);
            loaiChi.setTen(ten);
            list.add(loaiChi);
            cursor.moveToNext();
        }
        return list;
    }

    public int Update(LoaiChi loaiChi) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(LoaiChi.TEN_LC, loaiChi.getTen());
        int res = database.update(LoaiChi.TB_NAME, contentValues, "id_lc=" + loaiChi.getId(), null);
        return res;
    }

    public int Delete(LoaiChi loaiChi) {
        return database.delete(LoaiChi.TB_NAME, "id_lc=" + loaiChi.getId(), null);

    }

    public String selectTen(int id) {
        String name = "SELECT ten FROM loai_chi WHERE id_lc =" + id;
        Cursor cursor = database.rawQuery(name, null);
        cursor.moveToFirst();
        return cursor.getString(0);
    }


}
