package siga.artsoft.api.regimecurso;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import siga.artsoft.api.utils.IdEntity;

@Table(name="regimecurso")
@Entity(name="RegimeCurso")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegimeCurso extends IdEntity {

    private String nome;
}
