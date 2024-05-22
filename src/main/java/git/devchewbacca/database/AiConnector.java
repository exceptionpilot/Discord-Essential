package git.devchewbacca.database;

import git.devchewbacca.interfaces.adapter.IAiAdapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AiConnector implements IAiAdapter {

    private final Connection connection;

    public AiConnector(Connection connection) throws SQLException {

        this.connection = connection;

        String createTableSQL = "CREATE TABLE IF NOT EXISTS ai ("+
                "guildId BIGINT, " +
                "textChannelId BIGINT)";
        try (PreparedStatement statement = this.connection.prepareStatement(createTableSQL)) {
            statement.executeUpdate();
        }

    }

    @Override
    public void insert(long guildId, long textChannelId) {
        String query = "INSERT INTO ai (guildId, textChannelId) VALUES (?, ?)";

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
        String select = "SELECT * FROM ai WHERE guildId = ? LIMIT 1;";

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
        try (PreparedStatement statement = this.connection.prepareStatement("DELETE FROM ai WHERE guildId = ?;")) {
            statement.setLong(1, guildId);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException();
        }
    }
}
