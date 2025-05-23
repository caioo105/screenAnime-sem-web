package br.com.caioba.screenAnimes.service;

import br.com.caioba.screenAnimes.dto.AnimeDTO;
import br.com.caioba.screenAnimes.dto.EpisodioDTO;
import br.com.caioba.screenAnimes.model.Anime;
import br.com.caioba.screenAnimes.model.Categoria;
import br.com.caioba.screenAnimes.repository.Animerepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnimeService {

    @Autowired
    private Animerepository repositorio;

    public List<AnimeDTO> obterAnimes(){
        return converteDados(repositorio.findAll());
    }

    public List<AnimeDTO> obterTop5() {
        return converteDados(repositorio.findTop5ByOrderByAvaliacaoDesc());
    }

    private List<AnimeDTO> converteDados(List<Anime> anime){
        return anime.stream()
                .map(a -> new AnimeDTO(a.getId(), a.getTitulo() , a.getTotalTemporadas() , a.getAvaliacao(), a.getGenero() ,a.getPoster(), a.getSinopse()))
                .collect(Collectors.toList());
    }

    public List<AnimeDTO> obterLancamentos() {
        return converteDados(repositorio.findTop5ByOrderByEpisodiosDataLancamentoDesc());
    }

    public AnimeDTO obterId(Long id) {
        Optional<Anime> anime = repositorio.findById(id);

        if(anime.isPresent()){
            Anime a = anime.get();
            return new AnimeDTO(a.getId(), a.getTitulo() , a.getTotalTemporadas() , a.getAvaliacao(), a.getGenero() ,a.getPoster(), a.getSinopse());
        }
        return null;
    }

    public List<EpisodioDTO> obterTodasTemporadas(Long id) {
        Optional<Anime> anime = repositorio.findById(id);

        if(anime.isPresent()){
            Anime a = anime.get();
            return a.getEpisodios().stream()
                    .map(e -> new EpisodioDTO(e.getTemporada(),e.getNumEpi(), e.getTitulo()))
                    .collect(Collectors.toList());
        }
        return null;
    }

    public List<EpisodioDTO> obterEpisodiosPorTemporada(Long id, Long numero) {
        return repositorio.obterEpisodiosPorTemporada(id, numero)
                .stream()
                .map(e -> new EpisodioDTO(e.getTemporada(),e.getNumEpi(), e.getTitulo()))
                .collect(Collectors.toList());
    }

    public List<AnimeDTO> obterGenero(String genero) {
        Categoria categoria = Categoria.fromPortugues(genero);
        return converteDados(repositorio.findByGenero(categoria));
    }
}
