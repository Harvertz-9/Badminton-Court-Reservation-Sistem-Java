// Ida Bagus Putu Angga Lesmana | 2501010066
// I Putu Bryan Aditya Nugraha | 2501010016
// Mas Bagus Khrisna Adinata | 2501010098
package app;

import dao.BookingDAO;
import exception.JadwalBentrokException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import model.Booking;
import model.Lapangan;
import model.LapanganKarpet;
import model.LapanganVinyl;
import model.Member;
import service.BookingManager;

public class Main {

    private static final DateTimeFormatter FORMAT_TANGGAL =
            DateTimeFormatter.ofPattern("dd-MM-uuuu").withResolverStyle(ResolverStyle.STRICT);
    private static final DateTimeFormatter FORMAT_JAM =
            DateTimeFormatter.ofPattern("HH:mm");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            // Identitas kelompok.
            System.out.println();
            System.out.println("Kelompok 4");
            System.out.println();
            System.out.println("I Putu Bryan Aditya Nugraha | 2501010016");
            System.out.println("Ida Bagus Putu Angga Lesmana | 2501010066");
            System.out.println("Mas Bagus Khrisna Adinata | 2501010098");
            System.out.println();

            // [OOP: Polymorphism] List bertipe Lapangan diisi objek subclass.
            List<Lapangan> daftarLapangan = new ArrayList<>();
            daftarLapangan.add(new LapanganVinyl("L01", "Lapangan A", 50000));
            daftarLapangan.add(new LapanganVinyl("L02", "Lapangan B", 50000));
            daftarLapangan.add(new LapanganKarpet("L03", "Lapangan C", 50000));

            List<Member> daftarMember = new ArrayList<>();
            daftarMember.add(new Member("M001", "Budi Santoso", "081234567890"));
            Member sari = new Member("M002", "Sari Dewi", "085798765432");
            sari.setAktif(false);
            daftarMember.add(sari);

            BookingManager manager = new BookingManager(new BookingDAO());

            boolean jalan = true;
            while (jalan) {
                tampilkanMenu();
                String pilihan = scanner.nextLine().trim();
                try {
                    switch (pilihan) {
                        case "1":
                            lihatLapangan(daftarLapangan);
                            break;
                        case "2":
                            ubahLapangan(scanner, daftarLapangan);
                            break;
                        case "3":
                            daftarMemberBaru(scanner, daftarMember);
                            break;
                        case "4":
                            lihatMember(daftarMember);
                            break;
                        case "5":
                            buatBooking(scanner, daftarLapangan, daftarMember, manager);
                            break;
                        case "6":
                            lihatRiwayat(manager);
                            break;
                        case "7":
                            tandaiLunas(scanner, manager);
                            break;
                        case "0":
                            jalan = false;
                            System.out.println("Terima kasih telah menggunakan aplikasi.");
                            break;
                        default:
                            System.out.println("Pilihan tidak valid");
                            break;
                    }
                } catch (JadwalBentrokException e) {
                    System.out.println("Jadwal bentrok: " + e.getMessage());
                } catch (DateTimeParseException e) {
                    System.out.println("Format tanggal/jam salah: " + e.getMessage());
                } catch (NumberFormatException e) {
                    System.out.println("Input harus berupa angka");
                } catch (IllegalArgumentException e) {
                    System.out.println("Data tidak valid: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Terjadi kesalahan: " + e.getMessage());
                } finally {
                    System.out.println("--- Proses selesai ---");
                }
            }
        } finally {
            scanner.close();
        }
    }

    private static void tampilkanMenu() {
        System.out.println("===== BADMINTON COURT RESERVATION SYSTEM =====");
        System.out.println("1. Lihat daftar lapangan");
        System.out.println("2. Ubah data lapangan (nama / tarif dasar / ketersediaan)");
        System.out.println("3. Daftarkan member baru");
        System.out.println("4. Lihat daftar member");
        System.out.println("5. Buat booking");
        System.out.println("6. Lihat riwayat transaksi");
        System.out.println("7. Tandai booking LUNAS");
        System.out.println("0. Keluar");
        System.out.print("Pilih menu: ");
    }

    private static void lihatLapangan(List<Lapangan> daftarLapangan) {
        System.out.println("---------------------------------------------------------------------------");
        System.out.printf("%-5s %-16s %-12s %-14s %-14s %-10s%n",
                "ID", "Jenis", "Nama", "Tarif Dasar", "Tarif/Jam", "Tersedia");
        System.out.println("---------------------------------------------------------------------------");
        // [OOP: Polymorphism] satu perulangan memanggil hitungTarif() versi tiap subclass.
        for (Lapangan l : daftarLapangan) {
            System.out.printf("%-5s %-16s %-12s %-14s %-14s %-10s%n",
                    l.getId(), l.getClass().getSimpleName(), l.getNama(),
                    rupiah(l.getTarifDasar()), rupiah(l.hitungTarif()),
                    l.isTersedia() ? "Ya" : "Tidak");
        }
        System.out.println("---------------------------------------------------------------------------");
    }

    private static void ubahLapangan(Scanner scanner, List<Lapangan> daftarLapangan) {
        System.out.print("Masukkan ID lapangan: ");
        String id = scanner.nextLine().trim();
        Lapangan lapangan = cariLapangan(daftarLapangan, id);
        if (lapangan == null) {
            System.out.println("Lapangan tidak ditemukan");
            return;
        }
        System.out.print("Nama baru (kosongkan jika tidak diubah): ");
        String nama = scanner.nextLine().trim();
        if (!nama.isEmpty()) {
            lapangan.setNama(nama);
        }
        System.out.print("Tarif dasar baru (kosongkan jika tidak diubah): ");
        String tarif = scanner.nextLine().trim();
        if (!tarif.isEmpty()) {
            double nilai = Double.parseDouble(tarif);
            lapangan.setTarifDasar(nilai);
        }
        System.out.print("Ketersediaan (1=tersedia, 0=tidak tersedia, kosong=tidak diubah): ");
        String status = scanner.nextLine().trim();
        if (status.equals("1")) {
            lapangan.setTersedia(true);
        } else if (status.equals("0")) {
            lapangan.setTersedia(false);
        } else if (!status.isEmpty()) {
            System.out.println("Pilihan ketersediaan tidak valid");
        }
        System.out.println("Data lapangan berhasil diperbarui");
    }

    private static void daftarMemberBaru(Scanner scanner, List<Member> daftarMember) {
        System.out.print("Nama member: ");
        String nama = scanner.nextLine().trim();
        System.out.print("Nomor HP: ");
        String noHp = scanner.nextLine().trim();
        String id = idMemberBerikutnya(daftarMember);
        Member member = new Member(id, nama, noHp);
        daftarMember.add(member);
        System.out.println("Member " + member.getNama()
                + " berhasil didaftarkan dengan ID " + member.getId());
    }

    private static void lihatMember(List<Member> daftarMember) {
        System.out.println("------------------------------------------------------");
        System.out.printf("%-6s %-18s %-14s %-8s%n", "ID", "Nama", "No HP", "Aktif");
        System.out.println("------------------------------------------------------");
        for (Member m : daftarMember) {
            System.out.printf("%-6s %-18s %-14s %-8s%n",
                    m.getId(), m.getNama(), m.getNoHp(), m.isAktif() ? "Ya" : "Tidak");
        }
        System.out.println("------------------------------------------------------");
    }

    private static void buatBooking(Scanner scanner, List<Lapangan> daftarLapangan,
            List<Member> daftarMember, BookingManager manager) throws JadwalBentrokException {
        System.out.print("ID lapangan: ");
        Lapangan lapangan = cariLapangan(daftarLapangan, scanner.nextLine().trim());
        if (lapangan == null) {
            System.out.println("Lapangan tidak ditemukan");
            return;
        }
        if (!lapangan.isTersedia()) {
            System.out.println("Lapangan sedang tidak tersedia, booking dibatalkan");
            return;
        }
        System.out.print("Tanggal (dd-MM-yyyy): ");
        LocalDate tanggal = LocalDate.parse(scanner.nextLine().trim(), FORMAT_TANGGAL);
        System.out.print("Jam mulai (HH:mm): ");
        LocalTime jamMulai = LocalTime.parse(scanner.nextLine().trim());
        System.out.print("Durasi (jam): ");
        int durasi = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Jenis penyewa (1=Member, 2=Non-member): ");
        String jenis = scanner.nextLine().trim();

        String id = idBookingBerikutnya(manager);
        Booking booking;
        if (jenis.equals("1")) {
            System.out.print("ID member: ");
            Member member = cariMember(daftarMember, scanner.nextLine().trim());
            if (member == null) {
                System.out.println("Member tidak ditemukan");
                return;
            }
            booking = new Booking(id, member.getNama(), tanggal, jamMulai, durasi, lapangan, member);
        } else if (jenis.equals("2")) {
            System.out.print("Nama penyewa: ");
            String namaPenyewa = scanner.nextLine().trim();
            booking = new Booking(id, namaPenyewa, tanggal, jamMulai, durasi, lapangan, null);
        } else {
            System.out.println("Jenis penyewa tidak valid");
            return;
        }
        manager.tambahBooking(booking);
        System.out.println(booking.cetakStruk());
    }

    private static void lihatRiwayat(BookingManager manager) {
        List<Booking> riwayat = manager.getRiwayat();
        if (riwayat.isEmpty()) {
            System.out.println("Belum ada transaksi");
            return;
        }
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.printf("%-6s %-16s %-12s %-12s %-14s %-12s %-12s%n",
                "ID", "Penyewa", "Lapangan", "Tanggal", "Jam", "Total", "Status");
        System.out.println("---------------------------------------------------------------------------------------");
        for (Booking b : riwayat) {
            String jam = b.getJamMulai().format(FORMAT_JAM) + "-"
                    + b.getJamSelesai().format(FORMAT_JAM);
            System.out.printf("%-6s %-16s %-12s %-12s %-14s %-12s %-12s%n",
                    b.getId(), b.getNamaPenyewa(), b.getLapangan().getNama(),
                    b.getTanggal().format(FORMAT_TANGGAL), jam,
                    rupiah(b.getTotalBiaya()), b.getStatusBayar());
        }
        System.out.println("---------------------------------------------------------------------------------------");
    }

    private static void tandaiLunas(Scanner scanner, BookingManager manager) {
        System.out.print("ID booking: ");
        String id = scanner.nextLine().trim();
        for (Booking b : manager.getRiwayat()) {
            if (b.getId().equals(id)) {
                b.setStatusBayar("LUNAS");
                System.out.println("Booking " + id + " berhasil ditandai LUNAS");
                return;
            }
        }
        System.out.println("Booking tidak ditemukan");
    }

    private static Lapangan cariLapangan(List<Lapangan> daftarLapangan, String id) {
        for (Lapangan l : daftarLapangan) {
            if (l.getId().equalsIgnoreCase(id)) {
                return l;
            }
        }
        return null;
    }

    private static Member cariMember(List<Member> daftarMember, String id) {
        for (Member m : daftarMember) {
            if (m.getId().equalsIgnoreCase(id)) {
                return m;
            }
        }
        return null;
    }

    private static String idMemberBerikutnya(List<Member> daftarMember) {
        return String.format("M%03d", daftarMember.size() + 1);
    }

    private static String idBookingBerikutnya(BookingManager manager) {
        return String.format("B%03d", manager.getRiwayat().size() + 1);
    }

    private static String rupiah(double nilai) {
        return String.format(Locale.forLanguageTag("id-ID"), "Rp%,.0f", nilai);
    }
}
