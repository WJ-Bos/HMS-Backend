package main.wjbos.zenhotel.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
/**
 * This class is a Spring Configuration class that configures CORS(Cross-Origin Resource Sharing) mappings.
 * This is necessary when we want to make an AJAX request from a JavaScript application running on a different domain than our Spring application.
 * By default, the browser will not allow this because of the Same-Origin Policy.
 * By adding the CorsRegistry bean to the Spring application context, we are telling Spring to add the necessary headers to the response so that the browser will allow the request.
 */
public class CorsConfig {

    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                /**
                 * Add a mapping for CORS.
                 * The mapping is for all URLs("/**")
                 * and allows all GET, POST, PUT and DELETE HTTP methods.
                 * It also allows requests from all domains("*").
                 */
                registry.addMapping("/**")
                        .allowedMethods("GET","POST","PUT","DELETE")
                        .allowedOrigins("*");
            }
        };
    }
}