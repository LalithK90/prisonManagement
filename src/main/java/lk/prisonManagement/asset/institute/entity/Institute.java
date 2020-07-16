package lk.prisonManagement.asset.institute.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.prisonManagement.asset.institute.entity.Enum.PrisonType;
import lk.prisonManagement.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("Institute")
public class Institute extends AuditEntity {

    private String name;

    @Column(unique = true)
    private String land;

    private String address;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private PrisonType prisonType;
}
