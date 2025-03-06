package edu.school21.chat.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Message {
    private Long messageId;
    private User author;
    private Chatroom room;
    private String text;
    private LocalDateTime dateTime;

    @Override
    public int hashCode() {
        return Objects.hash(messageId, author, room);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Message message = (Message) obj;
        return Objects.equals(this.messageId, message.messageId) &&
                Objects.equals(this.author, message.author) &&
                Objects.equals(this.room, message.room);
    }

    @Override
    public String toString() {
        return "Message {" +
                "\n messageId=" + messageId +
                ",\n author='" + author + '\'' +
                ",\n room='" + room + '\'' +
                ",\n text='" + text + '\'' +
                ",\n dateTime='" + dateTime + '\'' +
                "\n}";
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Chatroom getRoom() {
        return room;
    }

    public void setRoom(Chatroom room) {
        this.room = room;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
