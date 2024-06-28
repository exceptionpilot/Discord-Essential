package git.devchewbacca.database;

import git.devchewbacca.interfaces.adapter.ITikTokLive;
import git.devchewbacca.modules.tiktok.TikTokLive;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TikTokLiveConnector implements ITikTokLive {

    private final Connection connection;

    public TikTokLiveConnector(Connection connection) throws SQLException {

        this.connection = connection;

        String createTableSQL = "CREATE TABLE IF NOT EXISTS tiktok ("+
                "guildId BIGINT, " +
                "username TEXT, " +
                "textChannelId BIGINT)";
        try (PreparedStatement statement = this.connection.prepareStatement(createTableSQL)) {
            statement.executeUpdate();
            System.out.println("TikTok table created");
        }

    }

    @Override
    public void insert(long guildId, String username, long textChannelId) {
        String query = "INSERT INTO tiktok (guildId, username, textChannelId) VALUES (?, ?, ?)";

        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setLong(1, guildId);
            statement.setString(2, username);
            statement.setLong(3, textChannelId);
            statement.executeUpdate();

        } catch (SQLException exception) {
            // Add a console log.
        }
    }

    @Override
    public TikTokLive find(long guildId, String username) {
        String selectByGuildAndUsername = "SELECT * FROM tiktok WHERE guildId = ? AND username = ?";

        try (PreparedStatement statement = this.connection.prepareStatement(selectByGuildAndUsername)) {
            statement.setLong(1, guildId);
            statement.setString(2, username);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new TikTokLive(
                        rs.getLong("guildId"),
                        rs.getString("username"),
                        rs.getLong("textChannelId"));
            }
        } catch (SQLException exception) {
            return null;
        }
        return null;
    }

    @Override
    public List<TikTokLive> findMany(long guildId) {
        List<TikTokLive> tikTokLives = new ArrayList<>();

        String selectByGuildAndUsername = "SELECT * FROM tiktok WHERE guildId = ?";

        try (PreparedStatement statement = this.connection.prepareStatement(selectByGuildAndUsername)) {
            statement.setLong(1, guildId);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                tikTokLives.add(new TikTokLive(
                        rs.getLong("guildId"),
                        rs.getString("username"),
                        rs.getLong("textChannelId")));
            }
            return tikTokLives;
        } catch (SQLException exception) {
            return tikTokLives;
        }
    }

    @Override
    public void delete(long guildId, String username) {
        try (PreparedStatement statement = this.connection.prepareStatement("DELETE FROM tiktok WHERE guildId = ? AND username = ?;")) {
            statement.setLong(1, guildId);
            statement.setString(2, username);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException();
        }
    }

    @Override
    public void deleteMany(long guildId) {
        try (PreparedStatement statement = this.connection.prepareStatement("DELETE * FROM tiktok WHERE guildId = ?")) {
            statement.setLong(1, guildId);
        } catch (SQLException exception) {
            throw new RuntimeException();
        }
    }
}
