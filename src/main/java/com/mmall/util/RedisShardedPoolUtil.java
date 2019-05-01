package com.mmall.util;

import com.mmall.common.RedisShardePool;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.ShardedJedis;

/**
 * @author wangmeng
 * @date 2019/4/26
 * @desciption
 */
@Slf4j
public class RedisShardedPoolUtil {


    //exTime的单位是秒
    public static Long expire(String key,int exTime){
        ShardedJedis jedis = null;
        Long result = null;

        try {
            jedis = RedisShardePool.getJedis();
            result = jedis.expire(key,exTime);
        } catch (Exception e) {
            log.error("expire key:{} error",key,e);
            RedisShardePool.returnBrokenResource(jedis);
            return result;
        }
        RedisShardePool.returnResource(jedis);
        return result;
    }


    //exTime的单位是秒
    public static String setEx(String key,String value,int exTime){
        ShardedJedis jedis = null;
        String result = null;

        try {
            jedis = RedisShardePool.getJedis();
            result = jedis.setex(key,exTime,value);
        } catch (Exception e) {
            log.error("set key:{} value:{} error",key,value,e);
            RedisShardePool.returnBrokenResource(jedis);
            return result;
        }
        RedisShardePool.returnResource(jedis);
        return result;
    }

    public static String set(String key,String value){
        ShardedJedis jedis = null;
        String result = null;

        try {
            jedis = RedisShardePool.getJedis();
            result = jedis.set(key,value);
        } catch (Exception e) {
            log.error("set key:{} value:{} error",key,value,e);
            RedisShardePool.returnBrokenResource(jedis);
            return result;
        }
        RedisShardePool.returnResource(jedis);
        return result;
    }

    public static String get(String key){
        ShardedJedis jedis = null;
        String result = null;

        try {
            jedis = RedisShardePool.getJedis();
            result = jedis.get(key);
        } catch (Exception e) {
            log.error("get key:{} error",key,e);
            RedisShardePool.returnBrokenResource(jedis);
            return result;
        }
        RedisShardePool.returnResource(jedis);
        return result;
    }

    public static Long del(String key){
        ShardedJedis jedis = null;
        Long result = null;

        try {
            jedis = RedisShardePool.getJedis();
            result = jedis.del(key);
        } catch (Exception e) {
            log.error("del key:{} error",key,e);
            RedisShardePool.returnBrokenResource(jedis);
            return result;
        }
        RedisShardePool.returnResource(jedis);
        return result;
    }

    public static Long setnx(String key,String value){
        ShardedJedis jedis = null;
        Long result = null;

        try {
            jedis = RedisShardePool.getJedis();
            result = jedis.setnx(key,value);
        } catch (Exception e) {
            log.error("setnx key:{} value:{} error",key,value,e);
            RedisShardePool.returnBrokenResource(jedis);
            return result;
        }
        RedisShardePool.returnResource(jedis);
        return result;
    }

    public static String getSet(String key,String value){
        ShardedJedis jedis = null;
        String result = null;

        try {
            jedis = RedisShardePool.getJedis();
            result = jedis.getSet(key,value);
        } catch (Exception e) {
            log.error("getSet key:{} value:{} error",key,value,e);
            RedisShardePool.returnBrokenResource(jedis);
            return result;
        }
        RedisShardePool.returnResource(jedis);
        return result;
    }

    public static void main(String[] args) {
        ShardedJedis jedis = RedisShardePool.getJedis();

        RedisShardedPoolUtil.set("ketTest","value");
        String value = RedisShardedPoolUtil.get("ketTest");
        RedisShardedPoolUtil.setEx("keyex","valueex",60*10);
        RedisShardedPoolUtil.expire("ketTest",60*20);

        RedisShardedPoolUtil.del("ketTest");
        System.out.println("*****************");
    }



}
