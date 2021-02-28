package br.com.igorcarvalho.pdfgen.controller;

import br.com.igorcarvalho.pdfgen.service.jasper.JasperReportService;
import br.com.igorcarvalho.pdfgen.service.jasper.provider.MultaReportProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;

@RestController
@RequestMapping("${app.api.context}/report")
public class ReportsController {


    @Autowired
    MultaReportProvider multaReportProvider;

    @GetMapping
    public ResponseEntity<String> cadastrarMensagem(UriComponentsBuilder uriBuilder) {
        File report = JasperReportService.getReport(multaReportProvider);
        return ResponseEntity.ok().body(report.getAbsolutePath());
    }
}
