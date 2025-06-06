import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskService extends CrudService<Task> {
    private static TaskService instance;

    private TaskService() throws SQLException {
        super();
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate("""
                        CREATE TABLE IF NOT EXISTS Task (
                            id BIGINT PRIMARY KEY,
                            title VARCHAR(255),
                            description TEXT,
                            status VARCHAR(50),
                            assigneduser BIGINT,
                            deadline DATETIME,
                            FOREIGN KEY (assigneduser) REFERENCES User(id)
                        )
                    """);
        }
    }

    public static TaskService getInstance() throws SQLException {
        if (instance == null) {
            instance = new TaskService();
        }
        return instance;
    }

    @Override
    public void create(Task task) throws SQLException {
        String sql = "INSERT INTO Task (id, title, description, status, assigneduser, deadline) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, task.getId());
            ps.setString(2, task.getTitle());
            ps.setString(3, task.getDescription());
            ps.setString(4, task.getStatus().name());
            if (task.getAssigneduser() != null) {
                ps.setLong(5, task.getAssigneduser().getId());
            } else {
                ps.setNull(5, Types.BIGINT);
            }
            if (task.getDeadline() != null) {
                ps.setTimestamp(6, Timestamp.valueOf(task.getDeadline()));
            } else {
                ps.setNull(6, Types.TIMESTAMP);
            }
            ps.executeUpdate();
        }
    }

    @Override
    public Task read(long id) throws SQLException {
        var sql = "SELECT * FROM Task WHERE id = ?";
        try (var ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    var title = rs.getString("title");
                    var description = rs.getString("description");
                    var statusStr = rs.getString("status");
                    var userId = rs.getLong("assigneduser");
                    var deadlineTs = rs.getTimestamp("deadline");

                    User assignedUser = null;
                    if (!rs.wasNull()) {
                        assignedUser = UserService.getInstance().read(userId);
                    }

                    var task = new Task(
                            id,
                            title,
                            description,
                            deadlineTs != null ? deadlineTs.toLocalDateTime() : null,
                            TaskStatus.valueOf(statusStr)
                    );
                    task.setAssigneduser(assignedUser);
                    return task;
                }
            }
        }
        return null;
    }

    @Override
    public List<Task> readAll() throws SQLException {
        List<Task> tasks = new ArrayList<>();
        var sql = "SELECT * FROM Task";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                var id = rs.getLong("id");
                var title = rs.getString("title");
                var description = rs.getString("description");
                var statusStr = rs.getString("status");
                var userId = rs.getLong("assigneduser");
                var deadlineTs = rs.getTimestamp("deadline");

                User assignedUser = null;
                if (!rs.wasNull()) {
                    assignedUser = UserService.getInstance().read(userId);
                }

                Task task = new Task(
                        id,
                        title,
                        description,
                        deadlineTs != null ? deadlineTs.toLocalDateTime() : null,
                        TaskStatus.valueOf(statusStr)
                );
                task.setAssigneduser(assignedUser);
                tasks.add(task);
            }
        }
        return tasks;
    }

    @Override
    public void update(Task task) throws SQLException {
        var sql = "UPDATE Task SET title = ?, description = ?, status = ?, assigneduser = ?, deadline = ? WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, task.getTitle());
            ps.setString(2, task.getDescription());
            ps.setString(3, task.getStatus().name());

            if (task.getAssigneduser() != null) {
                ps.setLong(4, task.getAssigneduser().getId());
            } else {
                ps.setNull(4, Types.BIGINT);
            }

            if (task.getDeadline() != null) {
                ps.setTimestamp(5, Timestamp.valueOf(task.getDeadline()));
            } else {
                ps.setNull(5, Types.TIMESTAMP);
            }

            ps.setLong(6, task.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(long id) throws SQLException {
        var sql = "DELETE FROM Task WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
            ;
        }
    }
}
