package fpt.capstone.bpcrs.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Brand.class)
public abstract class Brand_ extends fpt.capstone.bpcrs.component.Auditing_ {

	public static volatile ListAttribute<Brand, Car> cars;
	public static volatile SingularAttribute<Brand, String> logoLink;
	public static volatile SingularAttribute<Brand, String> name;

	public static final String CARS = "cars";
	public static final String LOGO_LINK = "logoLink";
	public static final String NAME = "name";

}

