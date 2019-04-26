package CommandPattern.Controller;

import CommandPattern.Command;

import org.json.JSONObject;

public class FreezeCommand implements Command {

    public JSONObject execute(JSONObject json) {
        PropertiesHandler.addProperty("freeze", "true");
        return new JSONObject();
    }
}
