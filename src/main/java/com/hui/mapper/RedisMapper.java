package com.hui.mapper;

import com.hui.bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * @author 辉
 * 座右铭:坚持总能遇见更好的自己!
 * @date 2019/11/4
 */
@Mapper
public interface RedisMapper {

    @Select("select * from user")
    List<User> queryAll();

}
