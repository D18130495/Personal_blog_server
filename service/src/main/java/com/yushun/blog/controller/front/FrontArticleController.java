package com.yushun.blog.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yushun.blog.common.result.Result;
import com.yushun.blog.common.utils.UserNullUtils;
import com.yushun.blog.mapper.ChannelMapper;
import com.yushun.blog.mapper.UserMapper;
import com.yushun.blog.model.article.Article;
import com.yushun.blog.model.channel.Channel;
import com.yushun.blog.model.user.User;
import com.yushun.blog.service.ArticleService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * front article controller
 * </p>
 *
 * @author yushun zeng
 * @since 2022-5-15
 */

@CrossOrigin
@RestController
@RequestMapping("/front/article")
public class FrontArticleController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ChannelMapper channelMapper;

    @GetMapping("/getRandomArticle")
    public Result getRandomArticle(){
        List<Article> randomArticleList = articleService.getRandomArticle();

        return Result.ok(randomArticleList);
    }

    @GetMapping("/getToppedArticleList")
    public Result getToppedArticleList(){
        List<Article> articleList = articleService.getToppedArticleList();

        return Result.ok(articleList);
    }

    @GetMapping("/getPaginatedArticlesList/{current}/{limit}")
    public Result getPaginatedArticlesList(@PathVariable Long current,
                                           @PathVariable Long limit){
        Page<Article> page = new Page<>(current, limit);

        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.eq("top", 0);

        Page<Article> paginatedArticlesList = articleService.page(page, wrapper);

        for(Article article : paginatedArticlesList.getRecords()) {
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
        return Result.ok(paginatedArticlesList);
    }

    @GetMapping("/getNoticeByChannelId/{channelId}")
    public Result getNoticeByChannelId(@PathVariable Long channelId) {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.eq("channel_Id", channelId).orderByDesc("id").last("limit 4");

        List<Article> noticesList = articleService.list(wrapper);

        return Result.ok(noticesList);
    }

    @GetMapping("/getArticleByViewTime")
    public Result getArticleByViewTime() {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("article_view").last("limit 8");

        List<Article> mostViewList = articleService.list(wrapper);

        return Result.ok(mostViewList);
    }

    @GetMapping("/getRecommendedArticle/{articleId}")
    public Result getRecommendedArticle(@PathVariable Long articleId) {
        Article article = new Article();
        article.setId(articleId);
        article.setFront(false);
        Article articleDetail = articleService.getArticleDetailByArticleId(article);

        return Result.ok(articleDetail);
    }

    @GetMapping("/getArticleById/{articleId}")
    public Result getArticleById(@PathVariable Long articleId){
        Article article = new Article();
        article.setId(articleId);
        article.setFront(true);
        Article articleDetail = articleService.getArticleDetailByArticleId(article);

        return Result.ok(articleDetail);
    }
}
