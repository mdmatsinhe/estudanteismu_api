package siga.artsoft.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import siga.artsoft.api.distrito.Distrito;
import siga.artsoft.api.distrito.DistritoService;
import siga.artsoft.api.nacionalidade.Nacionalidade;
import siga.artsoft.api.nacionalidade.NacionalidadeService;

import java.util.List;

@RestController
@RequestMapping("/api/distrito")
public class DistritoController {

    @Autowired
    private DistritoService distritoService;

    @GetMapping("/provincia/{provinciaId}")
    public List<Distrito> getbyProvincia(@PathVariable Long provinciaId) {
        return distritoService.findByProvinciaId(provinciaId);
    }
}
