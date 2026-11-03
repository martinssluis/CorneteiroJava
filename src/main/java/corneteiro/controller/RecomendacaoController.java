package corneteiro.controller;

import corneteiro.dto.Recomendacao.CustoBeneficioAtletasResponse;
import corneteiro.dto.Recomendacao.CustoBeneficioClubesResponse;
import corneteiro.exception.ApiErrorResponse;
import corneteiro.service.RecomendacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recomendacoes")
@Tag(name = "Recomendações", description = "Rankings calculados a partir do mercado do Cartola")
public class RecomendacaoController {

    private final RecomendacaoService recomendacaoService;

    public RecomendacaoController(RecomendacaoService recomendacaoService) {
        this.recomendacaoService = recomendacaoService;
    }

    @GetMapping("/custo-beneficio")
    @Operation(summary = "Custo-benefício de atletas",
            description = "Ranking de atletas por média de pontos dividida pelo preço. "
                    + "Só entram atletas com preço maior que zero, ao menos 'minJogos' jogos e status aceito.")
    @ApiResponse(responseCode = "200", description = "Ranking retornado (a lista pode vir vazia)")
    @ApiResponse(responseCode = "400", description = "Parâmetro inválido (minJogos negativo, status vazio ou limite fora de 1 a 100)",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "404", description = "Clube informado não existe",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Falha ao consultar a API do Cartola",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public CustoBeneficioAtletasResponse custoBeneficioAtletas(
            @Parameter(description = "Filtra por posição (1 goleiro, 2 lateral, 3 zagueiro, 4 meia, 5 atacante, 6 técnico)", example = "4")
            @RequestParam(required = false) Integer posicaoId,
            @Parameter(description = "Filtra por clube (ID do clube no Cartola)", example = "262")
            @RequestParam(required = false) Integer clubeId,
            @Parameter(description = "Mínimo de jogos disputados")
            @RequestParam(defaultValue = "3") int minJogos,
            @Parameter(description = "Status aceitos, separados por vírgula (7 provável, 2 dúvida, 6 nulo, 5 contundido, 3 suspenso)")
            @RequestParam(defaultValue = "7") List<Integer> status,
            @Parameter(description = "Quantidade máxima de resultados (1 a 100)")
            @RequestParam(defaultValue = "20") int limite) {
        return recomendacaoService.custoBeneficioAtletas(posicaoId, clubeId, minJogos, status, limite);
    }

    @GetMapping("/custo-beneficio/clubes")
    @Operation(summary = "Custo-benefício de clubes",
            description = "Ranking de clubes pela média do custo-benefício dos seus atletas elegíveis.")
    @ApiResponse(responseCode = "200", description = "Ranking retornado (a lista pode vir vazia)")
    @ApiResponse(responseCode = "400", description = "Parâmetro inválido (minJogos negativo, status vazio ou limite fora de 1 a 100)",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Falha ao consultar a API do Cartola",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public CustoBeneficioClubesResponse custoBeneficioClubes(
            @Parameter(description = "Considera só atletas desta posição", example = "5")
            @RequestParam(required = false) Integer posicaoId,
            @Parameter(description = "Mínimo de jogos disputados por atleta")
            @RequestParam(defaultValue = "3") int minJogos,
            @Parameter(description = "Status aceitos, separados por vírgula")
            @RequestParam(defaultValue = "7") List<Integer> status,
            @Parameter(description = "Quantidade máxima de clubes (1 a 100)")
            @RequestParam(defaultValue = "20") int limite) {
        return recomendacaoService.custoBeneficioClubes(posicaoId, minJogos, status, limite);
    }
}
