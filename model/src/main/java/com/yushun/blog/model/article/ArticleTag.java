package com.yushun.blog.model.article;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.yushun.blog.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * article tag
 * </p>
 *
 * @author yushun zeng
 * @since 2022-5-15
 */

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_article_tag")
@ApiModel(value="ArticleTag对象", description="文章标签")
public class ArticleTag extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer articleId;

    private Integer tagId;

    private Article article;
}
