package corneteiro.dto.Clube;

import java.util.List;

public class ClubeBuscaResponse {

    private String termoBusca;
    private Integer quantidade;
    private List<ClubeDTO> clubes;

    public ClubeBuscaResponse(){}

    public ClubeBuscaResponse(String termoBusca, Integer quantidade, List<ClubeDTO> clubes) {
        this.termoBusca = termoBusca;
        this.quantidade = quantidade;
        this.clubes = clubes;
    }

    public String getTermoBusca() {
        return termoBusca;
    }

    public void setTermoBusca(String termoBusca) {
        this.termoBusca = termoBusca;
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
