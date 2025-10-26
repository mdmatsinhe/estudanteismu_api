package siga.artsoft.api.emolumento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import siga.artsoft.api.tipoemolumento.TipoEmolumento;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class EmolumentoService {

    @Autowired
    private EmolumentoRepository emolumentoRepository;

    public Optional<Emolumento> findById(long l) {
        return emolumentoRepository.findById(l);
    }

    public Map<String, Emolumento> carregarEmolumentos() {
        Map<String, Emolumento> emolumentosMap = new HashMap<>();

        for (Emolumento emolumento : emolumentoRepository.findAll()) {
            emolumentosMap.put(emolumento.getTipoEmolumento().getAbreviatura(), emolumento);
        }
        return emolumentosMap;
    }
}
