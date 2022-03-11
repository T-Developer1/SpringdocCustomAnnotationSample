
package basicMaven.config;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

public class ExtendedRequestMappingHandlerMapping extends RequestMappingHandlerMapping
{

    

   public Map<String, Object> getDefaultAnnotationAttributes( Method method,
                                                              Class<?> handlerType,
                                                              @Nullable Class<? extends Annotation> defaultAnnotation )
   {
      Map<String, Object> defaultAnnotationAttributeMap = null;

      //gets Annotation on Method Level
      Annotation defaultConsumesAnnotation = AnnotationUtils.findAnnotation( method, defaultAnnotation );

      //gets Annotation on class Level if Method Level == null
      if ( defaultConsumesAnnotation == null )
         defaultConsumesAnnotation = AnnotationUtils.findAnnotation( handlerType, defaultAnnotation );

      if ( defaultConsumesAnnotation != null )
      {
         defaultAnnotationAttributeMap = AnnotationUtils.getAnnotationAttributes( defaultConsumesAnnotation );
      }

      return defaultAnnotationAttributeMap;
   }

   protected RequestMappingInfo createRequestMappingInfoConsumes( String[] consumesRequestCondition )
   {

      RequestMappingInfo.Builder builder = RequestMappingInfo.paths( null )
                                                             .consumes( consumesRequestCondition );

      return builder.build();
   }

   protected RequestMappingInfo createRequestMappingInfoProduces( String[] producesRequestCondition )
   {

      RequestMappingInfo.Builder builder = RequestMappingInfo.paths( null )
                                                             .produces( producesRequestCondition );

      return builder.build();
   }

   public String[] getCustomAnnotation( Method method,
                                          Class<?> handlerType,
                                          @Nullable Class<? extends Annotation> defaultAnnotation,
                                          String annotationKey )
   {
      String[] annotationValue = null;

      Map<String, Object> defaultAnnotationAttributeMap = getDefaultAnnotationAttributes( method,
                                                                                          handlerType,
                                                                                          defaultAnnotation );

      if ( defaultAnnotationAttributeMap != null )
      {
         annotationValue = (String[]) defaultAnnotationAttributeMap.get( annotationKey );
         if ( annotationValue.length != 0 )
         {
            return annotationValue;
         }
      }

      return annotationValue;
   }

   public RequestMappingInfo mergeCustomAttributes( Method method,
                                                    Class<?> handlerType,
                                                    RequestMappingInfo info )
   {

      String[] consumeValues =
                             getCustomAnnotation( method, handlerType, DefaultConsumes.class, "consumes" );

      String[] producesValues =
                              getCustomAnnotation( method, handlerType, DefaultProduces.class, "produces" );

      if ( consumeValues != null || producesValues != null )
      {

         RequestMappingInfo customRequestMappingInfo1 = createRequestMappingInfoConsumes( consumeValues );
         info = info.combine( customRequestMappingInfo1 );


         RequestMappingInfo customRequestMappingInfo2 = createRequestMappingInfoProduces( producesValues );
         info = info.combine( customRequestMappingInfo2 );

      }

      return info;

   }

   @Override
   @Nullable
   protected RequestMappingInfo getMappingForMethod( Method method, Class<?> handlerType )
   {
      RequestMappingInfo requestMappingInfo = super.getMappingForMethod( method, handlerType );

      if ( requestMappingInfo != null )
      {
         requestMappingInfo = mergeCustomAttributes( method, handlerType, requestMappingInfo );
      }

      return requestMappingInfo;
   }

}
