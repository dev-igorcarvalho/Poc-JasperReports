package br.com.igorcarvalho.pdfgen.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${app.api.context}/teste")
public class AppController {

    /**
     * Pega dados de um variavel
     * do arquivo de configuração
     * */
    @Value("${app.message}")
    private String appMessage;

    /**
     * Pega dados de um variavel de
     * ambiente do seu OS, caso
     * seja nula usa o valor default
     * passado após a vígurla
     * */
    @Value("${ENV_DB_URL:NENHUMA}")
    private String dbEnvironmentVariable;

    @GetMapping
    public String welcome() {
        return appMessage;
    }

    @GetMapping("/env-variable")
    public String getEnvironmentVariavle() {
        return "Enviroment variable test: " + dbEnvironmentVariable;
    }
}
