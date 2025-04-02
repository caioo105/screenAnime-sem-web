package br.com.caioba.screenAnimes.Principal;

import br.com.caioba.screenAnimes.model.*;
import br.com.caioba.screenAnimes.repository.Animerepository;
import br.com.caioba.screenAnimes.service.ConsumoApi;
import br.com.caioba.screenAnimes.service.ConverteDados;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitura = new Scanner(System.in);

    private ConsumoApi consumo = new ConsumoApi();

    private ConverteDados converte = new ConverteDados();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";

    private final String API_KEY = "&apikey=6585022c";

    private List<DadosAnimes> dadosAnimes = new ArrayList<>();

    private Animerepository repositorio;

    private List<Anime> animes = new ArrayList<>();

    private Optional<Anime> animeBusca;

    public Principal(Animerepository repositorio){
        this.repositorio = repositorio;
    }

    public void exibirMenu() {
        var opcao = -1;
        while(opcao != 0) {
            var menu = """
                    1 - Buscar Animes
                    2 - Buscar episódios
                    3 - Listar Animes buscados
                    4-  Buscar Animes por titulo
                    5-  Top 5 Animes
                    6-  Buscar Animes por genero
                    7-  Buscar Animes por temporada
                    8-  Buscar episodios por trechos
                    9-  Top 5 Episodios do Anime
                    10- Buscar ano do Anime
                    
                    0 - Sair                                 
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarAnimeWeb();
                    break;
                case 2:
                    buscarEpisodioPorAnime();
                    break;
                case 3:
                    listarAnimeBuscado();
                case 4:
                    buscarAnimePorTitulo();
                    break;
                case 5:
                    top5Animes();
                case 6:
                    buscarPorCategoria();
                    break;
                case 7:
                    buscarPorTemporadaEavaliacao();
                    break;
                case 8:
                    buscarTrechoEpisodio();
                    break;
                case 9:
                    top5EpisodiosPorAnime();
                    break;
                case 10:
                    buscarEpisodioAno();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
          }
        }




    private void buscarAnimeWeb() {
            DadosAnimes dados = getDadosAnime();
            Anime anime = new Anime(dados);
            //dadosAnimes.add(dados);
            repositorio.save(anime);
            System.out.println(dados);
        }

        private DadosAnimes getDadosAnime(){
        System.out.println("Digite o nome do Anime para busca");
        var nomeAnime = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeAnime.replace(" ", "+") + API_KEY);
        DadosAnimes dados = converte.obterDados(json, DadosAnimes.class);
        return dados;
        }

        private void buscarEpisodioPorAnime(){
        listarAnimeBuscado();
        System.out.println("Digite o nome do Anime: ");
        var nomeAnime = leitura.nextLine();

            Optional<Anime> anime = repositorio.findByTituloContainingIgnoreCase(nomeAnime);

            if(anime.isPresent()) {
                var animeEncontrado = anime.get();

                List<DadosTemporada> temporadas = new ArrayList<>();

                for (int i = 1; i <= animeEncontrado.getTotalTemporadas(); i++) {
                    var json = consumo.obterDados(ENDERECO + animeEncontrado.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                    DadosTemporada dadosTemporada = converte.obterDados(json, DadosTemporada.class);
                    temporadas.add(dadosTemporada);
                }
                temporadas.forEach(System.out::println);

                List<Episodios> episodios = temporadas.stream()
                        .flatMap(d -> d.episodios().stream()
                                .map(e -> new Episodios(d.numeroTemp(), e)))
                        .collect(Collectors.toList());

                animeEncontrado.setEpisodios(episodios);
                repositorio.save(animeEncontrado);

            }else{
                System.out.println("Anime nao encontrado!");
            }

    }

    private void listarAnimeBuscado(){
        animes = repositorio.findAll();
        animes.stream()
                .sorted(Comparator.comparing(Anime::getGenero))
                .forEach(System.out::println);
    }

    private void buscarAnimePorTitulo() {
        System.out.println("Digite o nome do Anime: ");
        var nomeAnime = leitura.nextLine();
        animeBusca = repositorio.findByTituloContainingIgnoreCase(nomeAnime);

        if(animeBusca.isPresent()){
            System.out.println("Dados do Anime: " + animeBusca.get());
        }else {
            System.out.println("Anime nao encontrado!");
        }
    }

    private void top5Animes() {
        List<Anime> topAnimes = repositorio.findTop5ByOrderByAvaliacaoDesc();
        topAnimes.forEach(a -> System.out.println(a.getTitulo() + " avaliacao: " + a.getAvaliacao()));
    }

    private void buscarPorCategoria() {
        System.out.println("Digite o genero desejado: ");
        var nomeGenero = leitura.nextLine();
        Categoria categoria = Categoria.fromPortugues(nomeGenero);
        List<Anime> animePorCategoria = repositorio.findByGenero(categoria);
        System.out.println("Animes com esse genero: " + nomeGenero);
        animePorCategoria.forEach(System.out::println);
    }
    private void buscarPorTemporadaEavaliacao() {
        System.out.println("Digite o numero de temporadas desejado: ");
        int temporadas = leitura.nextInt();
        System.out.println("Agora digite a avaliacao desejada");
        double avaliacao = leitura.nextDouble();
        List<Anime> animePorTemporadaEavaliacao = repositorio.animePorTemporadaEavaliacao(temporadas, avaliacao);
        animePorTemporadaEavaliacao.forEach(System.out::println);
    }

    private void buscarTrechoEpisodio(){
        System.out.println("Digite o trecho do episdio: ");
        var trechoEpisodio = leitura.nextLine();
        List<Episodios> episodiosEncontrados = repositorio.buscarEpisodioPorTrecho(trechoEpisodio);
        episodiosEncontrados.forEach(e ->
                System.out.printf("Anime: %s Temporada %s - Episodio %s - %s\n",
                        e.getAnime().getTitulo(), e.getTemporada(),
                        e.getNumEpi(), e.getTitulo()));
    }

    private void top5EpisodiosPorAnime(){
        buscarAnimePorTitulo();
        if(animeBusca.isPresent()){
            Anime anime = animeBusca.get();
            List<Episodios> top5Episodios = repositorio.top5EpisodiosPorAnime(anime);
            top5Episodios.forEach(e ->
                    System.out.printf("Anime: %s Temporada %s - Episodio %s - %s Avaliacao %s - \n",
                    e.getAnime().getTitulo(), e.getTemporada(),
                    e.getNumEpi(), e.getTitulo(), e.getAvaliacao()));
        }
    }

    private void buscarEpisodioAno(){
        buscarAnimePorTitulo();
        if(animeBusca.isPresent()){
            Anime anime = animeBusca.get();
            System.out.println("Digite o ano limite: ");
            var anoLancamento = leitura.nextInt();
            leitura.nextLine();
            List<Episodios> episodiosAno = repositorio.episodiosPorData(anime, anoLancamento);
            episodiosAno.forEach(System.out::println);
        }
    }

}