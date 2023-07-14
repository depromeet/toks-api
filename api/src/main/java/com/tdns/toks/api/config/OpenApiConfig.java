package com.tdns.toks.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static com.tdns.toks.core.common.security.Constants.TOKS_AUTH_HEADER_KEY;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI(@Value("${springdoc.version}") String appVersion) {
        return new OpenAPI()
                .addServersItem(new Server().url("/"))
                .components(new Components().addSecuritySchemes(TOKS_AUTH_HEADER_KEY,
                        new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name(TOKS_AUTH_HEADER_KEY)
                                .description("인증이 필요한 경우 ex) X-TOKS-AUTH-TOKEN xxxxxxx")
                ))
                .security(List.of(new SecurityRequirement().addList(TOKS_AUTH_HEADER_KEY)))
                .externalDocs(new ExternalDocumentation())
                .info(new Info()
                        .title("TOKS API Documents")
                        .description("TOKS Public APIs For Clients.")
                        .contact(new Contact().name("Contact 오개안말"))
                        .version(appVersion)
                        .license(new License().name("TDNS. All rights reserved.").url("https://tokstudy.com/"))
                );

    }
}
