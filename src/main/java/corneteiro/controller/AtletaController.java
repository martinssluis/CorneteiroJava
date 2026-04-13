package corneteiro.controller;

import corneteiro.dto.Atleta.AtletaBuscaResponse;
import corneteiro.dto.Atleta.AtletaDTO;
import corneteiro.dto.Atleta.AtletaListaResponse;
import corneteiro.service.AtletaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/atletas")
public class AtletaController {

    private final AtletaService atletaService;

    public AtletaController(AtletaService atletaService){
        this.atletaService = atletaService;
    }

    @GetMapping
    public AtletaListaResponse listarAtletas(){
        return atletaService.listarAtletas();
    }

    @GetMapping("/buscar")
    public AtletaBuscaResponse buscarPorNome(
            @RequestParam String nome,
            @RequestParam(defaultValue = "false") boolean exato
    ) {
        return atletaService.buscarAtletaPorNome(nome, exato);
    }

    @GetMapping("/{id}")
    public AtletaDTO buscarporId(@PathVariable Integer id){
        return atletaService.buscarAtletaPorId(id);
    }
}
