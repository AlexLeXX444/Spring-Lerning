package my.app.homework.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI openAPI() {
        OpenAPI openAPI = new OpenAPI();
        openAPI.info(new io.swagger.v3.oas.models.info.Info().title("API").description("API description").version("1.0"));
        openAPI.addServersItem(new io.swagger.v3.oas.models.servers.Server().url("http://localhost:8080"));
        openAPI.addSecurityItem(new io.swagger.v3.oas.models.security.SecurityRequirement().addList("Authorization"));
        openAPI.components(new io.swagger.v3.oas.models.Components()
                .addSecuritySchemes("Authorization", new io.swagger.v3.oas.models.security.SecurityScheme()
                        .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .in(io.swagger.v3.oas.models.security.SecurityScheme.In.HEADER)
                )
        );
        return openAPI;
    }
}
