package org.springframework.samples.petclinic.configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
  info =@Info(
    title = "PetClinic APIs",
    version = "v1.0",
    contact = @Contact(
      name = "DP1-2X2Y-Group Z", email = "dp1-2X2Y-groupZ@gmail.com", url = "https://dp1-2X2Y-groupZ.com"
    ),
    license = @License(
      name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"
    ),
    termsOfService = "${tos.uri}",
    description = "${api.description}"
  )
)
public class OpenApiConfiguration {
    
}