package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.DatabaseLoader;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;
import java.util.Optional;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        HikariConfig config = new HikariConfig("src/main/resources/application.properties");
        HikariDataSource ds = new HikariDataSource(config);
        DatabaseLoader databaseLoader = new DatabaseLoader(ds);
        databaseLoader.executeSqlFile("src/main/resources/schema.sql");
        databaseLoader.executeSqlFile("src/main/resources/data.sql");
        MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(ds);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a message ID");
        System.out.print("-> ");
        Long id = scanner.nextLong();

        Optional<Message> messageOptional = messagesRepository.findById(id);
        if (messageOptional.isPresent()) {
            System.out.println(messageOptional.get());
        } else System.out.println("No message found");
    }
}
