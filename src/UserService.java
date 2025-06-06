import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService extends CrudService<User> {
    private static UserService instance;

    private UserService() throws SQLException {
        super();
        try (var stmt = con.createStatement()) {
            stmt.executeUpdate("""
                        CREATE TABLE IF NOT EXISTS User (
                            id BIGINT PRIMARY KEY,
                            role VARCHAR(255)
                        )
                    """);
        }
    }

    public static UserService getInstance() throws SQLException {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    @Override
    public void create(User obj) throws SQLException {
        var sql = "INSERT INTO User (id, role) VALUES (?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, obj.getId());
            ps.setString(2, obj.getRole());
            ps.executeUpdate();
        }
    }

    @Override
    public User read(long id) throws SQLException {
        var sql = "SELECT id, role FROM User WHERE id = ?";
        try (var ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    var role = rs.getString("role");
                    return new User(id, role, List.of());
                }
            }
        }
        return null;
    }

    @Override
    public List<User> readAll() throws SQLException {
        List<User> users = new ArrayList<>();
        var sql = "SELECT id, role FROM User";
        try (var ps = con.prepareStatement(sql);
             var rs = ps.executeQuery()) {
            while (rs.next()) {
                var id = rs.getLong("id");
                var role = rs.getString("role");
                users.add(new User(id, role, List.of()));
            }
        }
        return users;
    }

    @Override
    public void update(User obj) throws SQLException {
        String sql = "UPDATE User SET role = ? WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, obj.getRole());
            ps.setLong(2, obj.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(long id) throws SQLException {
        String sql = "DELETE FROM User WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
            ;
        }
    }
}
