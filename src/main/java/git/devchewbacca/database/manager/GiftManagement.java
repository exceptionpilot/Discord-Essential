package git.devchewbacca.database.manager;

import git.devchewbacca.interfaces.adapter.IGiftAdapter;
import git.devchewbacca.modules.gift.Gift;

public class GiftManagement implements IGiftAdapter {

    private final IGiftAdapter giftAdapter;

    public GiftManagement(IGiftAdapter giftAdapter) {

        this.giftAdapter = giftAdapter;

    }

    @Override
    public void create(String name, String key, String url) {
        this.giftAdapter.create(name, key, url);
    }

    @Override
    public void delete(String key) {
        this.giftAdapter.delete(key);
    }

    @Override
    public Gift findFirst() {
        return this.giftAdapter.findFirst();
    }
}
