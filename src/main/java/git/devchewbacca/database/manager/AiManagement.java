package git.devchewbacca.database.manager;

import git.devchewbacca.interfaces.adapter.IAiAdapter;

public class AiManagement implements IAiAdapter {

    private final IAiAdapter aiAdapter;

    public AiManagement(IAiAdapter aiAdapter) {

        this.aiAdapter = aiAdapter;

    }

    @Override
    public void insert(long guildId, long textChannelId) {
        this.aiAdapter.insert(guildId, textChannelId);
    }

    @Override
    public long find(long guildId) {
        return this.aiAdapter.find(guildId);
    }

    @Override
    public void delete(long guildId) {
        this.aiAdapter.delete(guildId);
    }
}
