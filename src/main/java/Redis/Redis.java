package Redis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class Redis {


    private static JedisCluster jedis = new JedisCluster(new HostAndPort(getIP()[0],Integer.parseInt(getIP()[1])));

    public static JedisCluster getJedis() {
        return jedis;
    }


    public static String [] getIP(){
        String [] modified = new String[2];
        modified[0] = "localhost";
        modified[1] = "7000";
        Properties conf = new Properties();
        try {

            File file = new File("./src/main/java/CommandPattern/userStories/config.properties");
            FileInputStream confLoc = new FileInputStream(file);
            conf.load(confLoc);
            String url = conf.getProperty("JedisIP");
            modified = url.split("\\:");

            System.out.print(url + "test");
        }
        catch(Exception e)
        {
            System.out.print("JD ip not found");
        }
        System.out.println(modified[0]+":"+modified[1]);
        return modified;
    }

}


// sTRIPS
// CRispy
// Cordon BLU
// Beautiful Mess + big tasty
// Miss marvel + bg tasty
// strips only cheese