package vuplph13134.fpoly.assignment.DTO;

public class KhoanChi {
    private int id;
    private String ten;
    private double tien;
    private String ngay;
    private int id_user;
    private int id_lc;

    public static final String TB_NAME = "chi";
    public static final String COL_ID_CHI = "id";
    public static final String COL_TEN_CHI = "ten";
    public static final String COL_TIEN_CHI = "so_tien";
    public static final String COL_DATE_CHI = "ngay";
    public static final String COL_ID_USE_CHI = "id_user";
    public static final String COL_FK_IDLC = "id_lc";

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

    public int getId_lc() {
        return id_lc;
    }

    public void setId_lc(int id_lc) {
        this.id_lc = id_lc;
    }
}
