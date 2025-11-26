package siga.artsoft.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import siga.artsoft.api.nacionalidade.Nacionalidade;
import siga.artsoft.api.nacionalidade.NacionalidadeService;

import java.util.List;

@RestController
@RequestMapping("/api/nacionalidade")
public class NacionalidadeController {

    @Autowired
    private NacionalidadeService nacionalidadeService;

    @GetMapping
    public List<Nacionalidade> getAllNacionalidade() {
        return nacionalidadeService.getAllNacionalidade();
    }
}
