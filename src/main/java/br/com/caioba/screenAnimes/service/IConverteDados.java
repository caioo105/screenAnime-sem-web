package br.com.caioba.screenAnimes.service;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);
}
