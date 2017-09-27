/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jgeniselli.catalogacaoWS;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

/**
 *
 * @author jgeniselli
 */
@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {
    
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
      super.addResourceHandlers(registry);
      registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
      registry.addResourceHandler("/theme/**").addResourceLocations("classpath:/theme/");
  }
  
  private ApplicationContext applicationContext;

  public void setApplicationContext(ApplicationContext applicationContext) {
      this.applicationContext = applicationContext;
  }

  @Bean
  public ViewResolver viewResolver() {
      ThymeleafViewResolver resolver = new ThymeleafViewResolver();
      resolver.setTemplateEngine(templateEngine());
      resolver.setCharacterEncoding("UTF-8");
      return resolver;
  }

  @Bean
  public TemplateEngine templateEngine() {
      SpringTemplateEngine engine = new SpringTemplateEngine();
      engine.setEnableSpringELCompiler(true);
      engine.setTemplateResolver(templateResolver());
      return engine;
  }

  private ITemplateResolver templateResolver() {
      SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
      resolver.setApplicationContext(applicationContext);
      resolver.setPrefix("/WEB-INF/templates/");
      resolver.setTemplateMode(TemplateMode.HTML);
      return resolver;
  }
}
