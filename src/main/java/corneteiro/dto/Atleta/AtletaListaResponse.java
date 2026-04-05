package corneteiro.dto.Atleta;

import java.util.List;

public class AtletaListaResponse {

    private Integer quantidade;
    private List<AtletaDTO> atletas;

    public AtletaListaResponse() {
    }

    public AtletaListaResponse(Integer quantidade, List<AtletaDTO> atletas) {
        this.quantidade = quantidade;
        this.atletas = atletas;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public List<AtletaDTO> getAtletas() {
        return atletas;
    }

    public void setAtletas(List<AtletaDTO> atletas) {
        this.atletas = atletas;
    }
}
