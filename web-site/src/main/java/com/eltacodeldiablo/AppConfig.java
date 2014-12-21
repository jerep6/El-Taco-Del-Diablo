package com.eltacodeldiablo;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

@Configuration
@ComponentScan("com.eltacodeldiablo")
// http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html#mvc-config
@EnableWebMvc
// As opposed to using XML namespace element, the Java @PropertySource annotation does not automatically register a
// PropertySourcesPlaceholderConfigurer with Spring. Instead, the bean must be explicitly defined in the configuration
// to get the property resolution mechanism working.
// http://www.baeldung.com/2012/02/06/properties-with-spring/
@PropertySource("classpath:properties/environment.properties")
public class AppConfig extends WebMvcConfigurerAdapter {
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
		// Use Environment (spring object) to resolve @Value
		return new PropertySourcesPlaceholderConfigurer();
	}

	/** Serve statics resources (css, images, js; ...) */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("i18n/labels");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	// Need to register SpringTemplateEngine separately in order to i18n works
	@Bean
	public SpringTemplateEngine templateEngine() {
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
		templateResolver.setCacheable(false);
		templateResolver.setPrefix("/templates/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("XHTML");

		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);
		return templateEngine;
	}

	@Bean
	public ViewResolver viewResolver() {
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine());
		viewResolver.setCharacterEncoding("UTF-8");
		viewResolver.setOrder(1);
		return viewResolver;
	}

}
