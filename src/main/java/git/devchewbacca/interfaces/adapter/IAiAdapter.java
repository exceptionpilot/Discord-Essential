package git.devchewbacca.interfaces.adapter;

public interface IAiAdapter {

    void insert(long guildId, long textChannelId);
    long find(long guildId);
    void delete(long guildId);
}
