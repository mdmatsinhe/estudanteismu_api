package siga.artsoft.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/{id}/iniciar-verificacao-email")
    public ResponseEntity<?> iniciarAtualizacaoEmail(
            @PathVariable Long id,
            @RequestBody EstudantePrimeiroLoginDTO dadosAtualizacao) {

        System.out.println("=== Dados recebidos do front ===");
        System.out.println("Nome: " + dadosAtualizacao.getNome());
        System.out.println("Apelido: " + dadosAtualizacao.getApelido());
        System.out.println("Data Nascimento: " + dadosAtualizacao.getDataNascimento());
        System.out.println("SexoId: " + dadosAtualizacao.getSexoId());
        System.out.println("EstadoCivilId: " + dadosAtualizacao.getEstadoCivilId());
        System.out.println("Nuit: " + dadosAtualizacao.getNuit());
        System.out.println("Número Documento: " + dadosAtualizacao.getNumeroDocumentoIdentificacao());
        System.out.println("Tipo Documento Id: " + dadosAtualizacao.getTipoDocumentoIdentificacaoId());
        System.out.println("Email: " + dadosAtualizacao.getEmail());
        System.out.println("Telefone: " + dadosAtualizacao.getTelefone());
        System.out.println("NacionalidadeId: " + dadosAtualizacao.getNacionalidadeId());
        System.out.println("DistritoId: " + dadosAtualizacao.getDistritoId());
        System.out.println("=== Fim dos dados ===");

        try {
            Estudante estudanteEmVerificacao = estudanteService.iniciarAtualizacaoEmailEDataNascimento(id, dadosAtualizacao);

            return ResponseEntity.ok(estudanteEmVerificacao);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estudante> getEstudanteById(@PathVariable Long id) {
        Estudante estudante = estudanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudante não encontrado"));
        return ResponseEntity.ok(estudante);
    }
}
