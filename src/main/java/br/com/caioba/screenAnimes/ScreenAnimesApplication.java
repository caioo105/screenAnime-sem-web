package br.com.caioba.screenAnimes;

import br.com.caioba.screenAnimes.model.DadosAnimes;
import br.com.caioba.screenAnimes.service.ConsumoApi;
import br.com.caioba.screenAnimes.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenAnimesApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenAnimesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumoApi = new ConsumoApi();
		var json = consumoApi.obterDados("https://www.omdbapi.com/?t=Dragon+ball+z&apikey=6585022c");
		//System.out.println(json);
		//json = consumoApi.obterDados("https://coffee.alexflipnote.dev/random.json");
		System.out.println(json);
		ConverteDados conversor = new ConverteDados();
		DadosAnimes dados = conversor.obterDados(json, DadosAnimes.class);
		System.out.println(dados);

	}
}
