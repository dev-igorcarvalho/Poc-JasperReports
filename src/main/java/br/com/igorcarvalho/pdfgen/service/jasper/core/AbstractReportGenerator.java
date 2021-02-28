package br.com.igorcarvalho.pdfgen.service.jasper.core;

import br.com.igorcarvalho.pdfgen.model.abstracts.AbstractEntity;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
public abstract class AbstractReportGenerator<R extends AbstractEntity> {

    /**
     * Resgitra o path para a pasta
     * dos templates a partir da pasta
     * resources do springboot.
     */
    @Value("${app.jasper.template.location}")
    private String jrxlmPath;

    /**
     * Resgitra o nome do parameter key
     * utilizado como fonte de dados no
     * arquivo jrxml que sera usado como
     * template.
     */
    @Value("${app.jasper.template.data-source}")
    private String dataSourceParamKey;

    /**
     * Recupera a lista de entidades aa serem usadas
     * no report de forma agnostica de metodologia.
     * A lista fornecida pode ser recupera ou gerada
     * do jeito que quem estiver implementando
     * desejar.
     */
    protected abstract List<R> getEntities();

    /**
     * Deve ser implementado retornando o nome
     * do arquivo jrxml que sera usado como
     * template.
     */
    protected abstract String setJasperTemplate();

    /**
     * Deve ser implementado retornando um
     * Hashmap com os as chaves tendo o mesmo
     * nome dos parametros criados no jrxml
     */
    protected abstract  Map<String, Object> setParametersMap();


    /**
     * Retorna o arquivo de template jrxml
     */
    private File getTemplateFile() {
        Optional<File> file = Optional.empty();
        try {
            file = Optional
                    .ofNullable(ResourceUtils.getFile(
                            String.format("classpath:%s%s.jrxml", jrxlmPath,
                                    setJasperTemplate()))
                    );
        } catch (FileNotFoundException ex) {
            log.error("Arquivo de template Jasper nao encontrado", ex);
        } finally {
            return file.orElseThrow(
                    () -> new ReportGeneratorException(
                            "Falha em AbstractReportGenerator::getTemplateFile()")
            );
        }
    }

    /**
     * Gera os objetos em memoria
     * a partir do template jrxml para
     * ser usado na api do JasperReports
     */
    private JasperReport generateTemplate() {
        File file = getTemplateFile();
        Optional<JasperReport> report = Optional.empty();
        try {
            report = Optional.ofNullable(
                    JasperCompileManager.compileReport(file.getAbsolutePath())
            );
        } catch (JRException e) {
            log.error("Não foi possível compilar o arquivo jaser report", e);
        } finally {
            return report.orElseThrow(
                    () -> new ReportGeneratorException(
                            "Falha em AbstractReportGenerator::generateTemplate()")
            );
        }
    }


    /**
     * Gera uma colletion utilizavel pelo
     * JasperReports a partir da lista de entidades
     * fornecidas.
     */
    private JRBeanCollectionDataSource getDataSource() throws ReportGeneratorException {
        final List<R> entities = getEntities();
        if (entities != null || !entities.isEmpty()) {
            return new JRBeanCollectionDataSource(entities);
        } else {
            throw new ReportGeneratorException(
                    "Falha em AbstractReportGenerator::getDataSource()");
        }
    }


    /**
     * Cria o JasperReport para PDF em bytecode
     */
    private byte[] getJasperReport() {
        Map<String, Object> parameters = setParametersMap();
        parameters.put(dataSourceParamKey, getDataSource());

        Optional<byte[]> byteCodeReport = Optional.empty();
        try {
            JasperPrint jasperPrint = JasperFillManager
                    .fillReport(generateTemplate(), parameters, new JREmptyDataSource());
            byteCodeReport =
                    Optional.ofNullable(JasperExportManager.exportReportToPdf(jasperPrint));
        } catch (JRException | ReportGeneratorException e) {
            log.error("Não foi possível executar a geração do Report");
        } finally {
            return byteCodeReport.orElseThrow(
                    () -> new ReportGeneratorException(
                            "Falha em AbstractReportGenerator::getJasperReport()")
            );
        }
    }

    /**
     * Retorna um arquivo temporario
     * com o PDF do JasperReport desejado
     * a apartir do ByteCode utilizado
     */
    protected File getPdfReport() {
        Optional<File> tempFile = Optional.empty();
        try {
            tempFile = Optional.ofNullable(
                    File.createTempFile(Instant.now().toString(), ".pdf", null)
            );
            FileOutputStream fos =
                    new FileOutputStream(tempFile.get());
            fos.write(getJasperReport());
            fos.close();
        } catch (IOException e) {
            log.error("Não foi possível gravar o arquivo", e);
        } finally {
            return tempFile.orElseThrow(
                    () -> new ReportGeneratorException("Falha em AbstractReportGenerator::getPdfReport()")
            );
        }
    }
}
