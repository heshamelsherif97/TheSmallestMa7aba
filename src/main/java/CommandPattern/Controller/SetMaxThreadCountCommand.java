package CommandPattern.Controller;

import CommandPattern.Command;
import CommandPattern.userStories.AppThreadPool;
import org.json.JSONObject;

public class SetMaxThreadCountCommand implements Command {

    public JSONObject execute(JSONObject json) {

        int x = Integer.parseInt(json.getString("thread_count"));
        AppThreadPool.setNumberOfThreads(x);

        return new JSONObject();
    }
}
