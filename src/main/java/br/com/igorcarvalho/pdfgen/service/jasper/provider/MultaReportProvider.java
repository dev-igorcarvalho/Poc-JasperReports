package br.com.igorcarvalho.pdfgen.service.jasper.provider;

import br.com.igorcarvalho.pdfgen.model.entity.MultaEntity;
import br.com.igorcarvalho.pdfgen.service.jasper.core.AbstractReportGenerator;
import br.com.igorcarvalho.pdfgen.service.jasper.core.IReportGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;

@Slf4j
@Component
public class MultaReportProvider
        extends AbstractReportGenerator<MultaEntity>
        implements IReportGenerator {

    @Override
    protected List<MultaEntity> getEntities() {
        List<MultaEntity> result = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            final MultaEntity multa = new MultaEntity();
            final long now = System.currentTimeMillis();
            multa.setId(now);
            multa.setAtividade("Atividade: " + now);
            multa.setAutoInfracao("Infração: " + now);
            multa.setCodigoProtocolo("Protocolo: " + now);
            result.add(multa);
        }
        return result;

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
        parameters.put("assunto", "Teste com List<ProcessoVO>");
        return parameters;
    }

    @Override
    public Optional<File> generateReport() {
        return Optional.of(getPdfReport());
    }
}
