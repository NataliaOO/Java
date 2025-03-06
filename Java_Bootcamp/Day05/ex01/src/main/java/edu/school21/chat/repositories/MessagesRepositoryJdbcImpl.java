package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {

    private final DataSource dataSource;

    public MessagesRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Message> findById(Long id) {
        String query = "SELECT * FROM messages m " +
                "JOIN users u ON u.id = m.author_id JOIN chatrooms c ON c.id = m.chatroom_id WHERE m.id = ?";
        try(Connection collection = dataSource.getConnection()) {
            PreparedStatement statement = collection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User author = new User();
                author.setUserId(resultSet.getLong("author_id"));
                author.setLogin(resultSet.getString("login"));
                author.setPassword(resultSet.getString("password"));

                Chatroom room = new Chatroom();
                room.setRoomId(resultSet.getLong("chatroom_id"));
                room.setName(resultSet.getString("name"));

                Message message = new Message();
                message.setMessageId(resultSet.getLong("id"));
                message.setAuthor(author);
                message.setRoom(room);
                message.setText(resultSet.getString("text"));
                message.setDateTime(resultSet.getTimestamp("date_time").toLocalDateTime());
                return Optional.of(message);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
}
