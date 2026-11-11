# Badminton Court Reservation System

Aplikasi Java console untuk reservasi lapangan bulu tangkis. Dibuat untuk UTS mata kuliah Pemrograman Berorientasi Objek, Program Studi Sistem Informasi.

## Anggota Kelompok

| Nama | NIM |
|---|---|
| Ida Bagus Putu Angga Lesmana | 2501010066 |
| I Putu Bryan Aditya Nugraha | 2501010016 |
| Mas Bagus Khrisna Adinata | 2501010098 |

## Fitur

- Lihat dan ubah data lapangan (nama, tarif dasar, ketersediaan)
- Pendaftaran member dengan validasi nomor HP
- Booking lapangan dengan cek bentrok jadwal
- Perhitungan tarif otomatis (polymorphism) dan diskon member
- Cetak struk dan riwayat transaksi
- Penanganan error (exception) yang lengkap

## Struktur Project

```
src/
  model/      Lapangan (abstract), LapanganVinyl, LapanganKarpet, Member, Booking
  service/    BookingManager
  dao/        BookingDAO
  exception/  JadwalBentrokException
  app/        Main
bukti-output/  Bukti output program (alur utama & skenario error)
```

## Cara Menjalankan

Tanpa Maven/Gradle, hanya JDK 17+ (tanpa library eksternal).

### VS Code
1. Buka folder ini (`File > Open Folder`).
2. Buka `src/app/Main.java`, klik **Run** di atas `main`.
3. Ketik input di tab **Terminal** (bukan Debug Console).

### Apache NetBeans
1. `File > New Project > Java with Ant > Java Application`, hilangkan centang "Create Main Class".
2. Salin folder `model`, `service`, `dao`, `exception`, `app` dari `src` ke Source Packages.
3. Project Properties: JDK 17, Source/Binary Format 17, Encoding UTF-8, Main Class `app.Main`.
4. Clean and Build lalu F6.

### Terminal
```bash
javac -d out src/exception/*.java src/model/*.java src/dao/*.java src/service/*.java src/app/*.java
java -cp out app.Main
```

## Konsep OOP yang Diterapkan

- **Encapsulation** - atribut `private` dengan getter/setter, konstanta diskon di `Member`
- **Inheritance** - `LapanganVinyl` dan `LapanganKarpet` extends `Lapangan`
- **Polymorphism** - override `hitungTarif()` dipanggil lewat referensi `Lapangan`
- **Abstraction** - `Lapangan` abstract, `BookingDAO` menyembunyikan detail penyimpanan
- **Exception Handling** - `JadwalBentrokException`, `IllegalArgumentException`, `NumberFormatException`, `DateTimeParseException`

## Data Awal

| ID | Jenis | Nama | Tarif Dasar |
|---|---|---|---|
| L01 | LapanganVinyl | Lapangan A | 50000 |
| L02 | LapanganVinyl | Lapangan B | 50000 |
| L03 | LapanganKarpet | Lapangan C | 50000 |

Member awal: M001 Budi Santoso (aktif), M002 Sari Dewi (tidak aktif).
