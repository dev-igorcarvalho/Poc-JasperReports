package br.com.igorcarvalho.pdfgen.service.jasper.provider;

import br.com.igorcarvalho.pdfgen.model.entity.MultaEntity;
import br.com.igorcarvalho.pdfgen.service.jasper.core.AbstractReportGenerator;
import br.com.igorcarvalho.pdfgen.service.jasper.core.IReportGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import br.com.igorcarvalho.pdfgen.repository.MultaRepository;

import java.io.File;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class MultaReportProvider
        extends AbstractReportGenerator<MultaEntity>
        implements IReportGenerator {

    private final MultaRepository repository;

    @Override
    protected List<MultaEntity> getEntities() {
        return repository.findAll();
    }

    @Override
    protected String jasperTemplate() {
        return "PocReport";
    }

    @Override
    protected Map<String, Object> parametersMap() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("org", "IgorCarvalho");
        parameters.put("titulo", "Jasper to pdf POC");
        parameters.put("assunto", "Listagem de Multas");
        return parameters;
    }

    @Override
    public Optional<File> generateReport() {
        return Optional.of(getPdfReport());
    }
}
