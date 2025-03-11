package br.com.caioba.screenAnimes.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosAnimes(@JsonAlias("Title") String titulo,
                          @JsonAlias("totalSeasons") Integer totalTemporadas,
                          @JsonAlias("imdbRating") Double avaliacao,
                          @JsonAlias("Genre") String genero,
                          @JsonAlias("Poster") String poster,
                          @JsonAlias("Plot") String sinopse){
}
