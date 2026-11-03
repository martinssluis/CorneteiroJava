package corneteiro.controller;

import corneteiro.dto.Atleta.AtletaBuscaResponse;
import corneteiro.dto.Atleta.AtletaDTO;
import corneteiro.dto.Atleta.AtletaListaResponse;
import corneteiro.exception.ApiErrorResponse;
import corneteiro.service.AtletaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/atletas")
@Tag(name = "Atletas", description = "Endpoint para busca e listagem de atletas")
public class AtletaController {

    private final AtletaService atletaService;

    public AtletaController(AtletaService atletaService){
        this.atletaService = atletaService;
    }

    @GetMapping
    @Operation(summary = "Listar todos os atletas", description = "Retorna uma lista completa de todos os atletas do mercado")
    @ApiResponse(responseCode = "200", description = "Lista de atletas retornada com sucesso")
    @ApiResponse(responseCode = "500", description = "Falha ao consultar a API do Cartola",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public AtletaListaResponse listarAtletas(){
        return atletaService.listarAtletas();
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar atleta por nome", description = "Busca atletas pelo nome com opção de busca exata ou parcial")
    @ApiResponse(responseCode = "200", description = "Atletas encontrados (a lista pode vir vazia)")
    @ApiResponse(responseCode = "400", description = "Parâmetro 'nome' ausente ou em branco",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Falha ao consultar a API do Cartola",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public AtletaBuscaResponse buscarPorNome(
            @Parameter(description = "Nome, apelido ou slug do atleta", example = "Arrascaeta")
            @RequestParam String nome,
            @Parameter(description = "Se true, exige correspondência exata; se false, aceita parcial")
            @RequestParam(defaultValue = "false") boolean exato
    ) {
        return atletaService.buscarAtletaPorNome(nome, exato);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar atleta por ID", description = "Retorna um atleta específico pelo seu ID")
    @ApiResponse(responseCode = "200", description = "Atleta encontrado")
    @ApiResponse(responseCode = "404", description = "Atleta não encontrado",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Falha ao consultar a API do Cartola",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public AtletaDTO buscarporId(
            @Parameter(description = "ID do atleta no Cartola", example = "12345")
            @PathVariable Integer id){
        return atletaService.buscarAtletaPorId(id);
    }
}
