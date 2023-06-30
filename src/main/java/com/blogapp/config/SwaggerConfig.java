package com.blogapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@Configuration
public class SwaggerConfig {

    public static final String AUTHORIZATION_HEADER="Authorization";
    @Bean
    public Docket api()
    {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getInfo())
                .securityContexts(securityContexts())
                .securitySchemes(Arrays.asList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any()).build();
    }
    private ApiInfo getInfo()
    {
        return new ApiInfo("RESTful Web Services : API","This project is developed by Amar Kumar","version 1.0","AmarKumar","amarkumarofficial6@gmail.com","","");
    }
    private ApiKey apiKey()
    {
        return new ApiKey("JWT","AUTHORIZATION_HEADER","header");
    }
    private List<SecurityContext> securityContexts()
    {
        return  Arrays.asList(SecurityContext.builder().securityReferences(securityReferences()).build());
    }
    private List<SecurityReference>securityReferences()
    {
        AuthorizationScope scope= new AuthorizationScope("global","accessEverything");
        return  Arrays.asList(new SecurityReference("JWT",new AuthorizationScope[]{scope}));
    }
}
