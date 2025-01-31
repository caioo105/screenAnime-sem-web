package br.com.caioba.screenAnimes;

import br.com.caioba.screenAnimes.model.DadosAnimes;
import br.com.caioba.screenAnimes.model.DadosEpisodios;
import br.com.caioba.screenAnimes.model.DadosTemporada;
import br.com.caioba.screenAnimes.service.ConsumoApi;
import br.com.caioba.screenAnimes.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenAnimesApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenAnimesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumoApi = new ConsumoApi();
		var json = consumoApi.obterDados("https://www.omdbapi.com/?t=Dragon+ball+z&apikey=6585022c");

		System.out.println(json);
		ConverteDados conversor = new ConverteDados();
		DadosAnimes dados = conversor.obterDados(json, DadosAnimes.class);
		System.out.println(dados);
		json = consumoApi.obterDados("https://www.omdbapi.com/?t=Dragon+ball+z&season=1&episode=2&apikey=6585022c");
		DadosEpisodios dadosEpi = conversor.obterDados(json, DadosEpisodios.class);
		System.out.println(dadosEpi);

		List<DadosTemporada> temporadas = new ArrayList<>();

		for(int i = 1; i <= dados.totalTemporadas(); i++){
			json = consumoApi.obterDados("https://www.omdbapi.com/?t=Dragon+ball+z&season=" + i + "&apikey=6585022c");
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
		temporadas.forEach(System.out::println);
	}
}
