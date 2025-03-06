package edu.school21.chat.repositories;

import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseLoader {
    private final DataSource ds;

    public DatabaseLoader(DataSource ds) {
        this.ds = ds;
    }

    public void executeSqlFile(String path) {
        try(Connection conn = ds.getConnection();
            BufferedReader br = new BufferedReader(new FileReader(path));
            Statement stmt = conn.createStatement()) {

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }
            stmt.execute(sb.toString());
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
