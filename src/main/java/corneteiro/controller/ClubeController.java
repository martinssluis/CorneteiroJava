package corneteiro.controller;

import corneteiro.dto.Clube.ClubeListaResponse;
import corneteiro.service.ClubeService;
import org.springframework.web.bind.annotation.GetMapping;
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
}
