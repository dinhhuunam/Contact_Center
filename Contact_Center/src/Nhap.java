import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Nhap {
    //random 1 số bất kì trong mảng 0-1
    public static double generateRandomNumber() {
        // Khởi tạo đối tượng Random
        Random random = new Random();
        // Sinh số ngẫu nhiên nằm trong khoảng từ 0 đến 1
        double randomNumber = random.nextDouble();
        return randomNumber;
    }
    public static void main(String[] args) {
        double res = generateRandomNumber();
        System.out.println(res);
    }
}
