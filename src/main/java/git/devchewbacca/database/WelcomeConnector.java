package git.devchewbacca.database;

import git.devchewbacca.UtilityBot;
import git.devchewbacca.interfaces.adapter.IWelcomeAdapter;
import git.devchewbacca.modules.welcome.objects.Welcome;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class WelcomeConnector implements IWelcomeAdapter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Connection connection;

    public WelcomeConnector(Connection connection) {
        this.connection = connection;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS " + "Welcome(" +
                            "guildId BIGINT(22), " +
                            "textChannelId BIGINT(22), " +
                            "templateName BIGINT(22)," +
                            "isEnabled BIT)");
            statement.close();
        } catch (SQLException exception) {
            this.logger.info(exception.getMessage());
        }
    }

    @Override
    public void create(Guild guild, TextChannel channel, String templateName) {
        try {
            PreparedStatement statement = this.connection.prepareStatement("INSERT INTO Welcome values(?,?,?,?)");
            statement.setLong(1, guild.getIdLong());
            statement.setLong(2, channel.getIdLong());
            statement.setString(3, templateName);
            statement.setBoolean(4, true);
        } catch (SQLException exception) {
            //TODO: Add someting
        }
    }

    @Override
    public void update(Guild guild, TextChannel channel, String templateName, boolean isEnabled) {
        try {
            PreparedStatement statement = this.connection.prepareStatement("SELECT FROM Welcome WHERE guildId=?");
        }catch (SQLException e) {
            this.logger.info(e.getMessage());
        }
    }

    @Override
    public Welcome findByGuildId(long guildId) {
        JDA jda = UtilityBot.getInstance().getJDA();
        try {
            PreparedStatement statement = this.connection.prepareStatement("SELECT FROM Welcome WHERE guildId=?");

            return new Welcome(
                    jda.getGuildById(statement.getResultSet().getLong("guildId")),
                    jda.getTextChannelById(statement.getResultSet().getLong("textChannelId")),
                    statement.getResultSet().getString("templateName"),
                    statement.getResultSet().getBoolean("isEnabled")
            );

        } catch (SQLException e) {
            this.logger.info(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
