package siga.artsoft.api.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import siga.artsoft.api.contacorrente.*;
import siga.artsoft.api.estudante.Estudante;
import siga.artsoft.api.estudante.EstudanteService;
import siga.artsoft.api.inscricao.Inscricao;
import siga.artsoft.api.pauta.Pauta;
import siga.artsoft.api.pauta.PautaService;
import siga.artsoft.api.user.User;
import siga.artsoft.api.user.UserService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/contacorrente")
public class ContaCorrenteController {

    @Autowired
    private ContaCorrenteRepository contaCorrenteRepository;

    @Autowired
    private ContaCorrenteService contaCorrenteService;

    @Autowired
    private EstudanteService estudanteService;

    @Autowired
    private PautaService pautaService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> listarDebitos(@RequestBody @Valid DadosPesquisaContaCorrente dados){

        var lista= contaCorrenteRepository.escolherContaCorrentePorEstudanteAnoLectivoSemestre(dados.idEstudante(),dados.anoLectivo(),dados.semestre()).stream().map(DadosListagemContaCorrente::new);

        return ResponseEntity.ok(lista);
    }

    @PostMapping("/gerar-referencia")
    public ResponseEntity<ListarReferenciaRecorrenciaDTO> gerarReferencia(@RequestBody GerarReferenciaRecorrenciaDTO requestDTO) {
        ListarReferenciaRecorrenciaDTO responseDTO = null;
        try {
            Optional<Estudante> estudante = estudanteService.findById(requestDTO.getIdEstudante());
            Optional<Pauta> pauta = pautaService.findById(requestDTO.getIdPauta());

            Optional<User> user = userService.findById(requestDTO.getIdUser());

            ContaCorrente referencia = contaCorrenteService.gerarReferencia(estudante, pauta, user, requestDTO.getAnoLectivo(), requestDTO.getSemestre());

            // Converter para DTO
            responseDTO = new ListarReferenciaRecorrenciaDTO();
            responseDTO.setId(referencia.getId());
            responseDTO.setDebito(referencia.getDebito());
            responseDTO.setTotalDebito(referencia.getTotalDebito());
            responseDTO.setTipoEmolumento(referencia.getTipoEmolumento());
            responseDTO.setSituacao(referencia.getSituacao());
            responseDTO.setReferencia(referencia.getReferencia());
            responseDTO.setPrazo(referencia.getDataLimitePagamento());

            return ResponseEntity.ok(responseDTO);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
            System.out.println(responseDTO);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @GetMapping("/referencias-recorrencia")
    public ResponseEntity<List<ListarReferenciaRecorrenciaDTO>> listarReferenciasRecorrenciaPendentes(
            @RequestParam Long idEstudante,
            @RequestParam int anoLectivo,
            @RequestParam int semestre) {
        try {
            Optional<Estudante> estudante = estudanteService.findById(idEstudante);
            List<ContaCorrente> referencias = contaCorrenteService.listarReferenciasRecorrenciaPendentes(estudante, anoLectivo, semestre);

            // Converter para DTOs
            List<ListarReferenciaRecorrenciaDTO> responseDTOs = referencias.stream().map(referencia -> {
                ListarReferenciaRecorrenciaDTO dto = new ListarReferenciaRecorrenciaDTO();
                dto.setId(referencia.getId());
                dto.setDebito(referencia.getDebito());
                dto.setTotalDebito(referencia.getTotalDebito());
                dto.setTipoEmolumento(referencia.getTipoEmolumento());
                dto.setSituacao(referencia.getSituacao());
                dto.setReferencia(referencia.getReferencia());
                dto.setPrazo(referencia.getDataLimitePagamento());
                return dto;
            }).collect(Collectors.toList());

            return ResponseEntity.ok(responseDTOs);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }
    }

    @PostMapping("/verificarDividaTotal")
    public ResponseEntity<DadosDividaEstudanteDTO> verificarDividaTotal(@RequestBody @Valid VerificarDividaDTO dados) {
        try {
            DadosDividaEstudanteDTO resultado = contaCorrenteService.verificarDividaTotalEstudante(dados.getIdEstudante());
            return ResponseEntity.ok(resultado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
