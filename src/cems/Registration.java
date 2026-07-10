package cems;

import java.sql.Timestamp;

public class Registration {
    private int regId;
    private int eventId;
    private int studentId;
    private Timestamp regTime;
    private String status;

    public Registration() {}

    // getters and setters
    public int getRegId() { return regId; }
    public void setRegId(int regId) { this.regId = regId; }
    public int getEventId() { return eventId; }
    public void setEventId(int eventId) { this.eventId = eventId; }
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public Timestamp getRegTime() { return regTime; }
    public void setRegTime(Timestamp regTime) { this.regTime = regTime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
