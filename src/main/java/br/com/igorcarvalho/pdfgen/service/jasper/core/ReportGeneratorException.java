package br.com.igorcarvalho.pdfgen.service.jasper.core;

public class ReportGeneratorException extends RuntimeException {
    public ReportGeneratorException(String message) {
        super(message);
    }
    public ReportGeneratorException(String message, Throwable cause) {
        super(message, cause);
    }
}
