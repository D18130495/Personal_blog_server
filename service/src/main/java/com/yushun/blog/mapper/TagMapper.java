package com.yushun.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yushun.blog.model.tag.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/**
 * <p>
 * Tag Mapper
 * </p>
 *
 * @author yushun zeng
 * @since 20202-5-19
 */

@Repository
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

}
