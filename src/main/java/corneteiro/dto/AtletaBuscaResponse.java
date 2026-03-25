package corneteiro.dto;

import java.util.List;

public class AtletaBuscaResponse {

    private String termoBusca;
    private Integer quantidade;
    private List<AtletaDTO> atletas;

    public AtletaBuscaResponse(){}

    public AtletaBuscaResponse(String termoBusca, Integer quantidade, List<AtletaDTO> atletas) {
        this.termoBusca = termoBusca;
        this.quantidade = quantidade;
        this.atletas = atletas;
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

    public List<AtletaDTO> getAtletas() {
        return atletas;
    }

    public void setAtletas(List<AtletaDTO> atletas) {
        this.atletas = atletas;
    }
}
