package com.yushun.blog.model.channel;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.yushun.blog.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * channel
 * </p>
 *
 * @author yushun zeng
 * @since 2022-5-15
 */

@EqualsAndHashCode(callSuper = false)
@TableName("tb_channel")
@ApiModel(value="Channel对象", description="栏目")
public class Channel extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "上级栏目")
    @TableField("parent_id")
    private Long parentId;

    @ApiModelProperty(value = "栏目图片")
    @TableField("channel_img")
    private String channelImg;

    @ApiModelProperty(value = "摘要")
    @TableField("summary")
    private String summary;

    @ApiModelProperty(value = "是否单页 .Y为单页，Null为不单页")
    @TableField("single")
    private String single;

    @ApiModelProperty(value = "外链URL")
    @TableField("url")
    private String url;

    @ApiModelProperty(value = "SEO标题")
    @TableField("seo_title")
    private String seoTitle;

    @ApiModelProperty(value = "SEO关键字")
    @TableField("seo_keyword")
    private String seoKeyword;

    @ApiModelProperty(value = "SEO描述")
    @TableField("seo_description")
    private String seoDescription;

    @ApiModelProperty(value = "正文")
    @TableField("content")
    private String content;

//    @ApiModelProperty(value = "排序")
//    private Integer orderBy;

    @ApiModelProperty(value = "创建人ID")
    @TableField("create_userId")
    private Long createUser;

    @TableField(exist = false)
    @ApiModelProperty(value = "发布人名称")
    private String userName;

    @TableField("pos")
    private String pos;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getChannelImg() {
        return channelImg;
    }

    public void setChannelImg(String channelImg) {
        this.channelImg = channelImg;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSingle() {
        return single;
    }

    public void setSingle(String single) {
        this.single = single;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSeoTitle() {
        return seoTitle;
    }

    public void setSeoTitle(String seoTitle) {
        this.seoTitle = seoTitle;
    }

    public String getSeoKeyword() {
        return seoKeyword;
    }

    public void setSeoKeyword(String seoKeyword) {
        this.seoKeyword = seoKeyword;
    }

    public String getSeoDescription() {
        return seoDescription;
    }

    public void setSeoDescription(String seoDescription) {
        this.seoDescription = seoDescription;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }
}
