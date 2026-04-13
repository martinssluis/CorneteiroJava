package corneteiro.service;

import corneteiro.dto.Clube.ClubeBuscaResponse;
import corneteiro.dto.Clube.ClubeDTO;
import corneteiro.dto.Clube.ClubeListaResponse;
import corneteiro.dto.MercadoResponse;
import corneteiro.exception.RecursoNaoEncontradoException;
import corneteiro.exception.RequisicaoInvalidaException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ClubeService {

    private final MercadoProvider mercadoProvider;

    public ClubeService(MercadoProvider mercadoProvider) {
        this.mercadoProvider = mercadoProvider;
    }

    public ClubeListaResponse listarClubes() {
        MercadoResponse mercado = mercadoProvider.obterMercadoComClubes();

        Map<Integer, ClubeDTO> clubesMap = mercado.getClubes();
        List<ClubeDTO> clubes = new ArrayList<>(clubesMap.values());

        return new ClubeListaResponse(clubes.size(), clubes);
    }

    public ClubeBuscaResponse buscarClubePorNome(String nome, boolean exato) {
        if (nome == null || nome.isBlank()) {
            throw new RequisicaoInvalidaException("O nome para busca é obrigatório.");
        }

        MercadoResponse mercado = mercadoProvider.obterMercadoComClubes();

        List<ClubeDTO> clubesEncontrados = mercado.getClubes().values().stream()
                .filter(clube -> clube.getNome() != null)
                .filter(clube -> {
                    if (exato) {
                        return clube.getNome().equalsIgnoreCase(nome);
                    }
                    return clube.getNome().toLowerCase().contains(nome.toLowerCase());
                })
                .collect(Collectors.toList());

        return new ClubeBuscaResponse(nome, clubesEncontrados.size(), clubesEncontrados);
    }

    public ClubeDTO buscarClubePorId(Integer id) {
        MercadoResponse mercado = mercadoProvider.obterMercadoComClubes();

        ClubeDTO clube = mercado.getClubes().get(id);

        if (clube == null) {
            throw new RecursoNaoEncontradoException("Clube com id " + id + " não encontrado.");
        }

        return clube;
    }
}