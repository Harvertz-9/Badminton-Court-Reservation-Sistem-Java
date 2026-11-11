// Ida Bagus Putu Angga Lesmana | 2501010066
// I Putu Bryan Aditya Nugraha | 2501010016
// Mas Bagus Khrisna Adinata | 2501010098
package model;

// [OOP: Inheritance] LapanganKarpet mewarisi (extends) Lapangan.
public class LapanganKarpet extends Lapangan {

    public LapanganKarpet(String id, String nama, double tarifDasar) {
        super(id, nama, tarifDasar);
    }

    // [OOP: Polymorphism] Override hitungTarif(): tarif karpet lebih murah 20%.
    @Override
    public double hitungTarif() {
        return tarifDasar * 0.8;
    }
}
