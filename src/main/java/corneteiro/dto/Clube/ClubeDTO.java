package corneteiro.dto.Clube;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

@Schema(description = "Clube do Brasileirão no Cartola")
public record ClubeDTO(
        @Schema(description = "ID do clube no Cartola", example = "262")
        Integer id,
        @Schema(description = "Identificador textual do clube", example = "flamengo")
        String slug,
        @Schema(description = "Apelido do clube", example = "Mengão")
        String apelido,
        @Schema(description = "URLs dos escudos, indexadas pelo tamanho (60x60, 45x45, 30x30)")
        Map<String,String>escudos,
        @Schema(description = "Sigla do clube", example = "FLA")
        String nome
){
    @JsonProperty("nome_completo")
    @Schema(description = "Nome do clube, derivado do slug (a Cartola não envia esse dado)", example = "Flamengo")
    public String nomeCompleto() {
        return NomesClubes.doSlug(slug);
    }
}
