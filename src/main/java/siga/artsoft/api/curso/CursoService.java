package siga.artsoft.api.curso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import siga.artsoft.api.utils.AreaCurso;

import java.util.List;
import java.util.Map;

@Service
public class CursoService {

    private static final Map<Integer, AreaCurso> CURSOS_POR_AREA = Map.ofEntries(
            Map.entry(301, AreaCurso.CIENCIAS_TECNOLOGICAS),
            Map.entry(700, AreaCurso.CIENCIAS_TECNOLOGICAS),
            Map.entry(701, AreaCurso.CIENCIAS_TECNOLOGICAS),
            Map.entry(703, AreaCurso.CIENCIAS_TECNOLOGICAS),
            Map.entry(100, AreaCurso.CIENCIAS_SOCIAIS_HUMANIDADES),
            Map.entry(101, AreaCurso.CIENCIAS_SOCIAIS_HUMANIDADES),
            Map.entry(102, AreaCurso.CIENCIAS_SOCIAIS_HUMANIDADES),
            Map.entry(200, AreaCurso.CIENCIAS_SOCIAIS_HUMANIDADES),
            Map.entry(401, AreaCurso.CIENCIAS_SOCIAIS_HUMANIDADES),
            Map.entry(501, AreaCurso.CIENCIAS_SOCIAIS_HUMANIDADES),
            Map.entry(502, AreaCurso.CIENCIAS_SOCIAIS_HUMANIDADES),
            Map.entry(600, AreaCurso.CIENCIAS_SOCIAIS_HUMANIDADES)
    );


    @Autowired
    private CursoRepository cursoRepository;

    public List<Curso> findByFaculdadeId(Long faculdadeId){
        return cursoRepository.findCursoByFaculdadeId(faculdadeId);
    }

    public AreaCurso getAreaCurso(int cursoId) {
        return CURSOS_POR_AREA.getOrDefault(cursoId, null);
    }
}
