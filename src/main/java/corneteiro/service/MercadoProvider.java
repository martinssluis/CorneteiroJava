package corneteiro.service;

import corneteiro.client.CartolaApiClient;
import corneteiro.dto.MercadoResponse;
import corneteiro.exception.IntegracaoException;
import org.springframework.stereotype.Component;

@Component
public class MercadoProvider {

    private final CartolaApiClient cartolaApiClient;

    public MercadoProvider(CartolaApiClient cartolaApiClient) {
        this.cartolaApiClient = cartolaApiClient;
    }

    public MercadoResponse obterMercadoComAtletas() {
        MercadoResponse mercado = obterMercado();
        validarAtletas(mercado);
        return mercado;
    }

    public MercadoResponse obterMercadoComClubes() {
        MercadoResponse mercado = obterMercado();
        validarClubes(mercado);
        return mercado;
    }

    private MercadoResponse obterMercado() {
        MercadoResponse mercado = cartolaApiClient.buscarMercado();

        if (mercado == null) {
            throw new IntegracaoException("A API do Cartola retornou resposta nula para o mercado.");
        }

        return mercado;
    }

    private void validarAtletas(MercadoResponse mercado) {
        if (mercado.getAtletas() == null) {
            throw new IntegracaoException("A API do Cartola retornou mercado sem lista de atletas.");
        }
    }

    private void validarClubes(MercadoResponse mercado) {
        if (mercado.getClubes() == null) {
            throw new IntegracaoException("A API do Cartola retornou mercado sem lista de clubes.");
        }
    }
}