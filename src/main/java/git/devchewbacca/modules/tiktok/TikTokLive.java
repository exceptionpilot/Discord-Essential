package git.devchewbacca.modules.tiktok;

public class TikTokLive {

    private final long guildId;
    private final String username;
    private final long textChannelId;

    public TikTokLive(long guildId, String username, long textChannelId) {

        this.guildId = guildId;
        this.username = username;
        this.textChannelId = textChannelId;

    }

    public long getGuildId() {
        return guildId;
    }

    public String getUsername() {
        return username;
    }

    public long getTextChannelId() {
        return textChannelId;
    }
}
