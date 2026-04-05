package corneteiro.dto.Clube;

import java.util.List;

public class ClubeListaResponse {

    private Integer quantidade;
    private List<ClubeDTO> clubes;

    public ClubeListaResponse(){
    }

    public ClubeListaResponse(Integer quantidade, List<ClubeDTO> clubes){
        this.quantidade = quantidade;
        this.clubes = clubes;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public List<ClubeDTO> getClubes() {
        return clubes;
    }

    public void setClubes(List<ClubeDTO> clubes) {
        this.clubes = clubes;
    }
}
