package siga.artsoft.api.candidatura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class CandidaturaService {

    @Autowired
    private CandidaturaRepository candidaturaRepository;

    @Autowired
    private CandidaturaMapper candidaturaMapper;

    public Candidatura criar(CandidaturaDTO dto) {
        Candidatura candidatura = candidaturaMapper.toEntity(dto);
        Integer anoAtual = LocalDate.now().getYear()+1;

        if (candidaturaRepository.existsByNumeroDocAndAnoCandidatura(candidatura.getNumeroDoc(), anoAtual)) {
            throw new IllegalArgumentException("Já existe uma candidatura para este documento neste ano.");
        }

        candidatura.setCreatedAt(LocalDateTime.now());
        candidatura.setAnoCandidatura(LocalDate.now().getYear()+1);
        String numeroCandidato = gerarNumeroCandidato(anoAtual);
        candidatura.setNumeroCandidato(numeroCandidato);

        return candidaturaRepository.save(candidatura);
    }

    private String gerarNumeroCandidato(Integer ano) {
        long count = candidaturaRepository.count() + 1;
        return String.format("%d%04d", ano, count);
    }
}

