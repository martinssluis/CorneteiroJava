package corneteiro.dto.Atleta;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Resultado da busca de atletas por nome")
public record AtletaBuscaResponse(
        @Schema(description = "Termo pesquisado", example = "arrascaeta")
        String termoBusca,
        @Schema(description = "Total de atletas encontrados", example = "1")
        Integer quantidade,
        List<AtletaDTO> atletas
){}
