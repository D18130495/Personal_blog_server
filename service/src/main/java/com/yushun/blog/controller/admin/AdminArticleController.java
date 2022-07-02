package com.yushun.blog.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yushun.blog.common.result.Result;
import com.yushun.blog.common.utils.RequestUtils;
import com.yushun.blog.model.article.Article;
import com.yushun.blog.model.channel.Channel;
import com.yushun.blog.model.friend.FriendLink;
import com.yushun.blog.model.tag.Tag;
import com.yushun.blog.model.user.User;
import com.yushun.blog.service.ArticleService;
import com.yushun.blog.service.TagService;
import com.yushun.blog.service.UserService;
import com.yushun.blog.vo.admin.article.ArticleQueryVo;
import com.yushun.blog.vo.admin.channel.ChannelUpdateVo;
import com.yushun.blog.vo.admin.friend.FriendLinkQueryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * admin article controller
 * </p>
 *
 * @author yushun zeng
 * @since 2022-5-24
 *
 * CacheEvict for remove cache when admin user add, update and delete article
 *
 */

@CrossOrigin
@RestController
@RequestMapping("/admin/article")
public class AdminArticleController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    @GetMapping("/getArticleDataByArticleId/{articleId}")
    public Result getArticleDataByArticleId(@PathVariable Long articleId) {
        Article article = articleService.getById(articleId);

        Article packagedArticle = articleService.packageArticles(article);

        return Result.ok(packagedArticle);
    }

    @CacheEvict(value="toppedArticle", allEntries = true)
    @PostMapping("/addNewArticle")
    public Result addNewArticle(@RequestBody Article newArticle) {
        List<Article> articleList = articleService.list();

        for(Article existsArticle : articleList) {
            if(existsArticle.getTitle().equals(newArticle.getTitle())) {
                return Result.fail().message("Article already exists");
            }
        }

        boolean save = articleService.addNewArticle(newArticle);

        if(save) {
            return Result.ok().message("Successfully added new article");
        }else {
            return Result.fail().message("Failed to add new article");
        }
    }

    @CacheEvict(value="toppedArticle", allEntries = true)
    @PutMapping("/updateArticleByArticleId")
    public Result updateArticleByArticleId(@RequestBody Article updateArticle) {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.notIn("id", updateArticle.getId());
        List<Article> articleList = articleService.list(wrapper);

        for(Article existsArticle : articleList) {
            if(existsArticle.getTitle().equals(updateArticle.getTitle())) {
                return Result.fail().message("Article already exists");
            }
        }

        updateArticle.setUpdateTime(new Date());

        boolean update = articleService.updateArticle(updateArticle);

        if(update) {
            return Result.ok().message("Successfully updated article");
        }else {
            return Result.fail().message("Failed to update article");
        }
    }

    @CacheEvict(value="toppedArticle", allEntries = true)
    @DeleteMapping("/deleteArticleById/{articleId}")
    public Result deleteArticleById(@PathVariable Long articleId) {
        Article article = articleService.getById(articleId);
        article.setUpdateTime(new Date());

        boolean update = articleService.updateById(article);

        boolean delete = articleService.removeById(articleId);

        if(update && delete) {
            return Result.ok().message("Successfully deleted article");
        }else {
            return Result.fail().message("Failed to delete article");
        }
    }

    @GetMapping("/getArticleQueryPaginatedList/{current}/{limit}")
    public Result getArticleQueryPaginatedList(@PathVariable Long current,
                                               @PathVariable Long limit,
                                               ArticleQueryVo articleQueryVo) {
        Page<Article> page = new Page<>(current, limit);

        QueryWrapper<Article> wrapper = new QueryWrapper<>();

        if(!StringUtils.isEmpty(articleQueryVo.getTitle())) {
            wrapper.like("title", articleQueryVo.getTitle());
        }

        Page<Article> paginatedArticleList = articleService.page(page, wrapper);

        for(Article article : paginatedArticleList.getRecords()) {
            QueryWrapper<User> userWrapper = new QueryWrapper<>();
            userWrapper.eq("id", article.getCreateUserId());
            User user = userService.getOne(userWrapper);
            article.setUser(user);
        }

        return Result.ok(paginatedArticleList);
    }

    @PostMapping("/uploadArticleImageAndFile")
    public Result upload(@RequestParam MultipartFile file, HttpServletRequest request) throws IOException {
        String originalFilename = file.getOriginalFilename();

        String ex = originalFilename.substring(originalFilename.lastIndexOf(".") + 1,originalFilename.length());
        String newFileNamePrefix = UUID.randomUUID().toString();
        String newFileName = newFileNamePrefix + "." + ex;
        file.transferTo(new File("/data/app/personal_blog_client/dist/images/article", newFileName));

        return Result.ok("https://huaruoyumu.com/images/article/" + newFileName).message("Successfully uploaded article image");
    }
}
