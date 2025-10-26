package siga.artsoft.api.reports;

import net.sf.jasperreports.engine.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import siga.artsoft.api.controller.ReportController;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Map;

@Component
public class JasperReportUtil {
    public JasperReportUtil() {
    }

    public byte[] generateReport(String reportPath, Map<String, Object> parameters, Connection connection) throws JRException {
        InputStream reportStream = this.getClass().getResourceAsStream(reportPath);
        Logger logger = LoggerFactory.getLogger(ReportController.class);
        if (reportStream == null) {
            throw new IllegalArgumentException("Relatório não encontrado no caminho especificado: " + reportPath);
        } else {
            logger.debug("Arquivo de relatório encontrado: {}", reportPath);
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            logger.debug("Relatório compilado com sucesso.");
            JasperPrint jasperPrint = null;

            try {
                jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
                logger.debug("Relatório preenchido com sucesso. Páginas no relatório: {}", jasperPrint.getPages().size());
            } catch (Exception var9) {
                logger.error("Erro ao preencher o relatório: ", var9);
                throw var9;
            }

            return JasperExportManager.exportReportToPdf(jasperPrint);
        }
    }
}
