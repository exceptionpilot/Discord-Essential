package git.devchewbacca.lang;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import git.devchewbacca.lang.object.PlaceholderManagement;
import org.jetbrains.annotations.NotNull;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ResponseMessage {

    private final Map<String, JsonElement> store = new HashMap<>();

    private final PlaceholderManagement placeholderManagement;


    public ResponseMessage(PlaceholderManagement placeholderManagement) {
        this.placeholderManagement = placeholderManagement;
    }

    public String getMessage(String path) {
        if (this.store.get(path) !=null) {
            return this.store.get(path).getAsString();
        } else {
            try (FileReader fileReader = new FileReader("lang/en-EN.json")) {
                JsonElement result = this.fromFile(fileReader, path);
                this.store.put(path, result);
                return placeholderManagement.process(result.getAsString());
            } catch (IOException e) {
                return e.getMessage();
            }
        }
    }

    public JsonElement fromFile(@NotNull FileReader json, @NotNull String path) throws JsonSyntaxException {
        JsonObject object = new GsonBuilder().create().fromJson(json, JsonObject.class);
        String[] splits = path.split("\\.");
        for (String element : splits) {
            if (object != null) {
                JsonElement jsonElement = object.get(element);
                if (!jsonElement.isJsonObject())
                    return jsonElement;
                else
                    object = jsonElement.getAsJsonObject();
            } else return null;
        }
        return object;
    }
}
