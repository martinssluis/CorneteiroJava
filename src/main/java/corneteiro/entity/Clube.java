package corneteiro.entity;

import java.util.Map;

public class Clube {
    private String id;
    private String slug;
    private String apelido;
    private Map<String, String> escudos;

    public Clube(String id, String slug, String apelido, Map<String, String> escudos) {
        this.id = id;
        this.slug = slug;
        this.apelido = apelido;
        this.escudos = escudos;
    }

    public String getId() {
        return id;
    }

    public String getSlug() {
        return slug;
    }

    public String getApelido() {
        return apelido;
    }

    public String getEscudoGrande(){
        return this.escudos.get("60x60");
    }
    public String getEscudoMedio(){
        return this.escudos.get("45x45");
    }
    public String getEscudoPequeno(){
        return this.escudos.get("30x30");
    }
}
