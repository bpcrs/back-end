package fpt.capstone.bpcrs.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import fpt.capstone.bpcrs.component.Auditing;
import fpt.capstone.bpcrs.constant.AuthProvider;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

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

  @Column(columnDefinition = "TINYINT(1) default 1")
  private boolean approved;

  @Column(columnDefinition = "TINYINT(1) default 0")
  private boolean allowInvite;

  @Column(nullable = false)
  private String fullName;

  @ManyToOne(optional = false)
  private Role role;

  @Enumerated(EnumType.STRING)
  private AuthProvider provider;

  @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
  private Collection<Car> cars;

}
