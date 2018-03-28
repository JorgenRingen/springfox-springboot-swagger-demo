package org.example.swaggerdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .tags(new Tag("Saker", "Endepunkter for å operere på saker"))
                .protocols(Stream.of("http", "https").collect(Collectors.toSet()))
                .forCodeGeneration(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.example.swaggerdemo.resource.sakerdemo")) // @RestController's
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Employee API")
                .description("SpringFox Swagger Example")
                .license("Apache License 2.0")
                .version("1.0")
                .termsOfServiceUrl("http://www.github.com")
                .contact(new Contact(
                        "Jørgen Ringen",
                        "www.github.com/jorgenringen",
                        "jorgen.ringen@gmail.com"))
                .build();
    }
}
