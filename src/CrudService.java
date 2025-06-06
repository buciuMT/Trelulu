import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class CrudService<T> {
    protected final Connection con;

    protected CrudService() throws SQLException {
        con = DBManager.getConnection();
    }

    public abstract void create(T obj) throws SQLException;

    public abstract T read(long id) throws SQLException;

    public abstract List<T> readAll() throws SQLException;

    public abstract void update(T obj) throws SQLException;

    public abstract void delete(long id) throws SQLException;
}
