package corneteiro.dto.Recomendacao;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Atleta ranqueado por custo-benefício (média de pontos dividida pelo preço)")
public record CustoBeneficioAtletaDTO(
        @JsonProperty("atleta_id")
        @Schema(description = "ID do atleta no Cartola", example = "12345")
        Integer atletaId,

        @Schema(description = "Apelido", example = "Arrascaeta")
        String apelido,

        @JsonProperty("clube_id")
        @Schema(description = "ID do clube", example = "262")
        Integer clubeId,

        @JsonProperty("posicao_id")
        @Schema(description = "ID da posição (1 goleiro, 2 lateral, 3 zagueiro, 4 meia, 5 atacante, 6 técnico)", example = "4")
        Integer posicaoId,

        @JsonProperty("status_id")
        @Schema(description = "ID do status (ex.: 7 provável)", example = "7")
        Integer statusId,

        @JsonProperty("preco_num")
        @Schema(description = "Preço em cartoletas (C$)", example = "12.5")
        Double precoNum,

        @JsonProperty("media_num")
        @Schema(description = "Média de pontos na temporada", example = "6.1")
        Double mediaNum,

        @JsonProperty("jogos_num")
        @Schema(description = "Jogos disputados", example = "20")
        Integer jogosNum,

        @JsonProperty("custo_beneficio")
        @Schema(description = "Média dividida pelo preço, arredondada em 3 casas", example = "0.488")
        Double custoBeneficio,

        @Schema(description = "Explicação do cálculo com os valores usados")
        String motivo
) {}
