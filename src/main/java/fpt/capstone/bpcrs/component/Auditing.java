package fpt.capstone.bpcrs.component;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Auditing {

  @CreatedDate
  @ApiModelProperty(hidden = true)
  @Column(name = "created_date")
  protected LocalDateTime createdDate;

  @LastModifiedDate
  @ApiModelProperty(hidden = true)
  @Column(name = "last_modified_date")
  protected LocalDateTime lastModifiedDate;

  @Id
  @Column
  @ApiModelProperty(hidden = true)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
}
