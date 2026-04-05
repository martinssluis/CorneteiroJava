package corneteiro.dto;

import corneteiro.dto.Atleta.AtletaDTO;
import corneteiro.dto.Clube.ClubeDTO;

import java.util.List;
import java.util.Map;

public class MercadoResponse {

    private List<AtletaDTO> atletas;
    private Map<Integer, ClubeDTO> clubes;

    public List<AtletaDTO> getAtletas() {
        return atletas;
    }

    public void setAtletas(List<AtletaDTO> atletas) {
        this.atletas = atletas;
    }

    public Map<Integer, ClubeDTO> getClubes() {
        return clubes;
    }

    public void setClubes(Map<Integer, ClubeDTO> clubes) {
        this.clubes = clubes;
    }
}
