package corneteiro.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI corneteiroOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Corneteiro API")
                        .version("1.0.0")
                        .description("API que consome o mercado do Cartola FC e expõe endpoints "
                                + "para listar e buscar atletas e clubes do Brasileirão. "
                                + "Os dados ficam em cache por 5 minutos."))
                .servers(List.of(new Server().url("http://localhost:1895").description("Local")));
    }
}
