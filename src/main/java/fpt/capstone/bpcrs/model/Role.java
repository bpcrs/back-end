package fpt.capstone.bpcrs.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import fpt.capstone.bpcrs.component.Auditing;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "role")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(
        value = {"createdDate", "lastModifiedDate"},
        allowGetters = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
public class Role extends Auditing {

    @Builder
    public Role(LocalDateTime createdDate, LocalDateTime lastModifiedDate, Integer id, String name) {
        super(createdDate, lastModifiedDate, id);
        this.name = name;
    }

    @Column
    private String name;

    @Column(columnDefinition = "TINYINT(1) default 1")
    private boolean active;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private Set<Account> accounts;
}
