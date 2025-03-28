package br.com.caioba.screenAnimes.repository;

import br.com.caioba.screenAnimes.model.Anime;
import br.com.caioba.screenAnimes.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface Animerepository extends JpaRepository<Anime, Long> {
    Optional<Anime>findByTituloContainingIgnoreCase(String nomeAnime);

    List<Anime> findTop5ByOrderByAvaliacaoDesc();

    List<Anime> findByGenero(Categoria categoria);

    List<Anime>findBytotalTemporadasGreaterThanEqualAndAvaliacaoGreaterThanEqual(Integer temporadas, Double avaliacao);
}
