package edu.school21.chat.models;

import java.util.List;
import java.util.Objects;

public class User {
    private Long userId;
    private String login;
    private String password;
    private List<Chatroom> createdRooms;
    private List<Chatroom> socializingRooms;

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
        return "User{" +
                "userId=" + userId +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", createdRooms='" + (createdRooms != null ? createdRooms.size() : 0) + '\'' +
                ", socializingRooms:'" + (socializingRooms != null ? socializingRooms.size() : 0) + '\'' +
                '}';
    }
}
