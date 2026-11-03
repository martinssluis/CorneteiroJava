package corneteiro.dto.Recomendacao;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

@Schema(description = "Clube ranqueado pela média do custo-benefício dos seus atletas elegíveis")
public record CustoBeneficioClubeDTO(
        @JsonProperty("clube_id")
        @Schema(description = "ID do clube", example = "262")
        Integer clubeId,

        @Schema(description = "Nome do clube", example = "Flamengo")
        String nome,

        @Schema(description = "Apelido do clube", example = "Mengão")
        String apelido,

        @Schema(description = "URLs dos escudos por tamanho")
        Map<String, String> escudos,

        @JsonProperty("quantidade_atletas")
        @Schema(description = "Atletas elegíveis considerados no cálculo", example = "8")
        Integer quantidadeAtletas,

        @JsonProperty("custo_beneficio_medio")
        @Schema(description = "Média do custo-benefício dos atletas elegíveis, em 3 casas", example = "0.412")
        Double custoBeneficioMedio,

        @Schema(description = "Explicação do cálculo")
        String motivo
) {}
