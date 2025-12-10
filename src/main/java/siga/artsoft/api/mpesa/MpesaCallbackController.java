package siga.artsoft.api.mpesa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import siga.artsoft.api.contacorrente.ContaCorrenteService;
import siga.artsoft.api.user.User;
import siga.artsoft.api.user.UserRepository;
import siga.artsoft.api.user.UserService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/mpesa")
public class MpesaCallbackController {

    @Autowired
    private ContaCorrenteService contaCorrenteService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @PostMapping("/test")
    public ResponseEntity<String> receberCallback(@RequestBody Map<String, Object> payload) {
        try {
            String thirdPartyReference = (String) payload.get("output_ThirdPartyReference");
            String transactionId = (String) payload.get("output_TransactionID");
            BigDecimal valorPago = new BigDecimal((String) payload.get("amount"));
            Optional<User> user = userRepository.findById(1L);
            //Optional<User> user = userService.getSystemUser();

            contaCorrenteService.confirmarPagamentoMpesa(thirdPartyReference, transactionId, valorPago, user);

            return ResponseEntity.ok("Pagamento confirmado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: " + e.getMessage());
        }
    }
}
