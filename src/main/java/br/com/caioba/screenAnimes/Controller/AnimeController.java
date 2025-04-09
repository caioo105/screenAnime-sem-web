package br.com.caioba.screenAnimes.Controller;

import br.com.caioba.screenAnimes.dto.AnimeDTO;
import br.com.caioba.screenAnimes.model.Anime;
import br.com.caioba.screenAnimes.repository.Animerepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AnimeController {
    @Autowired
    private Animerepository repositorio;

    @GetMapping("/animes")
    public List<AnimeDTO> obterAnimes(){
        return repositorio.findAll()
                .stream()
                .map(a -> new AnimeDTO(a.getId(), a.getTitulo() , a.getTotalTemporadas() , a.getAvaliacao(), a.getGenero() ,a.getPoster(), a.getSinopse()))
                .collect(Collectors.toList());
    }
}
