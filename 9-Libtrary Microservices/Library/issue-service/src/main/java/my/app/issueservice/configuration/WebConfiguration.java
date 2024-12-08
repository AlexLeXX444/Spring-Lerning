package my.app.issueservice.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Перенаправляем корневую страницу на Swagger UI
        registry.addRedirectViewController("/", "/swagger-ui/index.html");
        registry.setOrder(org.springframework.core.Ordered.HIGHEST_PRECEDENCE);
    }
}
