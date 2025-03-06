package edu.school21.chat.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class Chatroom {
    private Long roomId;
    private String name;
    private User owner;
    private HashSet<Message> messages;

    public Chatroom() {
        this.messages = new HashSet<>();
    }

    public Chatroom(Long l, String room, User creator, HashSet<Message> es) {
        roomId = l;
        name = room;
        owner = creator;
        messages = es;
    }

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
        return "Chatroom {" +
                "roomId=" + roomId +
                ", name='" + name + '\'' +
                ", owner='" + owner.getLogin() + '\'' +
                ", messages='" + (messages != null ? messages.size() : 0) + '\'' +
                '}';
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public HashSet<Message> getMessages() {
        return messages;
    }

    public void setMessages(HashSet<Message> messages) {
        this.messages = messages;
    }
}
