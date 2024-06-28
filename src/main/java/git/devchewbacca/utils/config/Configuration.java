package git.devchewbacca.utils.config;

import com.google.gson.Gson;
import git.devchewbacca.modules.welcome.config.ImageTemplate;
import git.devchewbacca.utils.config.object.Config;

import java.io.FileReader;
import java.io.IOException;

public class Configuration {

    private final Config config;
    private final ImageTemplate imageTemplate;

    public Configuration() throws IOException {
        Gson gson = new Gson();
        this.config = gson.fromJson(new FileReader("config.json"), Config.class);
        this.imageTemplate = gson.fromJson(new FileReader("./templates/" + "GITHUB_DARK" + ".json"), ImageTemplate.class);
    }

    public Config getConfig() {
        return config;
    }

    public ImageTemplate getImageTemplate() {
        return imageTemplate;
    }
}
