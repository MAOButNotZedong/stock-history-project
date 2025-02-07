package org.example.finalproject.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Stock history api",
                description = "API для сохранения и получения исторических данных по акциям",
                version = "1.0.0",
                contact = @Contact(
                        name = "Alexander Martyshin",
                        url = "https://github.com/MAOButNotZedong"
                )
        )
)
public class OpenApiConfig {
}
