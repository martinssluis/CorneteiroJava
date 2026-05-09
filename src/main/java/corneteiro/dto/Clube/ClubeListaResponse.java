package corneteiro.dto.Clube;

import java.util.List;

public record ClubeListaResponse(
        Integer quantidade,
        List<ClubeDTO> clubes
){}