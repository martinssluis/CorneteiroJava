package corneteiro.dto.Atleta;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Atleta do mercado do Cartola. Os campos compostos são serializados em snake_case.")
public record AtletaDTO(
    @JsonProperty("atleta_id")
    @Schema(description = "ID do atleta no Cartola", example = "12345")
    Integer atletaId,

    @Schema(description = "Nome completo", example = "Giorgian De Arrascaeta")
    String nome,
    @Schema(description = "Apelido", example = "Arrascaeta")
    String apelido,
    @Schema(description = "Identificador textual", example = "giorgian-de-arrascaeta")
    String slug,
    @Schema(description = "URL da foto; o trecho FORMATO deve ser trocado pelo tamanho (ex.: 80x80)")
    String foto,

    @JsonProperty("apelido_abreviado")
    @Schema(description = "Apelido abreviado", example = "G. Arrascaeta")
    String apelidoAbreviado,

    @JsonProperty("clube_id")
    @Schema(description = "ID do clube; consulte /clubes/{id}", example = "262")
    Integer clubeId,

    @JsonProperty("posicao_id")
    @Schema(description = "ID da posição (1 goleiro, 2 lateral, 3 zagueiro, 4 meia, 5 atacante, 6 técnico)", example = "4")
    Integer posicaoId,

    @JsonProperty("status_id")
    @Schema(description = "ID do status no Cartola (ex.: 7 provável, 6 nulo)", example = "7")
    Integer statusId,

    @JsonProperty("preco_num")
    @Schema(description = "Preço em cartoletas (C$)", example = "12.5")
    Double precoNum,

    @JsonProperty("pontos_num")
    @Schema(description = "Pontuação na última rodada; null se não jogou", example = "6.3", nullable = true)
    Double pontosNum,

    @JsonProperty("media_num")
    @Schema(description = "Média de pontos na temporada", example = "6.1")
    Double mediaNum,

    @JsonProperty("variacao_num")
    @Schema(description = "Variação de preço na última rodada", example = "0.8")
    Double variacaoNum,

    @JsonProperty("jogos_num")
    @Schema(description = "Número de jogos disputados", example = "20")
    Integer jogosNum,

    @JsonProperty("entrou_em_campo")
    @Schema(description = "Se entrou em campo na última rodada", example = "true")
    Boolean entrouEmCampo
) {}
