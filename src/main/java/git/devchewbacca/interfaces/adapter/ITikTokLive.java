package git.devchewbacca.interfaces.adapter;

import git.devchewbacca.modules.tiktok.TikTokLive;

import java.util.List;

public interface ITikTokLive {

    void insert(long guildId, String username, long textChannelId);
    TikTokLive find(long guildId, String username);
    List<TikTokLive> findMany(long guildId);
    void delete(long guildId, String name);
    void deleteMany(long guildId);

}
