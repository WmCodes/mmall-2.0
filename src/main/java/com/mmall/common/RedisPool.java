package com.mmall.common;

import com.mmall.util.PropertiesUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author wangmeng
 * @date 2019/4/26
 * @desciption
 */
public class RedisPool {

    private static JedisPool pool;//jedis连接池
    private static Integer maxTotal = Integer.parseInt(PropertiesUtil.getProperty("redis.max.total","20")); //最大连接数
    private static Integer maxIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.max.idle","10"));//在jedispool中最大的idle状态(空闲的)的jedis实例的个数
    private static Integer minIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.min.idle","2"));//在jedispool中最小的idle状态(空闲的)的jedis实例的个数
    private static boolean testOnBorrow = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.borrow","true"));//在borrow一个jedis实例的时候，是否进行验证操作，如果赋值true，则得到的jedis实例肯定是可用的
    private static boolean testOnReturn = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.return","true"));//在return一个jedis实例的时候，是否进行验证操作，如果赋值true，则放回jedispool的jedis实例肯定是可用的

    private static String redisIp = PropertiesUtil.getProperty("redis.ip");
    private static Integer redisPort = Integer.parseInt(PropertiesUtil.getProperty("redis.port"));
    private static String redisPass = PropertiesUtil.getProperty("redis.password");
    private static String redisUri = PropertiesUtil.getProperty("redis.uri");
    private static  void initPool(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);
        config.setBlockWhenExhausted(true);//连接耗尽时  是否阻塞

       // pool = new JedisPool(config,redisIp,redisPort,1000*2,redisPass);

        try {
            pool = new JedisPool(config,new URI(redisUri) ,1000*2);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    static {
        initPool();
    }

    public static Jedis getJedis(){
        return pool.getResource();
    }

    public static void returnBrokenResource(Jedis jedis){
       pool.returnBrokenResource(jedis);
    }

    public static void returnResource(Jedis jedis){
            pool.returnResource(jedis);
    }


    public static void main(String[] args) {
        Jedis jedis = pool.getResource();
        jedis.set("geelkkey","value");
        returnResource(jedis);
        pool.destroy();
        System.out.println("************");
    }


}
