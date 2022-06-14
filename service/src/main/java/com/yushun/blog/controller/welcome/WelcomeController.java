package com.yushun.blog.controller.welcome;

import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * welcome controller
 * </p>
 *
 * @author yushun zeng
 * @since 2022-6-12
 */

@CrossOrigin
@RestController
@RequestMapping("/")
public class WelcomeController {
    @GetMapping("")
    public String welcome() {
        String welcome = "Welcome to yushun's personal blog back-end.";

        return welcome;
    }
}
