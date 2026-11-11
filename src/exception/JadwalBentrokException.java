// Ida Bagus Putu Angga Lesmana | 2501010066
// I Putu Bryan Aditya Nugraha | 2501010016
// Mas Bagus Khrisna Adinata | 2501010098
package exception;

// [OOP: Exception Handling] Exception buatan sendiri (checked) untuk jadwal bentrok.
public class JadwalBentrokException extends Exception {

    public JadwalBentrokException(String msg) {
        super(msg);
    }
}
