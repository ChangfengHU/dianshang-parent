package com.dianshang;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

/**
 * test redis
 *
 * @author by ChangFeng
 * @Created by Administrator on 2017/8/21 0021 2:52.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TestRedis {

    @Autowired
    private Jedis jedis;
    /**
     * 测试使用java代码（jedis）操作 redis服务器
     */
    @Test
    public void testRedis() {
        // 创建redis客户端对象并指定服务器地址 端口默认为6379
        Jedis jedis = new Jedis("172.93.44.150", 6379);
        // 使redis中的pno key值加1
        Long incr = jedis.incr("pno");
        System.out.println(incr);
    }

    /**
     * 测试使用java代码（jedis）操作 redis服务器
     */
    @Test
    public void testRedis1() {
        // 创建redis客户端对象并指定服务器地址 端口默认为6379
        Jedis jedis = new Jedis("172.93.39.173", 6379);
        // 使redis中的pno key值加1
        Long incr = jedis.incr("pno");
        System.err.println(incr);
    }

}
