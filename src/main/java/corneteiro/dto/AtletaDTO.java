package corneteiro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AtletaDTO {

    @JsonProperty("atleta_id")
    private Integer atletaId;

    private String nome;
    private String apelido;
    private String slug;
    private String foto;

    @JsonProperty("apelido_abreviado")
    private String apelidoAbreviado;

    @JsonProperty("clube_id")
    private Integer clubeId;

    @JsonProperty("posicao_id")
    private Integer posicaoId;

    @JsonProperty("status_id")
    private Integer statusId;

    @JsonProperty("preco_num")
    private Double precoNum;

    @JsonProperty("ponto_num")
    private Double pontosNum;

    @JsonProperty("media_num")
    private Double mediaNum;

    @JsonProperty("variacao_num")
    private Double variacaoNum;

    @JsonProperty("rodada_id")
    private Integer rodadaId;

    @JsonProperty("jogos_num")
    private Integer jogosNum;

    @JsonProperty("entrou_em_campo")
    private Boolean entrouEmCampo;

    public Integer getAtletaId() {
        return atletaId;
    }

    public void setAtletaId(Integer atletaId) {
        this.atletaId = atletaId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getApelidoAbreviado() {
        return apelidoAbreviado;
    }

    public void setApelidoAbreviado(String apelidoAbreviado) {
        this.apelidoAbreviado = apelidoAbreviado;
    }

    public Integer getClubeId() {
        return clubeId;
    }

    public void setClubeId(Integer clubeId) {
        this.clubeId = clubeId;
    }

    public Integer getPosicaoId() {
        return posicaoId;
    }

    public void setPosicaoId(Integer posicaoId) {
        this.posicaoId = posicaoId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Double getPrecoNum() {
        return precoNum;
    }

    public void setPrecoNum(Double precoNum) {
        this.precoNum = precoNum;
    }

    public Double getPontosNum() {
        return pontosNum;
    }

    public void setPontosNum(Double pontosNum) {
        this.pontosNum = pontosNum;
    }

    public Double getMediaNum() {
        return mediaNum;
    }

    public void setMediaNum(Double mediaNum) {
        this.mediaNum = mediaNum;
    }

    public Double getVariacaoNum() {
        return variacaoNum;
    }

    public void setVariacaoNum(Double variacaoNum) {
        this.variacaoNum = variacaoNum;
    }

    public Integer getRodadaId() {
        return rodadaId;
    }

    public void setRodadaId(Integer rodadaId) {
        this.rodadaId = rodadaId;
    }

    public Integer getJogosNum() {
        return jogosNum;
    }

    public void setJogosNum(Integer jogosNum) {
        this.jogosNum = jogosNum;
    }

    public Boolean getEntrouEmCampo() {
        return entrouEmCampo;
    }

    public void setEntrouEmCampo(Boolean entrouEmCampo) {
        this.entrouEmCampo = entrouEmCampo;
    }
}
