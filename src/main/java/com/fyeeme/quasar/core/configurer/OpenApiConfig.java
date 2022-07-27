package com.fyeeme.quasar.core.configurer;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
//    @Value("${springdoc.api-docs.path}")
//    private String apiDocsUrl;
//
//    @Bean
//    public SwaggerIndexTransformer indexPageTransformer(
//            SwaggerUiConfigProperties swaggerUiConfig, SwaggerUiOAuthProperties swaggerUiOAuthProperties,
//            SwaggerUiConfigParameters swaggerUiConfigParameters, SwaggerWelcomeCommon swaggerWelcomeCommon, ObjectMapperProvider objectMapperProvider
//    ) {
//        return new SwaggerIndexPageTransformer(swaggerUiConfig, swaggerUiOAuthProperties, swaggerUiConfigParameters, swaggerWelcomeCommon, objectMapperProvider) {
//            @Override
//            protected String overwriteSwaggerDefaultUrl(String html) {
//                return html.replace(Constants.SWAGGER_UI_DEFAULT_URL, apiDocsUrl);
//            }
//        };
//    }

    /**
     * Spring doc Open API configuration
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("kms API")
                        .description("This is a sample server Petstore server.  You can find out more about Swagger at [http://swagger.io](http://swagger.io) or on [irc.freenode.net, #swagger](http://swagger.io/irc/).  For this sample, you can use the api key `special-key` to test the authorization filters.")
                        .termsOfService("http://www.shimi-tech.com/termsofservice")
                        .version("1.01")
                        .summary("just a brief summary")
                        .contact(new Contact().name("Allen Lau").email("fyeeme@gmail.com").url("https://github.com/fyeeme"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("kms Wiki Documentation")
                        .url("https://springshop.wiki.github.org/docs"));
    }
}
