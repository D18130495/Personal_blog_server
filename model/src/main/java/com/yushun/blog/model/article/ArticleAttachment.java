package com.yushun.blog.model.article;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.yushun.blog.model.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
/**
 * <p>
 * article attachment
 * </p>
 *
 * @author yushun zeng
 * @since 2022-5-19
 */

@TableName("tb_article_attachment")
public class ArticleAttachment extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("article_id")
    private Long articleId;

    @TableField("url")
    private String url;

    @TableField("description")
    private String description;

    @TableField(exist = false)
    private String suffix;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
