package siga.artsoft.api.nivelhabilitacao;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import siga.artsoft.api.utils.IdEntity;

@Table(name="nivelhabilitacao")
@Entity(name="NivelHabilitacao")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NivelHabilitacao extends IdEntity {

    private String nome;
}
