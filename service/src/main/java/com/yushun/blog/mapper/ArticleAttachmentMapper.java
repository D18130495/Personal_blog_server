package com.yushun.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yushun.blog.model.article.ArticleAttachment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * article attachment Mapper
 * </p>
 *
 * @author yushun zeng
 * @since 2022-5-19
 */

@Repository
@Mapper
public interface ArticleAttachmentMapper extends BaseMapper<ArticleAttachment> {

}
