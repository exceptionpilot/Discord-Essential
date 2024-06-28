package git.devchewbacca.database;

import git.devchewbacca.interfaces.adapter.IWelcomeAdapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WelcomeConnector implements IWelcomeAdapter {

    private final Connection connection;

    public WelcomeConnector(Connection connection) throws SQLException {

        this.connection = connection;

        String createTableSQL = "CREATE TABLE IF NOT EXISTS welcome ("+
                "guildId BIGINT, " +
                "textChannelId BIGINT)";
        try (PreparedStatement statement = this.connection.prepareStatement(createTableSQL)) {
            statement.executeUpdate();
        }

    }

    @Override
    public void insert(long guildId, long textChannelId) {
        String query = "INSERT INTO welcome (guildId, textChannelId) VALUES (?, ?)";

        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setLong(1, guildId);
            statement.setLong(2, textChannelId);
            statement.executeUpdate();

        } catch (SQLException exception) {
            // Add a console log.
        }
    }

    @Override
    public long find(long guildId) {
        String select = "SELECT * FROM welcome WHERE guildId = ? LIMIT 1;";

        try (PreparedStatement statement = this.connection.prepareStatement(select)) {
            statement.setLong(1, guildId);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getLong("textChannelId");
            }
        } catch (SQLException exception) {
            return 0L;
        }
        return 0L;
    }

    @Override
    public void delete(long guildId) {
        try (PreparedStatement statement = this.connection.prepareStatement("DELETE FROM welcome WHERE guildId = ?;")) {
            statement.setLong(1, guildId);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException();
        }
    }
}
