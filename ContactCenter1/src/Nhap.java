import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Customer {
    int id;
    String name;
    public Customer(int id, String name) {
        this.id = id;
        this.name = name;
    }
    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
public class Nhap {
    public static void in(List<Customer> list){
        for(Customer customer:list){
            System.out.println(customer);
        }
    }
    public static int ranDomRingTime(){
        Random random = new Random();
        int min_ring_time = 10;
        int max_ring_time = 30;
        int range = max_ring_time - min_ring_time + 1;
        int randomNum = random.nextInt(range) + min_ring_time;
        return randomNum;
    }
    public static long calculateSecondsDifference(LocalTime time1, LocalTime time2) {
        // Sử dụng phương thức until() của ChronoUnit để tính số giây giữa hai thời điểm
        return Math.abs(ChronoUnit.SECONDS.between(time1, time2));
    }
    public static void main(String[] args) {
//        List<Customer> list = new ArrayList<>();
//        Customer customer1 = new Customer(1,"Nam");
//        Customer customer2 = new Customer(2,"Đinh Văn Ro");
//
//        list.add(customer1);
//        list.add(customer2);
//        //in(list);
//
//        Customer customer = list.get(0);
//        customer.id = 4;
//        in(list);
        LocalTime time1= LocalTime.of(8,0,10);
        LocalTime time2 = LocalTime.of(9,0,20);
        System.out.println(calculateSecondsDifference(time1,time2));
    }
}
