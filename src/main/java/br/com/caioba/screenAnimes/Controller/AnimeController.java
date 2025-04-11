package br.com.caioba.screenAnimes.Controller;

import br.com.caioba.screenAnimes.dto.AnimeDTO;
import br.com.caioba.screenAnimes.dto.EpisodioDTO;
import br.com.caioba.screenAnimes.model.Anime;
import br.com.caioba.screenAnimes.service.AnimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/animes")
public class AnimeController {

    @Autowired
    private AnimeService servico;


    @GetMapping
    public List<AnimeDTO> obterAnimes(){
       return servico.obterAnimes();
    }

    @GetMapping("/top5")
    public List<AnimeDTO> obterTop5(){
        return servico.obterTop5();
    }

    @GetMapping("/lancamentos")
    public List<AnimeDTO> obterLancamentos(){
        return servico.obterLancamentos();
    }

    @GetMapping("/{id}")
    public AnimeDTO obterId(@PathVariable Long id){
        return servico.obterId(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodioDTO> obterTodasTemporadas(@PathVariable Long id){
        return servico.obterTodasTemporadas(id);
    }

    @GetMapping("/{id}/temporadas/{numero}")
    public List<EpisodioDTO> obterEpisodiosPorTemporada(@PathVariable Long id, @PathVariable Long numero){
        return servico.obterEpisodiosPorTemporada(id, numero);
    }

    @GetMapping("/categoria/{genero}")
    public List<AnimeDTO> obterGenero(@PathVariable String genero){
        return servico.obterGenero(genero);
    }
}
