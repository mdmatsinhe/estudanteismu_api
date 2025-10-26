package siga.artsoft.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import siga.artsoft.api.provincia.Provincia;
import siga.artsoft.api.provincia.ProvinciaService;
import siga.artsoft.api.tipodocumentoidentificacao.TipoDocumentoIdentificacao;
import siga.artsoft.api.tipodocumentoidentificacao.TipoDocumentoIdentificacaoService;

import java.util.List;

@RestController
@RequestMapping("/api/tipo-documento-identificacao")
public class TipoDocumentoIdentificacaoController {

    @Autowired
    private TipoDocumentoIdentificacaoService tipoDocumentoIdentificacaoService;

    @GetMapping
    public List<TipoDocumentoIdentificacao> getAllTipoDocumentoIdentificacao() {
        return tipoDocumentoIdentificacaoService.getAllTipoDocumentoIdentificacao();
    }
}
