package com.douding.doudingcg.config;

import com.douding.doudingcg.utils.swagger.SwaggerMultiPackage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * @author Guo
 * @create 2024-04-26 14:36
 */
@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfig extends SwaggerMultiPackage {
    //是否开启swagger，正式环境一般是需要关闭的，可根据springboot的多环境配置进行设置
    @Value(value = "${swagger.enabled:}")
    Boolean swaggerEnabled;

    @Value("${spring.profiles.active:}")
    private String env;

    @Bean
    @Order(1)
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                // 是否开启
                .enable(swaggerEnabled).select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any()).build();
    }

    @Bean
    @Order(2)
    public Docket createWebRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                // 是否开启
                .enable(swaggerEnabled).select()
                .apis(basePackage("com.douding.doudingcg.controller.web"))
                .paths(PathSelectors.any()).build()
                .groupName("web端")
                ;
    }


    @Bean
    @Order(3)
    public Docket createAppRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                // 是否开启
                .enable(swaggerEnabled).select()
                .apis(RequestHandlerSelectors.basePackage("com.douding.doudingcg.controller.app"))
                .paths(PathSelectors.any()).build()
                .groupName("app端")
                .enableUrlTemplating(true)
                ;
    }


    @Bean
    @Order(4)
    public Docket createRpcRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                // 是否开启
                .enable(swaggerEnabled).select()
                .apis(RequestHandlerSelectors.basePackage("com.douding.doudingcg.controller.rpc"))
                .paths(PathSelectors.any()).build()
                .groupName("rpc端")
                ;
    }


    private ApiInfo apiInfo() {
        String url = "http://localhost:9999/";
        if("prod".equals(env)){
            url = "http://xiaoDouDing.com";
        }

        return new ApiInfoBuilder()
                .title("小豆丁后台接口文档")
                .description("Swagger2 自动生成api")
                // 作者信息
                .contact(new Contact("cg", url, "xiaodoudingcg@qq.com"))
                .version("1.0.0")
                .build();
    }

}
