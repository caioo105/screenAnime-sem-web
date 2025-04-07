package br.com.caioba.screenAnimes;

import br.com.caioba.screenAnimes.Principal.Principal;
import br.com.caioba.screenAnimes.model.DadosAnimes;
import br.com.caioba.screenAnimes.model.DadosEpisodios;
import br.com.caioba.screenAnimes.model.DadosTemporada;
import br.com.caioba.screenAnimes.repository.Animerepository;
import br.com.caioba.screenAnimes.service.ConsumoApi;
import br.com.caioba.screenAnimes.service.ConverteDados;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenAnimesApplication{

	public static void main(String[] args) {
		SpringApplication.run(ScreenAnimesApplication.class, args);
	}
}


