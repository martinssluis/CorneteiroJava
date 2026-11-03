package corneteiro.dto;

import corneteiro.dto.Clube.ClubeDTO;
import corneteiro.dto.Clube.NomesClubes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tools.jackson.databind.json.JsonMapper;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class ClubeDTOTest {

    @Autowired
    private JsonMapper jsonMapper;

    @Test
    void usaONomeOficialEnaoOApelido() {
        ClubeDTO tricolor = new ClubeDTO(276, "sao-paulo", "Tricolor", Map.of(), "SAO");

        assertEquals("São Paulo", tricolor.nomeCompleto());
    }

    @Test
    void nomesComAcentoESufixoSaoTratados() {
        assertEquals("Grêmio", NomesClubes.doSlug("gremio"));
        assertEquals("Vitória", NomesClubes.doSlug("vitoria"));
        assertEquals("Atlético-MG", NomesClubes.doSlug("atletico-mg"));
        assertEquals("Athletico-PR", NomesClubes.doSlug("atletico-pr"));
        assertEquals("Red Bull Bragantino", NomesClubes.doSlug("bragantino"));
    }

    @Test
    void slugDesconhecidoViraNomeComIniciaisMaiusculas() {
        assertEquals("Novo Clube", NomesClubes.doSlug("novo-clube"));
    }

    @Test
    void slugAusenteNaoQuebra() {
        assertNull(NomesClubes.doSlug(null));
        assertNull(NomesClubes.doSlug(" "));
    }

    @Test
    void jsonExpoeNomeCompletoSemQuebrarALeituraDaCartola() throws Exception {
        // formato enviado pela Cartola: não tem 'nome_completo'
        ClubeDTO lido = jsonMapper.readValue(
                "{\"id\":262,\"slug\":\"flamengo\",\"apelido\":\"Mengão\",\"nome\":\"FLA\",\"escudos\":{}}",
                ClubeDTO.class);

        String json = jsonMapper.writeValueAsString(lido);

        assertEquals("Flamengo", jsonMapper.readTree(json).get("nome_completo").asString());
        assertEquals("Mengão", jsonMapper.readTree(json).get("apelido").asString());
        assertEquals("FLA", jsonMapper.readTree(json).get("nome").asString());
    }
}
