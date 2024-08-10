package com.dev.accountservice;

import com.dev.accountservice.infraestructure.data.entities.Cuenta;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@SpringBootApplication( scanBasePackages = {"com.devsu"})
public class AccountServiceApplication implements RepositoryRestConfigurer  {

    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
    }

    @Override
    public void configureRepositoryRestConfiguration(final RepositoryRestConfiguration config,
                                                     final CorsRegistry cors) {
        config.exposeIdsFor(Cuenta.class);
    }

}
