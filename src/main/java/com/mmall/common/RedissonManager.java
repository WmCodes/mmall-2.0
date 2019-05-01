package com.mmall.common;

import com.mmall.util.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author wangmeng
 * @date 2019/5/1
 * @desciption
 */
@Component
@Slf4j
public class RedissonManager {

    private Config config =  new Config();

    private Redisson redisson = null;

    public Redisson getRedisson() {
        return redisson;
    }

    private static String redisUri1 = PropertiesUtil.getProperty("redis.uri");
    private static String redisUri2 = PropertiesUtil.getProperty("redis.uri2");

    @PostConstruct
    private void init(){
        try {
            SingleServerConfig singleServerConfig =  config.useSingleServer().setAddress("redis://192.168.1.13:6379");
            singleServerConfig.setPassword("123456");
            singleServerConfig.setDatabase(0);
            redisson = (Redisson)Redisson.create(config);
            log.info("初始化Redisson结束");
        } catch (Exception e) {
            log.error("redisson init error",e);
            e.printStackTrace();
        }


    }

}
