package corneteiro.dto;

import corneteiro.dto.Atleta.AtletaDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tools.jackson.databind.json.JsonMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Garante que o AtletaDTO lê o JSON exatamente como a API do Cartola envia
 * (em especial 'pontos_num'; já houve um bug lendo 'ponto_num', que deixava a pontuação sempre nula).
 */
@SpringBootTest
class AtletaDTOJsonTest {

    @Autowired
    private JsonMapper jsonMapper;

    private static final String ATLETA_DO_CARTOLA = """
            {
              "atleta_id": 100084,
              "nome": "Giorgian De Arrascaeta",
              "apelido": "Arrascaeta",
              "slug": "arrascaeta",
              "apelido_abreviado": "G. Arrascaeta",
              "clube_id": 262,
              "posicao_id": 4,
              "status_id": 7,
              "preco_num": 12.5,
              "pontos_num": 22.2,
              "media_num": 6.1,
              "variacao_num": 0.8,
              "jogos_num": 20,
              "entrou_em_campo": true,
              "scout": {"DS": 4}
            }
            """;

    @Test
    void lePontuacaoDoCampoPontosNum() throws Exception {
        AtletaDTO atleta = jsonMapper.readValue(ATLETA_DO_CARTOLA, AtletaDTO.class);

        assertEquals(22.2, atleta.pontosNum());
        assertEquals(6.1, atleta.mediaNum());
        assertEquals(12.5, atleta.precoNum());
        assertEquals(262, atleta.clubeId());
    }

    @Test
    void pontuacaoAusenteContinuaNula() throws Exception {
        AtletaDTO atleta = jsonMapper.readValue("{\"atleta_id\": 1, \"jogos_num\": 0}", AtletaDTO.class);

        assertNull(atleta.pontosNum());
    }

    @Test
    void serializaPontuacaoComOMesmoNomeDoCartola() throws Exception {
        AtletaDTO atleta = jsonMapper.readValue(ATLETA_DO_CARTOLA, AtletaDTO.class);

        String json = jsonMapper.writeValueAsString(atleta);

        assertEquals(22.2, jsonMapper.readTree(json).get("pontos_num").asDouble());
    }
}
