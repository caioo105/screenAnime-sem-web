package br.com.caioba.screenAnimes.dto;

import br.com.caioba.screenAnimes.model.Categoria;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record AnimeDTO(Long id,
                       String titulo,
                       Integer totalTemporadas,
                       Double avaliacao,
                       Categoria genero,
                       String poster,
                       String sinopse){
}
