package corneteiro.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * Garante que o cache de "mercado" funciona: várias leituras devem gerar uma única
 * chamada HTTP à API do Cartola. Usa-se um servidor HTTP falso (e não um mock do
 * CartolaApiClient) para que o client real e o proxy de cache do Spring participem do teste.
 */
@SpringBootTest
@Import(MercadoProviderCacheTest.RestClientFalso.class)
class MercadoProviderCacheTest {

    private static MockRestServiceServer servidor;

    @TestConfiguration
    static class RestClientFalso {
        @Bean
        @Primary
        RestClient restClientDeTeste() {
            RestClient.Builder builder = RestClient.builder().baseUrl("http://cartola.test");
            servidor = MockRestServiceServer.bindTo(builder).build();
            return builder.build();
        }
    }

    @Autowired
    private MercadoProvider mercadoProvider;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    void limparCacheEEsperarUmaUnicaChamada() {
        cacheManager.getCacheNames().forEach(nome -> cacheManager.getCache(nome).clear());
        servidor.reset();
        servidor.expect(once(), requestTo("http://cartola.test/atletas/mercado"))
                .andRespond(withSuccess("{\"atletas\":[],\"clubes\":{}}", MediaType.APPLICATION_JSON));
    }

    @Test
    void consultasRepetidasChamamAApiDoCartolaUmaUnicaVez() {
        mercadoProvider.obterMercadoComAtletas();
        mercadoProvider.obterMercadoComClubes();
        mercadoProvider.obterMercadoComAtletas();

        servidor.verify(); // falha se a chamada não ocorreu; chamadas extras já falham ao serem feitas
    }
}
