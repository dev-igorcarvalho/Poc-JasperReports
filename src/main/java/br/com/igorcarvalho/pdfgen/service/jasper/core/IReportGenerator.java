package br.com.igorcarvalho.pdfgen.service.jasper.core;

import java.io.File;
import java.util.Optional;

public interface IReportGenerator {
    Optional<File> generateReport();
}
