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
        return "Message{" +
                "messageId=" + messageId +
                ", author='" + author + '\'' +
                ", room='" + room + '\'' +
                ", text='" + text + '\'' +
                ", dateTime='" + dateTime + '\'' +
                '}';
    }
}
