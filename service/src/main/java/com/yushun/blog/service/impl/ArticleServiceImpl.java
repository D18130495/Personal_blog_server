package com.yushun.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yushun.blog.common.utils.UserNullUtils;
import com.yushun.blog.mapper.*;
import com.yushun.blog.model.article.Article;
import com.yushun.blog.model.article.ArticleAttachment;
import com.yushun.blog.model.article.ArticleTag;
import com.yushun.blog.model.channel.Channel;
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
}
