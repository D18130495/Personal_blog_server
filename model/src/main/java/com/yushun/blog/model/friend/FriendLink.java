package com.yushun.blog.model.friend;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.yushun.blog.model.base.BaseEntity;

/**
 * <p>
 * friend link
 * </p>
 *
 * @author yushun zeng
 * @since 2022-5-19
 */

@TableName("tb_friend_link")
public class FriendLink extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("url")
    private String url;

    @TableField("title")
    private String title;

    @TableField("target")
    private String target;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
