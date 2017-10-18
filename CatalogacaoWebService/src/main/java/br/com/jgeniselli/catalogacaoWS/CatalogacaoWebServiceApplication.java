package br.com.jgeniselli.catalogacaoWS;

import java.net.URL;
import java.net.URLClassLoader;
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
        
//        ClassLoader cl = ClassLoader.getSystemClassLoader();
//
//        URL[] urls = ((URLClassLoader)cl).getURLs();
//
//        for(URL url: urls){
//        	System.out.println("URL: " + url.getFile());
//        }
    }
}
