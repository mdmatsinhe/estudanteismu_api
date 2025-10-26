package siga.artsoft.api.tipodocumentoidentificacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoDocumentoIdentificacaoService {

    @Autowired
    private TipoDocumentoIdentificacaoRepository tipoDocumentoIdentificacaoRepository;

    public List<TipoDocumentoIdentificacao> getAllTipoDocumentoIdentificacao(){
        return tipoDocumentoIdentificacaoRepository.findAll();
    }
}
