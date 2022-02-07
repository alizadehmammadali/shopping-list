package io.jur.shoppinglist.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI curTaskApi() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Shop List Application API")
                        .description("Simple Shop List Spring Boot RESTFul services")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Mammadali Alizada")
                                .url("https://www.linkedin.com/in/alizadamammadali/")
                                .email("a.mammadali@gmail.com")));

    }
}