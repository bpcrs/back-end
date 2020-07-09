package fpt.capstone.bpcrs.config;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig {

  @Value("${app.host}")
  private String hostName;

  @Bean
  public Docket apiDocket() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.any())
        .build()
        .useDefaultResponseMessages(false).host(hostName)
        .forCodeGeneration(true)
        .apiInfo(getApiInfo())
        .securitySchemes(Collections.singletonList(apiKey()))
        .securityContexts(Collections.singletonList(securityContext()));
  }

  private ApiInfo getApiInfo() {
    return new ApiInfo(
        "Blockchain-based Personal Car Renting System",
        "FPT University Capstone project",
        "0.1",
        "TERMS OF SERVICE URL",
        null,
        "LICENSE",
        "LICENSE URL",
        Collections.emptyList());
  }

  private SecurityContext securityContext() {
    return SecurityContext.builder()
        .securityReferences(defaultAuth())
        .forPaths(PathSelectors.regex("/.*"))
        .build();
  }

  private List<SecurityReference> defaultAuth() {
    final AuthorizationScope authorizationScope =
        new AuthorizationScope("global", "accessEverything");
    final AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{authorizationScope};
    return Collections.singletonList(new SecurityReference("Bearer", authorizationScopes));
  }

  private ApiKey apiKey() {
    return new ApiKey("Bearer", "Authorization", "header");
  }
}
