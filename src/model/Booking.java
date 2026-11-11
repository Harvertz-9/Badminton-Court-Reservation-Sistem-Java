// Ida Bagus Putu Angga Lesmana | 2501010066
// I Putu Bryan Aditya Nugraha | 2501010016
// Mas Bagus Khrisna Adinata | 2501010098
package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Booking {

    private static final DateTimeFormatter FORMAT_TANGGAL =
            DateTimeFormatter.ofPattern("dd-MM-uuuu");
    private static final DateTimeFormatter FORMAT_JAM =
            DateTimeFormatter.ofPattern("HH:mm");

    private String id;
    private String namaPenyewa;
    private LocalDate tanggal;
    private LocalTime jamMulai;
    private int durasiJam;
    private double totalBiaya;
    private String statusBayar;
    private Member member;
    private Lapangan lapangan;

    public Booking(String id, String namaPenyewa, LocalDate tanggal, LocalTime jamMulai,
            int durasiJam, Lapangan lapangan, Member member) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID booking tidak boleh kosong");
        }
        if (namaPenyewa == null || namaPenyewa.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama penyewa tidak boleh kosong");
        }
        if (tanggal == null) {
            throw new IllegalArgumentException("Tanggal tidak boleh null");
        }
        if (jamMulai == null) {
            throw new IllegalArgumentException("Jam mulai tidak boleh null");
        }
        if (lapangan == null) {
            throw new IllegalArgumentException("Lapangan tidak boleh null");
        }
        if (durasiJam < 1 || durasiJam > 8) {
            throw new IllegalArgumentException("Durasi harus antara 1 sampai 8 jam");
        }
        if ((long) jamMulai.toSecondOfDay() + (long) durasiJam * 3600 > 86400) {
            throw new IllegalArgumentException("Waktu selesai tidak boleh melewati tengah malam");
        }
        this.id = id;
        this.namaPenyewa = namaPenyewa;
        this.tanggal = tanggal;
        this.jamMulai = jamMulai;
        this.durasiJam = durasiJam;
        this.lapangan = lapangan;
        this.member = member;
        this.statusBayar = "BELUM LUNAS";
        hitungTotal();
    }

    public String getId() {
        return id;
    }

    public String getNamaPenyewa() {
        return namaPenyewa;
    }

    public LocalDate getTanggal() {
        return tanggal;
    }

    public LocalTime getJamMulai() {
        return jamMulai;
    }

    public LocalTime getJamSelesai() {
        return jamMulai.plusHours(durasiJam);
    }

    public int getDurasiJam() {
        return durasiJam;
    }

    public Lapangan getLapangan() {
        return lapangan;
    }

    public Member getMember() {
        return member;
    }

    public double getTotalBiaya() {
        return totalBiaya;
    }

    public String getStatusBayar() {
        return statusBayar;
    }

    public void setStatusBayar(String status) {
        if (!"BELUM LUNAS".equals(status) && !"LUNAS".equals(status)) {
            throw new IllegalArgumentException("Status bayar hanya 'BELUM LUNAS' atau 'LUNAS'");
        }
        this.statusBayar = status;
    }

    public double hitungTotal() {
        // [OOP: Polymorphism] lapangan bertipe Lapangan, objek asli bisa LapanganVinyl/LapanganKarpet.
        double total = lapangan.hitungTarif() * durasiJam;
        if (member != null) {
            total = total - total * member.getDiskon();
        }
        this.totalBiaya = total;
        return total;
    }

    public String cetakStruk() {
        Locale localeId = Locale.forLanguageTag("id-ID");
        String garis = "================================================";
        String jamSelesai = getJamSelesai().equals(LocalTime.MIDNIGHT)
                ? "24:00"
                : getJamSelesai().format(FORMAT_JAM);
        double tarifPerJam = lapangan.hitungTarif();
        double subtotal = tarifPerJam * durasiJam;

        StringBuilder sb = new StringBuilder();
        sb.append(garis).append("\n");
        sb.append("      STRUK RESERVASI LAPANGAN BULU TANGKIS\n");
        sb.append(garis).append("\n");
        sb.append(String.format("ID Booking     : %s\n", id));
        sb.append(String.format("Nama Penyewa   : %s (%s)\n", namaPenyewa,
                member != null ? "Member" : "Non-member"));
        sb.append(String.format("Lapangan       : %s (%s)\n", lapangan.getNama(),
                lapangan.getClass().getSimpleName()));
        sb.append(String.format("Tanggal        : %s\n", tanggal.format(FORMAT_TANGGAL)));
        sb.append(String.format("Jam            : %s - %s (%d jam)\n",
                jamMulai.format(FORMAT_JAM), jamSelesai, durasiJam));
        sb.append(String.format("Tarif per jam  : %s\n",
                String.format(localeId, "Rp%,.0f", tarifPerJam)));
        sb.append(String.format("Subtotal       : %s\n",
                String.format(localeId, "Rp%,.0f", subtotal)));
        if (member != null) {
            double diskon = member.getDiskon();
            sb.append(String.format("Diskon Member  : %.0f%% (-%s)\n", diskon * 100,
                    String.format(localeId, "Rp%,.0f", subtotal * diskon)));
        } else {
            sb.append("Diskon Member  : -\n");
        }
        sb.append(String.format("Total Biaya    : %s\n",
                String.format(localeId, "Rp%,.0f", totalBiaya)));
        sb.append(String.format("Status Bayar   : %s\n", statusBayar));
        sb.append(garis);
        return sb.toString();
    }
}
