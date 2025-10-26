package siga.artsoft.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import siga.artsoft.api.provincia.Provincia;
import siga.artsoft.api.provincia.ProvinciaService;
import siga.artsoft.api.sexo.Sexo;
import siga.artsoft.api.sexo.SexoService;

import java.util.List;

@RestController
@RequestMapping("/api/provincia")
public class ProvinciaController {

    @Autowired
    private ProvinciaService provinciaService;

    @GetMapping
    public List<Provincia> getAllProvincia() {
        return provinciaService.getAllProvincia();
    }
}
