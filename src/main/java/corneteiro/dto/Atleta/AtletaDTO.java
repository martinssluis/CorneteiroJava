package corneteiro.dto.Atleta;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AtletaDTO(
    @JsonProperty("atleta_id")
    Integer atletaId,

    String nome,
    String apelido,
    String slug,
    String foto,

    @JsonProperty("apelido_abreviado")
    String apelidoAbreviado,

    @JsonProperty("clube_id")
    Integer clubeId,

    @JsonProperty("posicao_id")
    Integer posicaoId,

    @JsonProperty("status_id")
    Integer statusId,

    @JsonProperty("preco_num")
    Double precoNum,

    @JsonProperty("ponto_num")
    Double pontosNum,

    @JsonProperty("media_num")
    Double mediaNum,

    @JsonProperty("variacao_num")
    Double variacaoNum,

    @JsonProperty("jogos_num")
    Integer jogosNum,

    @JsonProperty("entrou_em_campo")
    Boolean entrouEmCampo
) {}
