package basicMaven.api;

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import basicMaven.config.DefaultConsumes;

@RestController
@EnableAspectJAutoProxy( proxyTargetClass = true )
public class BasicMavenController extends BaseController
{

   @GetMapping( value = "/test" )
   public String testPostMapping()
   {
      return "";

   }

   @DefaultConsumes
   @PostMapping( value = "/testPostMapping" )
   public @ResponseBody ResponseEntity<String> testPostMapping( String testString )
   {
      return new ResponseEntity<String>( "TestString", HttpStatus.OK );

   }

   @GetMapping( value = "/getTest" )
   public @ResponseBody ResponseEntity<String> testGetMapping()
   {
      return new ResponseEntity<String>( "TestString", HttpStatus.OK );
   }

}
