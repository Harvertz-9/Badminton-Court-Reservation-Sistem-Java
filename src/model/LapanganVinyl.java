// Ida Bagus Putu Angga Lesmana | 2501010066
// I Putu Bryan Aditya Nugraha | 2501010016
// Mas Bagus Khrisna Adinata | 2501010098
package model;

// [OOP: Inheritance] LapanganVinyl mewarisi (extends) Lapangan.
public class LapanganVinyl extends Lapangan {

    public LapanganVinyl(String id, String nama, double tarifDasar) {
        super(id, nama, tarifDasar);
    }

    // [OOP: Polymorphism] Override hitungTarif(): tarif penuh per jam.
    @Override
    public double hitungTarif() {
        return tarifDasar;
    }
}
