// Ida Bagus Putu Angga Lesmana | 2501010066
// I Putu Bryan Aditya Nugraha | 2501010016
// Mas Bagus Khrisna Adinata | 2501010098
package model;

public class Member {

    // [OOP: Encapsulation] Konstanta diskon disembunyikan di dalam class.
    private static final double DISKON_AKTIF = 0.10;

    private String id;
    private String nama;
    private String noHp;
    private boolean aktif;

    public Member(String id, String nama, String noHp) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID member tidak boleh kosong");
        }
        if (nama == null || nama.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama member tidak boleh kosong");
        }
        this.id = id;
        this.nama = nama;
        setNoHp(noHp);
        this.aktif = true;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        if (noHp == null || !noHp.matches("[0-9]{10,13}")) {
            throw new IllegalArgumentException("Nomor HP harus berupa angka 10-13 digit");
        }
        this.noHp = noHp;
    }

    public boolean isAktif() {
        return aktif;
    }

    public void setAktif(boolean aktif) {
        this.aktif = aktif;
    }

    // [OOP: Encapsulation] Diskon dihitung di dalam class, bukan di luar.
    public double getDiskon() {
        return aktif ? DISKON_AKTIF : 0.0;
    }
}
