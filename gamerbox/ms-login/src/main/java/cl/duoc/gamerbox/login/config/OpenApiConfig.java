package cl.duoc.gamerbox.login.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI configuracionOpenAPI() {

        Contact contacto = new Contact()
                .name("Repositorio de Github - Gamerbox")
                .url("https://github.com/visviriDeveloper/DSY1103-Gamerbox-07");

        License licencia = new License()
                .name("MIT License")
                .url("https://opensource.org/licenses/MIT");

        return new OpenAPI()
                .info(new Info()
                        .title("Microservicio de LOGIN - Gamerbox")
                        .description("API encargada de la autenticación de usuarios, emisión de tokens y registro de sesiones.")
                        .version("1.0.0")
                        .contact(contacto)
                        .license(licencia)
                );
    }
}
