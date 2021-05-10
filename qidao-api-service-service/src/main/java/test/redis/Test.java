package test.redis;

import cn.hutool.json.JSONUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Test {
    public static void main(String[] args) {

        Jedis jedis = new Jedis("localhost", 6379);
        String res = jedis.ping();

        System.out.println(res);

        jedis.flushDB();
        /**
         * demo 测试
         */
        jedis.set("k1", "v1");
        jedis.set("k2", "v2");
        jedis.set("k3", "v3");
        jedis.set("k4", "v4");

        Set<String> keys = jedis.keys("*");


        System.out.println(JSONUtil.toJsonStr(keys));

        HashMap<String, String> map = new HashMap<>();
        map.put("telephone", "123");
        map.put("address", "云清路");
        map.put("name", "zhatang");
        jedis.hmset("h1", map);
        List<String> list = jedis.hmget("h1", "telephone", "address");
        System.out.println(JSONUtil.toJsonStr(list));
        //开启事务
        Transaction multi = jedis.multi();
        multi.set("q1", "v1");
        multi.set("q2", "v2");
        multi.exec();
        Set<String> set = jedis.keys("*");
        System.out.println(JSONUtil.toJsonStr(set));

    }
}
