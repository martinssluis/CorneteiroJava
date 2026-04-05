package corneteiro.dto;

import corneteiro.dto.Atleta.AtletaDTO;

import java.util.List;

public class MercadoResponse {

    private List<AtletaDTO> atletas;

    public List<AtletaDTO> getAtletas(){
        return atletas;
    }

    public void setAtletas(List<AtletaDTO> atletas){
        this.atletas = atletas;
    }
}
