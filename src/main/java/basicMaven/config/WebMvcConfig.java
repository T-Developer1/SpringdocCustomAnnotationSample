package basicMaven.config;

import org.springdoc.webmvc.ui.SwaggerWebMvcConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration
//@EnableAspectJAutoProxy
public class WebMvcConfig extends WebMvcConfigurationSupport
{

   @Autowired
   private SwaggerWebMvcConfigurer swaggerWebMvcConfigurer;

   @Override
   protected void addResourceHandlers( ResourceHandlerRegistry registry )
   {
      swaggerWebMvcConfigurer.addResourceHandlers( registry );
   }

   /**
    * Set json as default content type, so json is produced, if client doesn't send an accept-header
    * 
    */
   @Override
   protected void configureContentNegotiation( ContentNegotiationConfigurer configurer )
   {
      configurer.defaultContentType( MediaType.APPLICATION_JSON );
   }

   @Override
   protected RequestMappingHandlerMapping createRequestMappingHandlerMapping()
   {
      return new ExtendedRequestMappingHandlerMapping();
   }

}
