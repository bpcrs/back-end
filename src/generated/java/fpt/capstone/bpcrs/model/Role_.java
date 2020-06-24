package fpt.capstone.bpcrs.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Role.class)
public abstract class Role_ extends fpt.capstone.bpcrs.component.Auditing_ {

	public static volatile SingularAttribute<Role, String> name;
	public static volatile SingularAttribute<Role, Boolean> active;
	public static volatile ListAttribute<Role, Account> accounts;

	public static final String NAME = "name";
	public static final String ACTIVE = "active";
	public static final String ACCOUNTS = "accounts";

}

