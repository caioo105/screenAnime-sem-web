package br.com.caioba.screenAnimes.model;

import br.com.caioba.screenAnimes.service.traducao.ConsultaMyMemory;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

@Entity
@Table(name = "Animes")
public class Anime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    private Integer totalTemporadas;

    private Double avaliacao;

    @Enumerated(EnumType.STRING)
    private Categoria genero;

    private String poster;

    private String sinopse;

    @Transient
    private List<Episodios> episodios = new ArrayList<>();

    public Anime(DadosAnimes dadosAnimes){
        this.titulo = dadosAnimes.titulo();
        this.totalTemporadas = dadosAnimes.totalTemporadas();
        this.avaliacao = OptionalDouble.of(Double.valueOf(dadosAnimes.avaliacao())).orElse(0);
        this.genero = Categoria.fromString(dadosAnimes.genero().split(",")[1].trim());
        this.poster = dadosAnimes.poster();
        this.sinopse = ConsultaMyMemory.obterTraducao(dadosAnimes.sinopse()).trim();
    }

    public List<Episodios> getEpisodios() {
        return episodios;
    }

    public void setEpisodios(List<Episodios> episodios) {
        this.episodios = episodios;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

