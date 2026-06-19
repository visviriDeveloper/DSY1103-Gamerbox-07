package cl.duoc.gamerbox.seguidores.config;

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
                        .title("Microservicio de SEGUIDORES - Gamerbox")
                        .description("API encargada de gestionar el ecosistema social, permitiendo a los usuarios seguir y dejar de seguir a otros miembros de la comunidad.")
                        .version("1.0.0")
                        .contact(contacto)
                        .license(licencia)
                );
    }
}
