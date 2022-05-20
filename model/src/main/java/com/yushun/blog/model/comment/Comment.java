package com.yushun.blog.model.comment;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.yushun.blog.model.base.BaseEntity;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * comment
 * </p>
 *
 * @author yushun zeng
 * @since 2022-5-20
 */

@TableName("tb_comment")
public class Comment extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "评论者")
    @TableField("author")
    private String author;

    @ApiModelProperty(value = "邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty(value = "正文")
    @TableField("content")
    private String content;

    @ApiModelProperty(value = "0 待批准, 1已通过, 2未通过")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "文章ID")
    @TableField("article_id")
    private Integer articleId;

    @TableField(exist = false)
    private String statusName;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
