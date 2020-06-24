package fpt.capstone.bpcrs.component;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Auditing.class)
public abstract class Auditing_ {

	public static volatile SingularAttribute<Auditing, LocalDateTime> createdDate;
	public static volatile SingularAttribute<Auditing, LocalDateTime> lastModifiedDate;
	public static volatile SingularAttribute<Auditing, Integer> id;

	public static final String CREATED_DATE = "createdDate";
	public static final String LAST_MODIFIED_DATE = "lastModifiedDate";
	public static final String ID = "id";

}

