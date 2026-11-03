package corneteiro.dto.Clube;

import java.util.Map;

public record ClubeDTO(
        Integer id,
        String slug,
        String apelido,
        Map<String,String>escudos,
        String nome
){}