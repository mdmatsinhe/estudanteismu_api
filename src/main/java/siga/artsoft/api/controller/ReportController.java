package siga.artsoft.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import siga.artsoft.api.estudante.Estudante;
import siga.artsoft.api.estudante.EstudanteRepository;
import siga.artsoft.api.reports.JasperReportUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/relatorios")
public class ReportController {

    @Autowired
    private JasperReportUtil jasperReportUtil;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private EstudanteRepository estudanteRepository;

    @PostMapping("/talao-pagamento")
    public ResponseEntity<byte[]> gerarTalaoPagamento(@RequestBody Map<String, Object> request) {
        Logger logger = LoggerFactory.getLogger(ReportController.class);

        try {
            logger.debug("Início da geração do relatório.");

            // Extrai o emolumentoId
            Integer emolumentoId = Integer.parseInt(request.get("id").toString());
            logger.debug("ID do emolumento recebido: {}", emolumentoId);

            // Parâmetros do relatório
            Map<String, Object> params = new HashMap<>();
            params.put("PAR_LOGO", getClass().getClassLoader().getResourceAsStream("pics/login_logo.jpg"));
            params.put("PAR_OPERADOR", "Operador Exemplo");
            params.put("PAR_EMOLUMENTOS", emolumentoId);
            Connection connection = dataSource.getConnection();
            // Geração do relatório
            byte[] pdf = jasperReportUtil.generateReport("/reports/Talao_pagamento.jrxml", params, connection);
            if (pdf == null || pdf.length == 0) {
                throw new IllegalStateException("O relatório gerado está vazio.");
            }

            logger.debug("Relatório gerado com sucesso.");

            // Retorno do relatório como PDF
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "talao_pagamento.pdf");

            return ResponseEntity.ok().headers(headers).body(pdf);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PostMapping("/boletim-inscricao")
    public ResponseEntity<byte[]> gerarBoletimInscricao(@RequestBody Map<String, Object> request) {
        Logger logger = LoggerFactory.getLogger(ReportController.class);

        try {
            logger.debug("Início da geração do relatório.");

            // Extrai o emolumentoId
            Integer estudanteId = Integer.parseInt(request.get("estudanteId").toString());
            Integer anoLectivo = Integer.parseInt(request.get("anoLectivo").toString());
          //  Integer semestre = Integer.parseInt(request.get("semestre").toString());
            Estudante estudante=estudanteRepository.getReferenceById(Long.valueOf(estudanteId));
            String codigoEntidade = "";

            if(estudante.getCurso().getFaculdade().getId()==2) {
                codigoEntidade="70924";
            }else if(estudante.getCurso().getFaculdade().getId()==1) {
                codigoEntidade="70920";
            }else if(estudante.getCurso().getFaculdade().getId()==3) {
                codigoEntidade="70923";
            }else if(estudante.getCurso().getFaculdade().getId()==4) {
                codigoEntidade="70922";
            }else if(estudante.getCurso().getFaculdade().getId()==5) {
                codigoEntidade="70921";
            }

            // Parâmetros do relatório
            Map<String, Object> params = new HashMap<>();
            params.put("PAR_LOGO", getClass().getClassLoader().getResourceAsStream("pics/logo_report.png"));
            params.put("PAR_ESTID", estudante.getId());
            params.put("PAR_ANOL", anoLectivo);
            params.put("entidade", codigoEntidade);
           // params.put("PAR_SEM", semestre);
            params.put("SUBREPORT_DIR", "reports/");
            params.put("PAR_OPERADOR", "Operador Exemplo");

            Connection connection = dataSource.getConnection();
            // Geração do relatório
            byte[] pdf = jasperReportUtil.generateReport("/reports/comprovativo_inscricao.jrxml", params, connection);
            if (pdf == null || pdf.length == 0) {
                throw new IllegalStateException("O relatório gerado está vazio.");
            }

            logger.debug("Relatório gerado com sucesso.");

            // Retorno do relatório como PDF
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "talao_pagamento.pdf");

            return ResponseEntity.ok().headers(headers).body(pdf);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PostMapping("/extrato")
    public ResponseEntity<byte[]> gerarExtrato(@RequestBody Map<String, Object> request) {
        Logger logger = LoggerFactory.getLogger(ReportController.class);

        try {
            logger.debug("Início da geração do relatório.");

            // Extrai o emolumentoId
            Integer estudanteId = Integer.parseInt(request.get("estudanteId").toString());
            Integer anoLectivo = Integer.parseInt(request.get("anoLectivo").toString());
            //  Integer semestre = Integer.parseInt(request.get("semestre").toString());
            Estudante estudante=estudanteRepository.getReferenceById(Long.valueOf(estudanteId));
            String codigoEntidade = "";

            if(estudante.getCurso().getFaculdade().getId()==2) {
                codigoEntidade="70924";
            }else if(estudante.getCurso().getFaculdade().getId()==1) {
                codigoEntidade="70920";
            }else if(estudante.getCurso().getFaculdade().getId()==3) {
                codigoEntidade="70923";
            }else if(estudante.getCurso().getFaculdade().getId()==4) {
                codigoEntidade="70922";
            }else if(estudante.getCurso().getFaculdade().getId()==5) {
                codigoEntidade="70921";
            }

            // Parâmetros do relatório
            Map<String, Object> params = new HashMap<>();
           // params.put("PAR_LOGO", getClass().getClassLoader().getResourceAsStream("pics/logo_report.png"));
            params.put("PAR_ESTUDANTE", estudante.getId());
            params.put("PAR_ANOLECTIVO", anoLectivo);
            params.put("PAR_ENTIDADE", codigoEntidade);
            // params.put("PAR_SEM", semestre);
            params.put("SUBREPORT_DIR", "reports/");
            params.put("PAR_OPERADOR", estudante.getNumero());

            Connection connection = dataSource.getConnection();
            // Geração do relatório
            byte[] pdf = jasperReportUtil.generateReport("/reports/Extrato_Financeiro.jrxml", params, connection);
            if (pdf == null || pdf.length == 0) {
                throw new IllegalStateException("O relatório gerado está vazio.");
            }

            logger.debug("Relatório gerado com sucesso.");

            // Retorno do relatório como PDF
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "talao_pagamento.pdf");

            return ResponseEntity.ok().headers(headers).body(pdf);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PostMapping("/recibo")
    public ResponseEntity<byte[]> gerarRecibo(@RequestBody Map<String, Object> request) {
        Logger logger = LoggerFactory.getLogger(ReportController.class);

        try {
            logger.debug("Início da geração do relatório.");

            // Extrai o emolumentoId
            Integer estudanteId = Integer.parseInt(request.get("estudanteId").toString());
            Long nrRecibo = Long.parseLong(request.get("recibo").toString());
            //  Integer semestre = Integer.parseInt(request.get("semestre").toString());
            Estudante estudante=estudanteRepository.getReferenceById(Long.valueOf(estudanteId));
            String codigoEntidade = "";

            if(estudante.getCurso().getFaculdade().getId()==2) {
                codigoEntidade="70923";
            }else if(estudante.getCurso().getFaculdade().getId()==1) {
                codigoEntidade="70920";
            }else if(estudante.getCurso().getFaculdade().getId()==3) {
                codigoEntidade="70924";
            }else if(estudante.getCurso().getFaculdade().getId()==4) {
                codigoEntidade="70922";
            }else if(estudante.getCurso().getFaculdade().getId()==5) {
                codigoEntidade="70921";
            }

            // Parâmetros do relatório
            Map<String, Object> params = new HashMap<>();
            // params.put("PAR_LOGO", getClass().getClassLoader().getResourceAsStream("pics/logo_report.png"));
           // params.put("PAR_ESTUDANTE", estudante.getId());
            //params.put("PAR_ANOLECTIVO", anoLectivo);
            params.put("PAR_ENTIDADE", codigoEntidade);
            params.put("PAR_RECIBO", nrRecibo);
            params.put("SUBREPORT_DIR", "reports/");
            params.put("PAR_OPERADOR", estudante.getNumero()+"");

            Connection connection = dataSource.getConnection();
            // Geração do relatório
            byte[] pdf = jasperReportUtil.generateReport("/reports/recibo_woodrose.jrxml", params, connection);
            if (pdf == null || pdf.length == 0) {
                throw new IllegalStateException("O relatório gerado está vazio.");
            }

            logger.debug("Relatório gerado com sucesso.");

            // Retorno do relatório como PDF
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "talao_pagamento.pdf");

            return ResponseEntity.ok().headers(headers).body(pdf);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

}
