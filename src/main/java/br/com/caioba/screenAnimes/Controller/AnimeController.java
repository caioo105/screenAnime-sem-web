package br.com.caioba.screenAnimes.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnimeController {
    @GetMapping("/animes")
    public String obterAnimes(){
        return "Aqui vai ser listados todos os animes";
    }
}
