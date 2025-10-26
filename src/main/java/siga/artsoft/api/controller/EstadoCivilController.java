package siga.artsoft.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import siga.artsoft.api.estadocivil.EstadoCivil;
import siga.artsoft.api.estadocivil.EstadoCivilService;

import java.util.List;

@RestController
@RequestMapping("/api/estado-civil")
public class EstadoCivilController {

    @Autowired
    private EstadoCivilService estadoCivilService;

    @GetMapping
    public List<EstadoCivil> getAllEstadoCivil() {
        return estadoCivilService.getAllEstadoCivil();
    }
}
