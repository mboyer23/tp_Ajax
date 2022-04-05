package monprojet.config;

import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Component
public class SpringDataRestConfig
    implements RepositoryRestConfigurer {

  @Override
  public void configureRepositoryRestConfiguration(
      RepositoryRestConfiguration config, CorsRegistry cors) {

    cors.addMapping("/**") // Toutes les adresses sont autorisées
        .allowedOrigins("*") // Toutes les origines sont autorisées
        .allowedMethods("*") // Toutes les méthodes http sont autorisées
        .allowCredentials(false).maxAge(3600);
  }
}