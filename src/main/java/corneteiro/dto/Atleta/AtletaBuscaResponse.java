package corneteiro.dto.Atleta;

import java.util.List;

public record AtletaBuscaResponse(
        String termoBusca,
        Integer quantidade,
        List<AtletaDTO> atletas
){}