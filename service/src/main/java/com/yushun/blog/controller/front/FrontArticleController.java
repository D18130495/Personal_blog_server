package com.yushun.blog.controller.front;

import com.yushun.blog.common.result.Result;
import com.yushun.blog.model.article.Article;
import com.yushun.blog.service.ArticleService;
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
}
