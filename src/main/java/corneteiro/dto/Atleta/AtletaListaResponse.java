package corneteiro.dto.Atleta;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Lista de atletas do mercado")
public record AtletaListaResponse(
        @Schema(description = "Total de atletas retornados", example = "700")
        Integer quantidade,
        List<AtletaDTO> atletas
){}
