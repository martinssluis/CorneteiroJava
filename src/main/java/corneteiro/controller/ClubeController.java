package corneteiro.controller;

import corneteiro.dto.Clube.ClubeBuscaResponse;
import corneteiro.dto.Clube.ClubeDTO;
import corneteiro.dto.Clube.ClubeListaResponse;
import corneteiro.exception.ApiErrorResponse;
import corneteiro.service.ClubeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clubes")
@Tag(name = "Clubes", description = "Endpoint para busca e listagem de clubes")
public class ClubeController {

    private final ClubeService clubeService;

    public ClubeController(ClubeService clubeService) { this.clubeService = clubeService; }

    @GetMapping
    @Operation(summary = "Listar todos os clubes", description = "Retorna uma lista de todos os clubes do Cartola")
    @ApiResponse(responseCode = "200", description = "Lista de clubes retornada com sucesso")
    @ApiResponse(responseCode = "500", description = "Falha ao consultar a API do Cartola",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ClubeListaResponse listarClubes(){
        return clubeService.listarClubes();
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar clube por nome", description = "Busca clubes pelo nome com opção de busca exata ou parcial")
    @ApiResponse(responseCode = "200", description = "Clubes encontrados (a lista pode vir vazia)")
    @ApiResponse(responseCode = "400", description = "Parâmetro 'nome' ausente ou em branco",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Falha ao consultar a API do Cartola",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ClubeBuscaResponse buscarPorNome(
            @Parameter(description = "Nome, apelido ou slug do clube", example = "Flamengo")
            @RequestParam String nome,
            @Parameter(description = "Se true, exige correspondência exata; se false, aceita parcial")
            @RequestParam(defaultValue = "false") boolean exato
            ){
        return clubeService.buscarClubePorNome(nome, exato);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar clube por ID", description = "Retorna um clube específico pelo seu ID")
    @ApiResponse(responseCode = "200", description = "Clube encontrado")
    @ApiResponse(responseCode = "404", description = "Clube não encontrado",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Falha ao consultar a API do Cartola",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ClubeDTO buscarporId(
            @Parameter(description = "ID do clube no Cartola", example = "262")
            @PathVariable Integer id){
        return clubeService.buscarClubePorId(id);
    }
}
