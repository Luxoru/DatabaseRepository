package me.luxoru.databaserepository.impl.mysql;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import me.luxoru.databaserepository.DatabaseContainer;
import me.luxoru.databaserepository.IDatabase;
import me.luxoru.databaserepository.impl.mysql.data.column.Column;


import java.sql.*;
import java.util.function.Consumer;


@Getter
@Slf4j(topic = "MYSQLContainer")
public class MySQLContainer extends DatabaseContainer<MySQLDatabase> {


    public MySQLContainer(MySQLDatabase database) {
        super(database);
    }

    public int executeInsert(@NonNull String query, @NonNull Column<?>[] columns) {
        return executeInsert(query, columns, null);
    }

    public int executeInsert(@NonNull String query, @NonNull Column<?>[] columns, Consumer<ResultSet> onComplete) {
        return executeInsert(query, columns, onComplete, null);
    }

    public int executeInsert(@NonNull String query, @NonNull Column<?>[] columns, Consumer<ResultSet> onComplete,
                             Consumer<SQLException> onException) {
        try (Connection connection = database.getDataSource().getConnection()) {
            return executeInsert(connection, query, columns, onComplete, onException);
        } catch (SQLException ex) {
            if (onException != null)
                onException.accept(ex);
            ex.printStackTrace();
        }
        return -1;
    }

    public int executeInsert(@NonNull Connection connection, @NonNull String query, @NonNull Column<?>[] columns) {
        return executeInsert(connection, query, columns, null);
    }


    public int executeInsert(@NonNull Connection connection, @NonNull String query, @NonNull Column<?>[] columns,
                             Consumer<ResultSet> onComplete) {
        return executeInsert(connection, query, columns, onComplete, null);
    }


    public int executeInsert(@NonNull Connection connection, @NonNull String query, @NonNull Column<?>[] columns,
                             Consumer<ResultSet> onComplete, Consumer<SQLException> onException) {
        int questionMarks = 0;
        for (char character : query.toCharArray()) {
            if (character == '?') {
                questionMarks++;
            }
        }
        if (questionMarks != columns.length)
            throw new IllegalArgumentException("Invalid amount of columns for query \"" + query + "\"");
        int affectedRows = 0;
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            int columnIndex = 1;
            for (Column<?> column : columns)
                statement.setString(columnIndex++, column.getValue() == null ? null : column.getValue().toString());
            affectedRows = statement.executeUpdate();
            if (onComplete != null)
                onComplete.accept(statement.getGeneratedKeys());
        } catch (SQLException ex) {
            if (onException != null)
                onException.accept(ex);
            ex.printStackTrace();
        }
        return affectedRows;
    }

    public void executeQuery(@NonNull String query, @NonNull Consumer<ResultSet> onComplete) {
        executeQuery(query, null, onComplete, null);
    }

    public void executeQuery(@NonNull String query, Column<?>[] columns, @NonNull Consumer<ResultSet> onComplete) {
        executeQuery(query, columns, onComplete, null);
    }


    public void executeQuery(@NonNull String query, @NonNull Consumer<ResultSet> onComplete, Consumer<SQLException> onException) {
        executeQuery(query, null, onComplete, onException);
    }


    public void executeQuery(@NonNull String query, Column<?>[] columns, @NonNull Consumer<ResultSet> onComplete,
                             Consumer<SQLException> onException) {
        try (Connection connection = database.getDataSource().getConnection()) {
            executeQuery(connection, query, columns, onComplete, onException);
        } catch (SQLException ex) {
            if (onException != null)
                onException.accept(ex);
            ex.printStackTrace();
        }
    }

    public void executeQuery(@NonNull Connection connection, @NonNull String query, @NonNull Consumer<ResultSet> onComplete,
                             Consumer<SQLException> onException) {
        executeQuery(connection, query, null, onComplete, onException);
    }

    public void executeQuery(@NonNull Connection connection, @NonNull String query, Column<?>[] columns,
                             @NonNull Consumer<ResultSet> onComplete, Consumer<SQLException> onException) {
        if (columns != null) {
            int questionMarks = 0;
            for (char character : query.toCharArray()) {
                if (character == '?') {
                    questionMarks++;
                }
            }
            if (questionMarks != columns.length)
                throw new IllegalArgumentException("Invalid amount of columns for query \"" + query + "\"");
        }
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            if (columns != null) {
                int columnIndex = 1;
                for (Column<?> column : columns)
                    statement.setString(columnIndex++, (column.getValue() == null ? null : column.getValue().toString()));
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                onComplete.accept(resultSet);
            } catch (SQLException ex) {
                if (onException != null)
                    onException.accept(ex);
                ex.printStackTrace();
            }
        } catch (SQLException ex) {
            if (onException != null)
                onException.accept(ex);
            ex.printStackTrace();
        }
    }


    public void executeQuery(@NonNull String query) {
        executeQuery(query, (Runnable) null);
    }

    public void executeQuery(@NonNull String query, Runnable onComplete) {
        executeQuery(query, onComplete, null);
    }

    public void executeQuery(@NonNull String query, Runnable onComplete, Consumer<SQLException> onException) {
        try (Connection connection = database.getDataSource().getConnection()) {
            executeQuery(connection, query, onComplete);
        } catch (SQLException ex) {
            if (onException != null)
                onException.accept(ex);
            ex.printStackTrace();
        }
    }

    public void executeQuery(@NonNull Connection connection, @NonNull String query) {
        executeQuery(connection, query, null);
    }


    public void executeQuery(@NonNull Connection connection, @NonNull String query, Runnable onComplete) {
        executeQuery(connection, query, onComplete, null);
    }


    public void executeQuery(@NonNull Connection connection, @NonNull String query, Runnable onComplete,
                             Consumer<SQLException> onException) {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.execute();
            if (onComplete != null)
                onComplete.run();
        } catch (SQLException ex) {
            if (onException != null)
                onException.accept(ex);
            ex.printStackTrace();
        }
    }





}
