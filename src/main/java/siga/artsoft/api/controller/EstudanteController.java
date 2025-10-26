package siga.artsoft.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import siga.artsoft.api.estudante.*;


@RestController
@RequestMapping("/api/estudante")
public class EstudanteController {

    @Autowired
    private EstudanteRepository estudanteRepository;

    @Autowired
    private EstudanteService estudanteService;

    @GetMapping("/get/{user_id}")
    public ResponseEntity<?> buscarEstudante(@PathVariable(value = "user_id") long userId){

        var estudante=estudanteRepository.findEstudanteByUserLoginId(userId);
        return ResponseEntity.ok(new DadosListagemEstudante(estudante));
    }

    // Endpoint para atualizar um estudante usando EstudanteDTO
    @PutMapping("/{id}")
    public ResponseEntity<EstudanteDTO> updateEstudante(@PathVariable Long id, @RequestBody EstudanteDTO estudanteDTO) {
        Estudante estudanteAtualizado = estudanteService.updateEstudante(id, estudanteDTO);
        // Converte o Estudante atualizado para EstudanteDTO antes de retornar
        EstudanteDTO estudanteAtualizadoDTO = new EstudanteDTO(estudanteAtualizado);
        return ResponseEntity.ok(estudanteAtualizadoDTO);
    }
}
