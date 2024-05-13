import java.time.LocalTime;

public class Time {
    LocalTime start_time;
    LocalTime end_time;
    double xac_suat;
    public Time(LocalTime start_time, LocalTime end_time, double xac_suat) {
        this.start_time = start_time;
        this.end_time = end_time;
        this.xac_suat = xac_suat;
    }

    @Override
    public String toString() {
        return "Time{" +
                "start_time=" + start_time +
                ", end_time=" + end_time +
                ", xac_suat=" + xac_suat +
                '}';
    }
}
