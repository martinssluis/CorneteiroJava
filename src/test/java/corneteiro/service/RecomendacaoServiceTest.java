package corneteiro.service;

import corneteiro.dto.Atleta.AtletaDTO;
import corneteiro.dto.Clube.ClubeDTO;
import corneteiro.dto.MercadoResponse;
import corneteiro.dto.Recomendacao.CustoBeneficioAtletaDTO;
import corneteiro.dto.Recomendacao.CustoBeneficioAtletasResponse;
import corneteiro.dto.Recomendacao.CustoBeneficioClubesResponse;
import corneteiro.exception.RecursoNaoEncontradoException;
import corneteiro.exception.RequisicaoInvalidaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RecomendacaoServiceTest {

    private static final int FLAMENGO = 262;
    private static final int CORINTHIANS = 264;
    private static final int PALMEIRAS = 275;
    private static final int SANTOS = 277;

    private static final int MEIA = 4;
    private static final int ATACANTE = 5;

    private static final int PROVAVEL = 7;
    private static final int DUVIDA = 2;

    private static final List<Integer> SO_PROVAVEIS = List.of(PROVAVEL);

    @Mock
    private MercadoProvider mercadoProvider;

    private RecomendacaoService service;

    @BeforeEach
    void montarMercado() {
        List<AtletaDTO> atletas = List.of(
                atleta(1, "Arrascaeta", FLAMENGO, MEIA, PROVAVEL, 10.0, 6.0, 20),   // 0.600
                atleta(2, "Pedro", FLAMENGO, ATACANTE, PROVAVEL, 5.0, 4.0, 2),      // 0.800, só 2 jogos
                atleta(3, "Neymar", SANTOS, ATACANTE, PROVAVEL, 20.0, 8.0, 10),     // 0.400
                atleta(4, "Veiga", PALMEIRAS, MEIA, PROVAVEL, 8.0, 5.6, 15),        // 0.700
                atleta(5, "Memphis", CORINTHIANS, ATACANTE, DUVIDA, 10.0, 9.0, 12), // 0.900, status dúvida
                atleta(6, "Rony", PALMEIRAS, ATACANTE, PROVAVEL, 0.0, 5.0, 10),     // preço zero
                atleta(7, "Gabigol", FLAMENGO, ATACANTE, PROVAVEL, 10.0, 7.0, 10)   // 0.700
        );
        Map<Integer, ClubeDTO> clubes = Map.of(
                FLAMENGO, new ClubeDTO(FLAMENGO, "flamengo", "Flamengo", Map.of(), "FLA"),
                CORINTHIANS, new ClubeDTO(CORINTHIANS, "corinthians", "Corinthians", Map.of(), "COR"),
                PALMEIRAS, new ClubeDTO(PALMEIRAS, "palmeiras", "Palmeiras", Map.of(), "PAL"),
                SANTOS, new ClubeDTO(SANTOS, "santos", "Santos", Map.of(), "SAN")
        );
        when(mercadoProvider.obterMercadoComAtletas()).thenReturn(new MercadoResponse(atletas, clubes));

        service = new RecomendacaoService(mercadoProvider);
    }

    private AtletaDTO atleta(int id, String apelido, int clubeId, int posicaoId, int statusId,
                             double preco, double media, int jogos) {
        return new AtletaDTO(id, apelido, apelido, apelido.toLowerCase(), null, apelido,
                clubeId, posicaoId, statusId, preco, null, media, 0.0, jogos, true);
    }

    private List<String> apelidos(CustoBeneficioAtletasResponse resposta) {
        return resposta.atletas().stream().map(CustoBeneficioAtletaDTO::apelido).toList();
    }

    // ---- atletas ----

    @Test
    void ordenaPorCustoBeneficioDesempatandoPelaMedia() {
        CustoBeneficioAtletasResponse resposta = service.custoBeneficioAtletas(null, null, 3, SO_PROVAVEIS, 20);

        // Gabigol e Veiga empatam em 0.700; Gabigol vem antes por ter média maior (7.0 > 5.6)
        assertEquals(List.of("Gabigol", "Veiga", "Arrascaeta", "Neymar"), apelidos(resposta));
        assertEquals(0.7, resposta.atletas().get(0).custoBeneficio());
    }

    @Test
    void exigeMinimoDeJogosEEsseMinimoEConfiguravel() {
        assertFalse(apelidos(service.custoBeneficioAtletas(null, null, 3, SO_PROVAVEIS, 20)).contains("Pedro"));

        CustoBeneficioAtletasResponse comMinimoMenor = service.custoBeneficioAtletas(null, null, 2, SO_PROVAVEIS, 20);
        assertEquals("Pedro", comMinimoMenor.atletas().get(0).apelido()); // 0.800 passa a liderar
    }

    @Test
    void ignoraPrecoZeroEStatusForaDoFiltro() {
        List<String> padrao = apelidos(service.custoBeneficioAtletas(null, null, 3, SO_PROVAVEIS, 20));
        assertFalse(padrao.contains("Rony"), "preço zero não pode entrar");
        assertFalse(padrao.contains("Memphis"), "status dúvida fica fora por padrão");

        List<String> comDuvida = apelidos(service.custoBeneficioAtletas(null, null, 3, List.of(PROVAVEL, DUVIDA), 20));
        assertEquals("Memphis", comDuvida.get(0)); // 0.900
        assertFalse(comDuvida.contains("Rony"));
    }

    @Test
    void filtraPorPosicao() {
        CustoBeneficioAtletasResponse resposta = service.custoBeneficioAtletas(ATACANTE, null, 3, SO_PROVAVEIS, 20);

        assertEquals(List.of("Gabigol", "Neymar"), apelidos(resposta));
    }

    @Test
    void filtraPorClube() {
        CustoBeneficioAtletasResponse resposta = service.custoBeneficioAtletas(null, FLAMENGO, 3, SO_PROVAVEIS, 20);

        assertEquals(List.of("Gabigol", "Arrascaeta"), apelidos(resposta));
    }

    @Test
    void respeitaOLimite() {
        assertEquals(2, service.custoBeneficioAtletas(null, null, 3, SO_PROVAVEIS, 2).quantidade());
    }

    @Test
    void motivoExplicaOCalculo() {
        CustoBeneficioAtletaDTO arrascaeta = service.custoBeneficioAtletas(null, FLAMENGO, 3, SO_PROVAVEIS, 20)
                .atletas().get(1);

        assertEquals("Média 6,00 em 20 jogos ÷ preço C$ 10,00 = 0,600", arrascaeta.motivo());
    }

    @Test
    void clubeInexistenteRetornaNaoEncontrado() {
        assertThrows(RecursoNaoEncontradoException.class,
                () -> service.custoBeneficioAtletas(null, 9999, 3, SO_PROVAVEIS, 20));
    }

    @Test
    void rejeitaParametrosInvalidos() {
        assertThrows(RequisicaoInvalidaException.class,
                () -> service.custoBeneficioAtletas(null, null, -1, SO_PROVAVEIS, 20));
        assertThrows(RequisicaoInvalidaException.class,
                () -> service.custoBeneficioAtletas(null, null, 3, List.of(), 20));
        assertThrows(RequisicaoInvalidaException.class,
                () -> service.custoBeneficioAtletas(null, null, 3, SO_PROVAVEIS, 0));
        assertThrows(RequisicaoInvalidaException.class,
                () -> service.custoBeneficioAtletas(null, null, 3, SO_PROVAVEIS, RecomendacaoService.LIMITE_MAXIMO + 1));
    }

    @Test
    void mercadoSemAtletasElegiveisRetornaListaVazia() {
        CustoBeneficioAtletasResponse resposta = service.custoBeneficioAtletas(null, null, 99, SO_PROVAVEIS, 20);

        assertEquals(0, resposta.quantidade());
        assertTrue(resposta.atletas().isEmpty());
    }

    // ---- clubes ----

    @Test
    void ranqueiaClubesPelaMediaDoCustoBeneficioDosElegiveis() {
        CustoBeneficioClubesResponse resposta = service.custoBeneficioClubes(null, 3, SO_PROVAVEIS, 20);

        // Palmeiras: Veiga 0.700 (Rony fora, preço 0) | Flamengo: (0.600 + 0.700) / 2 = 0.650 | Santos: 0.400
        assertEquals(List.of("Palmeiras", "Flamengo", "Santos"),
                resposta.clubes().stream().map(c -> c.apelido()).toList());
        assertEquals(0.65, resposta.clubes().get(1).custoBeneficioMedio());
        assertEquals(2, resposta.clubes().get(1).quantidadeAtletas());
    }

    @Test
    void ranqueiaClubesPorPosicao() {
        CustoBeneficioClubesResponse resposta = service.custoBeneficioClubes(ATACANTE, 3, SO_PROVAVEIS, 20);

        // Atacantes elegíveis: Gabigol (Flamengo, 0.700) e Neymar (Santos, 0.400)
        assertEquals(List.of("Flamengo", "Santos"),
                resposta.clubes().stream().map(c -> c.apelido()).toList());
    }

    @Test
    void clubesRespeitamLimiteEValidacao() {
        assertEquals(1, service.custoBeneficioClubes(null, 3, SO_PROVAVEIS, 1).quantidade());
        assertThrows(RequisicaoInvalidaException.class, () -> service.custoBeneficioClubes(null, -1, SO_PROVAVEIS, 20));
    }
}
