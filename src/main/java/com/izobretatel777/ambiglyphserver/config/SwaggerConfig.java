package com.izobretatel777.ambiglyphserver.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// configuration for autodocumentation
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        // Add JWT authentication support basic information to Swagger
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .info(new Info()
                        .title("Ambiglyph OpenApi reference")
                        .version("V1.0")
                        .description("API documentation for Ambiglyph server")
                        .license(new License().name("Licence").url("https://springdoc.org")));
    }


    // Groups
    @Bean
    public GroupedOpenApi WordsController() {
        return GroupedOpenApi.builder()
                .group("Users words (dictionary) control API")
                .pathsToMatch(
                        "/words/**"
                )
                .build();
    }

    @Bean
    public GroupedOpenApi TextScanning() {
        return GroupedOpenApi.builder()
                .group("Scanning API")
                .pathsToMatch(
                        "/check/**"
                )
                .build();
    }

    @Bean
    public GroupedOpenApi UserManagementApi() {
        return GroupedOpenApi.builder()
                .group("User management API")
                .pathsToMatch(
                        "/authenticate/**",
                        "/users/**"
                )
                .build();
    }
}
