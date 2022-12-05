package vuplph13134.fpoly.assignment.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import vuplph13134.fpoly.assignment.CreateDataBase.CreateDataBase;
import vuplph13134.fpoly.assignment.DTO.LoaiThu;

import java.util.ArrayList;

public class LoaiThuDao {
    SQLiteDatabase database;
    CreateDataBase createDataBase;

    public LoaiThuDao(Context context) {
        createDataBase = new CreateDataBase(context);
    }

    public void open() {
        database = createDataBase.getWritableDatabase();
    }

    public void close() {
        createDataBase.close();
    }

    public long Add(LoaiThu loaiThu) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(LoaiThu.TEN_LT, loaiThu.getTen());
        long res = database.insert(LoaiThu.TB_NAME, null, contentValues);
        return res;
    }

    public ArrayList<LoaiThu> GetAll() {
        ArrayList<LoaiThu> list = new ArrayList<>();
        String data[] = {
                LoaiThu.ID_LT, LoaiThu.TEN_LT
        };
        Cursor cursor = database.query(LoaiThu.TB_NAME, data, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            LoaiThu loaiThu = new LoaiThu();
            loaiThu.setId(cursor.getInt(0));
            loaiThu.setTen(cursor.getString(1));
            list.add(loaiThu);
            cursor.moveToNext();
        }
        return list;
    }

    public int Update(LoaiThu loaiThu) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(LoaiThu.TEN_LT, loaiThu.getTen());
        int res = database.update(LoaiThu.TB_NAME, contentValues, "id_lt=" + loaiThu.getId(), null);
        return res;
    }
    public int Delete(LoaiThu loaiThu){
        return database.delete(LoaiThu.TB_NAME,"id_lt="+loaiThu.getId(),null);
    }
}
