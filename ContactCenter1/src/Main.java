import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Main {
    //Hàm Main
    public static void main(String[] args) {
        double spin_call = 2.5;
        //số lượng agent ban đầu
        int agent = 15;
        //Random pickup rate by hour
        double[] PICKUP_RATE_BY_HOUR = {0.5, 0.5, 0.4, 0.4, 0.2, 0.2, 0.3, 0.3, 0.4, 0.5, 0.7, 0.8, 0.8};
        List<Time> list_pickUp = new ArrayList<>();
        int point = 0;
        for (int i = 8; i <= 20; i++) {
            LocalTime start = LocalTime.of(i, 0, 0);
            LocalTime end = LocalTime.of(i + 1, 0, 0);
            Time time = new Time(start, end, PICKUP_RATE_BY_HOUR[point]);
            list_pickUp.add(time);
            if (point < 11) point++;
        }
        //check kiểm tra xác suất theo thời gian
        for(Time time : list_pickUp){
            System.out.println(time);
        }
    }
}