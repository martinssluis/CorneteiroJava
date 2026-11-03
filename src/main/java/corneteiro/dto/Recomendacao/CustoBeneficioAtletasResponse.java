package corneteiro.dto.Recomendacao;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Ranking de custo-benefício de atletas, com os filtros aplicados")
public record CustoBeneficioAtletasResponse(
        @Schema(description = "Posição filtrada; null se não filtrou", nullable = true)
        @JsonProperty("posicao_id")
        Integer posicaoId,

        @Schema(description = "Clube filtrado; null se não filtrou", nullable = true)
        @JsonProperty("clube_id")
        Integer clubeId,

        @Schema(description = "Mínimo de jogos exigido", example = "3")
        @JsonProperty("min_jogos")
        Integer minJogos,

        @Schema(description = "Status aceitos", example = "[7]")
        List<Integer> status,

        @Schema(description = "Quantidade de atletas retornados", example = "20")
        Integer quantidade,

        List<CustoBeneficioAtletaDTO> atletas
) {}
