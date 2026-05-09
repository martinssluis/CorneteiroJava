package corneteiro.service;

import corneteiro.dto.Atleta.AtletaBuscaResponse;
import corneteiro.dto.Atleta.AtletaDTO;
import corneteiro.dto.Atleta.AtletaListaResponse;
import corneteiro.dto.MercadoResponse;
import corneteiro.exception.RequisicaoInvalidaException;
import corneteiro.exception.RecursoNaoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class AtletaService {

    private final MercadoProvider mercadoProvider;

    public AtletaService(MercadoProvider mercadoProvider) {
        this.mercadoProvider = mercadoProvider;
    }

    public AtletaListaResponse listarAtletas() {
        MercadoResponse mercado = mercadoProvider.obterMercadoComAtletas();
        List<AtletaDTO> atletas = mercado.atletas();

        return new AtletaListaResponse(atletas.size(), atletas);
    }

    public AtletaBuscaResponse buscarAtletaPorNome(String nome, boolean exato) {
        validarParametroNome(nome);

        MercadoResponse mercado = mercadoProvider.obterMercadoComAtletas();

        List<AtletaDTO> atletasEncontrados = mercado.atletas().stream()
                .filter(atleta -> correspondeEmCampos(
                        nome,
                        exato,
                        atleta.apelido(),
                        atleta.apelidoAbreviado(),
                        atleta.nome(),
                        atleta.slug()
                ))
                .toList();

        return new AtletaBuscaResponse(
                nome,
                atletasEncontrados.size(),
                atletasEncontrados
        );
    }

    public AtletaDTO buscarAtletaPorId(Integer id) {
        if (id == null) {
            throw new RequisicaoInvalidaException("O parâmetro 'id' é obrigatório.");
        }

        MercadoResponse mercado = mercadoProvider.obterMercadoComAtletas();

        return mercado.atletas().stream()
                .filter(atleta -> Objects.equals(atleta.atletaId(), id))
                .findFirst()
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Atleta com id " + id + " não encontrado."
                ));
    }

    private void validarParametroNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new RequisicaoInvalidaException("O parâmetro 'nome' é obrigatório.");
        }
    }

    private boolean correspondeEmCampos(String termo, boolean exato, String... campos) {
        return Stream.of(campos)
                .filter(Objects::nonNull)
                .anyMatch(campo -> corresponde(campo, termo, exato));
    }

    private boolean corresponde(String campo, String termo, boolean exato) {
        String campoNormalizado = normalizar(campo);
        String termoNormalizado = normalizar(termo);

        return exato
                ? campoNormalizado.equals(termoNormalizado)
                : campoNormalizado.contains(termoNormalizado);
    }

    private String normalizar(String valor) {
        return valor.trim().toLowerCase();
    }
}