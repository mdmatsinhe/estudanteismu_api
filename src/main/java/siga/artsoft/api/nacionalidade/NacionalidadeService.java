package siga.artsoft.api.nacionalidade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NacionalidadeService {

    @Autowired
    private NacionalidadeRepository nacionalidadeRepository;

    public List<Nacionalidade> getAllNacionalidade() {
        return nacionalidadeRepository.findAll();
    }
}
