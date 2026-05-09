package corneteiro.dto.Atleta;

import java.util.List;

public record AtletaListaResponse(
        Integer quantidade,
        List<AtletaDTO> atletas
){}