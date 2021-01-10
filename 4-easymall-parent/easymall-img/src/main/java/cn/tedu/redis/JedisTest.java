package cn.tedu.redis;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.*;
import redis.clients.jedis.util.Sharded;

import java.util.*;

public class JedisTest {
    @Test
    public void test() {
        //10.42.168.46  127.0.0.1
        Jedis jedis = new Jedis("10.42.168.46", 9000);
        jedis.auth("123456");
        jedis.set("name", "甄聪");
        System.out.println(jedis.get("name"));
    }

    @Test
    public void test1() {
        List<Jedis> jedisList = new ArrayList<Jedis>();
        jedisList.add(new Jedis("10.42.168.46", 6379));
        jedisList.add(new Jedis("10.42.168.46", 6380));
        jedisList.add(new Jedis("10.42.168.46", 6381));

        String keyT = "";
        for (int i = 0; i < 100; i++) {
            String key = UUID.randomUUID().toString();
            String val = "val+" + i;
            int index = (key.hashCode() & Integer.MAX_VALUE) % jedisList.size();
            Jedis jedis = jedisList.get(index);
            jedis.set(key, val);

            if (i == 50) {
                keyT = key;
            }
        }
        int index = (keyT.hashCode() & Integer.MAX_VALUE) % jedisList.size();
        Jedis jedis = jedisList.get(index);
        String s = jedis.get(keyT);
        System.out.println(keyT);
        System.out.println(s);
    }

    @Test
    public void test2() {
        List<JedisShardInfo> infoList = new ArrayList<>();
        infoList.add(new JedisShardInfo("10.42.168.46", 6379, 500, 500, 8));
        infoList.add(new JedisShardInfo("10.42.168.46", 6380));
        infoList.add(new JedisShardInfo("10.42.168.46", 6381));
        ShardedJedis shardedJedis = new ShardedJedis(infoList);

        String keyT = "";
        for (int i = 0; i < 1000; i++) {
            String key = UUID.randomUUID().toString();
            String val = "val+" + i;
            shardedJedis.set(key, val);
            if (i == 50) {
                keyT = key;
            }
        }
        System.out.println(shardedJedis.get(keyT));
        shardedJedis.close();
    }

    @Test
    public void test3() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(20);
        config.setMaxIdle(8);
        config.setMinIdle(2);
        JedisPool jedisPool = new JedisPool(config, "10.42.168.46", 6379);
        Jedis jedis = jedisPool.getResource();
        jedis.set("name", "haha");
        System.out.println(jedis.get("name"));
        jedis.close();
    }

    @Test
    public void test4() {
        List<JedisShardInfo> infoList = new ArrayList<>();
        infoList.add(new JedisShardInfo("10.42.168.46", 6379, 500, 500, 8));
        infoList.add(new JedisShardInfo("10.42.168.46", 6380));
        infoList.add(new JedisShardInfo("10.42.168.46", 6381));
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(20);
        config.setMaxIdle(8);
        config.setMinIdle(2);
        ShardedJedisPool jedisPool = new ShardedJedisPool(config,infoList);
        ShardedJedis jedis = jedisPool.getResource();
        jedis.set("age1", "282");
        System.out.println(jedis.get("age1"));
        jedis.close();
    }

    @Test
    public void test5(){
        Set<String> sentinel = new HashSet<>();
        sentinel.add(new HostAndPort("10.42.168.46",26379).toString());
        JedisSentinelPool pool = new JedisSentinelPool("mymaster",sentinel);
        System.out.println(pool.getCurrentHostMaster());
        Jedis jedis = pool.getResource();
        jedis.set("name","ajahahahah");
        System.out.println(jedis.get("name"));

    }

    @Test
    public void test6() {
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("10.42.168.46", 8000));
        nodes.add(new HostAndPort("10.42.168.46", 8001));
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(20);
        config.setMinIdle(8);
        config.setMinIdle(2);
        JedisCluster cluster = new JedisCluster(nodes,config);
        cluster.set("name","xixi");
        System.out.println(cluster.get("name"));


    }
}
