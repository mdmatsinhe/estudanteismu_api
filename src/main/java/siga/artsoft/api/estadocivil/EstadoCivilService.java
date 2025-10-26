package siga.artsoft.api.estadocivil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoCivilService {
    @Autowired
    private EstadoCivilRepository estadoCivilRepository;

    public List<EstadoCivil> getAllEstadoCivil() {
        return estadoCivilRepository.findAll();
    }
}
