package vuplph13134.fpoly.assignment.DTO;

public class KhoanThu {
    private int id;
    private String ten;
    private double tien;
    private String ngay;
    private int id_user;
    private int id_lt;

    public static final String TB_NAME="thu";
    public static final String COL_ID_THU="id";
    public static final String  COL_TEN_THU="ten";
    public static final String  COL_TIEN_THU="so_tien";
    public static final String  COL_DATE_THU="ngay";
    public static final String COL_ID_USE_THU="id_user";
    public static final String COL_FK_ID_LT="id_lt";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public double getTien() {
        return tien;
    }

    public void setTien(double tien) {
        this.tien = tien;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_lt() {
        return id_lt;
    }

    public void setId_lt(int id_lt) {
        this.id_lt = id_lt;
    }
}
