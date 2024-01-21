package git.devchewbacca.database.driver;

import git.devchewbacca.UtilityBot;
import git.devchewbacca.utils.config.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLite {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    Configuration configuration = UtilityBot.getInstance().getConfiguration();
    private Connection connection;

    public SQLite() {
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            this.connect();
    }

    private void connect() {
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:data.db");
        }catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException exception) {
                logger.info(exception.getMessage());
            }
        }
    }

    public void reconnect() {
        disconnect();
        connect();
    }

    public Connection getConnection() {
        return connection;
    }
}
