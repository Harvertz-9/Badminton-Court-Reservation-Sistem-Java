// Ida Bagus Putu Angga Lesmana | 2501010066
// I Putu Bryan Aditya Nugraha | 2501010016
// Mas Bagus Khrisna Adinata | 2501010098
package dao;

import java.util.ArrayList;
import java.util.List;
import model.Booking;

// [OOP: Abstraction] DAO menyembunyikan cara penyimpanan data dari class lain.
// Untuk UTS data disimpan di ArrayList (memori). Pada UAS isi method diganti
// dengan JDBC MySQL tanpa mengubah tanda tangan (signature) method.
public class BookingDAO {

    private final List<Booking> data = new ArrayList<>();

    public void simpan(Booking b) {
        data.add(b);
    }

    public List<Booking> ambilSemua() {
        return new ArrayList<>(data);
    }

    public void hapus(String id) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId().equals(id)) {
                data.remove(i);
                return;
            }
        }
    }
}
