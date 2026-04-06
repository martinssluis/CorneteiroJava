package corneteiro.service;

import corneteiro.client.CartolaApiClient;
import corneteiro.dto.Atleta.AtletaDTO;
import corneteiro.dto.Atleta.AtletaListaResponse;
import corneteiro.dto.Clube.ClubeBuscaResponse;
import corneteiro.dto.Clube.ClubeDTO;
import corneteiro.dto.Clube.ClubeListaResponse;
import corneteiro.dto.MercadoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static java.util.spi.ToolProvider.findFirst;

@Service
public class ClubeService {

    private final CartolaApiClient cartolaApiClient;

    public ClubeService(CartolaApiClient cartolaApiClient) {this.cartolaApiClient = cartolaApiClient;}

    public ClubeListaResponse listarClubes() {
        MercadoResponse mercado = cartolaApiClient.buscarMercado();

        if (mercado == null || mercado.getClubes() == null) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Resposta inválida da API do Cartola"
            );
        }

        return new ClubeListaResponse(
                mercado.getClubes().size(),
                mercado.getClubes().values().stream().toList()
        );
    }

    public ClubeDTO buscarClubesPorId(Integer id){
        if(id==null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Informe um id válido"
            );
        }

            MercadoResponse mercado = cartolaApiClient.buscarMercado();

            if(mercado == null || mercado.getClubes() == null){
                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Resposta inválida da API do Cartola"
                );
            }

            ClubeDTO clube = mercado.getClubes().get(id);

           if (clube == null){
               throw new ResponseStatusException(
                       HttpStatus.NOT_FOUND,
                       "Clube não encontrado"
               );
           }
           return clube;
        }

        public ClubeBuscaResponse buscarClubePorNome(String nome, boolean exato){
            if(nome == null || nome.isBlank()){
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Informe o parâmetro 'nome'"
                );
            }

            MercadoResponse mercado = cartolaApiClient.buscarMercado();

            if(mercado == null || mercado.getClubes() == null){
                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Resposta inválida da API do Carola"
                );
            }

            List<ClubeDTO> clubesFiltrados = mercado.getClubes().values().stream()
                    .filter(clube -> {
                        if(clube.getNome() == null) return false;

                        if(exato){
                            return clube.getNome().equalsIgnoreCase(nome);
                        } else{
                            return clube.getNome().toLowerCase().contains(nome.toLowerCase());
                        }
                    }).toList();

            if(clubesFiltrados.isEmpty()){
                 throw new ResponseStatusException(
                         HttpStatus.NOT_FOUND,
                         "Nenhum clube encontrado"
                 );
            }

            return new ClubeBuscaResponse(
                    nome,
                    clubesFiltrados.size(),
                    clubesFiltrados
            );
        }
    }

