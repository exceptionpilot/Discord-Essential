package git.devchewbacca.database.manager;

import git.devchewbacca.interfaces.adapter.ITikTokLive;
import git.devchewbacca.modules.tiktok.TikTokLive;

import java.util.List;

public class TikTokLiveManagement implements ITikTokLive {

    private final ITikTokLive tikTokLive;

    public TikTokLiveManagement(ITikTokLive tikTokLive) {

        this.tikTokLive = tikTokLive;

    }

    @Override
    public void insert(long guildId, String username, long textChannelId) {
        this.tikTokLive.insert(guildId, username, textChannelId);
    }

    @Override
    public TikTokLive find(long guildId, String username) {
        return this.tikTokLive.find(guildId, username);
    }

    @Override
    public List<TikTokLive> findMany(long guildId) {
        return this.tikTokLive.findMany(guildId);
    }

    @Override
    public void delete(long guildId, String name) {
        this.tikTokLive.delete(guildId, name);
    }

    @Override
    public void deleteMany(long guildId) {
        this.tikTokLive.deleteMany(guildId);
    }
}