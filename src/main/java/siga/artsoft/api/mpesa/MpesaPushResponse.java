package siga.artsoft.api.mpesa;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class MpesaPushResponse {
    private String responseCode;
    private String responseDescription;
    private String conversationId;
    private String merchantRequestId;
    private String thirdPartyReference;
    private String transactionId;

    public MpesaPushResponse(String responseCode, String responseDescription, String conversationId,
                             String merchantRequestId, String thirdPartyReference,  String transactionId) {
        this.responseCode = responseCode;
        this.responseDescription = responseDescription;
        this.conversationId = conversationId;
        this.merchantRequestId = merchantRequestId;
        this.thirdPartyReference = thirdPartyReference;
        this.transactionId = transactionId;
    }

    // Getters e setters
    public String getResponseCode() { return responseCode; }
    public void setResponseCode(String responseCode) { this.responseCode = responseCode; }
    public String getResponseDescription() { return responseDescription; }
    public void setResponseDescription(String responseDescription) { this.responseDescription = responseDescription; }
    public String getConversationId() { return conversationId; }
    public void setConversationId(String conversationId) { this.conversationId = conversationId; }
    public String getMerchantRequestId() { return merchantRequestId; }
    public void setMerchantRequestId(String merchantRequestId) { this.merchantRequestId = merchantRequestId; }
    public String getThirdPartyReference() { return thirdPartyReference; }
    public void setThirdPartyReference(String thirdPartyReference) { this.thirdPartyReference = thirdPartyReference; }

}
