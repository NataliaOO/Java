package edu.school21.chat.repositories;

import edu.school21.chat.exceptions.NotSavedSubEntityException;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.*;
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

    @Override
    public void save(Message message) {
        String query = "INSERT INTO messages (author_id, chatroom_id, text, date_time) VALUES (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, message.getAuthor().getUserId());
            statement.setLong(2, message.getRoom().getRoomId());
            statement.setString(3, message.getText());
            statement.setTimestamp(4, Timestamp.valueOf(message.getDateTime()));
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    message.setMessageId(generatedKeys.getLong(1));
                } else {
                    throw new NotSavedSubEntityException("Failed to retrieve generated ID.");
                }
            }
        } catch (SQLException | NullPointerException e ) {
            throw new NotSavedSubEntityException("Failed to save message: " + e.getMessage());
        }
    }

    @Override
    public void update(Message message) {
        if (message.getMessageId() == null) {
            throw new NotSavedSubEntityException("Message ID is null. Cannot update a message without an ID.");
        }
        String query = "UPDATE messages SET author_id = ?, chatroom_id = ?, text = ?, date_time = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, message.getAuthor().getUserId());
            statement.setLong(2, message.getRoom().getRoomId());
            statement.setString(3, message.getText());
            if (message.getDateTime() != null) {
                statement.setTimestamp(4, Timestamp.valueOf(message.getDateTime()));
            } else {
                statement.setNull(4, Types.TIMESTAMP);
            }
            statement.setLong(5, message.getMessageId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new NotSavedSubEntityException(e.getMessage());
        }
    }
}
