package fpt.capstone.bpcrs.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fpt.capstone.bpcrs.component.Auditing;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@Table(name = "account")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(
    value = {"createdDate", "lastModifiedDate"},
    allowGetters = true)
public class Account extends Auditing {

  @Column(unique = true, nullable = false, updatable = false)
  private String email;

  @Column(columnDefinition = "text")
  private String imageUrl;

  @Column(columnDefinition = "TINYINT(1) default 1")
  private boolean active;

  @Column(nullable = false)
  private String fullName;

  @ManyToOne(optional = false)
  private Role role;
}
