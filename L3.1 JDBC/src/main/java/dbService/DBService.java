package dbService;

import dbService.dao.UsersDAO;
import dbService.dataSets.UsersDataSet;
import org.h2.engine.Session;
import org.h2.jdbcx.JdbcDataSource;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * edited by ketaetc
 */
public class DBService {
    private final Connection connection;

    public DBService() {
        this.connection = getH2Connection();
        UsersDAO dao = new UsersDAO(connection);
        try {
            dao.dropTable();
            dao.createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public UsersDataSet getUser(long id) throws DBException {
        try {
            return (new UsersDAO(connection).get(id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public long addUser(String name, String password) throws DBException {
        try {
            connection.setAutoCommit(false);
            UsersDAO dao = new UsersDAO(connection);
            dao.createTable();
            dao.insertUser(name, password);
            connection.commit();
            return dao.getUserId(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public UsersDataSet getUserByName(String name) {
        try {
            UsersDAO dao = new UsersDAO(connection);
            long id = dao.getUserId(name);
            UsersDataSet usD = dao.get(id);
            return usD;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void cleanUp() throws DBException {
        UsersDAO dao = new UsersDAO(connection);
        try {
            dao.dropTable();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public void printConnectInfo() {
        try {
            System.out.println("DB name: " + connection.getMetaData().getDatabaseProductName());
            System.out.println("DB version: " + connection.getMetaData().getDatabaseProductVersion());
            System.out.println("Driver: " + connection.getMetaData().getDriverName());
            System.out.println("Autocommit: " + connection.getAutoCommit());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("UnusedDeclaration")
    public static Connection getMysqlConnection() {
        try {
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.jdbc.Driver").newInstance());

            StringBuilder url = new StringBuilder();

            url.
                    append("jdbc:mysql://").        //db type
                    append("localhost:").           //host name
                    append("3306/").                //port
                    append("db_example?").          //db name
                    append("user=tully&").          //login
                    append("password=tully");       //password

            System.out.println("URL: " + url + "\n");

            Connection connection = DriverManager.getConnection(url.toString());
            return connection;
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Connection getH2Connection() {
        try {

            String url = "jdbc:h2:~/IdeaProjects/stepic_java_webserver/L3.1 JDBC/h2db";
            String name = "test";
            String pass = "test";

            JdbcDataSource ds = new JdbcDataSource();
            ds.setURL(url);
            ds.setUser(name);
            ds.setPassword(pass);

            Connection connection = DriverManager.getConnection(url, name, pass);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createUsersTable() throws SQLException {
        UsersDAO dao = new UsersDAO(connection);
        System.out.println("Trying to create users table...");
        try {
            dao.createTable();
            System.out.println("Users table was created successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
