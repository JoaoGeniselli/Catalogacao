package br.com.jgeniselli.catalogacaoWS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class CatalogacaoWebServiceApplication extends SpringBootServletInitializer {
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CatalogacaoWebServiceApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(CatalogacaoWebServiceApplication.class, args);
    }
}
