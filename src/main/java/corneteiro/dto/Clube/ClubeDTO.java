package corneteiro.dto.Clube;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

@Schema(description = "Clube do Brasileirão no Cartola")
public record ClubeDTO(
        @Schema(description = "ID do clube no Cartola", example = "262")
        Integer id,
        @Schema(description = "Identificador textual do clube", example = "flamengo")
        String slug,
        @Schema(description = "Nome por extenso", example = "Flamengo")
        String apelido,
        @Schema(description = "URLs dos escudos, indexadas pelo tamanho (60x60, 45x45, 30x30)")
        Map<String,String>escudos,
        @Schema(description = "Sigla do clube", example = "FLA")
        String nome
){}
