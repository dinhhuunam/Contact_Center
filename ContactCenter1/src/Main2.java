import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Main2 {
    //Hàm khởi tạo list agent ban đầu
    private static void addAgent(List<Agent> list_agent, int num) {
        for (int i = 0; i < num; i++) {
            list_agent.add(new Agent(i + 1, "1", null));
        }
    }

    //kiểm tra xem có bao nhiêu agent đang rảnh
    public static int countAgent(List<Agent> list) {
        int count = 0;
        for (Agent agent : list) {
            if (agent.status.equals("1")) count++;
        }
        return count;
    }
    //random thời gian rung chuông
    public static int ranDomRingTime(int min, int max) {
        double mean = (min + max) / 2.0;
        double stdDev = (max - min) / 6.0;

        Random random = new Random();
        double uniformRandom = random.nextGaussian() * stdDev + mean;

        return (int) Math.round(uniformRandom);
    }
    //random thời gian nói chuyện điện thoại với khách hàng
    //thời gian thực tế sau khi rung chuông
    public static LocalTime addSecondsToTime(LocalTime time) {
        int second = ranDomRingTime(10,30);
        return time.plusSeconds(second);
    }
    //kiểm tra xem call random ra có bị lặp không
    //add vao mang call
    public static void addCallCsv(List<Call> list_call, LocalTime start, int num) {
        List<Call> calls = new ArrayList<>();
        while (calls.size() < num ) {
            LocalTime time = addSecondsToTime(start);
            Call call = new Call(null,0,start,time,null,null,null);
            calls.add(call);
        }
        list_call.addAll(calls);
        list_call.sort(new Comparator<Call>() {
            @Override
            public int compare(Call o1, Call o2) {
                return o1.pick_up_at.compareTo(o2.pick_up_at);
            }
        });
    }
    //random 1 số bất kì trong mảng 0-1
    public static double generateRandomNumber() {
        // Khởi tạo đối tượng Random
        Random random = new Random();
        // Sinh số ngẫu nhiên nằm trong khoảng từ 0 đến 1
        double randomNumber = random.nextDouble();
        return randomNumber;
    }
    //Kiểm tra xem 1 Time bất kì có tồn tại trong mảng list_call không
    public static boolean checkTime(List<Call> list_call, LocalTime time) {
        for(Call call : list_call){
            if(time.equals(call.pick_up_at)){
                return true;
            }
        }
        return false;
    }
    //nếu thời gian bất kì nằm trong list_call thì lấy ra thời gian bắt đầu cuộc gọi
    public static LocalTime getStartTime(List<Call> list_call, LocalTime time){
        LocalTime start = null;
        for(Call call : list_call){
            if(time.equals(call.pick_up_at)){
                start = call.call_at;
                break;
            }
        }return start;
    }
    //kiểm tra thời gian bất kì trong 1 khoảng
    public static boolean isTimeInInterval(LocalTime timeToCheck, LocalTime startTime, LocalTime endTime) {
        return !timeToCheck.isBefore(startTime) && !timeToCheck.isAfter(endTime);
    }
    //Hàm kiểm tra xem khách hàng có nhấc máy không
    public static boolean checkStatus(List<Time> pick_up, LocalTime checkTime, double num) {
        for (Time time : pick_up) {
            if (isTimeInInterval(checkTime, time.start_time, time.end_time)) {
                double point = time.xac_suat;
                //nếu xác suất cuộc gọi <= point tức là trong khoảng khách hàng có nhấc máy
                if (num <= point) return true;
            }
        }
        return false;
    }
    //thời gian thực tế sau khi nói chuyện
    public static LocalTime addSecondsToTalkTime(LocalTime time) {
        int second = ranDomRingTime(15,120);
        return time.plusSeconds(second);
    }
    //kiểm tra xem có agent nào đang rảnh không
    public static Agent checkAgent(List<Agent> list) {
        //sortListAgent(list);
        for (Agent agent : list) {
            if (agent.status.equals("1")) return agent;
        }
        return null;
    }
    //nếu thời gian hiện tại không nằm trong list_wait nhưng nằm trong thời gian hangupat của agent
    public static Agent checkCallAgent(List<Agent> list,LocalTime time){
        //sortListAgent(list);
        for(Agent agent : list){
            if(time.equals(agent.hangup) && agent.status.equals("0")) return agent;
        }
        return null;
    }
    //đánh dấu tất cả các agent có hangupat bằng với current time là rảnh
    public static void updateAgent(List<Agent> list,LocalTime time){
        for(Agent agent : list){
            if(time.equals(agent.hangup)){
                agent.status = "1"; //rảnh
            }
        }
    }
    //tính khoảng thời gian giữa 2 time (tính ra s)
    public static long calculateSecondsDifference(LocalTime time1, LocalTime time2) {
        // Sử dụng phương thức until() của ChronoUnit để tính số giây giữa hai thời điểm
        return Math.abs(ChronoUnit.SECONDS.between(time1, time2));
    }
    //kiểm tra xem cuộc gọi nào vẫn còn đang chờ
    public static Call checkCallWait(List<Call> list){
        for(Call call : list){
            if(call.tranfer_at == null) return call;
        }
        return null;
    }
    //Kiểm tra xem còn bao nhiêu cuộc đang đợi
    public static Call findCall(List<Call> list_csv,LocalTime time){
        for(Call call : list_csv){
            if(call.pick_up_at.equals(time)) return call;
        }
        return null;
    }
    //xóa cuộc gọi đã duyệt đến trong csv
    public static void removeCall(List<Call> list, LocalTime time){
        list.removeIf(call -> time.equals(call.pick_up_at));
    }
    //thêm cuộc gọi vào list_kq
    public static void addToKq(List<Call> list,List<Call> list_kq,LocalTime time, Call call){
        int cnt = countCall(list,time);
        for(int i=1;i<=cnt;i++){
            list_kq.add(call);
        }
    }
    //đếm xem có bao nhiêu cuộc thời gian là pick up at
    public static int countCall(List<Call> calls, LocalTime time){
        int count = 0 ;
        for(Call call : calls){
            if(time.equals(call.pick_up_at)){
                count++;
            }
        }
        return count;
    }
    //cập nhật lại trạng thái Agent
    public static void updateStatusAgent(List<Agent> list,Agent agent){
        for(Agent agent1 : list){
            if(agent.id == agent1.id){
                agent1.status = agent.status;
                agent1.hangup = agent.hangup;
                break;
            }
        }
    }
    //lưu vào list các cuộc gọi đang chờ
    public static void saveListCall(List<Call> list,List<Call> list_wait,LocalTime time, LocalTime start){
        for(Call call : list){
            if(time.equals(call.pick_up_at)){
                list_wait.add(new Call(null,0,start,time,null,null,null));
            }
        }
    }
    public static void main(String[] args) {
        double spin_call = 2;
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
//        for(Time time : list_pickUp){
//            System.out.println(time);
//        }

        //Thời gian bắt đầu mỗi lần quay cuộc gọi mới
        LocalTime start_time = null;
        //List cac agent đang rảnh
        List<Agent> list_agent = new ArrayList<>();
        addAgent(list_agent, agent);

        //List các call sau khi quay số
        List<Call> list_csv = new ArrayList<>();
        //List các call đang đợi 10s
        List<Call> list_wait = new ArrayList<>();
        //List tổng hợp kết quả
        List<Call> list_kq = new ArrayList<>();

        int so_cuoc_quay = 0;
        int dem_call = 0;
        for (int hour = 8; hour <= 21; hour++) {
            for (int minute = 0; minute <= 59; minute++) {
                for (int second = 0; second <= 59; second++) {
                    //thời gian hiện tại
                    LocalTime current = LocalTime.of(hour, minute, second);
                    //System.out.println("Current Time: "+current);
                    //nếu có agent đang rảnh: random danh sách list các cuộc gọi pick put at
                    if (countAgent(list_agent) > 0 && (list_csv.size() < (int) (countAgent(list_agent) * spin_call))) {
                        System.out.println("Nhanh 0");
                        System.out.println("Agent rảnh: " + countAgent(list_agent));
                        so_cuoc_quay = (int) (countAgent(list_agent) * spin_call);
                        System.out.println("so cuoc quay: " + so_cuoc_quay);
                        start_time = LocalTime.of(hour, minute, second);
                        System.out.println("Start time: " + start_time);
                        //sau khi random xong thì add vào list_csv
                        addCallCsv(list_csv, start_time, so_cuoc_quay);
                        System.out.println("List CSV: " + list_csv.size());

                    }
                    //xét xem thời gian hiện tại có nằm trong mảng list_csv không
                    //tức là thời gian hiện tại nằm trong list thời gian rung chuông ring Time
                    if (checkTime(list_csv, current)){
                        int cnt = countCall(list_csv,current);
                        for(int h=1; h<=cnt; h++){
                            //random tỉ lệ nghe máy
                            double res = generateRandomNumber();
                            //nếu khách hàng không nghe máy
                            if (!checkStatus(list_pickUp, current, res)) {
                                System.out.println("Nhánh 1");
                                System.out.println("Current Time: "+current);
                                System.out.println("Số lượng cuộc list_csv: "+list_csv.size());
                                Call call = new Call(null,0, getStartTime(list_csv,current), null, null,addSecondsToTime(getStartTime(list_csv,current)), "unconnected");
                                System.out.println(call);
                                if(call.call_at.isBefore(LocalTime.of(21,0))) list_kq.add(call);
                            }
                            //nếu khách hàng có nghe máy
                            else {
                                //kiểm tra xem có agent nào đang rảnh không
                                //nếu có
                                if (checkAgent(list_agent) != null) {
                                    System.out.println("Nhanh 2");
                                    System.out.println("Số lượng cuộc list_csv: "+list_csv.size());
                                    System.out.println("Current Time: "+current);
                                    //random thời gian nói chuyện
                                    LocalTime hangup_at = addSecondsToTalkTime(current);
                                    //set lại trạng thái của agent lúc này là bận - 0
                                    Agent agent1 = checkAgent(list_agent);
                                    System.out.println("Agent ranh: "+agent1);
                                    agent1.status = "0";
                                    agent1.hangup = hangup_at;
                                    updateStatusAgent(list_agent,agent1);
                                    Call call = new Call(Integer.toString(agent1.id),0, getStartTime(list_csv,current), current, current, hangup_at, "connected");
                                    System.out.println(call);
                                    if(call.call_at.isBefore(LocalTime.of(21,0))) list_kq.add(call);
                                }
                                //hiện tại không có agent nào rảnh thì lưu vào list các cuộc gọi đang chờ
                                else {
                                    System.out.println("Nhanh 3");
                                    System.out.println("Current Time: "+current);
                                    System.out.println("Số lượng cuộc list_csv: "+list_csv.size());
                                    Call call = new Call(null,0,getStartTime(list_csv,current),current,null,null,null);
                                    //thêm vào danh sách chờ
                                    list_wait.add(call);
                                    System.out.println("List Wait: ");

                                }
                            }
                        }
                        removeCall(list_csv,current);
                    }
                    else{
                        //nếu thời gian hiện tại rơi vào thời gian sau khi agent nói chuyện với khách hàng
                        if(checkCallAgent(list_agent,current)!=null){
                            System.out.println("Nhánh 4");
                            System.out.println("Current: "+current);

                            Agent agent1 = checkCallAgent(list_agent,current);
                            System.out.println(list_agent);
                            Call call = checkCallWait(list_wait);
                            System.out.println("CALL 1: "+call);
                            if(call!=null){
                                System.out.println("Nhánh 5");
                                System.out.println("CALL: "+call);
                                //kiểm tra chênh lệch thời gian
                                long time_differ = calculateSecondsDifference(agent1.hangup,call.pick_up_at);
                                System.out.println("time_differ: "+time_differ);
                                if(time_differ <= 10){
                                    System.out.println("Nhánh 6: ");
                                    //random thời gian nói chuyện
                                    LocalTime hangup_at = addSecondsToTalkTime(agent1.hangup);
                                    //call.tranfer_at = agent1.hangup;
                                    //call.hangup_at = hangup_at;
                                    list_wait.remove(call);
                                    Call call1 =  new Call(Integer.toString(agent1.id),0,call.call_at,call.pick_up_at,agent1.hangup,hangup_at,"connected");
                                    if(call1.call_at.isBefore(LocalTime.of(21,0))) list_kq.add(call1);
                                    agent1.status = "0";
                                    agent1.hangup = hangup_at;
                                    updateStatusAgent(list_agent,agent1);
                                    //addToKq(list_csv,list_kq,current,call);
                                    removeCall(list_csv,current);
                                }
                                //sau 10s thì bị abandon
                                else{
                                    System.out.println("Nhanh 7: ");
                                    LocalTime hangup_at = call.pick_up_at.plusSeconds(10);
                                    System.out.println(agent1);
                                    agent1.status = "1";
                                    agent1.hangup = null;
                                    updateStatusAgent(list_agent,agent1);
                                    System.out.println(list_agent);
                                    list_wait.remove(call);
                                    Call call1 = new Call(null,0,call.call_at,call.pick_up_at,null,hangup_at,"abandon");
                                    System.out.println(call1);
                                    if(call1.call_at.isBefore(LocalTime.of(21,0))) list_kq.add(call1);
                                    removeCall(list_csv,current);
                                }
                            }
                        }
                        else{
                            for(Agent agent1 : list_agent){
                                if(agent1.hangup != null && current.isAfter(agent1.hangup)){
                                    agent1.status = "1";
                                    agent1.hangup = null;
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println("Kết quả: ");
        for(int i=0;i<list_kq.size();i++){
            Call call = list_kq.get(i);
            call.customer_id = i+1;
            System.out.println(list_kq.get(i));
        }
        // Ghi dữ liệu vào file CSV
        try (FileWriter writer = new FileWriter("E:\\ContactCenter1\\data.csv")) {
            // Ghi header
            writer.append("agent_id,customer_id,call_at,pickup_at,transfer_at,hangup_at,status,sprin_call");
            writer.append("\n");

            int customerCounter = 1;

            // Ghi dữ liệu từ list_kq
            for (Call call : list_kq) {
                List<String> rowData = new ArrayList<>();
                rowData.add(call.agent_id != null ? call.agent_id : "null");
                rowData.add(String.valueOf(customerCounter)); // Sử dụng biến đếm thay cho customer_id
                rowData.add(call.call_at != null ? call.call_at.toString() : "null");
                rowData.add(call.pick_up_at != null ? call.pick_up_at.toString() : "null");
                rowData.add(call.tranfer_at != null ? call.tranfer_at.toString() : "null");
                rowData.add(call.hangup_at != null ? call.hangup_at.toString() : "null");
                rowData.add(call.status);
                rowData.add("2");
                writer.append(String.join(",", rowData));
                writer.append("\n");

                // Tăng giá trị của biến đếm sau mỗi lần ghi dữ liệu
                customerCounter++;
            }
            System.out.println("CSV file successfully created!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}