package corneteiro.controller;

import corneteiro.dto.Clube.ClubeBuscaResponse;
import corneteiro.dto.Clube.ClubeDTO;
import corneteiro.dto.Clube.ClubeListaResponse;
import corneteiro.service.ClubeService;
import io.swagger.v3.oas.annotations.Operation;
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
    public ClubeListaResponse listarClubes(){
        return clubeService.listarClubes();
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar clube por nome", description = "Busca clubes pelo nome com opção de busca exata ou parcial")
    public ClubeBuscaResponse buscarPorNome(
            @RequestParam String nome,
            @RequestParam(defaultValue = "false") boolean exato
            ){
        return clubeService.buscarClubePorNome(nome, exato);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar clube por ID", description = "Retorna um clubes específico pelo seu ID")
    public ClubeDTO buscarporId(@PathVariable Integer id){
        return clubeService.buscarClubePorId(id);
    }
}
