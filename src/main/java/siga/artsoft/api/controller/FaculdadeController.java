package siga.artsoft.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import siga.artsoft.api.faculdade.Faculdade;
import siga.artsoft.api.faculdade.FaculdadeService;

import java.util.List;

@RestController
@RequestMapping("/api/faculdade")
public class FaculdadeController {

    @Autowired
    private FaculdadeService faculdadeService;

    @GetMapping("/delegacao/{delegacaoId}")
    public List<Faculdade> getByDelegacao(@PathVariable Long delegacaoId) {
        return faculdadeService.findByDelegacaoId(delegacaoId);
    }
}
