package br.com.igorcarvalho.pdfgen.controller;

import br.com.igorcarvalho.pdfgen.model.dto.response.ServerStatusResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import br.com.igorcarvalho.pdfgen.repository.MultaRepository;

@RestController
@RequestMapping("${app.api.context}/status")
public class ServerStatusController {

    /**
     * Pega dados de um variavel
     * do arquivo de configuração
     */
    @Value("${app.profile}")
    private String enviroment;

    @Value("${spring.application.name}")
    private String appName;

    /**
     * Pega dados de um variavel de
     * ambiente do seu OS, caso
     * seja nula usa o valor default
     * passado após a vígurla
     */
    @Value("${ENV_DB_URL:NENHUMA}")
    private String dbEnvironmentVariable;

    @GetMapping("/env-variable")
    public String getEnvironmentVariavle() {
        return "Enviroment variable test: " + dbEnvironmentVariable;
    }

    @GetMapping
    public ResponseEntity<ServerStatusResponseDto> cadastrarMensagem(UriComponentsBuilder uriBuilder) {
        ServerStatusResponseDto status =
                ServerStatusResponseDto.builder()
                        .appName(appName)
                        .status("Online")
                        .profile(enviroment)
                        .build();
        return ResponseEntity.ok().body(status);
    }
}
