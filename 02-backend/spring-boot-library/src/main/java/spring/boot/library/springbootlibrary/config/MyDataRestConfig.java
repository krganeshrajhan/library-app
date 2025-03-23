package spring.boot.library.springbootlibrary.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import spring.boot.library.springbootlibrary.entity.Book;
import spring.boot.library.springbootlibrary.entity.Message;
import spring.boot.library.springbootlibrary.entity.Review;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    private String theAllowedOrigins = "http://localhost:3000";

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration configuration,
            CorsRegistry corsRegistry) {

        HttpMethod[] theUnsupportedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PATCH};


        configuration.exposeIdsFor(Book.class, Review.class, Message.class);

        // disable HTTP methods for Book: PUT, POST, DELETE and PATCH
        disableHttpMethods(Book.class, configuration, theUnsupportedActions);

        disableHttpMethods(Review.class, configuration, theUnsupportedActions);

        corsRegistry.addMapping(configuration.getBasePath() + "/**").allowedOrigins(theAllowedOrigins);
    }

    private static void disableHttpMethods(Class<?> aClass, RepositoryRestConfiguration configuration, HttpMethod[] theUnsupportedActions) {
        configuration.getExposureConfiguration()
                .forDomainType(aClass)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
    }

}
