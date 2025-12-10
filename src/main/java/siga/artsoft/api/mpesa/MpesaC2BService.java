package siga.artsoft.api.mpesa;

import com.fc.sdk.APIContext;
import com.fc.sdk.APIRequest;
import com.fc.sdk.APIResponse;
import com.fc.sdk.APIMethodType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class MpesaC2BService {

    @Value("${mpesa.apiKey}")
    private String apiKey;

    @Value("${mpesa.publicKey}")
    private String publicKey;

    @Value("${mpesa.ssl}")
    private boolean ssl;

    @Value("${mpesa.method}")
    private String method;

    @Value("${mpesa.host}")
    private String host;

    @Value("${mpesa.port}")
    private int port;

    @Value("${mpesa.path}")
    private String path;

    @Value("${mpesa.serviceProviderCode}")
    private String spCode;

    public APIResponse pagar(String msisdn, String amount, String reference) {

        log.info("📥 Iniciando pagamento M-Pesa C2B");
        log.info("➡️ MSISDN: {}", msisdn);
        log.info("➡️ Amount: {}", amount);

        // Garantir TransactionReference válido (1-20 chars)
        String transactionRef = (reference != null ? reference : "INV-" + System.currentTimeMillis());
        if (transactionRef.length() > 20) {
            transactionRef = transactionRef.substring(0, 20);
        }
        log.info("➡️ TransactionReference: {}", transactionRef);

        // Criar ThirdPartyReference único
        String thirdPartyRef = "TP" + System.currentTimeMillis() % 1000000;
        log.info("➡️ ThirdPartyReference: {}", thirdPartyRef);

        APIContext context = new APIContext();
        context.setApiKey(apiKey);
        context.setPublicKey(publicKey);
        context.setSsl(ssl);
        context.setMethodType(APIMethodType.valueOf(method));
        context.setAddress(host);
        context.setPort(port);
        context.setPath(path);

        // Headers
        context.addHeader("Origin", "developer.mpesa.vm.co.mz");

        // Parâmetros
        context.addParameter("input_TransactionReference", transactionRef);
        context.addParameter("input_CustomerMSISDN", msisdn);
        context.addParameter("input_Amount", amount);
        context.addParameter("input_ThirdPartyReference", thirdPartyRef);
        context.addParameter("input_ServiceProviderCode", spCode);

        // Logs
        log.info("🌍 Endpoint: {}:{}{}", host, port, path);
        log.info("🌐 Headers: Origin=developer.mpesa.vm.co.mz, Authorization=Bearer <token>");
        log.info("📦 Payload preparado para M-Pesa C2B");

        try {
            APIRequest request = new APIRequest(context);
            APIResponse response = request.execute();

            if (response == null) {
                log.error("❌ ERRO: resposta do M-Pesa veio NULL. Algo falhou no SDK ou na autenticação!");
                return null;
            }

            log.info("📥 Resposta recebida do M-Pesa:");
            log.info("➡️ Status: {} - {}", response.getStatusCode(), response.getReason());
            log.info("➡️ Result: {}", response.getResult());
            log.info("➡️ Params: {}", response.getParameters());

            return response;

        } catch (Exception e) {
            log.error("❌ Exceção ao executar chamada C2B", e);
            return null;
        }
    }

    public MpesaPushResponse enviarSTKPush(String msisdn, String amount, String reference, String thirdPartyReference) {

        APIResponse response = this.pagar(msisdn, amount, reference);

        if (response == null) {
            throw new RuntimeException("Falha ao enviar STK Push.");
        }

        return new MpesaPushResponse(
                (String) response.getParameters().get("output_ResponseCode"),
                (String) response.getParameters().get("output_ResponseDesc"),
                (String) response.getParameters().get("output_ConversationID"),
                null, // MerchantRequestId, se não houver, pode deixar null
                thirdPartyReference,
                (String) response.getParameters().get("output_TransactionID")
        );
    }

}
