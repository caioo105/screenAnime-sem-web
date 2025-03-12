package br.com.caioba.screenAnimes.model;

import br.com.caioba.screenAnimes.service.traducao.ConsultaMyMemory;

import java.util.OptionalDouble;

public class Anime {
    private String titulo;
    private Integer totalTemporadas;
    private Double avaliacao;
    private Categoria genero;
    private String poster;
    private String sinopse;

    public Anime(DadosAnimes dadosAnimes){
        this.titulo = dadosAnimes.titulo();
        this.totalTemporadas = dadosAnimes.totalTemporadas();
        this.avaliacao = OptionalDouble.of(Double.valueOf(dadosAnimes.avaliacao())).orElse(0);
        this.genero = Categoria.fromString(dadosAnimes.genero().split(",")[1].trim());
        this.poster = dadosAnimes.poster();
        this.sinopse = ConsultaMyMemory.obterTraducao(dadosAnimes.sinopse()).trim();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getTotalTemporadas() {
        return totalTemporadas;
    }

    public void setTotalTemporadas(Integer totalTemporadas) {
        this.totalTemporadas = totalTemporadas;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    @Override
    public String toString() {
        return  "genero: " + genero +
                " titulo: " + titulo + '\'' +
                " totalTemporadas: " + totalTemporadas +
                " avaliacao: " + avaliacao +
                " poster: " + poster + '\''+
                " sinopse: " + sinopse + '\'';
    }
}

