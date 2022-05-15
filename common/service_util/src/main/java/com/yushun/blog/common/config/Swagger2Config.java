package com.yushun.blog.common.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <p>
 * Swagger2
 * </p>
 *
 * @author yushun zeng
 * @since 2022-5-15
 */

@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket frontApiConfig(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("frontApi")
                .apiInfo(frontApiInfo())
                .select()
                //只显示api路径下的页面
                .paths(Predicates.and(PathSelectors.regex("/front/.*")))
                .build();
    }

    @Bean
    public Docket adminApiConfig(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("adminApi")
                .apiInfo(adminApiInfo())
                .select()
                //只显示admin路径下的页面
                .paths(Predicates.and(PathSelectors.regex("/admin/.*")))
                .build();
    }

    private ApiInfo frontApiInfo(){
        return new ApiInfoBuilder()
                .title("博客页面API")
                .description("Front API")
                .version("1.0")
                .contact(new Contact("yushun", "", "D18130495@mytudublin.ie"))
                .build();
    }

    private ApiInfo adminApiInfo(){
        return new ApiInfoBuilder()
                .title("后台管理系统API")
                .description("Admin API")
                .version("1.0")
                .contact(new Contact("yushun", "", "D18130495@mytudublin.ie"))
                .build();
    }
}
