package git.devchewbacca.utils.config.object;

public class Config {

    private final String lang;
    private final String template;
    private final String embedColorPrimary;
    private final String embedColorDanger;

    public Config(String lang, String template, String embedColorPrimary, String embedColorDanger) {
        this.lang = lang;
        this.template = template;
        this.embedColorPrimary = embedColorPrimary;
        this.embedColorDanger = embedColorDanger;
    }

    public String getLang() {
        return lang;
    }

    public String getTemplate() {
        return template;
    }

    public String getEmbedColorPrimary() {
        return embedColorPrimary;
    }

    public String getEmbedColorDanger() {
        return embedColorDanger;
    }
}
