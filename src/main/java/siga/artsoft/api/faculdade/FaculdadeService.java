package siga.artsoft.api.faculdade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FaculdadeService {

    @Autowired
    private FaculdadeRepository faculdadeRepository;

    public List<Faculdade> findByDelegacaoId(Long delegacaoId){
        return faculdadeRepository.findFaculdadeByDelegacaoId(delegacaoId);
    }
}
