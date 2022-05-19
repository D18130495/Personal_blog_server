package com.yushun.blog.model.article;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.yushun.blog.model.base.BaseEntity;;

/**
 * <p>
 * article tag
 * </p>
 *
 * @author yushun zeng
 * @since 2022-5-19
 */

@TableName("tb_article_tag")
public class ArticleTag extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("article_id")
    private Long articleId;

    @TableField("tag_id")
    private Long tagId;

    @TableField(exist = false)
    private Article article;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
