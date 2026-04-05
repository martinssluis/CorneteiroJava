package corneteiro.dto.Clube;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class ClubeDTO {

    private Integer id;
    private String nome;
    private String abreviacao;
    private String slug;
    private String apelido;

    @JsonProperty("nome_fantasia") // anotação do Jackson que serve para mapear nomes entre o JSON e o seu objeto Java
    private String nomeFantasia;

    @JsonProperty("url_editoria")
    private String urlEditoria;

    private Map<String, String> escudos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAbreviacao() {
        return abreviacao;
    }

    public void setAbreviacao(String abreviacao) {
        this.abreviacao = abreviacao;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getUrlEditoria() {
        return urlEditoria;
    }

    public void setUrlEditoria(String urlEditoria) {
        this.urlEditoria = urlEditoria;
    }

    public Map<String, String> getEscudos() {
        return escudos;
    }

    public void setEscudos(Map<String, String> escudos) {
        this.escudos = escudos;
    }
}
