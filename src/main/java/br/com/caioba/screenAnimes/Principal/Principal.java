package br.com.caioba.screenAnimes.Principal;

import br.com.caioba.screenAnimes.model.Anime;
import br.com.caioba.screenAnimes.model.DadosAnimes;
import br.com.caioba.screenAnimes.model.DadosTemporada;
import br.com.caioba.screenAnimes.model.Episodios;
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

            Optional<Anime> anime = animes.stream()
                    .filter(a -> a.getTitulo().toLowerCase().contains(nomeAnime.toLowerCase()))
                    .findFirst();

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
}