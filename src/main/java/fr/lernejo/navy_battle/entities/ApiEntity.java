package fr.lernejo.navy_battle.entities;

import org.json.JSONException;
import org.json.JSONObject;

public class ApiEntity {
    private final String url;
    private final String message;

    public ApiEntity(String url, String message) {
        this.url = url;
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public String getMessage() {
        return message;
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("url", url);
        obj.put("message", message);
        return obj;
    }

    public static ApiEntity fromJSON(JSONObject object) throws JSONException {
        return new ApiEntity(
            object.getString("url"),
            object.getString("message")
        );
    }

    public ApiEntity withURL(String url) {
        return new ApiEntity(
            url,
            this.message
        );
    }
}
