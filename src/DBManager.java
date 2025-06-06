import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    private static Connection con;

    private DBManager() {
        final var user = "";
        final var password = "";
    }

    static public Connection getConnection() throws SQLException {
        if (con == null || con.isClosed())
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trelulu", "root", "pa55word");
        return con;
    }
}
/*

docker run --name mysql-java -p 3306:3306 -v "./sql:/docker-entrypoint-initdb.d/" -e MYSQL_ROOT_PASSWORD=pa55word -e MYSQL_DATABASE=trelulu mysql:latestq
am folosit aceasta comanda sa creez baza de date
 */