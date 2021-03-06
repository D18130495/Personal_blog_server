package com.yushun.blog.model.article;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.yushun.blog.model.base.BaseEntity;
import com.yushun.blog.model.channel.Channel;
import com.yushun.blog.model.user.User;
import com.yushun.blog.vo.ArticleVo;
import com.yushun.blog.vo.Page;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * article
 * </p>
 *
 * @author yushun zeng
 * @since 2022-5-17
 */

@TableName("tb_article")
public class Article extends BaseEntity implements Serializable  {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "栏目id")
    private Long channelId;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "标题图")
    private String titleImg;

    @ApiModelProperty(value = "摘要")
    private String summary;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "外链URL")
    private String url;

    @ApiModelProperty(value = "正文")
    private String content;

    @ApiModelProperty(value = "0待发布1发布")
    private Integer status;

    @ApiModelProperty(value = "0/Null允许评论，不允许评论")
    private Integer commentStatus;

    @ApiModelProperty(value = "0/NULL非轮播 1 轮播")
    private Integer rotation;

    @ApiModelProperty(value = "0/null不置顶，1置顶")
    private Integer top;

    @ApiModelProperty(value = "创建人ID")
    private Long createUserId;

    @ApiModelProperty(value = "文章点击数")
    private Integer articleView;

    @TableField(exist = false)
    private User user;

    @TableField(exist = false)
    private Channel channel;

    @TableField(exist = false)
    private List<Map<String,Object>> articleAttachments;

    @TableField(exist = false)
    private List<Long> selectTagList;

    @TableField(exist = false)
    private List<Page> pageList;

    @TableField(exist = false)
    private List<ArticleVo> articleVo;

    @TableField(exist = false)
    private Boolean front;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleImg() {
        return titleImg;
    }

    public void setTitleImg(String titleImg) {
        this.titleImg = titleImg;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public Integer getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(Integer commentStatus) {
        this.commentStatus = commentStatus;
    }

    public Integer getRotation() {
        return rotation;
    }

    public void setRotation(Integer rotation) {
        this.rotation = rotation;
    }

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public List<Map<String, Object>> getArticleAttachments() {
        return articleAttachments;
    }

    public void setArticleAttachments(List<Map<String, Object>> articleAttachments) {
        this.articleAttachments = articleAttachments;
    }

    public List<Long> getSelectTagList() {
        return selectTagList;
    }

    public void setSelectTagList(List<Long> selectTagList) {
        this.selectTagList = selectTagList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Page> getPageList() {
        return pageList;
    }

    public void setPageList(List<Page> pageList) {
        this.pageList = pageList;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public List<ArticleVo> getArticleVo() {
        return articleVo;
    }

    public void setArticleVo(List<ArticleVo> articleVo) {
        this.articleVo = articleVo;
    }

    public Integer getArticleView() {
        return articleView;
    }

    public void setArticleView(Integer articleView) {
        this.articleView = articleView;
    }

    public Boolean getFront() {
        return front;
    }

    public void setFront(Boolean front) {
        this.front = front;
    }

    @Override
    public String toString() {
        return "Article{" +
                "channelId=" + channelId +
                ", title='" + title + '\'' +
                ", titleImg='" + titleImg + '\'' +
                ", summary='" + summary + '\'' +
                ", author='" + author + '\'' +
                ", url='" + url + '\'' +
                ", content='" + content + '\'' +
                ", status=" + status +
                ", commentStatus=" + commentStatus +
                ", rotation=" + rotation +
                ", top=" + top +
                ", createUserId=" + createUserId +
                ", articleView=" + articleView +
                ", user=" + user +
                ", channel=" + channel +
                ", articleAttachments=" + articleAttachments +
                ", selectTagList=" + selectTagList +
                ", pageList=" + pageList +
                ", articleVo=" + articleVo +
                ", front=" + front +
                '}';
    }
}
