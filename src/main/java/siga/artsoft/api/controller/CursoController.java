package siga.artsoft.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import siga.artsoft.api.curso.Curso;
import siga.artsoft.api.curso.CursoService;

import java.util.List;

@RestController
@RequestMapping("/api/curso")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping("/faculdade/{faculdadeId}")
    public List<Curso> getByFaculdade(@PathVariable Long faculdadeId) {
        return cursoService.findByFaculdadeId(faculdadeId);
    }
}
