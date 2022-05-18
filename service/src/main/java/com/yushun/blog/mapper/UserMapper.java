package com.yushun.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yushun.blog.model.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/**
 * <p>
 *  Mapper interface
 * </p>
 *
 * @author yushun zeng
 * @since 20202-5-18
 */

@Repository
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
