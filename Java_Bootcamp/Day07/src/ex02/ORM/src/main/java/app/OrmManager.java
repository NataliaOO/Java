package app;

import annotations.OrmColumn;
import annotations.OrmColumnId;
import annotations.OrmEntity;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

public class OrmManager {
    private final Connection conn;
    private final Map<Class<?>, String> createTableQueries = new HashMap<>();

    public OrmManager(DataSource ds) throws SQLException {
        this.conn = ds.getConnection();
        if (conn == null) {
            System.err.println("Error: can't connection to DB");
            System.exit(1);
        }
    }

    public void initialize(List<Class<?>> entities) {
        entities.forEach(this::processEntity);
        executeDropTables();
        executeCreateTables();
    }
    public void save(Object entity) {
        Class<?> clazz = entity.getClass();
        String tableName = clazz.getAnnotation(OrmEntity.class).table();
        List<String> columns = new ArrayList<>();
        List<Object> values = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(OrmColumn.class)) {
                OrmColumn column = field.getAnnotation(OrmColumn.class);
                columns.add(column.name());
                values.add(getFieldValue(field, entity));
            }
        }
        String query = String.format(
                "INSERT INTO %s (%s) VALUES (%s)",
                tableName,
                String.join(", ", columns),
                String.join(", ", Collections.nCopies(values.size(), "?"))
        );
        executeUpdate(query, values);
        System.out.println("Executed: " + query);
    }
    public void update(Object entity) {
        Class<?> clazz = entity.getClass();
        String tableName = clazz.getAnnotation(OrmEntity.class).table();
        List<String> updates = new ArrayList<>();
        List<Object> values = new ArrayList<>();
        Object idValue = null;
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(OrmColumnId.class)) {
                idValue = getFieldValue(field, entity);
            } else if (field.isAnnotationPresent(OrmColumn.class)) {
                OrmColumn annotation = field.getAnnotation(OrmColumn.class);
                updates.add(annotation.name() + " = ?");
                values.add(getFieldValue(field, entity));
            }
        }
        if (idValue == null) throw new RuntimeException("No @OrmColumnId found");
        String query = String.format(
            "UPDATE %s SET %s WHERE id = %s",
            tableName,
            String.join(", ", updates),
            idValue
        );
        executeUpdate(query, values);
        System.out.println("Executed: " + query);
    }
    public <T> T findById(Long id, Class<T> aClass) {
        String tableName = aClass.getAnnotation(OrmEntity.class).table();
        String query = "SELECT * FROM " + tableName + " WHERE id = " + id;
        try (Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query)) {
            System.out.println("Executed: " + query);
            if (!rs.next()) return null;

            T instance = aClass.getDeclaredConstructor().newInstance();
            for (Field field : aClass.getDeclaredFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(OrmColumn.class)) {
                    OrmColumn annotation = field.getAnnotation(OrmColumn.class);
                    Object value = rs.getObject(annotation.name());
                    field.set(instance, value);
                } else if (field.isAnnotationPresent(OrmColumnId.class)) {
                    field.set(instance, rs.getLong("id"));
                }
            }
            return instance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getSqlType(Class<?> type, int length) {
        if (type == String.class) return "VARCHAR(" + length + ")";
        if (type == Integer.class || type == int.class) return "INT";
        if (type == Long.class || type == long.class) return "BIGINT";
        if (type == Double.class || type == double.class) return "DOUBLE";
        if (type == Boolean.class || type == boolean.class) return "BOOLEAN";
        throw new IllegalArgumentException("Unsupported type: " + type);
    }
    private void processEntity(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(OrmEntity.class)) return;
        OrmEntity entityAnnotation = clazz.getAnnotation(OrmEntity.class);
        String tableName = entityAnnotation.table();
        List<String> columns = new ArrayList<>();
        String idColumn = null;
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(OrmColumnId.class)) {
                idColumn = field.getName() + " SERIAL PRIMARY KEY";
            } else if (field.isAnnotationPresent(OrmColumn.class)) {
                OrmColumn columnAnnotation = field.getAnnotation(OrmColumn.class);
                String sqlType = getSqlType(field.getType(), columnAnnotation.length());
                columns.add(columnAnnotation.name() + " " + sqlType);
            }
        }
        String query = String.format(
                "CREATE TABLE %s (%s%s);",
                tableName,
                idColumn != null ? idColumn + ", " : "",
                String.join(", ", columns)
        );
        createTableQueries.put(clazz, query);
    }
    private void executeDropTables() {
        createTableQueries.keySet().forEach(clazz -> {
            String tableName = clazz.getAnnotation(OrmEntity.class).table();
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("DROP TABLE IF EXISTS " + tableName);
                System.out.println("Executed: DROP TABLE IF EXISTS " + tableName);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
    private void executeCreateTables() {
        createTableQueries.forEach((clazz, query) -> {
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(query);
                System.out.println("Executed: " + query);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
    private Object getFieldValue(Field field, Object entity) {
        try {
            field.setAccessible(true);
            return field.get(entity);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    private void executeUpdate(String query, List<Object> values) {
        try (PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            for (int i = 0; i < values.size(); i++) {
                statement.setObject(i + 1, values.get(i));
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
