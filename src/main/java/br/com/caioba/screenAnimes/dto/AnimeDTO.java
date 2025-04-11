package br.com.caioba.screenAnimes.dto;

import br.com.caioba.screenAnimes.model.Categoria;

public record AnimeDTO(Long id,
                       String titulo,
                       Integer totalTemporadas,
                       Double avaliacao,
                       Categoria genero,
                       String poster,
                       String sinopse){
}
