package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UsersRepositoryJdbcImpl implements UsersRepository {
    private final DataSource dataSource;

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<User> findAll(int page, int size) {
        String query = """
        WITH user_pagination AS (
            SELECT id, login, password
            FROM users
            ORDER BY id
            LIMIT ? OFFSET ?
        ),
        user_created_rooms AS (
            SELECT u.id as user_id, c.id as chatroom_id, c.name
            FROM user_pagination u
            LEFT JOIN chatrooms c ON u.id = c.owner_id
        ),
        user_socializing_rooms AS (
            SELECT u.id as user_id, cu.chatroom_id, c.name
            FROM user_pagination u
            LEFT JOIN chatroom_users cu ON u.id = cu.user_id
            LEFT JOIN chatrooms c ON cu.chatroom_id = c.id
        )
        SELECT
            up.id, up.login, up.password,
            ucr.chatroom_id AS created_room_id, ucr.name AS created_room_name,
            usr.chatroom_id AS socializing_room_id, usr.name AS socializing_room_name
        FROM user_pagination up
        LEFT JOIN user_created_rooms ucr ON up.id = ucr.user_id
        LEFT JOIN user_socializing_rooms usr ON up.id = usr.user_id
        ORDER BY up.id;
        """;
        Map<Long, User> users = new HashMap<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1,size);
            preparedStatement.setInt(2,page);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Long userId = resultSet.getLong("id");
                    String login = resultSet.getString("login");
                    String password = resultSet.getString("password");
                    User user = users.computeIfAbsent(userId, k ->
                            new User(userId, login, password, new HashSet<>(), new HashSet<>()));

                    addChatroom(resultSet,"created_room_id", "created_room_name",
                            user.getCreatedRooms(), user);
                    addChatroom(resultSet, "socializing_room_id", "socializing_room_name",
                            user.getSocializingRooms(), user);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve users: " + e.getMessage(), e);
        }
        return new ArrayList<>(users.values());
    }

    private void addChatroom(ResultSet rs, String roomId, String roomName, HashSet<Chatroom> chatrooms, User user)
            throws SQLException {
        Long chatroomId = rs.getLong(roomId);
        if (!rs.wasNull()) {
            String createdRoomName = rs.getString(roomName);
            Chatroom chatroom = new Chatroom(chatroomId, createdRoomName, user, new HashSet<>());
            chatrooms.add(chatroom);
        }
    }
}
