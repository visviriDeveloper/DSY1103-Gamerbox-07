package cl.duoc.gamerbox.listas.config;

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
                        .title("Microservicio de LISTAS - Gamerbox")
                        .description("API encargada de la creación, organización y gestión de las colecciones personalizadas de videojuegos de los usuarios.")
                        .version("1.0.0")
                        .contact(contacto)
                        .license(licencia)
                );
    }
}