package cems;

import java.sql.Timestamp;

public class Notification {
    private int noteId;
    private Integer userId;
    private String message;
    private Timestamp createdAt;

    public Notification() {}

    // getters & setters
    public int getNoteId() { return noteId; }
    public void setNoteId(int noteId) { this.noteId = noteId; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
