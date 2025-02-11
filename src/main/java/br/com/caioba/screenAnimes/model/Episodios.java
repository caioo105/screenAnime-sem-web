package br.com.caioba.screenAnimes.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episodios {
    private Integer temporada;
    private String titulo;
    private Integer numEpi;
    private Double avaliacao;
    private String duracao;
    private LocalDate dataLancamento;

    public Episodios(Integer numeroTemporada, DadosEpisodios dadosEpisodios) {
        this.temporada = numeroTemporada;
        this.titulo = dadosEpisodios.titulo();
        this.numEpi = dadosEpisodios.numEpi();
        try{
            this.avaliacao = Double.valueOf(dadosEpisodios.avaliacao());
        }catch (NumberFormatException ex){
            this.avaliacao = 0.0;
        }
        try{
            this.dataLancamento = LocalDate.parse(dadosEpisodios.dataLancamento());
        }catch (DateTimeParseException ex){
            this.dataLancamento = null;
        }

    }

    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumEpi() {
        return numEpi;
    }

    public void setNumEpi(Integer numEpi) {
        this.numEpi = numEpi;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    @Override
    public String toString() {
        return "temporada=" + temporada +
                ", titulo='" + titulo + '\'' +
                ", numEpi=" + numEpi +
                ", avaliacao=" + avaliacao +
                ", duracao='" + duracao + '\'' +
                ", dataLancamento=" + dataLancamento;
    }
}
