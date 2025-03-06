package edu.school21.chat.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {
    private Long userId;
    private String login;
    private String password;
    private List<Chatroom> createdRooms;
    private List<Chatroom> socializingRooms;

    public User() {
        createdRooms = new ArrayList<>();
        socializingRooms = new ArrayList<>();
    }

    public User(Long l, String user, String user1, ArrayList<Chatroom> es, ArrayList<Chatroom> es1) {
        this.userId = l;
        this.login = user;
        this.password = user1;
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

    public List<Chatroom> getCreatedRooms() {
        return createdRooms;
    }

    public void setCreatedRooms(List<Chatroom> createdRooms) {
        this.createdRooms = createdRooms;
    }

    public List<Chatroom> getSocializingRooms() {
        return socializingRooms;
    }

    public void setSocializingRooms(List<Chatroom> socializingRooms) {
        this.socializingRooms = socializingRooms;
    }
}
