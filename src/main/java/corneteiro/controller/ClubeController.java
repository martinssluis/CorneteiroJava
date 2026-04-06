package corneteiro.controller;

import corneteiro.dto.Atleta.AtletaDTO;
import corneteiro.dto.Clube.ClubeDTO;
import corneteiro.dto.Clube.ClubeListaResponse;
import corneteiro.service.ClubeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clubes")
public class ClubeController {

    private final ClubeService clubeService;

    public ClubeController(ClubeService clubeService) { this.clubeService = clubeService; }

    @GetMapping
    public ClubeListaResponse listarClubes(){
        return clubeService.listarClubes();
    }

    @GetMapping("/{id}")
    public ClubeDTO buscarporId(@PathVariable Integer id){ return clubeService.buscarClubesPorId(id); }
}
