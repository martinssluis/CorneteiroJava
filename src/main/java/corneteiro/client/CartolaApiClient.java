package corneteiro.client;


import corneteiro.dto.MercadoResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class CartolaApiClient {

    private final RestClient restClient;

    public CartolaApiClient(RestClient restClient){
        this.restClient = restClient;
    }

    public MercadoResponse buscarMercado() {
        return restClient.get()
                .uri("/atletas/mercado")
                .header("User-Agent", "Mozilla/5.0")
                .header("Accept", "application/json")
                .retrieve()
                .body(MercadoResponse.class);
    }
}
