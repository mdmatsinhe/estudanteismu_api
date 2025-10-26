package siga.artsoft.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import siga.artsoft.api.estadocivil.EstadoCivil;
import siga.artsoft.api.estadocivil.EstadoCivilService;
import siga.artsoft.api.sexo.Sexo;
import siga.artsoft.api.sexo.SexoService;

import java.util.List;

@RestController
@RequestMapping("/api/sexo")
public class SexoController {

    @Autowired
    private SexoService sexoService;

    @GetMapping
    public List<Sexo> getAllSexo() {
        return sexoService.getAllSexo();
    }
}
