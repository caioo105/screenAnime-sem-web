package br.com.caioba.screenAnimes.repository;

import br.com.caioba.screenAnimes.model.Anime;
import br.com.caioba.screenAnimes.model.Categoria;
import br.com.caioba.screenAnimes.model.Episodios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface Animerepository extends JpaRepository<Anime, Long> {
    Optional<Anime>findByTituloContainingIgnoreCase(String nomeAnime);

    List<Anime> findTop5ByOrderByAvaliacaoDesc();

    List<Anime> findByGenero(Categoria categoria);

    List<Anime>findBytotalTemporadasGreaterThanEqualAndAvaliacaoGreaterThanEqual(Integer temporadas, Double avaliacao);

    @Query("select a from Anime a WHERE a.totalTemporadas <= :temporadas AND a.avaliacao >= :avaliacao")
    List<Anime> animePorTemporadaEavaliacao(int temporadas, double avaliacao);

    @Query("select e from Anime a JOIN a.episodios e WHERE e.titulo ILIKE %:trechoEpisodio%")
    List<Episodios> buscarEpisodioPorTrecho(String trechoEpisodio);
}
