package com.yushun.blog.controller.front;

import com.yushun.blog.common.result.Result;
import com.yushun.blog.model.article.Article;
import com.yushun.blog.service.ArticleService;
//import com.yushun.blog.vo.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * channel Mapper
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

        Page<Article> paginatedArticlesList = articleService.page(page);

        return Result.ok(paginatedArticlesList);
    }
}
