package fpt.capstone.bpcrs.component;

import fpt.capstone.bpcrs.util.ObjectMapperUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
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

  public Object buildObject(Object request, boolean isRequest){
    ModelMapper modelMapper = ObjectMapperUtils.getModelMapper();
    if (isRequest) {
      modelMapper.map(request, this);
      return this;
    }
    modelMapper.map(this, request);
    return request;
  }
}
