package com.yushun.blog.model.tag;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yushun.blog.model.base.BaseEntity;

import java.io.Serializable;

/**
 * <p>
 * tag
 * </p>
 *
 * @author yushun zeng
 * @since 2022-5-19
 */

@TableName("tb_tag")
public class Tag extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("tag_name")
    private String tagName;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
