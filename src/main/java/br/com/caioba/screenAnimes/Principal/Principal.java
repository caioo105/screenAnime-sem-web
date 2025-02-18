package br.com.caioba.screenAnimes.Principal;

import br.com.caioba.screenAnimes.model.DadosAnimes;
import br.com.caioba.screenAnimes.model.DadosEpisodios;
import br.com.caioba.screenAnimes.model.DadosTemporada;
import br.com.caioba.screenAnimes.model.Episodios;
import br.com.caioba.screenAnimes.service.ConsumoApi;
import br.com.caioba.screenAnimes.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitura = new Scanner(System.in);

    private ConsumoApi consumo = new ConsumoApi();

    private ConverteDados converte = new ConverteDados();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";

    private final String API_KEY = "&apikey=6585022c";

    public void exibirMenu() {
        System.out.println("Digite o nome anime: ");
        var nomeAnime = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeAnime.replace(" ", "+") + API_KEY);
        DadosAnimes dados = converte.obterDados(json, DadosAnimes.class);
        System.out.println(dados);

        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i <= dados.totalTemporadas(); i++) {
            json = consumo.obterDados(ENDERECO + nomeAnime.replace(" ", "+") + "&season=" + i + API_KEY);
            DadosTemporada dadosTemporada = converte.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }

        temporadas.forEach(System.out::println);

        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        List<DadosEpisodios> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());

       System.out.println("\nTOP 10 EPISODIOS");
        dadosEpisodios.stream().filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DadosEpisodios::avaliacao).reversed())
                .limit(10)
                .map(e -> e.titulo().toUpperCase())
                .forEach(System.out::println);

        List<Episodios> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodios(t.numeroTemp(), d))
                ).collect(Collectors.toList());

        episodios.forEach(System.out::println);

        System.out.println("Digite o nome do episodio");
        var trechoTitulo = leitura.nextLine();
        Optional<Episodios> episodioBuscado = episodios.stream()
                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
                .findFirst();

        if(episodioBuscado.isPresent()){
            System.out.println("Episodio Encontrado!");
            System.out.println("Temporada: " + episodioBuscado.get().getTemporada());
        }else{
            System.out.println("Episodio nao encontrado!");
        }

        System.out.println("A partir de que ano voce deseja ver os episodios?");
        var ano = leitura.nextInt();
        leitura.nextLine();

        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate dataBusca = LocalDate.of(ano, 1, 1);

        episodios.stream()
                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
                .forEach(e -> System.out.println(
                        "Temporada: " + e.getTemporada() +
                                "    Episdio: " + e.getTitulo() +
                                "    Data lancamento " + e.getDataLancamento().format(formatador)
                ));

        Map<Integer, Double> avalicoesPorTemporada = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.groupingBy(Episodios::getTemporada,
                        Collectors.averagingDouble(Episodios::getAvaliacao)));
        System.out.println(avalicoesPorTemporada);

        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodios::getAvaliacao));
        System.out.println("Media: " + est.getAverage());
        System.out.println("Melhor episodio: " + est.getMax());
        System.out.println("Pior episodio: " + est.getMin());
    }
}