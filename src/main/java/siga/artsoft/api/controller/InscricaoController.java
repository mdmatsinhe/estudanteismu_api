// No seu InscricaoController
package siga.artsoft.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import siga.artsoft.api.contacorrente.ContaCorrenteService;
import siga.artsoft.api.emolumento.Emolumento;
import siga.artsoft.api.emolumento.EmolumentoService;
import siga.artsoft.api.estudante.Estudante;
import siga.artsoft.api.inscricao.*;
import siga.artsoft.api.sessaoturma.SessaoTurma;

import java.util.Date;
import java.util.HashMap;
import java.util.List; // Importar List
import java.util.Map;
import java.util.stream.Collectors; // Importar Collectors

@RestController
@RequestMapping("/api/inscricao")
public class InscricaoController {

    @Autowired
    private InscricaoRepository inscricaoRepository;

    @Autowired
    private InscricaoService inscricaoService;

    @Autowired
    private ContaCorrenteService contaCorrenteService;

    @PostMapping
    public ResponseEntity<?> listarDisciplinasInscritas(@RequestBody @Valid DadosPesquisaInscricao dados){
        List<DadosListagemInscricao> lista = inscricaoRepository
                .escolherInscricaoPorEstudanteAnoLectivoSemestre(
                        dados.idEstudante(), dados.anoLectivo(), dados.semestre()
                )
                .stream()
                .map(DadosListagemInscricao::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(lista); // <<< Sempre retorna JSON, mesmo se vazio []
    }


        @PostMapping("/nova")
        public ResponseEntity<?> registrarInscricao(
                @RequestBody List<InscricaoRequest> inscricoes) {
            try {
                inscricaoService.registrarInscricao(inscricoes);
                return ResponseEntity.ok(inscricoes); // Retorna a lista original se bem-sucedida
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Erro ao registrar inscrições: " + e.getMessage());
            }
        }

    @GetMapping("/verificar-inscricao")
    public ResponseEntity<Boolean> verificarInscricao(
            @RequestParam Long estudanteId,
            @RequestParam int anoLectivo,
            @RequestParam int semestre) {
        boolean inscrito = inscricaoService.isEstudanteInscrito(estudanteId, anoLectivo, semestre);
        return ResponseEntity.ok(inscrito);
    }

    @PostMapping("/gerar-planos")
    public ResponseEntity<Map<String, String>> gerarPlanoFinanceiro(@RequestBody List<InscricaoRequest> inscricoes) {
        Map<String, String> response = new HashMap<>();

        if (inscricoes == null || inscricoes.isEmpty()) {
            response.put("mensagem", "Nenhuma inscrição fornecida.");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            System.out.println(inscricoes.size()+" disciplinas");
            contaCorrenteService.gerarPlanoFinanceiro(inscricoes);
            response.put("mensagem", "Plano financeiro gerado com sucesso.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.put("mensagem","Erro ao gerar plano financeiro: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}