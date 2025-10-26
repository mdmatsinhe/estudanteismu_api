package siga.artsoft.api.sexo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SexoService {

    @Autowired
    private SexoRepository sexoRepository;

    public List<Sexo> getAllSexo(){
        return sexoRepository.findAll();
    }
}
