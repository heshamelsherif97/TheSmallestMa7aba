package Redis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

public class Redis {

    private static JedisCluster jedis = new JedisCluster(new HostAndPort("172.17.0.8",7001));

    public static JedisCluster getJedis() {
        return jedis;
    }
}
