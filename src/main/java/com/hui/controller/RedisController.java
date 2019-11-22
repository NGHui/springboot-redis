package com.hui.controller;

import com.hui.bean.User;
import com.hui.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 辉
 * 座右铭:坚持总能遇见更好的自己!
 * @date 2019/11/4
 */
@Controller
public class RedisController {

    @Autowired
    private RedisService redisServiceImpl;

    /**
     * 高斌发查询测试,不缓存穿透
     * @return
     */
    @ResponseBody
    @RequestMapping("/query")
    public List<User> queryAll(){
        Runnable r = new Runnable(){
            @Override
            public void run() {
                redisServiceImpl.queryAll();
            }
        };

        //使用多线程测试高并发的问题
        //使用线程池提交数据,开启10000条线程访问
        ExecutorService executorService = Executors.newFixedThreadPool(25);
        for (int i = 0; i < 10000; i++) {
            //查询10000次
            executorService.submit(r);
        }
        return redisServiceImpl.queryAll();
    }

    /**
     * 高斌发查询测试,缓存穿透 会同时有很多人查数据库
     * @return
     */
    @ResponseBody
    @RequestMapping("/query1")
    public List<User> queryAll1(){
        Runnable r = new Runnable(){
            @Override
            public void run() {
                redisServiceImpl.queryAll1();
            }
        };
        //使用多线程测试高并发的问题
        //使用线程池提交数据,开启10000条线程访问
        ExecutorService executorService = Executors.newFixedThreadPool(25);
        for (int i = 0; i < 10000; i++) {
            //查询10000次
            executorService.submit(r);
        }
        return redisServiceImpl.queryAll1();
    }
}
