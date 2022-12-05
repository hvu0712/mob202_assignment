package vuplph13134.fpoly.assignment.DTO;

public class LoaiThu {
    private int id;
    private String ten;
    public static final String TB_NAME = "loai_thu";
    public static final String ID_LT = "id_lt";
    public static final String TEN_LT = "ten";

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
