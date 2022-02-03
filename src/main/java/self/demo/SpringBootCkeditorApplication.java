package self.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;


@SpringBootApplication
public class SpringBootCkeditorApplication implements ServletContextInitializer,WebMvcConfigurer {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootCkeditorApplication.class, args);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

    }
}
