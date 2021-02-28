package br.com.igorcarvalho.pdfgen.service.jasper;

import br.com.igorcarvalho.pdfgen.service.jasper.core.IReportGenerator;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
public class JasperReportService {
    public static File getReport(IReportGenerator reportGenerator) {
        File file = null;

        try {
            file = reportGenerator.generateReport().get();
        } catch (Exception e) {
            log.error("Falha ao receber o receber o Report", e);
        } finally {
            return file;
        }
    }
}
