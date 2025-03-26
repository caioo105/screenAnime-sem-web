package br.com.caioba.screenAnimes.model;

public enum Categoria {
    ACAO("Action", "acao"),
    AVENTURA("Adventure", "aventura"),
    DRAMA("Drama", "drama"),
    COMEDIA("Comedy", "comedia"),
    FANTASIA("Fantasy", "fantasia");

    private String categoriaOmdb;

    private String categoriaPortugues;

    Categoria(String categoriaOmdb, String categoriaPortuges){
        this.categoriaOmdb = categoriaOmdb;
        this.categoriaPortugues = categoriaPortuges;
    }

    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }

    public static Categoria fromPortugues(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaPortugues.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }
}
