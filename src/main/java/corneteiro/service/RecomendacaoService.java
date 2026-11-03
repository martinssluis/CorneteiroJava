package corneteiro.service;

import corneteiro.dto.Atleta.AtletaDTO;
import corneteiro.dto.Clube.ClubeDTO;
import corneteiro.dto.MercadoResponse;
import corneteiro.dto.Recomendacao.CustoBeneficioAtletaDTO;
import corneteiro.dto.Recomendacao.CustoBeneficioAtletasResponse;
import corneteiro.dto.Recomendacao.CustoBeneficioClubeDTO;
import corneteiro.dto.Recomendacao.CustoBeneficioClubesResponse;
import corneteiro.exception.RecursoNaoEncontradoException;
import corneteiro.exception.RequisicaoInvalidaException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Recomendações calculadas a partir do mercado atual do Cartola.
 *
 * Custo-benefício = média de pontos / preço. Usa a média da temporada (e não a pontuação de uma
 * única rodada) e exige um mínimo de jogos, para que um atleta com 1 jogo de nota alta não domine o ranking.
 */
@Service
public class RecomendacaoService {

    public static final int LIMITE_MAXIMO = 100;
    private static final Locale PT_BR = Locale.forLanguageTag("pt-BR");

    private final MercadoProvider mercadoProvider;

    public RecomendacaoService(MercadoProvider mercadoProvider) {
        this.mercadoProvider = mercadoProvider;
    }

    public CustoBeneficioAtletasResponse custoBeneficioAtletas(
            Integer posicaoId, Integer clubeId, int minJogos, List<Integer> status, int limite) {
        validarParametros(minJogos, status, limite);

        MercadoResponse mercado = mercadoProvider.obterMercadoComAtletas();
        if (clubeId != null && !mercado.clubes().containsKey(clubeId)) {
            throw new RecursoNaoEncontradoException("Clube com id " + clubeId + " não encontrado.");
        }

        List<CustoBeneficioAtletaDTO> ranking = elegiveis(mercado, posicaoId, minJogos, status)
                .filter(atleta -> clubeId == null || clubeId.equals(atleta.clubeId()))
                .map(this::paraDto)
                .sorted(comparadorAtletas())
                .limit(limite)
                .toList();

        return new CustoBeneficioAtletasResponse(posicaoId, clubeId, minJogos, status, ranking.size(), ranking);
    }

    public CustoBeneficioClubesResponse custoBeneficioClubes(
            Integer posicaoId, int minJogos, List<Integer> status, int limite) {
        validarParametros(minJogos, status, limite);

        MercadoResponse mercado = mercadoProvider.obterMercadoComAtletas();
        Map<Integer, ClubeDTO> clubes = mercado.clubes();

        Map<Integer, List<CustoBeneficioAtletaDTO>> porClube = elegiveis(mercado, posicaoId, minJogos, status)
                .filter(atleta -> clubes.containsKey(atleta.clubeId()))
                .map(this::paraDto)
                .collect(Collectors.groupingBy(CustoBeneficioAtletaDTO::clubeId));

        List<CustoBeneficioClubeDTO> ranking = porClube.entrySet().stream()
                .map(entry -> paraClubeDto(clubes.get(entry.getKey()), entry.getValue()))
                .sorted(Comparator.comparing(CustoBeneficioClubeDTO::custoBeneficioMedio).reversed()
                        .thenComparing(CustoBeneficioClubeDTO::nome))
                .limit(limite)
                .toList();

        return new CustoBeneficioClubesResponse(posicaoId, minJogos, status, ranking.size(), ranking);
    }

    private java.util.stream.Stream<AtletaDTO> elegiveis(
            MercadoResponse mercado, Integer posicaoId, int minJogos, List<Integer> status) {
        return mercado.atletas().stream()
                .filter(atleta -> atleta.precoNum() != null && atleta.precoNum() > 0)
                .filter(atleta -> atleta.mediaNum() != null)
                .filter(atleta -> atleta.jogosNum() != null && atleta.jogosNum() >= minJogos)
                .filter(atleta -> atleta.statusId() != null && status.contains(atleta.statusId()))
                .filter(atleta -> posicaoId == null || posicaoId.equals(atleta.posicaoId()));
    }

    private CustoBeneficioAtletaDTO paraDto(AtletaDTO atleta) {
        double custoBeneficio = arredondar(atleta.mediaNum() / atleta.precoNum());
        String motivo = String.format(PT_BR, "Média %.2f em %d jogos ÷ preço C$ %.2f = %.3f",
                atleta.mediaNum(), atleta.jogosNum(), atleta.precoNum(), custoBeneficio);

        return new CustoBeneficioAtletaDTO(
                atleta.atletaId(), atleta.apelido(), atleta.clubeId(), atleta.posicaoId(), atleta.statusId(),
                atleta.precoNum(), atleta.mediaNum(), atleta.jogosNum(), custoBeneficio, motivo);
    }

    private CustoBeneficioClubeDTO paraClubeDto(ClubeDTO clube, List<CustoBeneficioAtletaDTO> atletas) {
        double media = arredondar(atletas.stream()
                .mapToDouble(CustoBeneficioAtletaDTO::custoBeneficio)
                .average()
                .orElse(0));
        String motivo = String.format(PT_BR, "Média do custo-benefício de %d atletas elegíveis = %.3f",
                atletas.size(), media);

        return new CustoBeneficioClubeDTO(
                clube.id(), clube.nomeCompleto(), clube.apelido(), clube.escudos(), atletas.size(), media, motivo);
    }

    private Comparator<CustoBeneficioAtletaDTO> comparadorAtletas() {
        return Comparator.comparing(CustoBeneficioAtletaDTO::custoBeneficio).reversed()
                .thenComparing(Comparator.comparing(CustoBeneficioAtletaDTO::mediaNum).reversed())
                .thenComparing(CustoBeneficioAtletaDTO::apelido, Comparator.nullsLast(Comparator.naturalOrder()));
    }

    private double arredondar(double valor) {
        return Math.round(valor * 1000.0) / 1000.0;
    }

    private void validarParametros(int minJogos, List<Integer> status, int limite) {
        if (minJogos < 0) {
            throw new RequisicaoInvalidaException("O parâmetro 'minJogos' não pode ser negativo.");
        }
        if (status == null || status.isEmpty()) {
            throw new RequisicaoInvalidaException("Informe ao menos um 'status'.");
        }
        if (limite < 1 || limite > LIMITE_MAXIMO) {
            throw new RequisicaoInvalidaException("O parâmetro 'limite' deve estar entre 1 e " + LIMITE_MAXIMO + ".");
        }
    }
}
