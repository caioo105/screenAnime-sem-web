package br.com.caioba.screenAnimes.Principal;

import br.com.caioba.screenAnimes.model.Anime;
import br.com.caioba.screenAnimes.model.DadosAnimes;
import br.com.caioba.screenAnimes.model.DadosTemporada;
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

        private DadosAnimes getDadosAnime() {
            System.out.println("Digite o nome do Anime para busca");
            var nomeAnime = leitura.nextLine();
            var json = consumo.obterDados(ENDERECO + nomeAnime.replace(" ", "+") + API_KEY);
            DadosAnimes dados = converte.obterDados(json, DadosAnimes.class);
            return dados;
        }

        private void buscarEpisodioPorAnime(){
            DadosAnimes dadosAnime = getDadosAnime();
            List<DadosTemporada> temporadas = new ArrayList<>();

            for (int i = 1; i <= dadosAnime.totalTemporadas(); i++) {
                var json = consumo.obterDados(ENDERECO + dadosAnime.titulo().replace(" ", "+") + "&season=" + i + API_KEY);
                DadosTemporada dadosTemporada = converte.obterDados(json, DadosTemporada.class);
                temporadas.add(dadosTemporada);
            }
            temporadas.forEach(System.out::println);

    }

    private void listarAnimeBuscado(){
        List<Anime> animes = repositorio.findAll();
        animes.stream()
                .sorted(Comparator.comparing(Anime::getGenero))
                .forEach(System.out::println);
    }
}