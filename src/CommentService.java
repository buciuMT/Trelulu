import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CommentService extends CrudService<Comment> {
    private static CommentService instance;

    private CommentService() throws SQLException {
        super();
        try (var stmt = con.createStatement()) {
            stmt.executeUpdate("""
                        CREATE TABLE IF NOT EXISTS Comment (
                            id BIGINT PRIMARY KEY,
                            text TEXT,
                            author BIGINT,
                            timestamp DATETIME,
                            FOREIGN KEY (author) REFERENCES User(id)
                        )
                    """);
        }
    }

    public static CommentService getInstance() throws SQLException {
        if (instance == null)
            instance = new CommentService();
        return instance;
    }

    @Override
    public void create(Comment comment) throws SQLException {
        var sql = "INSERT INTO Comment (id, text, author, timestamp) VALUES (?, ?, ?, ?)";
        try (var ps = con.prepareStatement(sql)) {
            ps.setLong(1, comment.getId());
            ps.setString(2, comment.getText());

            if (comment.getAuthor() != null)
                ps.setLong(3, comment.getAuthor().getId());
            else
                ps.setNull(3, Types.BIGINT);

            if (comment.getTimestamp() != null)
                ps.setTimestamp(4, Timestamp.valueOf(comment.getTimestamp()));
            else
                ps.setNull(4, Types.TIMESTAMP);

            ps.executeUpdate();
        }
    }

    @Override
    public Comment read(long id) throws SQLException {
        var sql = "SELECT * FROM Comment WHERE id = ?";
        try (var ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    var text = rs.getString("text");
                    var authorId = rs.getLong("author");
                    var timestamp = rs.getTimestamp("timestamp");

                    var author = !rs.wasNull() ? UserService.getInstance().read(authorId) : null;
                    var time = timestamp != null ? timestamp.toLocalDateTime() : null;

                    return new Comment(id, text, author, time);
                }
            }
        }
        return null;
    }

    @Override
    public List<Comment> readAll() throws SQLException {
        var comments = new ArrayList<Comment>();
        var sql = "SELECT * FROM Comment";
        try (var ps = con.prepareStatement(sql);
             var rs = ps.executeQuery()) {
            while (rs.next()) {
                var id = rs.getLong("id");
                var text = rs.getString("text");
                var authorId = rs.getLong("author");
                var timestamp = rs.getTimestamp("timestamp");

                var author = !rs.wasNull() ? UserService.getInstance().read(authorId) : null;
                var time = timestamp != null ? timestamp.toLocalDateTime() : null;

                comments.add(new Comment(id, text, author, time));
            }
        }
        return comments;
    }

    @Override
    public void update(Comment comment) throws SQLException {
        var sql = "UPDATE Comment SET text = ?, author = ?, timestamp = ? WHERE id = ?";
        try (var ps = con.prepareStatement(sql)) {
            ps.setString(1, comment.getText());

            if (comment.getAuthor() != null)
                ps.setLong(2, comment.getAuthor().getId());
            else
                ps.setNull(2, Types.BIGINT);

            if (comment.getTimestamp() != null)
                ps.setTimestamp(3, Timestamp.valueOf(comment.getTimestamp()));
            else
                ps.setNull(3, Types.TIMESTAMP);

            ps.setLong(4, comment.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(long id) throws SQLException {
        var sql = "DELETE FROM Comment WHERE id = ?";
        try (var ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
            ;
        }
    }
}
