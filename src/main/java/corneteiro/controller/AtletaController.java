package corneteiro.controller;

import corneteiro.dto.AtletaBuscaResponse;
import corneteiro.dto.AtletaDTO;
import corneteiro.service.AtletaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/atletas")
public class AtletaController {

    private final AtletaService atletaService;

    public AtletaController(AtletaService atletaService){
        this.atletaService = atletaService;
    }

    @GetMapping("/buscar")
    public AtletaBuscaResponse buscarPorNome(
            @RequestParam String nome,
            @RequestParam(defaultValue = "false") boolean exato
    ) {
        return atletaService.buscarAtletasPorNome(nome, exato);
    }

    @GetMapping("/{id}")
    public AtletaDTO buscarporId(@PathVariable Integer id){
        return atletaService.buscarAtletasPorId(id);
    }
}
