package com.tdns.toks.api.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI(@Value("${springdoc.version}") String appVersion) {
        return new OpenAPI()
            .addServersItem(new Server().url("/"))
            .components(new Components().addSecuritySchemes("Authorization",
                new SecurityScheme()
                    .type(SecurityScheme.Type.APIKEY)
                    .in(SecurityScheme.In.HEADER)
                    .name("Authorization")
                    .description("인증이 필요한 경우 ex) bearer xxxxxxx")
            ))
            .security(List.of(new SecurityRequirement().addList("Authorization")))
            .externalDocs(new ExternalDocumentation())
            .info(new Info()
                    .title("TOKS API Documents")
                    .description("TOKS Public APIs For Clients.")
                    .contact(new Contact().name("Contact 오개안말"))
                    .version(appVersion)
                .license(new License().name("TDNS. All rights reserved.").url("https://weverse.co/"))
            );

    }

}
