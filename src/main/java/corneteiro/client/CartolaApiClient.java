package corneteiro.client;


import corneteiro.dto.MercadoResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class CartolaApiClient {

    private final RestClient restClient;

    public CartolaApiClient(RestClient restClient){
        this.restClient = restClient;
    }

    // O cache fica aqui (método público, chamado de outro bean) para o proxy do Spring interceptar.
    // Em MercadoProvider ele não funcionava: @Cacheable não atua em métodos private nem em auto-invocação.
    @Cacheable(value = "mercado", unless = "#result == null")
    public MercadoResponse buscarMercado() {
        return restClient.get()
                .uri("/atletas/mercado")
                .header("User-Agent", "Mozilla/5.0")
                .header("Accept", "application/json")
                .retrieve()
                .body(MercadoResponse.class);
    }
}
