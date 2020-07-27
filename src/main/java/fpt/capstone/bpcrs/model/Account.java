package fpt.capstone.bpcrs.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import fpt.capstone.bpcrs.component.Auditing;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "account")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(
        value = {"createdDate", "lastModifiedDate"},
        allowGetters = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "ordinal")
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
    @ApiModelProperty(hidden = true)
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(columnDefinition = "text")
    private String phone;

    @Column(columnDefinition = "text")
    private String identification;

    @Column(columnDefinition = "text")
    private String imageUrlLicense1;

    @Column(columnDefinition = "text")
    private String imageUrlLicense2;

    @Column(columnDefinition = "text")
    private String imageUrlLicense3;

    @Column(columnDefinition = "text")
    private String imageUrlLicense4;

    @Column(columnDefinition = "TINYINT(1) default 0")
    private boolean licenseCheck;
}
