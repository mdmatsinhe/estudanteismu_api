package siga.artsoft.api.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import siga.artsoft.api.user.User;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IdEntity implements Serializable, Entity {

    private static final long serialVersionUID = -6468535868748071777L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated", nullable = false)
    private Date updated;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "created_by")
    @NotNull
    private User createdBy;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "updated_by")
    @NotNull
    private User updatedBy;

    @PrePersist
    public void onCreate() {
        updated = created = new Date();
    }

    @PreUpdate
    public void onUpdate() {
        updated = new Date();
    }

    public Date getCreated() {
        return created;
    }

    public Date getUpdated() {
        return updated;
    }

    public Long getId() {
        return id;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return String.format("%s(id=%d)", this.getClass().getSimpleName(),
                this.getId());
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }

    @Override
    public String name() {
        return null;
    }
}

