package CommandPattern.userStories;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.server.ExportException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import CommandPattern.userStories.*;

public class CommandMap {
    private static ConcurrentHashMap<String, Class<?>> cmdMap = new ConcurrentHashMap<>();

    public static void instantiate(){
        cmdMap.put("login", login.class);
        cmdMap.put("fib", fib.class);
        cmdMap.put("getUsers", getAllUsers.class);
        cmdMap.put("getUser", getSingleUser.class);
        cmdMap.put("SignUp", SignUp.class);
        cmdMap.put("updateFirstName", updateFirstName.class);
        cmdMap.put("updateLastName", updateLastName.class);
        cmdMap.put("updatePassword", updatePassword.class);
//        try {
//            File entries = new File("src/main/UserCommandsConfig.txt");
//            BufferedReader reader = new BufferedReader(new FileReader(entries));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String[] lineArray = line.split(",");
//                try {
//                    cmdMap.put(lineArray[0], Class.forName(lineArray[1].trim()));
//                }
//                catch (Exception e){
//                    System.out.println(e);
//                }
//            }
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
    }

    public static Class<?> queryClass(String cmd){
        return cmdMap.get(cmd);
    }
    public static ConcurrentMap getinstance(){
        return cmdMap;
    }
}