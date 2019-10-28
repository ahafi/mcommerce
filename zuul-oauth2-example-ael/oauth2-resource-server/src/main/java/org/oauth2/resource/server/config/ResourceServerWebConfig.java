package org.oauth2.resource.server.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan({ "org.oauth2.resource.server" })
public class ResourceServerWebConfig implements WebMvcConfigurer {
    //
}
