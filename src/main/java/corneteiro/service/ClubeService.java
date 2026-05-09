package corneteiro.service;

import corneteiro.dto.Clube.ClubeBuscaResponse;
import corneteiro.dto.Clube.ClubeDTO;
import corneteiro.dto.Clube.ClubeListaResponse;
import corneteiro.dto.MercadoResponse;
import corneteiro.exception.RequisicaoInvalidaException;
import corneteiro.exception.RecursoNaoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class ClubeService {

    private final MercadoProvider mercadoProvider;

    public ClubeService(MercadoProvider mercadoProvider) {
        this.mercadoProvider = mercadoProvider;
    }

    public ClubeListaResponse listarClubes() {
        MercadoResponse mercado = mercadoProvider.obterMercadoComClubes();

        List<ClubeDTO> clubes = mercado.clubes().values().stream().toList();

        return new ClubeListaResponse(clubes.size(), clubes);
    }

    public ClubeBuscaResponse buscarClubePorNome(String nome, boolean exato) {
        validarParametroNome(nome);

        MercadoResponse mercado = mercadoProvider.obterMercadoComClubes();

        List<ClubeDTO> clubesEncontrados = mercado.clubes().values().stream()
                .filter(clube -> correspondeEmCampos(
                        nome,
                        exato,
                        clube.abreviacao(),
                        clube.apelido(),
                        clube.nome(),
                        clube.nomeFantasia(),
                        clube.slug()
                ))
                .toList();

        return new ClubeBuscaResponse(
                nome,
                clubesEncontrados.size(),
                clubesEncontrados
        );
    }

    public ClubeDTO buscarClubePorId(Integer id) {
        if (id == null) {
            throw new RequisicaoInvalidaException("O parâmetro 'id' é obrigatório.");
        }

        MercadoResponse mercado = mercadoProvider.obterMercadoComClubes();
        Map<Integer, ClubeDTO> clubes = mercado.clubes();

        ClubeDTO clube = clubes.get(id);

        if (clube == null) {
            throw new RecursoNaoEncontradoException("Clube com id " + id + " não encontrado.");
        }

        return clube;
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