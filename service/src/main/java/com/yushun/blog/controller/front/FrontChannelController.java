package com.yushun.blog.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yushun.blog.common.result.Result;
import com.yushun.blog.common.utils.UserNullUtils;
import com.yushun.blog.mapper.ChannelMapper;
import com.yushun.blog.mapper.UserMapper;
import com.yushun.blog.model.article.Article;
import com.yushun.blog.model.channel.Channel;
import com.yushun.blog.model.user.User;
import com.yushun.blog.service.ArticleService;
import com.yushun.blog.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * front channel controller
 * </p>
 *
 * @author yushun zeng
 * @since 2022-5-15
 */

@CrossOrigin
@RestController
@RequestMapping("/front/channel")
public class FrontChannelController {
    @Autowired
    private ChannelService channelService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ChannelMapper channelMapper;

//    @GetMapping("/findAll")
//    public Result findAllChannel() {
//        List<Channel> list = channelService.list();
//        return Result.ok(list);
//    }
//
//    @DeleteMapping("/{id}")
//    public void remove(@PathVariable Long id) {
//        channelService.removeById(id);
//    }

    @GetMapping("/getChannelByChannelId/{channelId}")
    public Result getChannelByChannelId(@PathVariable Long channelId){
        Channel channel = channelService.getById(channelId);

        return Result.ok(channel);
    }

    @GetMapping("/getChannelByPos/{pos}")
    public Result getChannelByPos(@PathVariable String pos){
        if (StringUtils.isEmpty(pos)){
            return Result.fail();
        }

        List<Channel> channelPos = channelService.getChannelByPos(pos.toUpperCase());

        List<Map<String,Object>> mapList = new ArrayList<>();
        for (Channel channel : channelPos) {
            if(channel.getParentId() == 0) {
                Map<String,Object> map = new HashMap<>();
                map.put("id", channel.getId());
                map.put("name", channel.getName());

                if("Y".equals(channel.getSingle())) {
                    map.put("single", true);
                }

                List<Map<String,Object>> children = new ArrayList<>();
                for(Channel entity : channelPos) {
                    if(entity.getParentId().equals(channel.getId())) {
                        Map<String,Object> subMap = new HashMap<>();
                        subMap.put("id", entity.getId());
                        subMap.put("name", entity.getName());
                        if("Y".equals(entity.getSingle())){
                            map.put("single", true);
                        }
                        children.add(subMap);
                    }
                }
                if (children.size() > 0){
                    map.put("children", children);
                }
                mapList.add(map);
            }
        }
        return Result.ok(mapList);
    }

    @GetMapping("/getArticleByChannelId/{channelId}")
    public Result getArticleByChannelId(@PathVariable Long channelId){
        List<Article> articleList = articleService.getArticleByChannelId(channelId);

        return Result.ok(articleList);
    }

    @GetMapping("/getPaginatedChannelArticleByChannelId/{current}/{limit}/{channelId}")
    public Result getArticleByChannelId(@PathVariable Long current,
                                        @PathVariable Long limit,
                                        @PathVariable Long channelId){
        Page<Article> page = new Page<>(current, limit);

        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.eq("top", 0);
        wrapper.eq("channel_id", channelId);

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
}
