package siga.artsoft.api.distrito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistritoService {

    @Autowired
    private DistritoRepository distritoRepository;

    public List<Distrito> findByProvinciaId(Long provinciaId){
        return distritoRepository.findByProvinciaId(provinciaId);
    }
}
