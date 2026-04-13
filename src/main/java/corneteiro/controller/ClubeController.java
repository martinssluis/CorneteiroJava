package corneteiro.controller;

import corneteiro.dto.Clube.ClubeBuscaResponse;
import corneteiro.dto.Clube.ClubeDTO;
import corneteiro.dto.Clube.ClubeListaResponse;
import corneteiro.service.ClubeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clubes")
public class ClubeController {

    private final ClubeService clubeService;

    public ClubeController(ClubeService clubeService) { this.clubeService = clubeService; }

    @GetMapping
    public ClubeListaResponse listarClubes(){
        return clubeService.listarClubes();
    }

    @GetMapping("/buscar")
    public ClubeBuscaResponse buscarPorNome(
            @RequestParam String nome,
            @RequestParam(defaultValue = "false") boolean exato
            ){
        return clubeService.buscarClubePorNome(nome, exato);
    }

    @GetMapping("/{id}")
    public ClubeDTO buscarporId(@PathVariable Integer id){ return clubeService.buscarClubePorId(id); }
}
