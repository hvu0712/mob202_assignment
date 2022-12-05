package vuplph13134.fpoly.assignment.CreateDataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class CreateDataBase extends SQLiteOpenHelper {
    public static final String DB_NAME = "QuanLyThuChi.db";
    public static final int VER_SION = 1;

    public CreateDataBase(Context context) {
        super(context, DB_NAME, null, VER_SION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String loai_chi = "CREATE TABLE loai_chi(id_lc INTEGER NOT NULL UNIQUE, ten TEXT NOT NULL UNIQUE, PRIMARY KEY (id_lc AUTOINCREMENT))";
        String chi = "CREATE TABLE chi (id INTEGER NOT NULL UNIQUE, ten TEXT NOT NULL, so_tien REAL NOT NULL, ngay TEXT NOT NULL, id_user INTEGER, id_lc INTEGER NOT NULL, PRIMARY KEY(id AUTOINCREMENT), FOREIGN KEY (id_lc) REFERENCES loai_chi(id_lc) ON DELETE CASCADE)";
        String loai_thu = "CREATE TABLE loai_thu (id_lt INTEGER NOT NULL UNIQUE , ten TEXT NOT NULL UNIQUE, PRIMARY KEY (id_lt AUTOINCREMENT))";
        String thu = "CREATE TABLE thu(id INTEGER NOT NULL UNIQUE, ten TEXT NOT NULL, so_tien REAL NOT NULL, ngay TEXT NOT NULL, id_user INTEGER, id_lt INTEGER NOT NULL, PRIMARY KEY(id AUTOINCREMENT), FOREIGN KEY (id_lt) REFERENCES loai_thu(id_lt) ON DELETE CASCADE)";
        db.execSQL(loai_chi);
        db.execSQL(chi);
        db.execSQL(loai_thu);
        db.execSQL(thu);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
