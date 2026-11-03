package corneteiro.dto.Clube;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Lista de clubes")
public record ClubeListaResponse(
        @Schema(description = "Total de clubes retornados", example = "20")
        Integer quantidade,
        List<ClubeDTO> clubes
){}
