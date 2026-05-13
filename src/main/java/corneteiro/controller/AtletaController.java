package corneteiro.controller;

import corneteiro.dto.Atleta.AtletaBuscaResponse;
import corneteiro.dto.Atleta.AtletaDTO;
import corneteiro.dto.Atleta.AtletaListaResponse;
import corneteiro.service.AtletaService;
import io.swagger.v3.oas.annotations.Operation;
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
    public AtletaListaResponse listarAtletas(){
        return atletaService.listarAtletas();
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar atleta por nome", description = "Busca atletas pelo nome com opção de busca exata ou parcial")
    public AtletaBuscaResponse buscarPorNome(
            @RequestParam String nome,
            @RequestParam(defaultValue = "false") boolean exato
    ) {
        return atletaService.buscarAtletaPorNome(nome, exato);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar atleta por ID", description = "Retorna um atleta específico pelo seu ID")
    public AtletaDTO buscarporId(@PathVariable Integer id){
        return atletaService.buscarAtletaPorId(id);
    }
}
