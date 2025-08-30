package web.planorama.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class DeterminarImagemUploadConfig implements WebMvcConfigurer{

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Quando acessar /uploads/**, vai buscar na pasta uploads do sistema de arquivos
        registry.addResourceHandler("/uploadsUser/**")
                .addResourceLocations("file:uploadsUser/");
    }
}
