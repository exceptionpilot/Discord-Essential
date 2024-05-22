package git.devchewbacca.modules.gift;

public class Gift {

    private final String name;
    private final String key;
    private final String url;


    public Gift(String name, String key, String url) {
        this.name = name;
        this.key = key;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public String getUrl() {
        return url;
    }
}
