package br.com.caioba.screenAnimes.repository;

import br.com.caioba.screenAnimes.model.Anime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Animerepository extends JpaRepository<Anime, Long> {
}
