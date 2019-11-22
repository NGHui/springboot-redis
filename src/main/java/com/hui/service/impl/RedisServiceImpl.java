package com.hui.service.impl;

import com.hui.bean.User;
import com.hui.mapper.RedisMapper;
import com.hui.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 辉
 * 座右铭:坚持总能遇见更好的自己!
 * @date 2019/11/5
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisMapper redisMapper;

    @Autowired
    RedisTemplate<Object,Object> redisTemplate;


    /**
     * 解决高并发缓存穿透问题
     * @return
     */
    @Override
    public List<User> queryAll() {
        //字符串序列化器
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        //解决高并发:缓存穿透问题
        //查询缓存
        List<User> allUser = (List<User>)redisTemplate.opsForValue().get("allUser");
        //双重检测锁
        if (allUser==null){
            //加上线程锁
            synchronized (this){
                //从redis获取一下
                 allUser = (List<User>)redisTemplate.opsForValue().get("allUser");
                //缓存为空,查数据库
                if (allUser==null){
                    System.out.println("我是查数据库!.....");
                    allUser = redisMapper.queryAll();
                    //将查到的数据放在数据库中
                    redisTemplate.opsForValue().set("allUser",allUser);
                }else {
                    System.out.println("我是查缓存!.....");
                }
            }
        }else {
            System.out.println("我是查缓存!");
        }
        return allUser;
    }

    /**
     * 存在缓存穿透问题
     * @return
     */
    public List<User> queryAll1() {
        //字符串序列化器
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        //查询缓存
        List<User> allUser = (List<User>)redisTemplate.opsForValue().get("allUser");
        if (allUser==null){
            //缓存为空,查数据库
            allUser = redisMapper.queryAll();
            System.out.println("我是查数据库!");
            //将查到的数据放在数据库中
            redisTemplate.opsForValue().set("allUser",allUser);
        }else {
            System.out.println("我是查缓存");
        }
        return allUser;
    }
}
