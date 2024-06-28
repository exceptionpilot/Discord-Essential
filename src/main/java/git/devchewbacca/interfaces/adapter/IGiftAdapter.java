package git.devchewbacca.interfaces.adapter;

import git.devchewbacca.modules.gift.Gift;

public interface IGiftAdapter {

    void create(String name, String key, String url);
    void delete(String key);
    Gift findFirst();

}
