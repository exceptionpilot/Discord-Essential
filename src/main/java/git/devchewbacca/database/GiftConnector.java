package git.devchewbacca.database;

import git.devchewbacca.interfaces.adapter.IGiftAdapter;
import git.devchewbacca.modules.gift.Gift;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GiftConnector implements IGiftAdapter {

    private final Connection connection;

    public GiftConnector(Connection connection) throws SQLException {

        this.connection = connection;

        String createTableSQL = "CREATE TABLE IF NOT EXISTS gift ("+
                "name TEXT, " +
                "key TEXT, " +
                "url TEXT)";
        try (PreparedStatement statement = this.connection.prepareStatement(createTableSQL)) {
            statement.executeUpdate();
        }


    }

    @Override
    public void create(String name, String key, String url) {

        String query = "INSERT INTO gift (name, key, url) VALUES (?, ?, ?)";

        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setString(2, key);
            statement.setString(3, url);
            statement.executeUpdate();

        } catch (SQLException exception) {
            // Add a console log.
        }
    }

    @Override
    public void delete(String key) {

        try (PreparedStatement statement = this.connection.prepareStatement("DELETE FROM gift WHERE key = ?")) {
            statement.setString(1, key);
        } catch (SQLException exception) {

        }

    }

    @Override
    public Gift findFirst() {

        String selectFirstSQL = "SELECT * FROM gift ORDER BY key LIMIT 1";

        Gift gift = null;
        try (PreparedStatement statement = this.connection.prepareStatement(selectFirstSQL)) {
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String key = rs.getString("key");
                String urlValue = rs.getString("url");

                // Ausgabe des selektierten Eintrags
                System.out.println("Selected Entry:");
                System.out.println("Name: " + name);
                System.out.println("Key: " + key);
                System.out.println("URL: " + urlValue);

                gift = new Gift(name, key, urlValue);
            }
        } catch (SQLException exception) {
            // Handle Error
        }
        return gift;
    }
}
