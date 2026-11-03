package corneteiro.dto.Clube;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Resultado da busca de clubes por nome")
public record ClubeBuscaResponse(
        @Schema(description = "Termo pesquisado", example = "fla")
        String termoBusca,
        @Schema(description = "Total de clubes encontrados", example = "1")
        Integer quantidade,
        List<ClubeDTO> clubes
){}
