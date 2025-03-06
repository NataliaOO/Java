package edu.school21.chat.models;

import java.util.HashSet;
import java.util.Objects;

public class User {
    private Long userId;
    private String login;
    private String password;
    private HashSet<Chatroom> createdRooms;
    private HashSet<Chatroom> socializingRooms;

    public User() {
        createdRooms = new HashSet<>();
        socializingRooms = new HashSet<>();
    }

    public User(Long l, String login, String password, HashSet<Chatroom> es, HashSet<Chatroom> es1) {
        this.userId = l;
        this.login = login;
        this.password = password;
        this.createdRooms = es;
        this.socializingRooms = es1;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, login);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return Objects.equals(this.userId, user.userId) &&
                Objects.equals(this.login, user.login);
    }

    @Override
    public String toString() {
        return "User {" +
                "userId=" + userId +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", createdRooms='" + (createdRooms != null ? createdRooms.size() : 0) + '\'' +
                ", socializingRooms:'" + (socializingRooms != null ? socializingRooms.size() : 0) + '\'' +
                '}';
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HashSet<Chatroom> getCreatedRooms() {
        return createdRooms;
    }

    public void setCreatedRooms(HashSet<Chatroom> createdRooms) {
        this.createdRooms = createdRooms;
    }

    public HashSet<Chatroom> getSocializingRooms() {
        return socializingRooms;
    }

    public void setSocializingRooms(HashSet<Chatroom> socializingRooms) {
        this.socializingRooms = socializingRooms;
    }
}
