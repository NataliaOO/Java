import app.OrmManager;
import app.User;

import java.sql.SQLException;
import java.util.Collections;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Main {
    public static void main(String[] args) throws SQLException {
        HikariConfig config = new HikariConfig("src/main/resources/application.properties");
        HikariDataSource ds = new HikariDataSource(config);
        OrmManager orm = new OrmManager(ds);
        orm.initialize(Collections.singletonList(User.class));

        User user = new User("John", "Doe", 30);
        orm.save(user);
        User found = orm.findById(1L, User.class);
        found.setAge(31);
        orm.update(found);
    }
}
