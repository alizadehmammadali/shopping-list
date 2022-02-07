package io.jur.shoppinglist;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@RequiredArgsConstructor
public class ShoppingListApplication{

    public static void main(String[] args) {
        SpringApplication.run(ShoppingListApplication.class, args);
    }
}
