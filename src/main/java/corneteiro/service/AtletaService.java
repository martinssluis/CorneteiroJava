package corneteiro.service;

import corneteiro.dto.Atleta.AtletaBuscaResponse;
import corneteiro.dto.Atleta.AtletaDTO;
import corneteiro.dto.Atleta.AtletaListaResponse;
import corneteiro.dto.MercadoResponse;
import corneteiro.exception.RecursoNaoEncontradoException;
import corneteiro.exception.RequisicaoInvalidaException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AtletaService {

    private final MercadoProvider mercadoProvider;

    public AtletaService(MercadoProvider mercadoProvider) {
        this.mercadoProvider = mercadoProvider;
    }

    public AtletaListaResponse listarAtletas() {
        MercadoResponse mercado = mercadoProvider.obterMercadoComAtletas();
        List<AtletaDTO> atletas = mercado.getAtletas();

        return new AtletaListaResponse(atletas.size(), atletas);
    }

    public AtletaBuscaResponse buscarAtletaPorNome(String nome, boolean exato) {
        if (nome == null || nome.isBlank()) {
            throw new RequisicaoInvalidaException("O nome para busca é obrigatório.");
        }

        MercadoResponse mercado = mercadoProvider.obterMercadoComAtletas();

        List<AtletaDTO> encontrados = mercado.getAtletas().stream()
                .filter(atleta -> {
                    if (atleta.getApelido() == null) {
                        return false;
                    }

                    if (exato) {
                        return atleta.getApelido().equalsIgnoreCase(nome);
                    }

                    return atleta.getApelido().toLowerCase().contains(nome.toLowerCase());
                })
                .collect(Collectors.toList());

        return new AtletaBuscaResponse(nome, encontrados.size(), encontrados);
    }

    public AtletaDTO buscarAtletaPorId(Integer id) {
        MercadoResponse mercado = mercadoProvider.obterMercadoComAtletas();

        return mercado.getAtletas().stream()
                .filter(atleta -> atleta.getAtletaId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Atleta com id " + id + " não encontrado."
                ));
    }
}