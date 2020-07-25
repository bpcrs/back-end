package fpt.capstone.bpcrs.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Criteria.class)
public abstract class Criteria_ extends fpt.capstone.bpcrs.component.Auditing_ {

	public static volatile SingularAttribute<Criteria, String> unit;
	public static volatile SingularAttribute<Criteria, String> name;
	public static volatile SingularAttribute<Criteria, Boolean> isRenter;
	public static volatile CollectionAttribute<Criteria, Agreement> agreements;

	public static final String UNIT = "unit";
	public static final String NAME = "name";
	public static final String IS_RENTER = "isRenter";
	public static final String AGREEMENTS = "agreements";

}

