package cl.duoc.gamerbox.comentarios.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI configurarOpenApi() {
        // Información de contacto
        Contact contacto = new Contact()
                .name("Darithza Scarleht Cárdenas Vargas")
                .email("dari.cardenas@duocuc.cl")
                .url("https://www.duoc.cl");
        Contact contacto1 = new Contact()
                .name("Álvaro David Morales Yanquin")
                .email("al.moralesy@duoc.cl")
                .url("https://www.duoc.cl");
        Contact contacto2 = new Contact()
                .name("Matias Fernando Wenger Meza")
                .email("ma.wenger@duocuc.cl")
                .url("https://www.duoc.cl");

        // Licencia del proyecto
        License licencia = new License()
                .name("MIT")
                .url("https://opensource.org/licences/MIT");

        // Información principal de la API
        Info informacionApi = new Info()
                .description("""
                        API para la administracion
                        de comentarios de reseñas
                        """)
                .version("1.0")
                .termsOfService("https://www.duoc.cl")
                .contact(contacto)
                .license(licencia);
        // Documentación externa (GitHub)
        ExternalDocumentation github = new ExternalDocumentation()
                .description("Repositorio oficial del proyecto en GitHub")
                .url("https://github.com/visviriDeveloper/DSY1103-Gamerbox-07");
        // Configuración OpenAPI
        return new OpenAPI()
                .info(informacionApi)
                .externalDocs(github);
    }
}