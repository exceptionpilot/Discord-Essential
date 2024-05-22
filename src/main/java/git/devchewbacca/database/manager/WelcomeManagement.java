package git.devchewbacca.database.manager;

import git.devchewbacca.interfaces.adapter.IAiAdapter;
import git.devchewbacca.interfaces.adapter.IWelcomeAdapter;

public class WelcomeManagement implements IWelcomeAdapter {

    private final IWelcomeAdapter welcomeAdapter;

    public WelcomeManagement(IWelcomeAdapter welcomeAdapter) {

        this.welcomeAdapter = welcomeAdapter;

    }

    @Override
    public void insert(long guildId, long textChannelId) {
        this.welcomeAdapter.insert(guildId, textChannelId);
    }

    @Override
    public long find(long guildId) {
        return this.welcomeAdapter.find(guildId);
    }

    @Override
    public void delete(long guildId) {
        this.welcomeAdapter.delete(guildId);
    }
}
