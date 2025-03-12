package br.com.caioba.screenAnimes.service.traducao;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosResposta( @JsonAlias(value = "translatedText") String textoTraduzido) {
}
