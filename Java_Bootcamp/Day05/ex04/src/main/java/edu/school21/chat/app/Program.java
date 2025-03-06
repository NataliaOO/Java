package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class Program {
    public static void main(String[] args) {
        Properties properties = loadProperties();
        String dbUrl = properties.getProperty("db.url");
        String user = properties.getProperty("db.user");
        String password = properties.getProperty("db.password");

        DatabaseLoader databaseLoader = new DatabaseLoader(dbUrl, user, password);
        databaseLoader.executeSqlFile("src/main/resources/schema.sql");
        databaseLoader.executeSqlFile("src/main/resources/data.sql");

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbUrl);
        config.setUsername(user);
        config.setPassword(password);
        config.setMaximumPoolSize(Integer.parseInt(properties.getProperty("db.pool.size")));

        HikariDataSource ds = new HikariDataSource(config);
        UsersRepository usersRepository = new UsersRepositoryJdbcImpl(ds);

        int page = 2;
        int size = 3;
        List<User> users = usersRepository.findAll(page, size);

        System.out.println("Users on page " + page + " (size " + users.size() + "):");
        for (User u : users) {
            System.out.println(u);
        }
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = Program.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) throw new RuntimeException("application.properties not found");
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }
}
