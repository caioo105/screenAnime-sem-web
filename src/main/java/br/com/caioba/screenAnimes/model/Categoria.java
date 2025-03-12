package br.com.caioba.screenAnimes.model;

public enum Categoria {
    ACAO("Action"),
    AVENTURA("Adventure"),
    DRAMA("Drama"),
    COMEDIA("Comedy"),
    FANTASIA("Fantasy");

    private String categoriaOmdb;

    Categoria(String categoriaOmdb){
        this.categoriaOmdb = categoriaOmdb;
    }

    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }
}
