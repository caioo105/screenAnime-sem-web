package br.com.caioba.screenAnimes.Principal;

import br.com.caioba.screenAnimes.model.DadosAnimes;
import br.com.caioba.screenAnimes.model.DadosTemporada;
import br.com.caioba.screenAnimes.service.ConsumoApi;
import br.com.caioba.screenAnimes.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private Scanner leitura = new Scanner(System.in);

    private ConsumoApi consumo = new ConsumoApi();

    private ConverteDados converte = new ConverteDados();

    private final  String ENDERECO = "https://www.omdbapi.com/?t=";

    private final String API_KEY = "&apikey=6585022c";

    public void exibirMenu(){
        System.out.println("Digite o nome anime: ");
        var nomeAnime = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeAnime.replace(" ","+") + API_KEY);
        DadosAnimes dados = converte.obterDados(json, DadosAnimes.class);
        System.out.println(dados);

        List<DadosTemporada> temporadas = new ArrayList<>();

        for(int i = 1; i <= dados.totalTemporadas(); i++){
            json = consumo.obterDados(ENDERECO + nomeAnime.replace(" ","+") + "&season=" + i + API_KEY);
            DadosTemporada dadosTemporada = converte.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }

        temporadas.forEach(System.out::println);

        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
    }
}
