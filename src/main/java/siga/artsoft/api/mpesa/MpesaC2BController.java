package siga.artsoft.api.mpesa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import siga.artsoft.api.contacorrente.ContaCorrente;
import siga.artsoft.api.contacorrente.ContaCorrenteService;
import siga.artsoft.api.user.User;
import siga.artsoft.api.user.UserRepository;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/mpesa")
@Slf4j
public class MpesaC2BController {

    private final MpesaC2BService service;
    @Autowired
    private ContaCorrenteService contaCorrenteService;

    @Autowired
    private MpesaC2BService mpesaC2BService;

    @Autowired
    private UserRepository userRepository;

    public MpesaC2BController(MpesaC2BService service) {
        this.service = service;
    }

    @PostMapping("/c2b/pay")
    public Object pay(@RequestBody Map<String, String> request) {

        return service.pagar(
                request.get("msisdn"),
                request.get("amount"),
                request.get("reference")
        );
    }

    @PostMapping("/pagar")
    public MpesaPushResponse iniciarPagamento(@RequestBody MpesaPagamentoRequest request) {
        return contaCorrenteService.iniciarPagamentoMpesa(request.getContaId(), request.getMsisdn());

    }

//    @PostMapping("/confirmar")
//    public ResponseEntity<?> confirmarPagamento(
//            @RequestParam String thirdPartyReference,
//            @RequestParam String transactionId,
//            @RequestParam BigDecimal valorPago,
//            @RequestParam Long userId
//    ) {
//        try {
//            Optional<User> user = userRepository.findById(userId);
//
//            contaCorrenteService.confirmarPagamentoMpesa(thirdPartyReference, transactionId, valorPago, user);
//
//            return ResponseEntity.ok("Pagamento confirmado com sucesso.");
//
//        } catch (IllegalArgumentException | IllegalStateException e) {
//            log.error("Erro ao confirmar pagamento M-Pesa", e);
//            return ResponseEntity.badRequest().body(e.getMessage());
//        } catch (Exception e) {
//            log.error("Erro inesperado ao confirmar pagamento M-Pesa", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Erro interno ao confirmar pagamento.");
//        }
//    }

    @PostMapping("/callback")
    public ResponseEntity<String> mpesaCallback(@RequestBody Map<String, String> payload) {
        String thirdPartyReference = payload.get("thirdPartyReference");
        String transactionId = payload.get("conversationId");

        Optional<ContaCorrente> contaOpt = contaCorrenteService.getContaPorReferencia(thirdPartyReference);
        if (contaOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Transação não encontrada: " + thirdPartyReference);
        }

        BigDecimal valorPago = contaOpt.get().getTotalDebito();

        User user = userRepository.findById(1L)
                .orElseThrow(() -> new IllegalStateException("Usuário padrão não encontrado"));

        try {
            contaCorrenteService.confirmarPagamentoMpesa(thirdPartyReference, transactionId, valorPago, Optional.of(user));
            return ResponseEntity.ok("Pagamento confirmado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao confirmar pagamento: " + e.getMessage());
        }
    }

}

