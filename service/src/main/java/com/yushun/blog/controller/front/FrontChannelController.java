package com.yushun.blog.controller.front;

import com.yushun.blog.common.result.Result;
import com.yushun.blog.model.article.Article;
import com.yushun.blog.model.channel.Channel;
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
}
