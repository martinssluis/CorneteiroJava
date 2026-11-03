package corneteiro.dto.Recomendacao;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Ranking de clubes por custo-benefício, com os filtros aplicados")
public record CustoBeneficioClubesResponse(
        @Schema(description = "Posição filtrada; null se não filtrou", nullable = true)
        @JsonProperty("posicao_id")
        Integer posicaoId,

        @Schema(description = "Mínimo de jogos exigido por atleta", example = "3")
        @JsonProperty("min_jogos")
        Integer minJogos,

        @Schema(description = "Status aceitos", example = "[7]")
        List<Integer> status,

        @Schema(description = "Quantidade de clubes retornados", example = "20")
        Integer quantidade,

        List<CustoBeneficioClubeDTO> clubes
) {}
