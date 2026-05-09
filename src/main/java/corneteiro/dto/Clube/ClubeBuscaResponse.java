package corneteiro.dto.Clube;

import java.util.List;

public record ClubeBuscaResponse(
        String termoBusca,
        Integer quantidade,
        List<ClubeDTO> clubes
){}