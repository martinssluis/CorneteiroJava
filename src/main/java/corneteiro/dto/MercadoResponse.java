package corneteiro.dto;

import corneteiro.dto.Atleta.AtletaDTO;
import corneteiro.dto.Clube.ClubeDTO;

import java.util.List;
import java.util.Map;

public record MercadoResponse(
        List<AtletaDTO> atletas,
        Map<Integer, ClubeDTO> clubes
){}
