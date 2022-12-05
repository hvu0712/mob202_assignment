package vuplph13134.fpoly.assignment.DTO;

public class LoaiChi {
    private int id;
    private String ten;

    public static final String TB_NAME="loai_chi";
    public static final String ID_LC="id_lc";
    public static final String TEN_LC="ten";

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
}
