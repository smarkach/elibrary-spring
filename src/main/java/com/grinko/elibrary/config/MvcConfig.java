package com.grinko.elibrary.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Spring MVC configuration.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.grinko.elibrary")
public class MvcConfig extends WebMvcConfigurerAdapter {

}
