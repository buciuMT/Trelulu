import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LabelService extends CrudService<Label> {
    private static LabelService instance;

    private LabelService() throws SQLException {
        super();
        try (var stmt = con.createStatement()) {
            stmt.executeUpdate("""
                        CREATE TABLE IF NOT EXISTS Label (
                            id BIGINT PRIMARY KEY,
                            text VARCHAR(255),
                            author BIGINT,
                            task BIGINT,
                            timestamp DATETIME,
                            FOREIGN KEY (author) REFERENCES User(id),
                            FOREIGN KEY (task) REFERENCES Task(id)
                        )
                    """);
        }
    }

    public static LabelService getInstance() throws SQLException {
        if (instance == null) {
            instance = new LabelService();
        }
        return instance;
    }

    @Override
    public void create(Label label) throws SQLException {
        var sql = "INSERT INTO Label (id, text, author, task, timestamp) VALUES (?, ?, ?, ?, ?)";
        try (var ps = con.prepareStatement(sql)) {
            ps.setLong(1, label.getId());
            ps.setString(2, label.getText());

            if (label.getAuthor() != null)
                ps.setLong(3, label.getAuthor().getId());
            else
                ps.setNull(3, Types.BIGINT);

            if (label.getTask() != null)
                ps.setLong(4, label.getTask().getId());
            else
                ps.setNull(4, Types.BIGINT);

            if (label.getTimestamp() != null)
                ps.setTimestamp(5, Timestamp.valueOf(label.getTimestamp()));
            else
                ps.setNull(5, Types.TIMESTAMP);

            ps.executeUpdate();
        }
    }

    @Override
    public Label read(long id) throws SQLException {
        var sql = "SELECT * FROM Label WHERE id = ?";
        try (var ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    var text = rs.getString("text");
                    var authorId = rs.getLong("author");
                    var taskId = rs.getLong("task");
                    var timestamp = rs.getTimestamp("timestamp");

                    var author = !rs.wasNull() ? UserService.getInstance().read(authorId) : null;
                    var task = !rs.wasNull() ? TaskService.getInstance().read(taskId) : null;
                    var time = timestamp != null ? timestamp.toLocalDateTime() : null;

                    return new Label(id, text, author, task, time);
                }
            }
        }
        return null;
    }

    @Override
    public List<Label> readAll() throws SQLException {
        var labels = new ArrayList<Label>();
        var sql = "SELECT * FROM Label";
        try (var ps = con.prepareStatement(sql);
             var rs = ps.executeQuery()) {
            while (rs.next()) {
                var id = rs.getLong("id");
                var text = rs.getString("text");
                var authorId = rs.getLong("author");
                var taskId = rs.getLong("task");
                var timestamp = rs.getTimestamp("timestamp");

                var author = !rs.wasNull() ? UserService.getInstance().read(authorId) : null;// deceeeee? deceee 0 by default
                var task = !rs.wasNull() ? TaskService.getInstance().read(taskId) : null;
                var time = timestamp != null ? timestamp.toLocalDateTime() : null;

                labels.add(new Label(id, text, author, task, time));
            }
        }
        return labels;
    }

    @Override
    public void update(Label label) throws SQLException {
        var sql = "UPDATE Label SET text = ?, author = ?, task = ?, timestamp = ? WHERE id = ?";
        try (var ps = con.prepareStatement(sql)) {
            ps.setString(1, label.getText());

            if (label.getAuthor() != null)
                ps.setLong(2, label.getAuthor().getId());
            else
                ps.setNull(2, Types.BIGINT);

            if (label.getTask() != null)
                ps.setLong(3, label.getTask().getId());
            else
                ps.setNull(3, Types.BIGINT);

            if (label.getTimestamp() != null)
                ps.setTimestamp(4, Timestamp.valueOf(label.getTimestamp()));
            else
                ps.setNull(4, Types.TIMESTAMP);

            ps.setLong(5, label.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(long id) throws SQLException {
        var sql = "DELETE FROM Label WHERE id = ?";
        try (var ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
            ;
        }
    }
}
