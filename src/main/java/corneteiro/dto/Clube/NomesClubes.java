package corneteiro.dto.Clube;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A API do Cartola não envia o nome completo dos clubes: 'nome' é a sigla (FLA) e 'apelido' é o
 * apelido da torcida (Mengão, Tricolor). O nome oficial é derivado do 'slug', que é estável.
 */
public final class NomesClubes {

    private static final Map<String, String> POR_SLUG = Map.ofEntries(
            Map.entry("atletico-mg", "Atlético-MG"),
            Map.entry("atletico-pr", "Athletico-PR"),
            Map.entry("bahia", "Bahia"),
            Map.entry("botafogo", "Botafogo"),
            Map.entry("bragantino", "Red Bull Bragantino"),
            Map.entry("chapecoense", "Chapecoense"),
            Map.entry("corinthians", "Corinthians"),
            Map.entry("coritiba", "Coritiba"),
            Map.entry("cruzeiro", "Cruzeiro"),
            Map.entry("flamengo", "Flamengo"),
            Map.entry("fluminense", "Fluminense"),
            Map.entry("gremio", "Grêmio"),
            Map.entry("internacional", "Internacional"),
            Map.entry("mirassol", "Mirassol"),
            Map.entry("palmeiras", "Palmeiras"),
            Map.entry("remo", "Remo"),
            Map.entry("santos", "Santos"),
            Map.entry("sao-paulo", "São Paulo"),
            Map.entry("vasco", "Vasco"),
            Map.entry("vitoria", "Vitória")
    );

    private NomesClubes() {
    }

    /** Nome do clube a partir do slug. Para um slug desconhecido, devolve o slug com iniciais maiúsculas. */
    public static String doSlug(String slug) {
        if (slug == null || slug.isBlank()) {
            return null;
        }
        String conhecido = POR_SLUG.get(slug);
        if (conhecido != null) {
            return conhecido;
        }
        return Arrays.stream(slug.split("-"))
                .filter(parte -> !parte.isBlank())
                .map(parte -> Character.toUpperCase(parte.charAt(0)) + parte.substring(1))
                .collect(Collectors.joining(" "));
    }
}
