package siga.artsoft.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import siga.artsoft.api.delegacao.Delegacao;
import siga.artsoft.api.delegacao.DelegacaoService;

import java.util.List;

@RestController
@RequestMapping("/api/delegacao")
public class DelegacaoController {

    @Autowired
    private DelegacaoService delegacaoService;

    @GetMapping
    public List<Delegacao> getAllDelegacao() {
       return delegacaoService.getAllDelegacao();
    }
}
