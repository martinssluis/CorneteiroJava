package corneteiro.dto.Clube;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public record ClubeDTO(
        Integer id,
        String nome,
        String abreviacao,
        String slug,
        String apelido,

        @JsonProperty("nome_fantasia")
        String nomeFantasia,

        @JsonProperty("url_editoria")
        String urlEditoria,

        Map<String, String>escudos
){}