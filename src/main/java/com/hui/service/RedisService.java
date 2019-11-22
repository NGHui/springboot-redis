package com.hui.service;

import com.hui.bean.User;

import java.util.List;

/**
 * @author 辉
 * 座右铭:坚持总能遇见更好的自己!
 * @date 2019/11/5
 */
public interface RedisService {

    /**
     * 高并发,不缓存穿透
     * @return
     */
     List<User> queryAll();

    /**
     * 高斌发,缓存穿透
     * @return
     */
     List<User> queryAll1();
}
