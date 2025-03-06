package edu.school21.chat.models;

import java.util.List;
import java.util.Objects;

public class Chatroom {
    private Long roomId;
    private String name;
    private User owner;
    private List<Message> messages;

    @Override
    public int hashCode() {
        return Objects.hash(roomId, name, owner);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Chatroom chatroom = (Chatroom) obj;
        return Objects.equals(this.roomId, chatroom.roomId) &&
                Objects.equals(this.name, chatroom.name) &&
                Objects.equals(this.owner, chatroom.owner);
    }

    @Override
    public String toString() {
        return "Chatroom{" +
                "roomId=" + roomId +
                ", name='" + name + '\'' +
                ", owner='" + owner + '\'' +
                ", messages='" + (messages != null ? messages.size() : 0) + '\'' +
                '}';
    }
}
