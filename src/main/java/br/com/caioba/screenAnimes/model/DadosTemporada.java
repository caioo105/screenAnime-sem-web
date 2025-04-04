package br.com.caioba.screenAnimes.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosTemporada(@JsonAlias("Season") Integer numeroTemp,
                             @JsonAlias("Episodes") List<DadosEpisodios> episodios) {
}
