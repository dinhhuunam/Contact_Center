import java.time.LocalTime;

public class Call {
    String agent_id;
    int customer_id;
    LocalTime call_at;
    LocalTime pick_up_at;
    LocalTime tranfer_at;
    LocalTime hangup_at;
    String status;

    public Call(String agent_id, int customer_id, LocalTime call_at, LocalTime pick_up_at, LocalTime tranfer_at, LocalTime hangup_at, String status) {
        this.agent_id = agent_id;
        this.customer_id = customer_id;
        this.call_at = call_at;
        this.pick_up_at = pick_up_at;
        this.tranfer_at = tranfer_at;
        this.hangup_at = hangup_at;
        this.status = status;
    }
    public Call(String agent_id, LocalTime call_at, LocalTime pick_up_at, LocalTime tranfer_at, LocalTime hangup_at, String status) {
        this.agent_id = agent_id;
        this.call_at = call_at;
        this.pick_up_at = pick_up_at;
        this.tranfer_at = tranfer_at;
        this.hangup_at = hangup_at;
        this.status = status;
    }
    public Call(LocalTime call_at, LocalTime pick_up_at, LocalTime tranfer_at, LocalTime hangup_at) {
        this.call_at = call_at;
        this.pick_up_at = pick_up_at;
        this.tranfer_at = tranfer_at;
        this.hangup_at = hangup_at;
    }
    public Call(LocalTime hangup_at) {
        this.hangup_at = hangup_at;
    }

    @Override
    public String toString() {
        return "Call{" +
                "agent_id=" + agent_id +
                ", customer_id=" + customer_id +
                ", call_at=" + call_at +
                ", pick_up_at=" + pick_up_at +
                ", tranfer_at=" + tranfer_at +
                ", hangup_at=" + hangup_at +
                ", status='" + status + '\'' +
                '}';
    }
}
