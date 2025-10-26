package siga.artsoft.api.provincia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinciaService {

    @Autowired
    private ProvinciaRepository provinciaRepository;

    public List<Provincia> getAllProvincia(){
        return provinciaRepository.findAll();
    }
}
