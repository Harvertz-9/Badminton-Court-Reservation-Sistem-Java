// Ida Bagus Putu Angga Lesmana | 2501010066
// I Putu Bryan Aditya Nugraha | 2501010016
// Mas Bagus Khrisna Adinata | 2501010098
package model;

// [OOP: Abstraction] Class abstrak sebagai cetak biru semua jenis lapangan.
public abstract class Lapangan {

    // [OOP: Encapsulation] Semua atribut private, akses lewat getter/setter.
    private String id;
    private String nama;
    protected double tarifDasar;
    private boolean tersedia;

    public Lapangan(String id, String nama, double tarifDasar) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID lapangan tidak boleh kosong");
        }
        this.id = id;
        setNama(nama);
        setTarifDasar(tarifDasar);
        this.tersedia = true;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        if (nama == null || nama.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama lapangan tidak boleh kosong");
        }
        this.nama = nama;
    }

    public double getTarifDasar() {
        return tarifDasar;
    }

    public void setTarifDasar(double tarifDasar) {
        if (tarifDasar <= 0) {
            throw new IllegalArgumentException("Tarif dasar harus lebih dari 0");
        }
        this.tarifDasar = tarifDasar;
    }

    public boolean isTersedia() {
        return tersedia;
    }

    public void setTersedia(boolean status) {
        this.tersedia = status;
    }

    // [OOP: Abstraction] Method abstrak yang wajib diimplementasikan subclass.
    public abstract double hitungTarif();
}
