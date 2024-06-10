package bancofie.com.bo.Fienaku.settings;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Hidden
public class SwaggerSettings {

    @Bean
    public OpenAPI getOpenApi() {

        String members = "### Integrantes:\n"
                + "| **Nombre** | **Correo** |\n"
                + "|--------|-------|\n"
                + "| Jose Enrique Limachi Garcia **(Representante)** | jose.limachi@bancofie.com.bo |\n"
                + "| Alis Estefani Mollindeo Condori | alis.mollinedo@bancofie.com.bo |\n"
                + "| David Sebastian Ilario Rivero | david.ilario@bancofie.com.bo |\n"
                + "| Gabriela Belen Yujra Contretas | gabriela.yujra@bancofie.com.bo |\n"
                + "| Shayla Karen Marin Quispe | shayla.marin@bancofie.com.bo |\n";

        return new OpenAPI()
                .components(new Components())
                .info(
                    new Info()
                            .title("Proyecto Fienaku")
                            .description("Desarrollado por el equipo azul, pasantes del Banco Fie S.A. en el área de ingeniería de sistemas\n\n" 
                                    + members + 
                                    "\n\n[repository](https://github.com/Super-07Z/Fienaku)")
                            .version("1.0")
                );
    }
}
//http://localhost:8080/swagger-ui.html