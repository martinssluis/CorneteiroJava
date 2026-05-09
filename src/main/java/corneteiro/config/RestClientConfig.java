package corneteiro.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;


@Configuration
public class RestClientConfig {

    @Value("${cartola.api.base-url}") //serve para injetar valores externos
    private String baseUrl;

    @Bean // cria um objeto para o spring reutilizar
    public RestClient restClient(){
        return RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }
}
