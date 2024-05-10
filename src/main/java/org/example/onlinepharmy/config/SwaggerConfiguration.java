package org.example.onlinepharmy.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Online-pharmacy",
                description = "This application is for testing cases only",
                version = "${app.version}",
                contact = @Contact(
                        name = "Dostonbek",
                        url = "https://github.com/Madaminov2002/online-pharmacy",
                        email = "dostonshavkatov53@gmail.com"
                ),
                license = @License(
                        name = "Apache 3.0",
                        url = "https://starter.spring.io"
                ),
                termsOfService = "https://wwww.wiki.com",
                summary = "The project is fully supported by G33 team"
        ),
        servers = {
                @Server(url = "http://localhost:8081/swagger",
                        description = "Development Server"),
                @Server(url = "http://localhost:8082/swagger",
                        description = "Production Server"),
                @Server(url = "http://localhost:8080/swagger",
                        description = "Local server")
        }
)
public class SwaggerConfiguration {


    @Bean
    public OpenAPI openAPI() {

        return new OpenAPI().addSecurityItem(new SecurityRequirement()
                        .addList("BearerAuthentication"))
                .components(new Components()
                        .addSecuritySchemes("BearerAuthentication", new SecurityScheme()
                                .name("Bearer")
                                .type(SecurityScheme.Type.HTTP).bearerFormat("Jwt")
                                .in(SecurityScheme.In.HEADER)
                                .scheme("Bearer")));
    }

}
