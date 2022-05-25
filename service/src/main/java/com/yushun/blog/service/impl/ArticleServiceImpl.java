package com.yushun.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yushun.blog.common.utils.UserNullUtils;
import com.yushun.blog.mapper.*;
import com.yushun.blog.model.article.Article;
import com.yushun.blog.model.article.ArticleAttachment;
import com.yushun.blog.model.article.ArticleTag;
import com.yushun.blog.model.channel.Channel;
import com.yushun.blog.model.tag.Tag;
import com.yushun.blog.model.user.User;
import com.yushun.blog.service.ArticleService;
import com.yushun.blog.vo.ArticleVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * article service impl
 * </p>
 *
 * @author yushun zeng
 * @since 2022-5-17
 */

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ChannelMapper channelMapper;

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Autowired
    private ArticleAttachmentMapper articleAttachmentMapper;

    @Override
    public List<Article> getRandomArticle() {
        List<Article> articleList = articleMapper.getRandomArticle();

        return articleList;
    }

    @Override
    public List<Article> getArticleByChannelId(Long channelId) {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.eq("channel_Id", channelId).orderByDesc("id").last("limit 5");

        List<Article> articleList = baseMapper.selectList(wrapper);

        for(Article article : articleList) {
            QueryWrapper<User> userWrapper = new QueryWrapper<>();
            userWrapper.eq("id", article.getCreateUserId());
            User user = userMapper.selectOne(userWrapper);

            if(user != null) {
                article.setUser(user);
            }else {
                article.setUser(UserNullUtils.userIsNull());
            }
        }

        return articleList;
    }

    @Override
    public List<Article> getToppedArticleList(){
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.eq("top", 1);

        List<Article> articleList = baseMapper.selectList(wrapper);

        for (Article article : articleList) {
            QueryWrapper<User> userWrapper = new QueryWrapper<>();
            userWrapper.eq("id", article.getCreateUserId());
            User user = userMapper.selectOne(userWrapper);

            QueryWrapper<Channel> channelWrapper = new QueryWrapper<>();
            channelWrapper.eq("id", article.getChannelId());
            Channel channel = channelMapper.selectOne(channelWrapper);

            article.setChannel(channel);

            if(user != null) {
                article.setUser(user);
            }else {
                article.setUser(UserNullUtils.userIsNull());
            }
        }
        return articleList;
    }

    @Override
    public List<Article> getToppedArticleListByChannelId(Long channelId) {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.eq("top", 1);
        wrapper.eq("channel_id", channelId);

        List<Article> articleList = baseMapper.selectList(wrapper);

        for (Article article : articleList) {
            QueryWrapper<User> userWrapper = new QueryWrapper<>();
            userWrapper.eq("id", article.getCreateUserId());
            User user = userMapper.selectOne(userWrapper);

            QueryWrapper<Channel> channelWrapper = new QueryWrapper<>();
            channelWrapper.eq("id", article.getChannelId());
            Channel channel = channelMapper.selectOne(channelWrapper);

            article.setChannel(channel);

            if(user != null) {
                article.setUser(user);
            }else {
                article.setUser(UserNullUtils.userIsNull());
            }
        }
        return articleList;
    }

    @Override
    public List<Article> getToppedArticleByTagId(Long tagId) {
        QueryWrapper<ArticleTag> articleTagWrapper = new QueryWrapper<>();
        articleTagWrapper.eq("tag_id", tagId);

        List<ArticleTag> articleTagList = articleTagMapper.selectList(articleTagWrapper);
        List<Article> articleList = new ArrayList<>();

        for (ArticleTag articleTag : articleTagList) {
            QueryWrapper<Article> articleWrapper = new QueryWrapper<>();
            articleWrapper.eq("id", articleTag.getArticleId());
            articleWrapper.eq("top", 1);
            Article article = baseMapper.selectOne(articleWrapper);

            if(article == null) {
                continue;
            }

            QueryWrapper<User> userWrapper = new QueryWrapper<>();
            userWrapper.eq("id", article.getCreateUserId());
            User user = userMapper.selectOne(userWrapper);

            QueryWrapper<Channel> channelWrapper = new QueryWrapper<>();
            channelWrapper.eq("id", article.getChannelId());
            Channel channel = channelMapper.selectOne(channelWrapper);

            article.setChannel(channel);

            if(user != null) {
                article.setUser(user);
            }else {
                article.setUser(UserNullUtils.userIsNull());
            }

            articleList.add(article);
        }
        return articleList;
    }

    @Override
    public Article getArticleDetailByArticleId(Article article) {
        Long articleId = article.getId();

        Article articleDetail = baseMapper.selectById(articleId);

        // add view count
        if (article.getFront()){
            articleDetail.setArticleView(articleDetail.getArticleView() + 1);
            articleMapper.updateById(articleDetail);
        }

        // search user information
        if (articleDetail != null){
            QueryWrapper<User> userWrapper = new QueryWrapper<>();
            userWrapper.eq("id", articleDetail.getCreateUserId());
            User user = userMapper.selectOne(userWrapper);

            if (user != null){
                articleDetail.setUser(user);
            }else {
                articleDetail.setUser(UserNullUtils.userIsNull());
            }
        }

        // search article tag
        QueryWrapper<ArticleTag> articleTagWrapper = new QueryWrapper<>();
        articleTagWrapper.eq("article_id", articleId);
        List<ArticleTag> articleTagList = articleTagMapper.selectList(articleTagWrapper);

        // search article attachment tag
        QueryWrapper<ArticleAttachment> articleAttachmentWrapper = new QueryWrapper<>();
        articleAttachmentWrapper.eq("article_id", articleId);
        List<ArticleAttachment> articleAttachmentList = articleAttachmentMapper.selectList(articleAttachmentWrapper);

        List<Long> tags = new ArrayList<>();

        // set article information
        List<Map<String,Object>> articleAttachment = new ArrayList<>();

        articleTagList.forEach(item -> {
            tags.add(item.getTagId());
        });

        articleAttachmentList.forEach(item -> {
            HashMap<String, Object> map = new HashMap<>();
            map.put("name", item.getDescription());
            map.put("url", item.getUrl());
            articleAttachment.add(map);
        });

        articleDetail.setSelectTagList(tags);
        articleDetail.setArticleAttachments(articleAttachment);

        // Recommended For You, return 6 tag
        if(articleTagList.size() != 0){
            Random random = new Random();
            Long tagId = articleTagList.get(random.nextInt(articleTagList.size())).getTagId();

            QueryWrapper<ArticleTag> tagWrapper = new QueryWrapper<>();
            tagWrapper.eq("tag_id", tagId).last("limit 8");
            List<ArticleTag> tagList = articleTagMapper.selectList(tagWrapper);

            List<ArticleVo> articleVos = new ArrayList<>();

            for (ArticleTag articleTag : tagList) {
                QueryWrapper<Article> articleWrapper = new QueryWrapper<>();
                articleWrapper.eq("id", articleTag.getArticleId());
                Article articleLike = articleMapper.selectOne(articleWrapper);

                ArticleVo articleVo = new ArticleVo();

                if (articleLike != null) {
                    BeanUtils.copyProperties(articleLike, articleVo);
                    articleVos.add(articleVo);
                }
            }

            articleDetail.setArticleVo(articleVos);
        }

        return articleDetail;
    }

    @Override
    public boolean addNewArticle(Article newArticle) {
        Article article = new Article();
        BeanUtils.copyProperties(newArticle, article);
        article.setArticleView(0);
        article.setCreateTime(new Date());
        article.setUpdateTime(new Date());
        article.setIsDeleted(0);

        int insert = articleMapper.insert(article);

        if(article.getArticleAttachments() != null) {
            article.getArticleAttachments().forEach(item -> {
                ArticleAttachment articleAttachment = new ArticleAttachment();
                articleAttachment.setArticleId(article.getId());
                articleAttachment.setDescription(item.get("name") + "");
                articleAttachment.setUrl(item.get("url") + "");
                articleAttachment.setCreateTime(new Date());
                articleAttachment.setUpdateTime(new Date());
                articleAttachment.setIsDeleted(0);
                articleAttachmentMapper.insert(articleAttachment);
            });
        }

        if(article.getSelectTagList() != null) {
            article.getSelectTagList().forEach(tag -> {
                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticleId(article.getId());
                articleTag.setTagId(tag);
                articleTag.setCreateTime(new Date());
                articleTag.setUpdateTime(new Date());
                articleTag.setIsDeleted(0);
                articleTagMapper.insert(articleTag);
            });
        }

        return insert == 1;
    }

    @Override
    public Article packageArticles(Article article) {
        Long articleId = article.getId();

        QueryWrapper<ArticleAttachment> attachmentQueryWrapper = new QueryWrapper<>();
        attachmentQueryWrapper.eq("article_id", articleId);
        List<ArticleAttachment> articleAttachmentList = articleAttachmentMapper.selectList(attachmentQueryWrapper);

        List<Map<String,Object>> attachmentList = new ArrayList<>();
        if(articleAttachmentList != null) {
            for(ArticleAttachment articleAttachment: articleAttachmentList) {
                Map<String, Object> attachment = new HashMap<>();
                attachment.put("name", articleAttachment.getDescription());
                attachment.put("url", articleAttachment.getUrl());
                attachmentList.add(attachment);
            }

            article.setArticleAttachments(attachmentList);
        }

        QueryWrapper<ArticleTag> tagQueryWrapper = new QueryWrapper<>();
        tagQueryWrapper.eq("article_id", articleId);
        List<ArticleTag> articleTagList = articleTagMapper.selectList(tagQueryWrapper);

        List<Long> tagIdList = new ArrayList<>();
        if(articleTagList != null) {
            for(ArticleTag articleTag: articleTagList) {
                tagIdList.add(articleTag.getTagId());
            }

            article.setSelectTagList(tagIdList);
        }

        return article;
    }

    @Override
    public boolean updateArticle(Article updateArticle) {
        Article article = new Article();
        BeanUtils.copyProperties(updateArticle, article);
        article.setUpdateTime(new Date());

        int update = articleMapper.updateById(article);

        QueryWrapper<ArticleAttachment> attachmentQueryWrapper = new QueryWrapper<>();
        attachmentQueryWrapper.eq("article_id", updateArticle.getId());
        articleAttachmentMapper.delete(attachmentQueryWrapper);

        QueryWrapper<ArticleTag> articleTagQueryWrapper = new QueryWrapper<>();
        articleTagQueryWrapper.eq("article_id", updateArticle.getId());
        articleTagMapper.delete(articleTagQueryWrapper);

        if(article.getArticleAttachments() != null) {
            article.getArticleAttachments().forEach(item -> {
                ArticleAttachment articleAttachment = new ArticleAttachment();
                articleAttachment.setArticleId(article.getId());
                articleAttachment.setDescription(item.get("name") + "");
                articleAttachment.setUrl(item.get("url") + "");
                articleAttachment.setCreateTime(new Date());
                articleAttachment.setUpdateTime(new Date());
                articleAttachment.setIsDeleted(0);
                articleAttachmentMapper.insert(articleAttachment);
            });
        }

        if(article.getSelectTagList() != null) {
            article.getSelectTagList().forEach(tag -> {
                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticleId(article.getId());
                articleTag.setTagId(tag);
                articleTag.setCreateTime(new Date());
                articleTag.setUpdateTime(new Date());
                articleTag.setIsDeleted(0);
                articleTagMapper.insert(articleTag);
            });
        }

//        QueryWrapper<ArticleTag> articleTagQueryWrapper = new QueryWrapper<>();
//        articleTagQueryWrapper.eq("article_id", updateArticle.getId()).notIn("tag_id", updateArticle.getSelectTagList());
//        articleTagMapper.delete(articleTagQueryWrapper);
//
//        QueryWrapper<ArticleTag> currentArticleTagQueryWrapper = new QueryWrapper<>();
//        currentArticleTagQueryWrapper.eq("article_id", updateArticle.getId());
//        List<ArticleTag> currentArticleTagList = articleTagMapper.selectList(currentArticleTagQueryWrapper);
//
//        if(article.getSelectTagList() != null) {
//            article.getSelectTagList().forEach(tag -> {
//                if() {
//                    QueryWrapper<ArticleTag> tagWrapper = new QueryWrapper<>();
//                    tagWrapper.eq("article_id", updateArticle.getId()).eq("tag_id", tag);
//                    ArticleTag articleTag = articleTagMapper.selectOne(tagWrapper);
//                    articleTag.setUpdateTime(new Date());
//                    articleTagMapper.updateById(articleTag);
//                }else {
//                    ArticleTag articleTag = new ArticleTag();
//                    articleTag.setArticleId(article.getId());
//                    articleTag.setTagId(tag);
//                    articleTag.setCreateTime(new Date());
//                    articleTag.setUpdateTime(new Date());
//                    articleTag.setIsDeleted(0);
//                    articleTagMapper.insert(articleTag);
//                }
//            });
//        }

        return update == 1;
    }
}
