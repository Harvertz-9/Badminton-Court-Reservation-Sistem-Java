// Ida Bagus Putu Angga Lesmana | 2501010066
// I Putu Bryan Aditya Nugraha | 2501010016
// Mas Bagus Khrisna Adinata | 2501010098
package service;

import dao.BookingDAO;
import exception.JadwalBentrokException;
import java.util.ArrayList;
import java.util.List;
import model.Booking;

public class BookingManager {

    private final List<Booking> daftarBooking;
    private final BookingDAO dao;

    public BookingManager(BookingDAO dao) {
        this.dao = dao;
        this.daftarBooking = new ArrayList<>(dao.ambilSemua());
    }

    // [OOP: Exception Handling] Melempar exception checked saat jadwal bentrok.
    public void tambahBooking(Booking b) throws JadwalBentrokException {
        if (b == null) {
            throw new IllegalArgumentException("Booking tidak boleh null");
        }
        for (Booking lama : daftarBooking) {
            if (lama.getId().equals(b.getId())) {
                throw new IllegalArgumentException("ID booking sudah dipakai");
            }
        }
        if (cekBentrok(b)) {
            throw new JadwalBentrokException("Jadwal bentrok pada " + b.getLapangan().getNama()
                    + " tanggal " + b.getTanggal().format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-uuuu"))
                    + " jam " + b.getJamMulai() + " - " + b.getJamSelesai());
        }
        dao.simpan(b);
        daftarBooking.add(b);
    }

    public boolean cekBentrok(Booking b) {
        int mulaiBaru = b.getJamMulai().toSecondOfDay();
        int selesaiBaru = mulaiBaru + b.getDurasiJam() * 3600;
        for (Booking lama : daftarBooking) {
            if (!lama.getLapangan().getId().equals(b.getLapangan().getId())) {
                continue;
            }
            if (!lama.getTanggal().equals(b.getTanggal())) {
                continue;
            }
            int mulaiLama = lama.getJamMulai().toSecondOfDay();
            int selesaiLama = mulaiLama + lama.getDurasiJam() * 3600;
            if (mulaiBaru < selesaiLama && mulaiLama < selesaiBaru) {
                return true;
            }
        }
        return false;
    }

    public List<Booking> getRiwayat() {
        return new ArrayList<>(daftarBooking);
    }
}
