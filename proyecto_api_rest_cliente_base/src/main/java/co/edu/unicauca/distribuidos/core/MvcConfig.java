// Archivo: MvcConfig.java
package co.edu.unicauca.distribuidos.core;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/uploads/images/**")
                .addResourceLocations("file:///C:/Users/Ana_Sofia/OneDrive/Documentos/UNIVERSIDAD/imagenesParcial/");
    }
}