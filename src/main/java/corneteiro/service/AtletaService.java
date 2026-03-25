package corneteiro.service;

import corneteiro.client.CartolaApiClient;
import corneteiro.dto.AtletaBuscaResponse;
import corneteiro.dto.AtletaDTO;
import corneteiro.dto.MercadoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.text.Normalizer;
import java.util.List;
import java.util.Locale;

@Service
public class AtletaService {

    private final CartolaApiClient cartolaApiClient;

    public AtletaService(CartolaApiClient cartolaApiClient){
        this.cartolaApiClient = cartolaApiClient;
    }

    public AtletaBuscaResponse buscarAtletasPorNome(String nome, boolean exato) {
        if (nome == null || nome.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Informe o parâmetro 'nome'"
            );
        }

        MercadoResponse mercado = cartolaApiClient.buscarMercado();

        if(mercado == null || mercado.getAtletas() == null){
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Resposta inválida da API do Cartola"
            );
        }

        String termoNormalizado = normalizar(nome);

        List<AtletaDTO> resultados = mercado.getAtletas().stream()
                .filter(atleta -> correspondeBusca(atleta, termoNormalizado, exato))
                .toList();

        if(resultados.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Nenhum atleta encontrado"
            );
        }
        return new AtletaBuscaResponse(nome, resultados.size(), resultados);
    }

    private boolean correspondeBusca(AtletaDTO atleta, String termo, boolean exato){
        String nome = normalizar(atleta.getNome());
        String apelido = normalizar(atleta.getApelido());
        String slug = normalizar(atleta.getSlug());

        if(exato){
            return nome.equals(termo)
                    || apelido.equals(termo)
                    || slug.equals(termo);
        }

        return nome.contains(termo)
                || apelido.contains(termo)
                || slug.contains(termo);
    }
    private String normalizar(String valor) {
        if (valor == null) {
            return "";
        }

        String texto = Normalizer.normalize(valor, Normalizer.Form.NFD);
        return texto.replaceAll("\\p{M}", "")
                .toLowerCase(Locale.ROOT)
                .trim();
    }
}
