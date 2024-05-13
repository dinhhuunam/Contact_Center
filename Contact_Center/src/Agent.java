import java.time.LocalTime;

public class Agent {
    int id;
    //trang thái của agent
    //1 - đang rảnh
    //0 - bận
    String status;
    LocalTime hangup;//thời gian sau khi nói chuyện với khách hàng

    public Agent(int id, String status, LocalTime hangup) {
        this.id = id;
        this.status = status;
        this.hangup = hangup;
    }

    @Override
    public String toString() {
        return "Agent{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", hangup=" + hangup +
                '}';
    }
}
