package com.yushun.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yushun.blog.common.utils.UserNullUtils;
import com.yushun.blog.mapper.ArticleMapper;
import com.yushun.blog.mapper.ChannelMapper;
import com.yushun.blog.mapper.UserMapper;
import com.yushun.blog.model.article.Article;
import com.yushun.blog.model.channel.Channel;
import com.yushun.blog.model.user.User;
import com.yushun.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
