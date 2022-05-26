package com.yushun.blog.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yushun.blog.common.result.Result;
import com.yushun.blog.common.utils.UserNullUtils;
import com.yushun.blog.mapper.ArticleTagMapper;
import com.yushun.blog.mapper.ChannelMapper;
import com.yushun.blog.mapper.UserMapper;
import com.yushun.blog.model.article.Article;
import com.yushun.blog.model.article.ArticleTag;
import com.yushun.blog.model.channel.Channel;
import com.yushun.blog.model.user.User;
import com.yushun.blog.service.ArticleService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yushun.blog.vo.admin.article.ArticleQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private ArticleTagMapper articleTagMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ChannelMapper channelMapper;

    @GetMapping("/getRandomArticle")
    public Result getRandomArticle() {
        List<Article> randomArticleList = articleService.getRandomArticle();

        return Result.ok(randomArticleList);
    }

    @GetMapping("/getToppedArticleList")
    public Result getToppedArticleList() {
        List<Article> articleList = articleService.getToppedArticleList();

        return Result.ok(articleList);
    }

    @GetMapping("/getToppedArticleListByChannelId/{channelId}")
    public Result getToppedArticleListByChannelId(@PathVariable Long channelId) {
        List<Article> articleList = articleService.getToppedArticleListByChannelId(channelId);

        return Result.ok(articleList);
    }

    @GetMapping("/getToppedArticleByTagId/{tagId}")
    public Result getToppedArticleByTagId(@PathVariable Long tagId) {
        List<Article> articleList = articleService.getToppedArticleByTagId(tagId);

        return Result.ok(articleList);
    }

    @GetMapping("/getPaginatedArticlesList/{current}/{limit}")
    public Result getPaginatedArticlesList(@PathVariable Long current,
                                           @PathVariable Long limit) {
        Page<Article> page = new Page<>(current, limit);

        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.eq("top", 0);

        Page<Article> paginatedArticlesList = articleService.page(page, wrapper);

        if(paginatedArticlesList != null) {
            for (Article article : paginatedArticlesList.getRecords()) {
                QueryWrapper<User> userWrapper = new QueryWrapper<>();
                userWrapper.eq("id", article.getCreateUserId());
                User user = userMapper.selectOne(userWrapper);

                QueryWrapper<Channel> channelWrapper = new QueryWrapper<>();
                channelWrapper.eq("id", article.getChannelId());
                Channel channel = channelMapper.selectOne(channelWrapper);

                article.setChannel(channel);

                if (user != null) {
                    article.setUser(user);
                } else {
                    article.setUser(UserNullUtils.userIsNull());
                }
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

    @GetMapping("/getPaginatedTagArticleByTagId/{current}/{limit}/{tagId}")
    public Result getPaginatedTagArticleByTagId(@PathVariable Long current,
                                                @PathVariable Long limit,
                                                @PathVariable Long tagId){
        QueryWrapper<ArticleTag> articleTagWrapper = new QueryWrapper<>();
        articleTagWrapper.eq("tag_id", tagId);

        List<ArticleTag> articleTagList = articleTagMapper.selectList(articleTagWrapper);

        List<Article> articleList = new ArrayList<>();

        for (ArticleTag articleTag : articleTagList) {
            QueryWrapper<Article> articleWrapper = new QueryWrapper<>();
            articleWrapper.eq("id", articleTag.getArticleId());
            Article article = articleService.getOne(articleWrapper);

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

        // pagination
        Map<String, Object> paginatedArticlesList = new HashMap<>();

        if(current == 0L) {
            current = 1L;
        }

        long startIndex = (current - 1) * limit;
        long endIndex = current * limit;
        List<Article> subList;

        if(current * limit <= articleList.size()) {
            subList = articleList.subList((int) startIndex, (int) endIndex);
        }else {
            subList = articleList.subList((int) startIndex, articleList.size());
        }

        paginatedArticlesList.put("records", subList);
        paginatedArticlesList.put("total", articleList.size());

        return Result.ok(paginatedArticlesList);
    }

    @GetMapping("/getArticleQueryPaginatedList/{current}/{limit}")
    public Result getArticleQueryPaginatedList(@PathVariable Long current,
                                               @PathVariable Long limit,
                                               ArticleQueryVo articleQueryVo){
        Page<Article> page = new Page<>(current, limit);

        QueryWrapper<Article> wrapper = new QueryWrapper<>();

        if(!StringUtils.isEmpty(articleQueryVo.getTitle())) {
            wrapper.like("title", articleQueryVo.getTitle());
        }

        Page<Article> paginatedArticlesList = articleService.page(page, wrapper);

        if(paginatedArticlesList != null) {
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
        }

        return Result.ok(paginatedArticlesList);
    }
}
