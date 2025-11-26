package siga.artsoft.api.delegacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DelegacaoService {

    @Autowired
    private DelegacaoRepository delegacaoRepository;

    public List<Delegacao> getAllDelegacao(){
        return delegacaoRepository.findAll();
    }
}
