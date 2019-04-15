package CommandPattern.Chatting;


import CommandPattern.Chatting.*;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CommandMap {
    private static ConcurrentMap<String, Class<?>> cmdMap = new ConcurrentHashMap<>();

    public static void instantiate(){
        cmdMap = new ConcurrentHashMap<>();
    }

    public static Class<?> queryClass(String cmd){
        return cmdMap.get(cmd);
    }
    public static ConcurrentMap getinstance(){
        return cmdMap;
    }
}
